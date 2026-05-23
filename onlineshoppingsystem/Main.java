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
 
    public static void main (String args[]){
        
    }

    // Mutators (Setters)
    // Adding Customer
    public static void addCustomer(Customer c) {

        try {
            if (c == null){
                throw new Exception("Customer cannot be null.");
            }

            customers.add(c); 
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
            System.out.println("Shop added successfully!");
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


    // ─── CURRENT USER ──────────────────────────────────────────────
    public static void setCurrentUser(User u)  { 
        currentUser = u;
    }


    public static User getCurrentUser() { 
        return currentUser; 
    }


    public static Customer getCurrentCustomer() {
        if (currentUser instanceof Customer)
            return (Customer) currentUser;
        return null;
    }

 
    public static Seller getCurrentSeller() {
        if (currentUser instanceof Seller)
            return (Seller) currentUser;
        return null;
    }

 
    public static Admin getCurrentAdmin() {
        if (currentUser instanceof Admin)
            return (Admin) currentUser;
        return null;
    }
 
    // ─── FIND / SEARCH ─────────────────────────────────────────────
    public static Customer findCustomer(String email, String password) {

        for (Customer c : customers) {
            if (c.getEmail().equals(email) && c.getPassword().equals(password))
                return c;
        }
        return null;
    }

    public static Seller findSeller(String email, String password) {
        
        for (Seller s : sellers) {
            if (s.getEmail().equals(email) && s.getPassword().equals(password))
                return s;
        }
        return null;
    }



    public static Admin findAdmin(String email, String password) {
        for (Admin a : admins) {
            if (a.getEmail().equals(email) && a.getPassword().equals(password))
                return a;
        }
        return null;
    }


 
    public static boolean emailExists(String email) {
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
    ShopCategory clothes     = new ShopCategory("Clothes");
    ShopCategory books       = new ShopCategory("Books");

    categories.add(electronics);
    categories.add(clothes);
    categories.add(books);

    // Shops
    Shop techShop  = new Shop(1, "TechZone", electronics);
    Shop styleShop = new Shop(2, "StyleHub", clothes);
    Shop bookShop  = new Shop(3, "BookCorner", books);

    shops.add(techShop);
    shops.add(styleShop);
    shops.add(bookShop);

    // Products
    Product p1 = new Product("Samsung A55", 85000, 10, techShop);
    Product p2 = new Product("Wireless Earbuds", 3500, 25, techShop);
    Product p3 = new Product("USB-C Charger", 1200, 50, techShop);

}
}