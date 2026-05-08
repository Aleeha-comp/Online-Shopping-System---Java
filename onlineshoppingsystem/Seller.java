package onlineshoppingsystem;

public class Seller extends User {
    
    private Shop shop;
    private double rating;

    public Seller(int userId, String name, String email, String password, Shop shop, double rating) {
        super(userId, name, email, password);
        this.shop = shop;
        this.rating = rating;
    }
    
    // Accessors (Getters)
    public Shop getShop() {
        return shop;
    }

    public double getRating() {
        return rating;
    }
    
}
