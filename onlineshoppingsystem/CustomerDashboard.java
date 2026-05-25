
package onlineshoppingsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerDashboard extends JFrame {

    private Customer customer;

    private JTable productTable;
    private DefaultTableModel productModel;

    private JTable cartTable;
    private DefaultTableModel cartModel;

    private JLabel totalLabel;
    private JComboBox<String> categoryBox;

    public CustomerDashboard() {

        customer = Main.getCurrentCustomer();

        setTitle("Customer Dashboard");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Products", createProductPanel());
        tabs.addTab("Cart", createCartPanel());

        add(tabs);

        loadCategories();
        loadProducts("All");
        refreshCart();
    }

    private JPanel createProductPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Category:"));

        categoryBox = new JComboBox<>();
        topPanel.add(categoryBox);

        JButton showButton = new JButton("Show Products");
        topPanel.add(showButton);

        panel.add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Category", "Shop", "Price", "Stock"};

        productModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(productModel);

        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton addButton = new JButton("Add To Cart");
        JButton logoutButton = new JButton("Logout");

        bottomPanel.add(addButton);
        bottomPanel.add(logoutButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        showButton.addActionListener(e -> {
            String category = (String) categoryBox.getSelectedItem();
            loadProducts(category);
        });

        addButton.addActionListener(e -> addToCart());

        logoutButton.addActionListener(e -> logout());

        return panel;
    }

    private JPanel createCartPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Product", "Price", "Quantity", "Subtotal"};

        cartModel = new DefaultTableModel(columns, 0);
        cartTable = new JTable(cartModel);

        panel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        totalLabel = new JLabel("Total: Rs. 0.00");
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();

        JButton removeButton = new JButton("Remove Item");
        JButton checkoutButton = new JButton("Checkout");

        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        removeButton.addActionListener(e -> removeFromCart());

        checkoutButton.addActionListener(e -> openCheckout());

        return panel;
    }

    private void loadCategories() {

        categoryBox.removeAllItems();

        categoryBox.addItem("All");

        for (ShopCategory category : Main.getCategories()) {
            categoryBox.addItem(category.getName());
        }
    }

    private void loadProducts(String selectedCategory) {

        productModel.setRowCount(0);

        for (Shop shop : Main.getShops()) {

            String categoryName = shop.getShopCategory().getName();

            if (selectedCategory.equals("All") || selectedCategory.equals(categoryName)) {

                for (Product p : shop.getProducts()) {

                    productModel.addRow(new Object[]{
                            p.getProductId(),
                            p.getName(),
                            categoryName,
                            shop.getShopName(),
                            p.getDiscountedPrice(),
                            p.getStock()
                    });
                }
            }
        }
    }

    private void addToCart() {

        int row = productTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product first");
            return;
        }

        int id = (int) productModel.getValueAt(row, 0);

        Product product = findProductById(id);

        if (product == null) {
            JOptionPane.showMessageDialog(this, "Product not found");
            return;
        }

        String input = JOptionPane.showInputDialog(this, "Enter quantity:");

        if (input == null || input.isEmpty()) {
            return;
        }

        int qty = Integer.parseInt(input);

        if (!product.isInStock(qty)) {
            JOptionPane.showMessageDialog(this, "Not enough stock");
            return;
        }

        customer.getCart().addItem(product, qty);

        refreshCart();

        JOptionPane.showMessageDialog(this, "Product added to cart");
    }

    private void refreshCart() {

        cartModel.setRowCount(0);

        for (CartItem item : customer.getCart().getItems()) {

            cartModel.addRow(new Object[]{
                    item.getProduct().getName(),
                    item.getProduct().getDiscountedPrice(),
                    item.getQuantity(),
                    item.getSubtotal()
            });
        }

        totalLabel.setText("Total: Rs. " + customer.getCart().getTotal());
    }



    private void removeFromCart() {

        int row = cartTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select item first");
            return;
        }

        String name = (String) cartModel.getValueAt(row, 0);

        Product product = findProductByName(name);

        if (product != null) {
            customer.getCart().removeItem(product);
            refreshCart();
        }
    }

