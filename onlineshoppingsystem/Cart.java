package onlineshoppingsystem;

import java.io.*;
import java.util.*;

public class Cart implements Serializable{
    private int cartId;
    private List<CartItem> items;

    public Cart() {
        this.cartId = 1;
        this.items = new ArrayList<>();
    }

    public Cart(int cartId) {
        if (cartId <= 0) {
            throw new IllegalArgumentException(
                "Cart ID must be greater than 0."
            );
        }

        this.cartId = cartId;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int qty) {

        if (product == null) {
            throw new IllegalArgumentException(
                "Product cannot be null."
            );
        }

        if (qty <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be greater than 0."
            );
        }

        for (CartItem item : items) {
            if (item.getProduct().getProductId()
                    == product.getProductId()) {

                item.updateQty(item.getQuantity() + qty);

                System.out.println(
                    "Updated quantity for: "
                    + product.getName()
                );
                return;
            }
        }

        items.add(new CartItem(product, qty));

        System.out.println(
            "Added to cart: "
            + product.getName()
            + " x" + qty
        );
    }

    public void removeItem(Product product) {

        if (product == null) {
            throw new IllegalArgumentException(
                "Product cannot be null."
            );
        }

        boolean removed = items.removeIf(
            item -> item.getProduct().getProductId()
                    == product.getProductId()
        );

        if (!removed) {
            throw new IllegalArgumentException(
                "Product not found in cart."
            );
        }

        System.out.println(
            "Removed from cart: "
            + product.getName()
        );
    }

    public double getTotal() {
        double total = 0;

        for (CartItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }

    public void displayCart() {

        if (items.isEmpty()) {
            throw new IllegalStateException(
                "Cart is empty."
            );
        }

        System.out.println("--- Cart Contents ---");

        for (CartItem item : items) {
            System.out.println("  " + item);
        }

        System.out.println(
            "  Total: Rs."
            + String.format("%.2f", getTotal())
        );
    }

    public List<CartItem> getItems() {
        return items;
    }

    public int getCartId() {
        return cartId;
    }
}