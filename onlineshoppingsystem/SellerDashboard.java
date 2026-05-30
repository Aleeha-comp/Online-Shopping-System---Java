package onlineshoppingsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// GUI window for the seller to manage their shop and products
public class SellerDashboard extends JFrame {

    private Seller seller;        
    private JTable table;         
    private DefaultTableModel model; 

    public SellerDashboard() {

        // get the current seller from the main app
        seller = Main.getCurrentSeller();

        // ---------------- basic window setup ----------------
        setTitle("Seller Dashboard");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------------- welcome heading at the top ----------------
        JLabel heading = new JLabel("Welcome " + seller.getName());
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setHorizontalAlignment(JLabel.CENTER);
        add(heading, BorderLayout.NORTH);

        // column headers for the product table
        String[] columns = {"ID", "Name", "Price", "Discount", "Stock", "Rating"};

        // set up the table 
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---------------- bottom panel holding all action buttons ----------------
        JPanel panel = new JPanel();

        JButton addButton = new JButton("Add Product");
        JButton removeButton = new JButton("Remove Product");
        JButton discountButton = new JButton("Apply Discount");
        JButton updateButton = new JButton("Update Product");
        JButton logoutButton = new JButton("Logout");

        //  button sizes
        addButton.setPreferredSize(new Dimension(140, 35));
        removeButton.setPreferredSize(new Dimension(150, 35));
        discountButton.setPreferredSize(new Dimension(150, 35));
        updateButton.setPreferredSize(new Dimension(150, 35));
        logoutButton.setPreferredSize(new Dimension(100, 35));

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(discountButton);
        panel.add(updateButton);
        panel.add(logoutButton);

        add(panel, BorderLayout.SOUTH);

    
        addButton.addActionListener(e -> addProduct());
        removeButton.addActionListener(e -> removeProduct());
        discountButton.addActionListener(e -> applyDiscount());
        updateButton.addActionListener(e -> updateProduct());
        logoutButton.addActionListener(e -> logout());

        loadProducts();
    }

    // loads the product table from the seller's shop
    private void loadProducts() {

        model.setRowCount(0);

        // nothing to load if the seller has no shop
        if (seller.getShop() == null) {
            return;
        }

        // add one row per product in the shop
        for (Product p : seller.getShop().getProducts()) {

            model.addRow(new Object[]{
                    p.getProductId(),
                    p.getName(),
                    p.getPrice(),
                    p.getDiscountPct(),
                    p.getStock(),
                    String.format("%.1f", p.getAverageRating()) + "/5"
            });
        }
    }

    // adding a new product to the seller's shop
    private void addProduct() {

        if (seller.getShop() == null) {
            JOptionPane.showMessageDialog(this, "You do not have a shop.");
            return;
        }

        // ask for product name
        String name = JOptionPane.showInputDialog(this, "Enter product name:");

        if (name == null) {
            return;
        }

        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product name cannot be empty.");
            return;
        }

        // ask for product price
        String priceText = JOptionPane.showInputDialog(this, "Enter price:");

        if (priceText == null) {
            return;
        }

        double price;

        try {
            price = Double.parseDouble(priceText.trim());

            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price. Price must be a number.");
            return;
        }

        // ask for stock quantity
        String stockText = JOptionPane.showInputDialog(this, "Enter stock:");

        if (stockText == null) {
            return;
        }

        int stock;

        try {
            stock = Integer.parseInt(stockText.trim());

            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "Stock cannot be negative.");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid stock. Stock must be a number.");
            return;
        }

        // create the product and add it to the seller's shop
        Product product = new Product(name.trim(), price, stock, seller.getShop());

        seller.addProduct(product);

        // register the shop globally if it isn't already
        if (!Main.getShops().contains(seller.getShop())) {
            Main.getShops().add(seller.getShop());
        }

        Main.saveAllData();
        loadProducts();

        JOptionPane.showMessageDialog(this, "Product added successfully.");
    }
       

    // removing the selected product from the seller's shop
    private void removeProduct() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product first.");
            return;
        }

        String name = (String) model.getValueAt(row, 1);

        Product product = findProductByName(name);

        if (product != null) {

            seller.removeProduct(product);

            Main.saveAllData();

            loadProducts();

            JOptionPane.showMessageDialog(this, "Product removed.");
        }
    }

    //  updating the name, price, and stock of the selected product
    private void updateProduct() {

    int row = table.getSelectedRow();

    if (row == -1) {

        JOptionPane.showMessageDialog(
                this,
                "Select a product first."
        );

        return;
    }

    String oldName =
            (String) model.getValueAt(row, 1);

    Product product =
            findProductByName(oldName);

    if (product == null) {

        JOptionPane.showMessageDialog(
                this,
                "Product not found."
        );

        return;
    }

    // ================= NEW NAME =================

    String newName = JOptionPane.showInputDialog(
                    this, "Enter new product name:",
                    product.getName()
            );

    if (newName == null) {
        return;
    }

    if (newName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Name cannot be empty.");
        return;
    }

    // ================= NEW PRICE =================

    String priceText = JOptionPane.showInputDialog(
                    this, "Enter new price:",
                    product.getPrice() );

    if (priceText == null) {
        return;
    }

    double newPrice;

    try {

        newPrice = Double.parseDouble(priceText);
        if (newPrice <= 0) {
            JOptionPane.showMessageDialog(
                    this, "Invalid price.");
            return;
        }

    }

    catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
                this, "Price must be numeric." );
        return;
    }

    // ================= NEW STOCK =================

    String stockText =  JOptionPane.showInputDialog(
                    this, "Enter new stock:",
                    product.getStock() );

    if (stockText == null) {
        return;
    }

    int newStock;

    try {

        newStock = Integer.parseInt(stockText);

        if (newStock < 0) {
            JOptionPane.showMessageDialog(
                    this, "Invalid stock.");

            return;
        }

    }

    catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
                this, "Stock must be numeric.");
        return;
    }

    // ================= UPDATE PRODUCT =================

    // apply all three updated values to the product
    product.setName(newName.trim());

    product.setPrice(newPrice);

    product.setStock(newStock);

    Main.saveAllData();

    loadProducts();

    JOptionPane.showMessageDialog(
            this, "Product updated successfully.");
}

        //  applying a discount percentage to the selected product
        private void applyDiscount() {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a product first.");
                return;
            }

            String name = (String) model.getValueAt(row, 1);

            Product product = findProductByName(name);

            if (product == null) {
                JOptionPane.showMessageDialog(this, "Product not found.");
                return;
            }

            // ask the seller to enter a discount percentage
            String discountText = JOptionPane.showInputDialog(
                    this, "Enter discount percentage:" );

            if (discountText == null) {
                return;
            }

            double discount;

            try {

                if (discountText.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this, "Discount cannot be empty.");
                    return;
                }

                discount = Double.parseDouble(
                        discountText.trim()
                );

                // discount must be a valid percentage
                if (discount < 0 || discount > 100) {
                    JOptionPane.showMessageDialog(
                            this, "Discount must be between 0 and 100." );
                    return;
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this, "Invalid discount. Enter numbers only.");
                return;
            }

            product.applyDiscount(discount);

            Main.saveAllData();

            loadProducts();

            JOptionPane.showMessageDialog(
                    this, "Discount applied." );
        }


    // finds a product in the seller's shop by its name used for updating and removing products
    private Product findProductByName(String name) {

        if (seller.getShop() == null) {
            return null;
        }

        for (Product p : seller.getShop().getProducts()) {

            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }

    // saves data, and returns to the login screen
    private void logout() {

        Main.saveAllData();

        Main.logout();

        new LoginFrame().setVisible(true);

        dispose();
    }
}