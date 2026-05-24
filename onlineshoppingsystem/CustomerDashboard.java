package onlineshoppingsystem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class CustomerDashboard extends JFrame implements Serializable{

    private Customer customer;
    private JTable productTable;
    private DefaultTableModel productModel;

    private JTable cartTable;
    private DefaultTableModel cartModel;

    public CustomerDashboard() {

        customer = Main.getCurrentCustomer();

        setTitle("Customer Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));

        add(mainPanel);

        // ================= PRODUCTS =================

        JPanel productPanel = new JPanel(new BorderLayout());

        JLabel productLabel = new JLabel("Products");
        productPanel.add(productLabel, BorderLayout.NORTH);

        String[] productColumns = {
                "ID",
                "Name",
                "Price",
                "Stock"
        };

        productModel = new DefaultTableModel(productColumns, 0);

        productTable = new JTable(productModel);

        JScrollPane productScroll = new JScrollPane(productTable);

        productPanel.add(productScroll, BorderLayout.CENTER);

        JButton addCartButton = new JButton("Add To Cart");

        productPanel.add(addCartButton, BorderLayout.SOUTH);

        mainPanel.add(productPanel);

        // ================= CART =================

        JPanel cartPanel = new JPanel(new BorderLayout());

        JLabel cartLabel = new JLabel("My Cart");

        cartPanel.add(cartLabel, BorderLayout.NORTH);

        String[] cartColumns = {
                "Product",
                "Price",
                "Quantity"
        };

        cartModel = new DefaultTableModel(cartColumns, 0);

        cartTable = new JTable(cartModel);

        JScrollPane cartScroll = new JScrollPane(cartTable);

        cartPanel.add(cartScroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton removeButton = new JButton("Remove");

        JButton checkoutButton = new JButton("Checkout");

        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(logoutButton);

        cartPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(cartPanel);

        // ================= BUTTON ACTIONS =================

        addCartButton.addActionListener(e -> addToCart());

        removeButton.addActionListener(e -> removeFromCart());

        checkoutButton.addActionListener(e -> checkout());

        logoutButton.addActionListener(e -> logout());

        loadProducts();
    }

    // ================= LOAD PRODUCTS =================

    private void loadProducts() {

        productModel.setRowCount(0);

        for (Shop shop : Main.getShops()) {

            for (Product p : shop.getProducts()) {

                productModel.addRow(new Object[]{
                        p.getProductId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStock()
                });
            }
        }
    }

    // ================= ADD TO CART =================

    private void addToCart() {

        int row = productTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(this, "Select a product");

            return;
        }

        int id = (int) productModel.getValueAt(row, 0);

        Product product = findProductById(id);

        String input = JOptionPane.showInputDialog(
                this,
                "Enter quantity"
        );

        int qty = Integer.parseInt(input);

        customer.getCart().addItem(product, qty);

        refreshCart();

        JOptionPane.showMessageDialog(this, "Added To Cart");
    }

     // ================= REFRESH CART =================

    private void refreshCart() {

        cartModel.setRowCount(0);

        for (CartItem item : customer.getCart().getItems()) {

            cartModel.addRow(new Object[]{
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity()
            });
        }
    }

    // ================= REMOVE FROM CART =================

    private void removeFromCart() {

        int row = cartTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(this, "Select item");

            return;
        }

        String name = (String) cartModel.getValueAt(row, 0);

        Product product = findProductByName(name);

        customer.getCart().removeItem(product);

        refreshCart();
    }

    // ================= CHECKOUT =================

    private void checkout() {

        if (customer.getCart().getItems().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Cart is empty");

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Order Placed Successfully"
        );

         customer.getCart().getItems().clear();

        refreshCart();
    }

    // ================= FIND PRODUCT =================

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

    // ================= LOGOUT =================

    private void logout() {

        Main.saveAllData();

        Main.setCurrentUser(null);

        new LoginFrame().setVisible(true);

        dispose();
    }
}
