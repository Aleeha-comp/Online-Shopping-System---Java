package onlineshoppingsystem;

import java.util.*;
import java.io.Serializable;

public class Admin extends User implements Serializable {

    private String department;

    public Admin(String userId, String name, String email, String password, String department) {
        super(userId, name, email, password);

        try {
            if (department == null || department.isEmpty()){
                throw new Exception ("Department cannot be empty.");
            }

            this.department = department;
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            this.department = "Unknown";
        }
    }

    // Accessor (Getter)
    public String getDepartment() {
        return department;
    }

    // Mutator (Setter)
    public void setDepartment(String department) {
        try {
            if (department == null || department.isEmpty()) {
                throw new Exception("Department cannot be empty.");
            }

            this.department = department;
            System.out.println("Department updated successfully!");
        }

        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
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