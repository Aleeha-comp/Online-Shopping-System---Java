package onlineshoppingsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private Admin admin;

    private JTable shopTable;
    private DefaultTableModel shopModel;

    private JTable userTable;
    private DefaultTableModel userModel;

    public AdminDashboard() {

        admin = Main.getCurrentAdmin();

        setTitle("Admin Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        add(mainPanel);

        // ================= SHOPS =================
        JPanel shopPanel = new JPanel(new BorderLayout());

        JLabel shopLabel = new JLabel("Shops");
        shopPanel.add(shopLabel, BorderLayout.NORTH);

        String[] shopColumns = {"ID", "Name", "Category", "Products"};

        shopModel = new DefaultTableModel(shopColumns, 0);
        shopTable = new JTable(shopModel);

        JScrollPane shopScrollPane = new JScrollPane(shopTable);
        shopPanel.add(shopScrollPane, BorderLayout.CENTER);

        JPanel shopButtonPanel = new JPanel();

        JButton removeShopButton = new JButton("Remove Shop");
        JButton refreshShopButton = new JButton("Refresh Shops");

        shopButtonPanel.add(removeShopButton);
        shopButtonPanel.add(refreshShopButton);

        shopPanel.add(shopButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(shopPanel);

        // ================= USERS =================
        JPanel userPanel = new JPanel(new BorderLayout());

        JLabel userLabel = new JLabel("Users");
        userPanel.add(userLabel, BorderLayout.NORTH);

        String[] userColumns = {"ID", "Name", "Email", "Role"};

        userModel = new DefaultTableModel(userColumns, 0);
        userTable = new JTable(userModel);

        JScrollPane userScrollPane = new JScrollPane(userTable);
        userPanel.add(userScrollPane, BorderLayout.CENTER);

        JPanel userButtonPanel = new JPanel();

        JButton refreshUserButton = new JButton("Refresh Users");
        JButton removeUserButton = new JButton("Remove User");
        JButton logoutButton = new JButton("Logout");

        userButtonPanel.add(refreshUserButton);
        userButtonPanel.add(removeUserButton);
        userButtonPanel.add(logoutButton);

        userPanel.add(userButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(userPanel);

        // ================= BUTTON ACTIONS =================
        removeShopButton.addActionListener(e -> removeShop());

        refreshShopButton.addActionListener(e -> loadShops());

        refreshUserButton.addActionListener(e -> loadUsers());

        removeUserButton.addActionListener(e -> removeUser());

        logoutButton.addActionListener(e -> logout());

        // ================= LOAD DATA =================
        loadShops();
        loadUsers();
    }

    // ================= LOAD SHOPS ================
    private void loadShops() {

        shopModel.setRowCount(0);

        for (Shop shop : Main.getShops()) {

            shopModel.addRow(new Object[]{
                    shop.getShopId(),
                    shop.getShopName(),
                    shop.getShopCategory().getName(),
                    shop.getProducts().size()
            });
        }
    }

    // ================= LOAD USERS ================
    private void loadUsers() {

        userModel.setRowCount(0);

        for (Customer customer : Main.getCustomers()) {

            userModel.addRow(new Object[]{
                    customer.getUserId(),
                    customer.getName(),
                    customer.getEmail(),
                    "Customer"
            });
        }

        // Sellers
        for (Seller seller : Main.getSellers()) {

            userModel.addRow(new Object[]{
                    seller.getUserId(),
                    seller.getName(),
                    seller.getEmail(),
                    "Seller"
            });
        }

        // Admins
        for (Admin admin : Main.getAdmins()) {

            userModel.addRow(new Object[]{
                    admin.getUserId(),
                    admin.getName(),
                    admin.getEmail(),
                    "Admin"
            });
        }
    }

    // ================= REMOVE SHOP ================
    private void removeShop() {

        int row = shopTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(this, "Please select a shop");

            return;
        }

        String shopName = (String) shopModel.getValueAt(row, 1);

        Shop shop = findShopByName(shopName);

        if (shop != null) {

            // Remove shop from seller also
            Seller owner = findOwnerOfShop(shop);

            if (owner != null) {

                Main.getSellers().remove(owner);
            }

            // Remove shop from main shop list
            Main.getShops().remove(shop);

            // Save updated data
            Main.saveAllData();

            loadShops();

            loadUsers();

            JOptionPane.showMessageDialog(
                    this,
                    "Shop removed successfully!"
            );
        }
    }

    // ================= REMOVE USER ================
    private void removeUser() {

        int row = userTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(this, "Please select a user");

            return;
        }

        // Column 2 contains email
        String userEmail = (String) userModel.getValueAt(row, 2);

        String userRole = (String) userModel.getValueAt(row, 3);

        // Remove Customer
        if ("Customer".equals(userRole)) {

            Customer customer = findCustomerByEmail(userEmail);

            if (customer != null) {

                Main.getCustomers().remove(customer);
            }
        }

        // Remove Seller
        else if ("Seller".equals(userRole)) {

            Seller seller = findSellerByEmail(userEmail);

            if (seller != null) {

                // Remove seller shop first
                Shop sellerShop = seller.getShop();

                if (sellerShop != null) {

                    Main.getShops().remove(sellerShop);
                }

                Main.getSellers().remove(seller);
            }
        }

        // Admin cannot be removed , protecting admin
        else if ("Admin".equals(userRole)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Admin cannot be removed"
            );

            return;
        }

        // Save updated data
        Main.saveAllData();

        loadUsers();

        loadShops();

        JOptionPane.showMessageDialog(
                this,
                "User removed successfully!"
        );
    }

    // ================= FIND SHOP BY NAME ================
    private Shop findShopByName(String name) {

        for (Shop shop : Main.getShops()) {

            if (shop.getShopName().equals(name)) {

                return shop;
            }
        }

        return null;
    }

    // ================= FIND CUSTOMER BY EMAIL ================
    private Customer findCustomerByEmail(String email) {

        for (Customer customer : Main.getCustomers()) {

            if (customer.getEmail().equals(email)) {

                return customer;
            }
        }

        return null;
    }

    // ================= FIND SELLER BY EMAIL ================
    private Seller findSellerByEmail(String email) {

        for (Seller seller : Main.getSellers()) {

            if (seller.getEmail().equals(email)) {

                return seller;
            }
        }

        return null;
    }

    // ================= FIND OWNER OF SHOP ================
    private Seller findOwnerOfShop(Shop shop) {

        for (Seller seller : Main.getSellers()) {

            if (seller.getShop() != null
                    && seller.getShop().equals(shop)) {

                return seller;
            }
        }

        return null;
    }

    // ================= LOGOUT ================
    private void logout() {

        Main.saveAllData();

        Main.logout();

        new LoginFrame().setVisible(true);

        dispose();
    }
}