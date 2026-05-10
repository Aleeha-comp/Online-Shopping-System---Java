package onlineshoppingsystem;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int cartId;
    private List<CartItem> items;

    public Cart() {
        this.cartId = 0; 
        this.items = new ArrayList<>();
    }   

    public Cart(int cartId) {
        this.cartId = cartId;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int qty) {
        for (CartItem item : items) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                item.updateQty(item.getQuantity() + qty);
                return;
            }
        }
        items.add(new CartItem(product, qty));
        System.out.println("Added to cart: " + product.getName() + " x" + qty);
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().getProductId() == product.getProductId());
        System.out.println("Removed from cart: " + product.getName());
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) total += item.getSubtotal();
        return total;
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("--- Cart Contents ---");
        for (CartItem item : items) System.out.println("  " + item);
        System.out.println("  Total: Rs." + String.format("%.2f", getTotal()));
    }

    public List<CartItem> getItems() { return items; }
    public int getCartId() { return cartId; }
}