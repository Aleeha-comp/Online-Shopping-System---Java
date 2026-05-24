package onlineshoppingsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shop implements Serializable {

    private int shopId;
    private String shopName;
    private ShopCategory shopCategory;

    // Lists
    private List<Product> products;
    private List<Double> ratings;

    public Shop(int shopId, String shopName, ShopCategory shopCategory) {

          try {

            // Exception for invalid ID
            if (shopId <= 0) {
                throw new IllegalArgumentException("Shop ID must be positive.");
            }

            // Exception for empty name
            if (shopName == null || shopName.trim().isEmpty()) {
                throw new IllegalArgumentException("Shop name cannot be empty.");
            }

            // Exception for null category
            if (shopCategory == null) {
                throw new NullPointerException("Shop category cannot be null.");
            }

        this.shopId = shopId;
        this.shopName = shopName;
        this.shopCategory = shopCategory;

        // Create empty lists
        products = new ArrayList<>();
        ratings = new ArrayList<>();

        // Add shop to category
        shopCategory.addShop(this);
    }
        catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
        catch (NullPointerException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Add product
    public void addProduct(Product p) {
         try {
            // Null check
            if (p == null) {
                throw new NullPointerException("Product cannot be null.");
            }

            // Duplicate check
            if (products.contains(p)) {
                throw new IllegalArgumentException("Product already exists.");
            }

        products.add(p);

        System.out.println( "Product '" + p.getName() + "' added to shop '" + shopName + "'.");
    }
      catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Remove product
    public void removeProduct(Product p) {
         try {
             // Null check
            if (p == null) {
                throw new NullPointerException("Product cannot be null.");
            }

            // Check if product exists
            if (!products.contains(p)) {
                throw new IllegalArgumentException("Product not found in shop.");
            }

        products.remove(p);

        System.out.println( "Product '" + p.getName() + "' removed from shop '" + shopName + "'.");
    }
        catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Return copy of products list
    public List<Product> getProducts() {

        return new ArrayList<>(products);
    }


    // Add rating
    public void addRating(double rating) {
         try {
            // Rating must be between 0 and 5
            if (rating < 0 || rating > 5) {
                throw new IllegalArgumentException( "Rating must be between 0 and 5.");
            }

        ratings.add(rating);
    }
    catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Calculate average rating
    public double getShopRating() {

         try {
            // If no ratings
            if (ratings.isEmpty()) {
                throw new ArithmeticException( "No ratings available.");
            }

        double sum = 0;

        // Add all ratings
        for (double r : ratings) {
            sum += r;
        }

        // Return average
        return sum / ratings.size();
    }
     catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
            return 0.0;
        }
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