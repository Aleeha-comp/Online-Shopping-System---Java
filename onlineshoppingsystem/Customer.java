package onlineshoppingsystem;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private List<Address> addresses;
    private Cart cart;
    private List<Order> orders;

    public Customer(String userId, String name, String email, String password) {
        super(userId, name, email, password);

        try {
            this.addresses = new ArrayList<>();

            this.cart = new Cart();           // cart tied to customer // should not ye pass userID??????
            
            if (this.cart == null) {
                throw new Exception("Cart could not be created.");
            }

            this.orders = new ArrayList<>();  
        }
        
        catch (Exception e) {

            System.out.println("Error creating customer: " + e.getMessage());
        }
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

        try {
            if (address != null) {
                this.addresses.add(address);
                System.out.println("Address added successfully!.");
            } 
            
            else {
            throw new Exception("Invalid address!");
            }
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Placing order
    public Order placeOrder(Address address, Payment payment) {

        try {
            
            // Cart Validation
            if (cart == null) {
                throw new Exception("Cart does not exist");      // This means that the cart is not initialized
            }

            // Empty cart Validatio (Tells that the cart is empty)
            if (cart.getItems().isEmpty()) {
                throw new Exception("Cart is empty");
            }

            // Address Validation
            if(address == null) {
                throw new Exception("Please provide shipping address.");
            }

            // Payment Validation
            if (payment == null) {
                throw new Exception("Payment method is missing.");
            }
        
            // Generating order id
            int orderId = orders.size() + 1;

            // Create order
            Order order = new Order(orderId, cart, address);

            // Process Payment
            order.processPayment(payment);

            // Saving order to list
            orders.add(order);
            System.out.println("Order placed successfully! Order ID: " + orderId);

            cart.getItems().clear();           // Clear the cart after placing the order
            return order;
        }

        catch (Exception e) {
            System.out.println("Error placing order: " + e.getMessage());
            return null;
        }
    }

    // View Order History
    public void viewOrderHistory() {

        try{
            if (orders.isEmpty()) {
                throw new Exception("No orders yet!");
            }

            System.out.println("----------> YOUR ORDER HISTORY <----------");
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Items: " + order.getItems());
                System.out.println("Shipping Address: " + order.getShippingAddress().getFullAddress());
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
