package onlineshoppingsystem;

import java.util.*;

public class Main {

    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Seller> sellers = new ArrayList<>();
    private static ArrayList<Admin> admins = new ArrayList<>();
    private static ArrayList<Shop> shops = new ArrayList<>();
    private static ArrayList<ShopCategory> categories = new ArrayList<>();

    // Currently logged-in user
    private static User currentUser = null;
 
    public static void main(String args[]) {

    // LOAD SAVED DATA
    loadAllData();
    Product.setIdCounter(getMaxProductId() + 1);

    // ONLY ADD SAMPLE DATA FIRST TIME
    if (shops.isEmpty()) {
        initializeData();
        saveAllData();
    }

    admins.add(new Admin ("A1", "System Admin", "admin@gmail.com", "1234"));

    new LoginFrame().setVisible(true);
}

    // Mutators (Setters)
    // Adding Customer
    public static void addCustomer(Customer c) {

    try {

        if (c == null) {
            throw new Exception("Customer cannot be null.");
        }

        customers.add(c);

        // SAVE IMMEDIATELY
        saveAllData();

        System.out.println("Customer added successfully!");
    }

    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    // Adding Seller
    public static void addSeller(Seller s) {

    try {
        if (s == null) {
            throw new Exception("Seller cannot be null.");
        }

        sellers.add(s);

        // SAVE IMMEDIATELY
        saveAllData();

        System.out.println("Seller added successfully!");
    }

    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    // Adding Admin
    public static void addAdmin(Admin a) { 

        try {
            if (a == null) {
                throw new Exception("Admin cannot be null.");
            }

            admins.add(a); 
            System.out.println("Admin added successfully!");
        } 

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Adding Shop
    public static void addShop(Shop s) {

    try {
        if (s == null) {
            throw new Exception("Shop cannot be null.");
        }

        shops.add(s);

        // SAVE IMMEDIATELY
        saveAllData();

        System.out.println(
                "Shop added successfully!"
        );
    }

    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

    // Adding ShopCategory
    public static void addCategory(ShopCategory sc) { 

        try {
            if (sc == null) {
                throw new Exception("Category cannot be null.");
            }
        
            categories.add(sc); 
             System.out.println("Category added successfully!");
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Accessors (Getters)   Getting the data stored in the lists
    // Getting Customers
    public static List<Customer> getCustomers() { 

         try {
            if (customers == null) {
                throw new Exception("Customer list is empty.");
            }
        } 
        
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return customers; 
    }

    // Getting Sellers
    public static List<Seller> getSellers() { 

        try {
            if (sellers == null) {
                throw new Exception("Seller list is empty.");
            }
        } 
        
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return sellers; 
    }

    // Getting Admins
    public static List<Admin> getAdmins() { 

        try {
            if (admins == null) {
                throw new Exception("Admin list is empty.");
            }
        } 
        
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return admins; 
    }

    // Getting Shops
    public static List<Shop> getShops() { 

        try {
            if (shops == null) {
                throw new Exception("Shop list is empty.");
            }
        } 
        
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return shops; 
    }

    // Getting ShopCategories
    public static List<ShopCategory> getCategories() { 

        try {
            if (categories == null) {
                throw new Exception("Category list is empty.");
            }
        } 
        
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return categories; 
    }

// Current user ---------------------------------------------------
    
    public static void setCurrentUser(User u) {

        if (u == null) {
        throw new IllegalArgumentException("User cannot be null.");
        }

        currentUser = u;
    }


    public static User getCurrentUser() {

        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }
        return currentUser;
    }

    public static Customer getCurrentCustomer() {

        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

        if (!(currentUser instanceof Customer)) {
            throw new IllegalStateException("Current user is not a Customer.");
        }

        return (Customer) currentUser;
    }

    public static Seller getCurrentSeller() {
        
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }

        if (!(currentUser instanceof Seller)) {
            throw new IllegalStateException("Current user is not a Seller.");
        }

        return (Seller) currentUser;
    }


    public static Admin getCurrentAdmin() {
    
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }
        
        if (!(currentUser instanceof Admin)) {
            throw new IllegalStateException("Current user is not an Admin.");
        }

        return (Admin) currentUser;
    }



// ─── FIND / SEARCH ─────────────────────────────────────────────

    public static Customer findCustomer(String email, String password) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
    
        for (Customer c : customers) {
            if (c.getEmail().equals(email) && c.getPassword().equals(password))
            return c;
        }

        return null;
    }


    public static Seller findSeller(String email, String password) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        for (Seller s : sellers) {
            if (s.getEmail().equals(email) && s.getPassword().equals(password))
                return s;
        }

        return null;
    }


public static Admin findAdmin(String email, String password) {

    if (email == null || email.trim().isEmpty()) {
        throw new IllegalArgumentException("Email cannot be null or empty.");
    }

    if (password == null || password.trim().isEmpty()) {
        throw new IllegalArgumentException("Password cannot be null or empty.");
    }

    for (Admin a : admins) {
        if (a.getEmail().equals(email) && a.getPassword().equals(password))
            return a;
    }
    
    return null;
}



public static boolean emailExists(String email) {

    if (email == null || email.trim().isEmpty()) {
        throw new IllegalArgumentException("Email cannot be null or empty.");
    }

    for (Customer c : customers) {
        if (c.getEmail().equals(email))
            return true;
    }

    for (Seller s : sellers) {
        if (s.getEmail().equals(email))
            return true;
    }

    for (Admin a : admins) {
        if (a.getEmail().equals(email))
            return true;
    }

    return false;
}


    
    public static void initializeData() {

    // Only add data if shops list is empty
    if (!shops.isEmpty()) {
        return;
    }

    // Categories
    ShopCategory electronics = new ShopCategory("Electronics");
    ShopCategory clothes = new ShopCategory("Clothes");
    ShopCategory books = new ShopCategory("Books");
    ShopCategory accessories = new ShopCategory("Accessories");

    categories.add(electronics);
    categories.add(clothes);
    categories.add(books);
    categories.add(accessories);

    // Shops
    Shop techShop  = new Shop(1, "TechZone", electronics);
    Shop styleShop = new Shop(2, "StyleHub", clothes);
    Shop bookShop  = new Shop(3, "BookCorner", books);
    Shop accessoryShop = new Shop(4, "AccessoryHub", accessories);

    shops.add(techShop);
    shops.add(styleShop);
    shops.add(bookShop);
    shops.add(accessoryShop);

    // Products
    Product p1 = new Product("Samsung A55", 85000, 10, techShop);
    Product p2 = new Product("Wireless Earbuds", 3500, 25, techShop);
    Product p3 = new Product("USB-C Charger", 1200, 50, techShop);
    Product p4 = new Product("Casual T-Shirt", 900, 30, styleShop);
    Product p5 = new Product("Denim Jeans", 2500, 15, styleShop);
    Product p6 = new Product("Clean Code Book", 1800, 20, bookShop);
    Product p7 = new Product("Hunger Games", 700, 20, bookShop);
    Product p8 = new Product("Java Programming Book", 2200, 15, bookShop);
    Product p9 = new Product("Atomic Habits", 1100, 10, bookShop);
    Product p10 = new Product("Noise Cancelling Headphones", 15000, 10, techShop);
    Product p11 = new Product("Smartwatch", 12000, 20, techShop);
    Product p12 = new Product("Graphic T-Shirt", 1200, 25, styleShop);
    Product p13 = new Product("Necklace", 2500, 30, accessoryShop);
    Product p14 = new Product("Sunglasses", 3000, 20, accessoryShop);
    Product p15 = new Product("Bracelet", 2000, 15, accessoryShop);
    Product p16 = new Product("Earrings", 1500, 25, accessoryShop);
    Product p17 = new Product("Handbag", 5000, 10, accessoryShop);
    Product p18 = new Product("Baggy jeans ", 4500, 25, styleShop);

    techShop.addProduct(p1);
    techShop.addProduct(p2);
    techShop.addProduct(p3);
    techShop.addProduct(p10);
    techShop.addProduct(p11);

    styleShop.addProduct(p4);
    styleShop.addProduct(p5);
    styleShop.addProduct(p12);
    styleShop.addProduct(p18);



    bookShop.addProduct(p6);
    bookShop.addProduct(p7);
    bookShop.addProduct(p8);
    bookShop.addProduct(p9);

    accessoryShop.addProduct(p13);
    accessoryShop.addProduct(p14);
    accessoryShop.addProduct(p15);
    accessoryShop.addProduct(p16);
    accessoryShop.addProduct(p17);

// Sample users
    customers.add(new Customer("C001", "Ali Khan", "ali@gmail.com", "1234"));
    customers.add(new Customer("C002", "Sara Ahmed", "sara@gmail.com", "1234"));
    admins.add(new Admin("A001", "Admin User", "admin@shop.com", "admin123"));
    }
    
//Product ID generator
    public static int getMaxProductId() {
    int max = 0;
    for (Shop shop : shops) {
        for (Product product : shop.getProducts()) {
            if (product.getProductId() > max) {
                max = product.getProductId();
            }
        }
    }
    return max;
}

// ─── CLEAR ALL (used on logout) ────────────────────────────────
    public static void logout() {
        try {
            currentUser = null;
        } catch (Exception e) {
            System.out.println("Logout Error: " + e.getMessage());
        }
    }
    
// Saving data to file
public static void saveAllData() {

    // Exception fo customer
    try {
        if (customers == null) {
            throw new Exception("Customer list is empty. Add data to customer list!");
        }

        FileHandling.saveData("customers.dat", customers);
    }

    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }

    // Exception for seller
    try {
        if (sellers == null) {
            throw new Exception("Seller list is empty. Add data to seller list!");
        }

        FileHandling.saveData("sellers.dat", sellers);
    }

    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }

    // Exception for admin
    try {
        if (admins == null) {
            throw new Exception("Admin list is empty. Add data to admin list!");
        }

        FileHandling.saveData("admins.dat", admins);
    }

    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }

    // Exception for Shops
    try {
        if (shops == null) {
            throw new Exception("Shop list is empty. Add data to shop list!");
        }

        FileHandling.saveData("shops.dat", shops);
    }

    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }

    // Exception for Categories
    try {
        if (categories == null) {
            throw new Exception("Category list is empty. Add data to categories list!");
        }

        FileHandling.saveData("categories.dat", categories);
    }

    catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

// Loading Data from file
public static void loadAllData() {

    customers = FileHandling.loadData("customers.dat");

    sellers = FileHandling.loadData("sellers.dat");

    admins = FileHandling.loadData("admins.dat");

    shops = FileHandling.loadData("shops.dat");

    categories = FileHandling.loadData("categories.dat");

    System.out.println("All data loaded successfully!");
}

}
