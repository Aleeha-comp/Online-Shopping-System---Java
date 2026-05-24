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

        shopPanel.add(new JScrollPane(shopTable), BorderLayout.CENTER);

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

        userPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel userButtonPanel = new JPanel();

        JButton refreshUserButton = new JButton("Refresh Users");
        JButton logoutButton = new JButton("Logout");

        userButtonPanel.add(refreshUserButton);
        userButtonPanel.add(logoutButton);

        userPanel.add(userButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(userPanel);

        // ================= BUTTON ACTIONS =================

        removeShopButton.addActionListener(e -> removeShop());

        refreshShopButton.addActionListener(e -> loadShops());

        refreshUserButton.addActionListener(e -> loadUsers());

        logoutButton.addActionListener(e -> logout());

        loadShops();
        loadUsers();
    }

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

        for (Seller seller : Main.getSellers()) {

            userModel.addRow(new Object[]{
                    seller.getUserId(),
                    seller.getName(),
                    seller.getEmail(),
                    "Seller"
            });
        }

        for (Admin admin : Main.getAdmins()) {

            userModel.addRow(new Object[]{
                    admin.getUserId(),
                    admin.getName(),
                    admin.getEmail(),
                    "Admin"
            });
        }
    }

    private void removeShop() {

        int row = shopTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(this, "Select a shop");

            return;
        }

        String shopName = (String) shopModel.getValueAt(row, 1);

        Shop shop = findShopByName(shopName);

        if (shop != null) {

            Main.getShops().remove(shop);

            loadShops();

            JOptionPane.showMessageDialog(this, "Shop removed");
        }
    }

    private Shop findShopByName(String name) {

        for (Shop shop : Main.getShops()) {

            if (shop.getShopName().equals(name)) {

                return shop;
            }
        }

        return null;
    }

    private void logout() {

        Main.saveAllData();

        Main.setCurrentUser(null);

        new LoginFrame().setVisible(true);

        dispose();
    }
}