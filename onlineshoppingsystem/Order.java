package onlineshoppingsystem;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private String status;
    private LocalDate orderDate;
    private List<CartItem> items;
    private Address deliveryAddress;
    private Payment payment;

    public Order() {
        this.orderId = 0;
        this.orderDate = LocalDate.now();
        this.status = "Pending";
        this.deliveryAddress = null;
        this.items = new ArrayList<>();
    }

    public Order(int orderId, Cart cart, Address deliveryAddress) {
        this.orderId = orderId;
        this.orderDate = LocalDate.now();
        this.status = "Pending";
        this.deliveryAddress = deliveryAddress;
        this.items = new ArrayList<>();


        for (CartItem cartItem : cart.getItems()) {
            // store the cart item directly in the order
            items.add(cartItem);
            cartItem.getProduct().reduceStock(cartItem.getQuantity());
        }
    }

    public boolean processPayment(Payment payment) {
        this.payment = payment;
        payment.processPayment();
        this.status = "Confirmed";
        System.out.println("Order #" + orderId + " confirmed!");
        return true;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) total += item.getSubtotal();
        return total;
    }

    public void displayOrder() {
        System.out.println("=== Order #" + orderId + " ===");
        System.out.println("Date: " + orderDate + " | Status: " + status);
        System.out.println("Deliver to: " + deliveryAddress.getFullAddress());
        System.out.println("Items:");
        for (CartItem item : items) System.out.println("  " + item);
        System.out.println("Total: Rs." + String.format("%.2f", getTotal()));
    }

    public int getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public List<CartItem> getItems() { return items; }
    public Address getShippingAddress() { return deliveryAddress; }
    public String getPaymentStatus() { return status; }
}

// Minimal Address implementation to satisfy references from Order.
class Address {
    private String street;
    private String city;
    private String state;
    private String zip;

    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (street != null && !street.isEmpty()) sb.append(street).append(", ");
        if (city != null && !city.isEmpty()) sb.append(city).append(", ");
        if (state != null && !state.isEmpty()) sb.append(state).append(" ");
        if (zip != null && !zip.isEmpty()) sb.append(zip);
        return sb.toString().trim();
    }
}
