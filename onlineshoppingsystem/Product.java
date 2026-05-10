package onlineshoppingsystem;


// Product class implements Discountable interface
public class Product implements Discountable {

    // Attributes
    private int productId;

    private String name;

    private double price;
    private int stock;

    private Shop shop;                    // Association

    private double discountPct;

    // Automatic ID generator
    private static int idCounter = 1;

    // Constructor
    public Product(String name,
                   double price,
                   int stock,
                   Shop shop) {

        this.productId = idCounter++;

        this.name = name;

        this.price = price;
        this.stock = stock;

        this.shop = shop;
        this.discountPct = 0.0;
    }

    // Apply discount
    @Override
    public void applyDiscount(double percentage) {

        if (percentage < 0 || percentage > 100) {

            throw new IllegalArgumentException(
                "Discount must be between 0% and 100%!"
            );
        }

        this.discountPct = percentage;

        System.out.println(
            "Discount of " + percentage +
            "% applied to: " + name
        );
    }

    // Get discounted price
    @Override
    public double getDiscountedPrice() {

        return price - (price * discountPct / 100.0);
    }

    // Check stock
    public boolean isInStock() {

        return stock > 0;
    }

    // Check stock for quantity
    public boolean isInStock(int qty) {

        return stock >= qty;
    }

    // Reduce stock
    public void reduceStock(int qty) {

        if (qty <= 0) {

            throw new IllegalArgumentException(
                "Quantity must be greater than 0!"
            );
        }

        if (qty > stock) {

            throw new IllegalArgumentException(
                "Not enough stock available!"
            );
        }

        stock -= qty;
    }

    // Increase stock
    public void increaseStock(int qty) {

        if (qty <= 0) {

            throw new IllegalArgumentException(
                "Quantity must be greater than 0!"
            );
        }

        stock += qty;
    }

    // Product details
    public String getDetails() {

    return "Product[" + productId + "] " + name
        + " | Price: Rs." + String.format("%.2f", getDiscountedPrice())
        + " | Stock: " + stock
        + " | Shop: " + shop.getShopName();
}
    // Getters
    public int getProductId() {

        return productId;
    }

    public String getName() {

        return name;
    }


    public double getPrice() {

        return price;
    }

    public int getStock() {

        return stock;
    }

    public Shop getShop() {

        return shop;
    }

    public double getDiscountPct() {

        return discountPct;
    }

    // Setters
    public void setName(String name) {

        this.name = name;
    }

    public void setPrice(double price) {

        if (price < 0) {

            throw new IllegalArgumentException(
                "Price cannot be negative!"
            );
        }

        this.price = price;
    }
    public void setStock(int stock) {

        if (stock < 0) {

            throw new IllegalArgumentException(
                "Stock cannot be negative!"
            );
        }

        this.stock = stock;
    }
    public void setShop(Shop shop) {

        if (shop == null) {

            throw new IllegalArgumentException(
                "Shop cannot be null!"
            );
        }

        this.shop = shop;
    }

    // toString method
    @Override
    public String toString() {

        return name
            + " - Rs. "
            + String.format("%.2f", getDiscountedPrice())
            + (discountPct > 0
                ? " (" + (int) discountPct + "% off)"
                : "");
    }
}