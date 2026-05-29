package onlineshoppingsystem;

import java.io.Serializable;
import java.util.*;

public class Product implements Serializable, Discountable {

    private int productId;

    private String name;

    private double price;

    private int stock;

    private Shop shop;

    private double discountPct;

    private List<Review> reviews;

    private static int idCounter = 1;

    // Constructor
    public Product(String name, double price, int stock, Shop shop) {

        try {

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be empty!");
            }

            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative!");
            }

            if (stock < 0) {
                throw new IllegalArgumentException("Stock cannot be negative!");
            }

            if (shop == null) {
                throw new NullPointerException("Shop cannot be null!");
            }

            this.productId = idCounter++;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.shop = shop;
            this.discountPct = 0.0;
            this.reviews = new ArrayList<>();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= DISCOUNT =================

    @Override
    public void applyDiscount(double percentage) {

        try {

            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException(
                        "Discount must be between 0 and 100!"
                );
            }

            this.discountPct = percentage;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public double getDiscountedPrice() {

        return price - (price * discountPct / 100.0);
    }

    // ================= STOCK =================

    public boolean isInStock() {

        return stock > 0;
    }

    public boolean isInStock(int qty) {

        try {

            if (qty <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0!");
            }

            return stock >= qty;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void reduceStock(int qty) {

        try {

            if (qty <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0!");
            }

            if (qty > stock) {
                throw new IllegalArgumentException("Not enough stock!");
            }

            stock -= qty;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void increaseStock(int qty) {

        try {

            if (qty <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0!");
            }

            stock += qty;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= REVIEWS =================

    public void addReview(Review review) {

        if (review != null) {

            reviews.add(review);
        }
    }

    public List<Review> getReviews() {

        return reviews;
    }

    public double getAverageRating() {

        if (reviews.isEmpty()) {

            return 0.0;
        }

        int sum = 0;

        for (Review review : reviews) {

            sum += review.getRating();
        }

        return (double) sum / reviews.size();
    }

    // ================= ADD RATING =================

    public void addRating(int rating, String reviewerName) {

        Review review = new Review( reviews.size() + 1,
                rating,
                reviewerName
        );

        reviews.add(review);
    }

    // ================= DETAILS =================

    public String getDetails() {

        return "Product[" + productId + "] " + name
                + " | Price: Rs. " + String.format("%.2f", getDiscountedPrice())
                + " | Stock: " + stock
                + " | Shop: " + shop.getShopName()
                + " | Rating: " + String.format("%.1f", getAverageRating()) + "/5";
    }

    // ================= GETTERS =================

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

    // ================= SETTERS =================

    public void setName(String name) {

        if (name != null && !name.trim().isEmpty()) {

            this.name = name;
        }
    }

    public void setPrice(double price) {

        if (price >= 0) {

            this.price = price;
        }
    }

    public void setStock(int stock) {

        if (stock >= 0) {

            this.stock = stock;
        }
    }

    public void setShop(Shop shop) {

        if (shop != null) {

            this.shop = shop;
        }
    }

    // ================= STRING =================

    @Override
    public String toString() {

        return name + " - Rs. "
                + String.format("%.2f", getDiscountedPrice())
                + (discountPct > 0
                ? " (" + (int) discountPct + "% off)"
                : "")
                + " | Rating: "
                + String.format("%.1f", getAverageRating())
                + "/5";
    }
}