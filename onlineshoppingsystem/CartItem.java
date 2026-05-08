
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem() {
        this.product = null;
        this.quantity = 0;
    }   
    
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    public void updateQty(int newQty) {
        this.quantity = newQty;
        System.out.println("Quantity updated to " + newQty + " for: " + product.getName());
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return product.getName() + " x" + quantity
            + " = Rs." + String.format("%.2f", getSubtotal());
    }
}