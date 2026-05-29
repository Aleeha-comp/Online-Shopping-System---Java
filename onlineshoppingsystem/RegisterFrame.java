package onlineshoppingsystem;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JTextField shopField;

    // NEW CATEGORY BOX
    private JComboBox<String> categoryBox;

    public RegisterFrame() {

        setTitle("Register");

        // Increased size a little
        setSize(400, 420);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        panel.setLayout(null);

        add(panel);

        JLabel nameLabel = new JLabel("Name:");

        nameLabel.setBounds(50, 40, 100, 25);

        panel.add(nameLabel);

        nameField = new JTextField();

        nameField.setBounds(150, 40, 150, 25);

        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");

        emailLabel.setBounds(50, 80, 100, 25);

        panel.add(emailLabel);

        emailField = new JTextField();

        emailField.setBounds(150, 80, 150, 25);

        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");

        passwordLabel.setBounds(50, 120, 100, 25);

        panel.add(passwordLabel);

        passwordField = new JPasswordField();

        passwordField.setBounds(150, 120, 150, 25);

        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");

        roleLabel.setBounds(50, 160, 100, 25);

        panel.add(roleLabel);

        roleBox = new JComboBox<>();

        roleBox.addItem("Customer");

        roleBox.addItem("Seller");

        roleBox.setBounds(150, 160, 150, 25);

        panel.add(roleBox);

        // ================= SHOP NAME =================

        JLabel shopLabel = new JLabel("Shop Name:");

        shopLabel.setBounds(50, 200, 100, 25);

        panel.add(shopLabel);

        shopField = new JTextField();

        shopField.setBounds(150, 200, 150, 25);

        panel.add(shopField);

        // ================= CATEGORY =================

        JLabel categoryLabel = new JLabel("Category:");

        categoryLabel.setBounds(50, 240, 100, 25);

        panel.add(categoryLabel);

        categoryBox = new JComboBox<>();

        categoryBox.addItem("Electronics");

        categoryBox.addItem("Clothes");

        categoryBox.addItem("Books");

        categoryBox.addItem("Accessories");

        categoryBox.setBounds(150, 240, 170, 25);

        panel.add(categoryBox);

        // ================= HIDE INITIALLY =================

        shopLabel.setVisible(false);

        shopField.setVisible(false);

        categoryLabel.setVisible(false);

        categoryBox.setVisible(false);

        // ================= ROLE CHANGE =================

        roleBox.addActionListener(e -> {

            String role =
                    (String) roleBox.getSelectedItem();

            if (role.equals("Seller")) {

                shopLabel.setVisible(true);

                shopField.setVisible(true);

                categoryLabel.setVisible(true);

                categoryBox.setVisible(true);

            }

            else {

                shopLabel.setVisible(false);

                shopField.setVisible(false);

                categoryLabel.setVisible(false);

                categoryBox.setVisible(false);
            }
        });

        JButton registerButton =
                new JButton("Register");

        // moved lower
        registerButton.setBounds(120, 310, 120, 30);

        panel.add(registerButton);

        registerButton.addActionListener(
                e -> registerUser()
        );
    }

    private void registerUser() {

        String name = nameField.getText();

        String email = emailField.getText();

        String password =
                new String(passwordField.getPassword());

        String role =
                (String) roleBox.getSelectedItem();

        // ================= EMPTY FIELD CHECK =================

        if (name.isEmpty()
                || email.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Fill all fields"
            );

            return;
        }

        // ================= EMAIL ALREADY EXISTS =================

        if (Main.emailExists(email)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account already exists.\nPlease login instead."
            );

            return;
        }

        // ================= CUSTOMER =================

        if (role.equals("Customer")) {

            String userId =
                    "C" + (Main.getCustomers().size() + 1);

            Customer customer =
                    new Customer(
                            userId,
                            name,
                            email,
                            password
                    );

            Main.addCustomer(customer);
        }

        // ================= SELLER =================

        else {

            String shopName =
                    shopField.getText();

            if (shopName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter shop name"
                );

                return;
            }

            // ================= GET CATEGORY =================

            String selectedCategory =
                    (String) categoryBox.getSelectedItem();

            // ================= FIND CATEGORY =================

            ShopCategory category =
                    findCategoryByName(selectedCategory);

            // ================= CREATE IF NOT EXISTS =================

            if (category == null) {

                category =
                        new ShopCategory(selectedCategory);

                Main.addCategory(category);
            }

            String userId =
                    "S" + (Main.getSellers().size() + 1);

            Shop shop =
                    new Shop(
                            Main.getShops().size() + 1,
                            shopName,
                            category
                    );

            Seller seller =
                    new Seller(
                            userId,
                            name,
                            email,
                            password,
                            shop,
                            0.0
                    );

            Main.addSeller(seller);

            Main.addShop(shop);
        }

        // ================= SAVE DATA =================

        Main.saveAllData();

        JOptionPane.showMessageDialog(
                this,
                "Account Created Successfully"
        );

        dispose();
    }

    // ================= FIND CATEGORY METHOD =================

    private ShopCategory findCategoryByName(String name) {

        for (ShopCategory category :
                Main.getCategories()) {

            if (category.getName().equals(name)) {

                return category;
            }
        }

        return null;
    }
}