package onlineshoppingsystem;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private int shopId;
    private String shopName;
    private ShopCategory shopCategory;

    // Lists
    private List<Product> products;
    private List<Double> ratings;

    public Shop(int shopId, String shopName, ShopCategory shopCategory) {

        this.shopId = shopId;
        this.shopName = shopName;
        this.shopCategory = shopCategory;

        // Create empty lists
        products = new ArrayList<>();
        ratings = new ArrayList<>();

        // Add shop to category
        shopCategory.addShop(this);
    }

    // Add product
    public void addProduct(Product p) {

        products.add(p);

        System.out.println(
            "Product '" + p.getName() +
            "' added to shop '" + shopName + "'."
        );
    }

    // Remove product
    public void removeProduct(Product p) {

        products.remove(p);

        System.out.println(
            "Product '" + p.getName() +
            "' removed from shop '" + shopName + "'."
        );
    }

    // Return copy of products list
    public List<Product> getProducts() {

        return new ArrayList<>(products);
    }

    // Add rating
    public void addRating(double rating) {

        ratings.add(rating);
    }

    // Calculate average rating
    public double getShopRating() {

        // If no ratings
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double sum = 0;

        // Add all ratings
        for (double r : ratings) {
            sum += r;
        }

        // Return average
        return sum / ratings.size();
    }

    // Getters
    public int getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    // Display shop info
    public String toString() {

        return "Shop ID: " + shopId +
               "\nShop Name: " + shopName +
               "\nCategory: " + shopCategory.getName() +
               "\nRating: " + String.format("%.1f", getShopRating());
    }
}