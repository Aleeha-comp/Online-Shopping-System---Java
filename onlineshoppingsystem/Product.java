package onlineshoppingsystem;


// Product class implements Discountable interface
public class Product implements Discountable {

    private int productId;

    private String name;

    private double price;
    private int stock;

    private Shop shop;                    // Association

    private double discountPct;

    // Automatic ID generator
    private static int idCounter = 1;

    // Constructor
    public Product(String name,double price,int stock,Shop shop) {

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

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Apply discount
    @Override
    public void applyDiscount(double percentage) {
         try {
            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException(
                    "Discount must be between 0% and 100%!"
                );
            }

            this.discountPct = percentage;

            System.out.println("Discount of " + percentage +"% applied to: " + name );

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
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

       try {
            if (qty <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0!");
            }

            return stock >= qty;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Reduce stock
    public void reduceStock(int qty) {

            try {
            if (qty <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0!");
            }

            if (qty > stock) {
                throw new IllegalArgumentException( "Not enough stock available!");
            }

            stock -= qty;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Increase stock
    public void increaseStock(int qty) {

            try {
            if (qty <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0!");
            }

            stock += qty;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
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

        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be empty!");
            }

            this.name = name;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setPrice(double price) {

          try {
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative!");
            }

            this.price = price;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setStock(int stock) {

          try {
            if (stock < 0) {
                throw new IllegalArgumentException("Stock cannot be negative!");
            }

            this.stock = stock;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setShop(Shop shop) {

        try {
            if (shop == null) {
                throw new NullPointerException("Shop cannot be null!");
            }

            this.shop = shop;

        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // toString method
    @Override
    public String toString() {

        return name + " - Rs. "
                    + String.format("%.2f", getDiscountedPrice())
                    + (discountPct > 0
                        ? " (" + (int) discountPct + "% off)"
                        : "");
    }
}