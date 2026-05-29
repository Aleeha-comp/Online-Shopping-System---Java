package onlineshoppingsystem;

import java.io.Serializable;
import java.util.ArrayList;

public class Seller extends User implements Serializable {
    
    private Shop shop;
    private double rating;
    private double totalSales;

    private ArrayList<Customer> customers;

    public Seller(String userId, String name, String email, String password, Shop shop, double rating) {
        super(userId, name, email, password);

        try{
            // Validation checks
            if (shop == null) {
                throw new IllegalArgumentException("Shop cannot be null.");
            }

            //Validation for rating
            if (rating < 0 || rating > 5) {
                throw new IllegalArgumentException("Rating must be between 0 and 5.");
            }

            this.shop = shop;
            this.rating = rating;
            this.totalSales = 0;
            this.customers = new ArrayList<>();
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

            // Default values
            this.shop = null;
            this.rating = 0;
        }
    }
    
    // Getters
    public Shop getShop() {
        return shop;
    }

    public double getRating() {
        return rating;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    // Setters
    public void setRating(double rating) {

        try {
            if (rating < 0 || rating > 5) {
                throw new Exception("Invalid rating! Rating must be between 0 and 5.");
            }
            
            else{
                this.rating = rating;
                System.out.println("Rating updated successfully!");
            }
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }      
    }

    // Add Product
    public void addProduct(Product product) {

        try {
            if (product == null) {
                throw new Exception("Cannot add null product.");
            }

            if (shop == null) {
                throw new Exception("Shop does not exist.");
            }

            shop.addProduct(product);
            System.out.println(product.getName() + " added to " + shop.getShopName());
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Removing Product
    public void removeProduct(Product product) {

        try {
            if (product == null) {
                throw new Exception("Cannot remove null product.");
            }

            if (shop == null) {
                throw new Exception("Shop does not exist.");
            }

            // Check if product exists first
            if (!shop.getProducts().contains(product)) {
                throw new Exception("Product not found in shop.");
            }
        
            shop.removeProduct(product);

            System.out.println(product.getName() + " removed from " + shop.getShopName());
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // calculation total sales of the seller
    public void addSale(double amount) {
        totalSales += amount;
    }

    // Add customer to seller's list
    public void addCustomer(Customer customer) {
        if (!customers.contains(customer)) {
            customers.add(customer);
        }
    }
    
    @Override
    public String displayRole() {
        return "Seller";
    }

}
