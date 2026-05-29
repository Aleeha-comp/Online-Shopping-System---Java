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
    public void setShop(Shop shop) {
        if (shop == null) {
            throw new IllegalArgumentException("Shop cannot be null.");
        }

        this.shop = shop;
    }

    public void setRating(double rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Invalid rating! Rating must be between 0 and 5.");
        }
            
        else{
            this.rating = rating;
            System.out.println("Rating updated successfully!");
        }     
    }

    // Add Product
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot add null product.");
        }

        if (shop == null) {
            throw new IllegalArgumentException("Shop does not exist.");
        }

        shop.addProduct(product);
        System.out.println(product.getName() + " added to " + shop.getShopName());
    }

    // Removing Product
    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot remove null product.");
        }

        if (shop == null) {
            throw new IllegalArgumentException("Shop does not exist.");
        }

        // Check if product exists first
        if (!shop.getProducts().contains(product)) {
            throw new IllegalArgumentException("Product not found in shop.");
        }
        
        shop.removeProduct(product);
        System.out.println(product.getName() + " removed from " + shop.getShopName());
    }

    // calculation total sales of the seller
    public void addSale(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Sale amount cannot be negative.");
        }

        totalSales += amount;
    }

    // Add customer to seller's list
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        if (!customers.contains(customer)) {
            customers.add(customer);
        }
    }
    
    @Override
    public String displayRole() {
        return "Seller";
    }

}
