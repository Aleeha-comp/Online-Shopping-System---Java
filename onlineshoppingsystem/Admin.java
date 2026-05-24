package onlineshoppingsystem;

import java.util.*;
import java.io.Serializable;

public class Admin extends User implements Serializable {

    public Admin(String userId, String name, String email, String password, String department) {
        super(userId, name, email, password);
    }
    
    // Manege shops
    public void manageShops(List<Shop> shops) {
        try {
            if (shops == null || shops.isEmpty()) {
                throw new Exception("No shops to manage.");
            }

            System.out.println("Admin " + getName() + " is managing the following shops:");

            for (Shop shop : shops) {
                System.out.println("- " + shop.getShopName());
            }
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Remove shop
    public void removeShop(Shop shop, List<Shop> shops) {
        try{
            if (shops == null || shops.isEmpty()) {
                throw new Exception("No shops available to remove.");
            }

            if (shops.contains(shop)) {

                shops.remove(shop);
                System.out.println("Shop removed successfully!");
            } 
            
            else {
                throw new Exception("Shop not found.");
            }
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String displayRole() {
        return "Admin";
    }
}