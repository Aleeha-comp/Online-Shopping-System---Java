package onlineshoppingsystem;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem() {
        this.product = null;
        this.quantity = 0;
    }

    public CartItem(Product product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException(
                "Product cannot be null."
            );
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be greater than 0."
            );
        }

        this.product = product;
        this.quantity = quantity;
    }

    public double getSubtotal() {

        if (product == null) {
            throw new IllegalStateException(
                "Cannot calculate subtotal. Product is null."
            );
        }

        return product.getPrice() * quantity;
    }

    public void updateQty(int newQty) {

        if (newQty <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be greater than 0."
            );
        }

        this.quantity = newQty;

        System.out.println(
            "Quantity updated to "
            + newQty
            + " for: "
            + product.getName()
        );
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {

        if (product == null) {
            throw new IllegalStateException(
                "Product information is missing."
            );
        }

        return product.getName() + " x" + quantity
                + " = Rs."
                + String.format("%.2f", getSubtotal());
    }
}