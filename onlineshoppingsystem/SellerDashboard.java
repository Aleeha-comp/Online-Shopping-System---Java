package onlineshoppingsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SellerDashboard extends JFrame {

    private Seller seller;
    private JTable table;
    private DefaultTableModel model;

    public SellerDashboard() {

        seller = Main.getCurrentSeller();

        setTitle("Seller Dashboard");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel("Welcome " + seller.getName());
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setHorizontalAlignment(JLabel.CENTER);
        add(heading, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Price", "Discount", "Stock", "Rating"};

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JButton addButton = new JButton("Add Product");
        JButton removeButton = new JButton("Remove Product");
        JButton discountButton = new JButton("Apply Discount");
        JButton logoutButton = new JButton("Logout");

        addButton.setPreferredSize(new Dimension(140, 35));
        removeButton.setPreferredSize(new Dimension(150, 35));
        discountButton.setPreferredSize(new Dimension(150, 35));
        logoutButton.setPreferredSize(new Dimension(100, 35));

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(discountButton);
        panel.add(logoutButton);

        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addProduct());
        removeButton.addActionListener(e -> removeProduct());
        discountButton.addActionListener(e -> applyDiscount());
        logoutButton.addActionListener(e -> logout());

        loadProducts();
    }

    private void loadProducts() {

        model.setRowCount(0);

        if (seller.getShop() == null) {
            return;
        }

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

    private void addProduct() {

        if (seller.getShop() == null) {
            JOptionPane.showMessageDialog(this, "You do not have a shop.");
            return;
        }

        String name = JOptionPane.showInputDialog(this, "Enter product name:");

        if (name == null) {
            return;
        }

        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product name cannot be empty.");
            return;
        }

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

        Product product = new Product(name.trim(), price, stock, seller.getShop());

        seller.addProduct(product);

        if (!Main.getShops().contains(seller.getShop())) {
            Main.getShops().add(seller.getShop());
        }

        Main.saveAllData();
        loadProducts();

        JOptionPane.showMessageDialog(this, "Product added successfully.");
    }
       

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

            String discountText = JOptionPane.showInputDialog(
                    this,
                    "Enter discount percentage:"
            );

            if (discountText == null) {
                return;
            }

            double discount;

            try {

                if (discountText.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Discount cannot be empty."
                    );
                    return;
                }

                discount = Double.parseDouble(
                        discountText.trim()
                );

                if (discount < 0 || discount > 100) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Discount must be between 0 and 100."
                    );
                    return;
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid discount. Enter numbers only."
                );
                return;
            }

            product.applyDiscount(discount);

            Main.saveAllData();

            loadProducts();

            JOptionPane.showMessageDialog(
                    this,
                    "Discount applied."
            );
        }


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

    private void logout() {

        Main.saveAllData();

        Main.logout();

        new LoginFrame().setVisible(true);

        dispose();
    }
}