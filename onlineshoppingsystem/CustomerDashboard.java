
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

    private void openCheckout() {

        if (customer.getCart().getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty");
            return;
        }

        JDialog dialog = new JDialog(this, "Checkout", true);
        dialog.setSize(400, 430);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        dialog.add(panel);

        JLabel streetLabel = new JLabel("Street:");
        streetLabel.setBounds(40, 30, 100, 25);
        panel.add(streetLabel);

        JTextField streetField = new JTextField();
        streetField.setBounds(160, 30, 180, 25);
        panel.add(streetField);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(40, 70, 100, 25);
        panel.add(cityLabel);

        JTextField cityField = new JTextField();
        cityField.setBounds(160, 70, 180, 25);
        panel.add(cityField);

        JLabel provinceLabel = new JLabel("Province:");
        provinceLabel.setBounds(40, 110, 100, 25);
        panel.add(provinceLabel);

        JTextField provinceField = new JTextField();
        provinceField.setBounds(160, 110, 180, 25);
        panel.add(provinceField);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(40, 150, 100, 25);
        panel.add(countryLabel);

        JTextField countryField = new JTextField();
        countryField.setBounds(160, 150, 180, 25);
        panel.add(countryField);

        JLabel zipLabel = new JLabel("Zip Code:");
        zipLabel.setBounds(40, 190, 100, 25);
        panel.add(zipLabel);

        JTextField zipField = new JTextField();
        zipField.setBounds(160, 190, 180, 25);
        panel.add(zipField);

        JLabel paymentLabel = new JLabel("Payment:");
        paymentLabel.setBounds(40, 230, 100, 25);
        panel.add(paymentLabel);

        JComboBox<String> paymentBox = new JComboBox<>();
        paymentBox.addItem("Cash on Delivery");
        paymentBox.addItem("Credit Card");
        paymentBox.addItem("EasyPaisa");
        paymentBox.setBounds(160, 230, 180, 25);
        panel.add(paymentBox);

        JLabel extraLabel = new JLabel("Card/Phone:");
        extraLabel.setBounds(40, 270, 100, 25);
        panel.add(extraLabel);

        JTextField extraField = new JTextField();
        extraField.setBounds(160, 270, 180, 25);
        panel.add(extraField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(40, 310, 100, 25);
        panel.add(cvvLabel);

        JTextField cvvField = new JTextField();
        cvvField.setBounds(160, 310, 180, 25);
        panel.add(cvvField);

        JButton placeButton = new JButton("Place Order");
        placeButton.setBounds(120, 350, 150, 30);
        panel.add(placeButton);

        placeButton.addActionListener(e -> {

            if (streetField.getText().isEmpty()
                    || cityField.getText().isEmpty()
                    || provinceField.getText().isEmpty()
                    || countryField.getText().isEmpty()
                    || zipField.getText().isEmpty()) {

                JOptionPane.showMessageDialog(dialog, "Fill address fields");
                return;
            }

            Address address = new Address(
                    streetField.getText(),
                    cityField.getText(),
                    provinceField.getText(),
                    countryField.getText(),
                    zipField.getText(),
                    "Home"
            );

            customer.addAddress(address);

            double total = customer.getCart().getTotal();

            Payment payment;

            int paymentId = customer.getOrders().size() + 1;

            String method = (String) paymentBox.getSelectedItem();

            if (method.equals("Credit Card")) {

                String cardNo = extraField.getText();
                String cvv = cvvField.getText();

                payment = new CreditCardPayment(paymentId, total, cardNo, cvv);

            } else if (method.equals("EasyPaisa")) {

                String phone = extraField.getText();

                payment = new EasyPaisaPayment(paymentId, total, phone);

            } else {

                payment = new CashOnDelivery(paymentId, total, true);
            }

            Order order = customer.placeOrder(address, payment);

            if (order != null) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Order placed successfully\nTotal: Rs. " + order.getTotal()
                );

                dialog.dispose();

                refreshCart();

                loadProducts((String) categoryBox.getSelectedItem());

                Main.saveAllData();
            }
        });

        dialog.setVisible(true);
    }

    private Product findProductById(int id) {

        for (Shop shop : Main.getShops()) {

            for (Product p : shop.getProducts()) {

                if (p.getProductId() == id) {
                    return p;
                }
            }
        }

        return null;
    }

    private Product findProductByName(String name) {

        for (Shop shop : Main.getShops()) {

            for (Product p : shop.getProducts()) {

                if (p.getName().equals(name)) {
                    return p;
                }
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

