package onlineshoppingsystem;

import java.util.List;

public class Admin extends User {

    private String department;

    public Admin(String userId, String name, String email, String password, String department) {
        super(userId, name, email, password);
        this.department = department;
    }

    // Accessor (Getter)
    public String getDepartment() {
        return department;
    }

    // Mutator (Setter)
    public void setDepartment(String department) {
        if (department == null || department.isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }
        this.department = department;
        System.out.println("Department updated successfully!");
    }
    
    public void manageShops(List<Shop> shops) {
        if (shops == null || shops.isEmpty()) {
            System.out.println("No shops to manage.");
            return;
        }

        System.out.println("Admin " + getName() + " is managing the following shops:");
        for (Shop shop : shops) {
            System.out.println("- " + shop.getShopName());
        }
    }

    public void removeShop(Shop shop, List<Shop> shops) {
        if (shops == null || shops.isEmpty()) {
            System.out.println("No shops available to remove.");
            return;
        }

        if (shops.contains(shop)) {
            shops.remove(shop);
            System.out.println("Shop removed successfully!");
        } else {
            System.out.println("Shop not found.");
        }
    }

    @Override
    public String displayRole() {
        return "Admin";
    }
}