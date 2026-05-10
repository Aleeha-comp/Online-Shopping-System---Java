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
            items.add(new OrderItem(cartItem.getProduct(), cartItem.getQuantity()));
            cartItem.getProduct().reduceStock(cartItem.getQuantity());
        }
    }

    public boolean processPayment(Payment payment) {
        this.payment = payment;
        boolean success = payment.processPayment();
        if (success) {
            this.status = "Confirmed";
            System.out.println("Order #" + orderId + " confirmed!");
        } else {
            this.status = "Payment Failed";
        }
        return success;
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) total += item.getSubtotal();
        return total;
    }

    public void displayOrder() {
        System.out.println("=== Order #" + orderId + " ===");
        System.out.println("Date: " + orderDate + " | Status: " + status);
        System.out.println("Deliver to: " + deliveryAddress.getFullAddress());
        System.out.println("Items:");
        for (OrderItem item : items) System.out.println("  " + item);
        System.out.println("Total: Rs." + String.format("%.2f", getTotal()));
    }

    public int getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }
}
