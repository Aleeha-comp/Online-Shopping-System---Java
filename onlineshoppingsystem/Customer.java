package onlineshoppingsystem;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private List<Address> addresses;
    private Cart cart;
    private List<Order> orders;

    public Customer(String userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.addresses = new ArrayList<>();
        this.cart = new Cart(userId);           // cart tied to customer
        this.orders = new ArrayList<>();    
    }

    // Accessors (Getters)
    public List<Address> getAddresses() {
        return addresses;
    }

    public Cart getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    // Mutators (Setters)
    public void addAddress(Address address) {
        if (address != null) {
            this.addresses.add(address);
            System.out.println("Address added successfully!.");
        } else {
            System.out.println("Invalid address!");
        }
    }

    public Order placeOrder(Address address, Payment payment) {

        try {
            
            if (cart == null) {
                System.out.println("Cart does not exist");      // This means that the cart is not initialized
                return null;
            }

            if (cart.getItems().isEmpty()) {
                System.out.println("Cart is empty");
                return null;
            }

            if(address == null) {
                System.out.println("Please provide shipping address.");
                return null;
            }

            if (payment == null) {
                System.out.println("Payment method is missing.");
                return null;
            }
        
            int orderId = orders.size() + 1; // Simple order ID generation

            Order order = new Order(orderId, this.getUserId(), cart.getItems(), address, payment);
            order.processPayment(payment);
            orders.add(order);
            System.out.println("Order placed successfully! Order ID: " + orderId);
            cart.clear();           // Clear the cart after placing the order
            return order;
        }

        catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
            return null;
        }
    }

    public void viewOrderHistory() {
        try{
            if (orders.isEmpty()) {
                System.out.println("No orders yet!");
                return;
            }

            System.out.println("----------> YOUR ORDER HISTORY <----------");
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Items: " + order.getItems());
                System.out.println("Shipping Address: " + order.getShippingAddress());
                System.out.println("Payment Status: " + order.getPaymentStatus());
            }
        } 
        
        catch (Exception e) {
            System.out.println("Error viewing order history: " + e.getMessage());
        }
    }

    @Override
    public String displayRole() {
        return "Customer";
    }
    
}
