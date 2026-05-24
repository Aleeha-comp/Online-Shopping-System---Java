package onlineshoppingsystem;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ShopCategory implements Serializable {

    private int    categoryId;
    private String name;          // Electronics, Clothes, Makeup, Sports, Books
    private List<Shop> shops;     // ASSOCIATION: category knows which shops belong to it

    private static int idCounter = 1;

    public ShopCategory(String name) {

          try {
            // Check empty or null name
            if (name == null || name.trim().isEmpty()) {

                throw new IllegalArgumentException("Category name cannot be empty.");
        }
            this.categoryId = idCounter++;
            this.name       = name;
            this.shops      = new ArrayList<>();
    }catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Add shop
    public void addShop(Shop shop) {

        try {
            // Null check
            if (shop == null) {
                throw new NullPointerException( "Shop cannot be null.");
            }
            // Duplicate check
            if (shops.contains(shop)) {
                throw new IllegalArgumentException("Shop already exists in this category.");
            }

            shops.add(shop);

            System.out.println(
                "Shop '" + shop.getShopName() +
                "' added to category '" + name + "'."
            );
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Remove shop
     public void removeShop(Shop shop) {

        try {
            // Null check
            if (shop == null) {
                throw new NullPointerException("Shop cannot be null.");
            }

            // Check if exists
            if (!shops.contains(shop)) {

                throw new IllegalArgumentException("Shop does not exist in this category.");
            }

            shops.remove(shop);

            System.out.println("Shop '" + shop.getShopName() +"' removed from category '" + name + "'." );
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
         catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public int getCategoryId() { 
        return categoryId; 
    }
    public String     getName(){ 
        return name; 
    }
    public List<Shop> getShops(){ 
        return new ArrayList<>(shops); 
    }

    public void setName(String name) {

        try {
            // Validate new name
            if (name == null || name.trim().isEmpty()) {

                throw new IllegalArgumentException( "Category name cannot be empty.");
            }

            this.name = name;

            System.out.println("Category name updated successfully.");
        }catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public String toString() { 
        return name; 
    }
}