package onlineshoppingsystem;

import java.util.*;
import java.io.*;

public class Order implements Serializable{
    private int orderId;
    private String status;
    private List<CartItem> items;
    private Address deliveryAddress;
    private Payment payment;

    //constructors
    public Order() {
        this.orderId = 1;
        this.status = "Pending";
        this.deliveryAddress = null;
        this.items = new ArrayList<>();
    }

    public Order(int orderId, Cart cart, Address deliveryAddress) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than 0.");
        }

        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }

        if (deliveryAddress == null) {
            throw new IllegalArgumentException("Delivery address cannot be null.");
        }

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create an order from an empty cart."
            );
        }

        this.orderId = orderId;
        this.status = "Pending";
        this.deliveryAddress = deliveryAddress;
        this.items = new ArrayList<>();

        
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem == null) {
                throw new IllegalStateException("Cart contains an invalid item.");
            }

            items.add(cartItem);

            cartItem.getProduct().reduceStock(cartItem.getQuantity());
        }
    }

//getters
    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Address getShippingAddress() {
        return deliveryAddress;
    }

    public String getPaymentStatus() {
        return status;
    }

    //payment process
    public boolean processPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment object cannot be null.");
        }
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot process payment for an empty order.");
        }

        this.payment = payment;
        payment.processPayment();
        this.status = "Confirmed";
        System.out.println("Order #" + orderId + " confirmed!");
        return true;
    }

    //get total payment
    public double getTotal() {
        double total = 0;

        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    //display order details
    public void displayOrder() {

        if (items.isEmpty()) {
            throw new IllegalStateException("Order contains no items.");
        }

        if (deliveryAddress == null) {
            throw new IllegalStateException("Delivery address is missing.");
        }

        System.out.println("=== Order #" + orderId + " ===");
        System.out.println( " | Status: " + status);
        System.out.println("Deliver to: " +  deliveryAddress.getFullAddress() );
        System.out.println("Items:");

        for (CartItem item : items) {
            System.out.println("  " + item);
        }

        System.out.println("Total: Rs." + String.format("%.2f", getTotal()));
    }

    
}