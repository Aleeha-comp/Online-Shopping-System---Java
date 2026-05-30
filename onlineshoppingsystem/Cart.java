package onlineshoppingsystem;

import java.io.*;
import java.util.*;
public class Cart implements Serializable {

    private int cartId;
    private String customerId;
    private List<CartItem> items;


    // constructors
    public Cart() {
        this.cartId = 1;
        this.customerId = "";
        this.items = new ArrayList<>();
    }


    public Cart(int cartId, String customerId) {
        if (cartId <= 0) {
            throw new IllegalArgumentException("Cart ID must be greater than 0.");
        }

        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty.");
        }

        this.cartId = cartId;
        this.customerId = customerId;
        this.items = new ArrayList<>();
    }


    // getters
    public int getCartId() {
        return cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }


    // adding item in the cart
    public void addItem(Product product, int qty) {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }

        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        for (CartItem item : items) {

            if (item.getProduct().getProductId() == product.getProductId()) {

                item.updateQty(item.getQuantity() + qty);

                System.out.println("Updated quantity for: " + product.getName());

                return;
            }
        }

        items.add(new CartItem(product, qty));

        System.out.println("Added to cart: " + product.getName() + " x" + qty);
    }

    // removing item from the cart
    public void removeItem(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }

        boolean removed = items.removeIf(
            item -> item.getProduct().getProductId() == product.getProductId());

        if (!removed) {
            throw new IllegalArgumentException("Product not found in cart.");
        }

        System.out.println("Removed from cart: " + product.getName());
    }


    // calculating total price of the cart
    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }


    
    // displaying cart details
    public void displayCart() {

        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        System.out.println("--- Cart Contents ---");

        System.out.println("Customer ID: " + customerId);

        for (CartItem item : items) {
            System.out.println(item);
        }

        System.out.println("Total: Rs." + String.format("%.2f", getTotal()));
    }
}