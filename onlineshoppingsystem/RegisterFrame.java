package onlineshoppingsystem;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JTextField shopField;

    public RegisterFrame() {

        setTitle("Register");
        setSize(400, 350);
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

        JLabel shopLabel = new JLabel("Shop Name:");
        shopLabel.setBounds(50, 200, 100, 25);
        panel.add(shopLabel);

        shopField = new JTextField();
        shopField.setBounds(150, 200, 150, 25);
        panel.add(shopField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(120, 250, 120, 30);
        panel.add(registerButton);

        registerButton.addActionListener(e -> registerUser());
    }

    private void registerUser() {

        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields");
            return;
        }

        if (role.equals("Customer")) {

            String userId = "C" + (Main.getCustomers().size() + 1);

            Customer customer = new Customer(userId, name, email, password);

            Main.addCustomer(customer);

        } else {

            String shopName = shopField.getText();

            if (shopName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter shop name");
                return;
            }

            String userId = "S" + (Main.getSellers().size() + 1);

            ShopCategory category = new ShopCategory("General");

            Shop shop = new Shop(
                    Main.getShops().size() + 1,
                    shopName,
                    category
            );

            Seller seller = new Seller(
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

        JOptionPane.showMessageDialog(this, "Account Created");

        dispose();
    }
}