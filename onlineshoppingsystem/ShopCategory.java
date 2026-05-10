package onlineshoppingsystem;

import java.util.ArrayList;
import java.util.List;

public class ShopCategory {

    private int    categoryId;
    private String name;          // Electronics, Clothes, Makeup, Sports, Books
    private List<Shop> shops;     // ASSOCIATION: category knows which shops belong to it

    private static int idCounter = 1;

    public ShopCategory(String name) {
        this.categoryId = idCounter++;
        this.name       = name;
        this.shops      = new ArrayList<>();
    }

    public void addShop(Shop shop)    { 
        shops.add(shop); 
    }
    public void removeShop(Shop shop) { 
        shops.remove(shop); 
    }

    
    public int        getCategoryId() { 
        return categoryId; 
    }
    public String     getName(){ 
        return name; 
    }
    public List<Shop> getShops(){ 
        return new ArrayList<>(shops); 
    }

    public void setName(String name){ 
        this.name = name; 
    }
    
    public String toString() { return name; }
}