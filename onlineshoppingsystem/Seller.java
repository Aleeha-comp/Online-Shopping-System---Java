package onlineshoppingsystem;

public class Seller extends User {
    
    private Shop shop;
    private double rating;

    public Seller(int userId, String name, String email, String password, Shop shop, double rating) {
        super(userId, name, email, password);

        // Validation checks
        if (shop == null) {
            throw new IllegalArgumentException("Shop cannot be null.");
        }

        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }

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

    // Mutators (Setters)
    public void setRating(double rating) {
        if (rating < 0 || rating > 5) {
            System.out.println("Invalid rating! Rating must be between 0 and 5.");
        }
        else {
            this.rating = rating;
            System.out.println("Rating updated successfully!");
        }
            
    }
    public void addProduct(Product product) {
        if (product == null) {
            System.out.println("Cannot add null product.");
            return;
        }

        if (shop == null) {
            System.out.println("Shop does not exist.");
            return;
        }

        shop.addProduct(product);
        System.out.println(product.getName() + " added to " + shop.getShopName());
    }

    public void removeProduct(Product product) {
        if (product == null) {
            System.out.println("Cannot remove null product.");
            return;
        }

        if (shop == null) {
            System.out.println("Shop does not exist.");
            return;
        }

        // Check if product exists first
        if (!shop.getProducts().contains(product)) {
            System.out.println("Product not found in shop.");
            return;
        }
        
        shop.removeProduct(product);
        System.out.println(product.getName() + " removed from " + shop.getShopName());
    }

    @Override
    public String displayRole() {
        return "Seller";
    }

}
