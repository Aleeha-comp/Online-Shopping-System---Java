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
        setSize(650, 450);
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
        String priceText = JOptionPane.showInputDialog(this, "Enter price:");
        String stockText = JOptionPane.showInputDialog(this, "Enter stock:");

        if (name == null || priceText == null || stockText == null) {
            return;
        }

        double price = Double.parseDouble(priceText);
        int stock = Integer.parseInt(stockText);

        Product product = new Product(name, price, stock, seller.getShop());

        seller.addProduct(product);

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

        if (product != null) {

            String discountText = JOptionPane.showInputDialog(this, "Enter discount percentage:");

            if (discountText == null) {
                return;
            }

            double discount = Double.parseDouble(discountText);

            product.applyDiscount(discount);

            Main.saveAllData();

            loadProducts();

            JOptionPane.showMessageDialog(this, "Discount applied.");
        }
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