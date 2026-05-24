package onlineshoppingsystem;

import javax.swing.*;
import java.awt.*;

/*
 * RegisterFrame — lets new users create an account.
 *
 * Viva points:
 * - Extends JDialog (modal window, not a full JFrame)
 * - JDialog blocks the parent window until closed
 * - Validates all fields before creating a user object
 * - Calls Main.addCustomer() / Main.addSeller() to save the new user
 */
public class RegisterFrame extends JDialog {

    // ── Fields ─────────────────────────────────────────────────────
    private JTextField     nameField;
    private JTextField     emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JTextField     extraField;     // department (Admin) or rating (Seller)
    private JLabel         extraLabel;

    // ── Constructor ────────────────────────────────────────────────
    public RegisterFrame(JFrame parent) {
        super(parent, "Register New Account", true);   // true = modal

        setSize(420, 380);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));
        add(panel);

        // ── Title ──────────────────────────────────────────────────
        JLabel title = new JLabel("Create New Account");
        title.setFont(new Font("Arial", Font.BOLD, 15));
        title.setBounds(120, 15, 220, 25);
        panel.add(title);

        // ── Name ───────────────────────────────────────────────────
        addLabel(panel, "Name:",     50, 60);
        nameField = new JTextField();
        nameField.setBounds(160, 60, 200, 25);
        panel.add(nameField);

        // ── Email ──────────────────────────────────────────────────
        addLabel(panel, "Email:",    50, 100);
        emailField = new JTextField();
        emailField.setBounds(160, 100, 200, 25);
        panel.add(emailField);

        // ── Password ───────────────────────────────────────────────
        addLabel(panel, "Password:", 50, 140);
        passwordField = new JPasswordField();
        passwordField.setBounds(160, 140, 200, 25);
        panel.add(passwordField);

        // ── Role ───────────────────────────────────────────────────
        addLabel(panel, "Role:", 50, 180);
        roleBox = new JComboBox<>(new String[]{"Customer", "Seller"});
        roleBox.setBounds(160, 180, 200, 25);
        panel.add(roleBox);

        // ── Extra field (changes based on role) ────────────────────
        extraLabel = new JLabel("(extra)");
        extraLabel.setBounds(50, 220, 100, 25);
        panel.add(extraLabel);

        extraField = new JTextField();
        extraField.setBounds(160, 220, 200, 25);
        panel.add(extraField);

        updateExtraField("Customer");   // set initial label

        // When role changes, update the extra field label
        roleBox.addActionListener(e -> updateExtraField((String) roleBox.getSelectedItem()));

        // ── Register button ────────────────────────────────────────
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(120, 280, 180, 35);
        registerBtn.setBackground(new Color(60, 179, 113));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        panel.add(registerBtn);

        registerBtn.addActionListener(e -> handleRegister());
    }

    // Helper to add a JLabel quickly
    private void addLabel(JPanel panel, String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, 100, 25);
        panel.add(lbl);
    }

    // Show the right label for the extra field
    private void updateExtraField(String role) {
        if ("Customer".equals(role)) {
            extraLabel.setText("(not needed)");
            extraField.setEnabled(false);
            extraField.setText("");
        } else if ("Seller".equals(role)) {
            extraLabel.setText("Shop name:");
            extraField.setEnabled(true);
        }
    }

    // Called when Register button is clicked
    private void handleRegister() {
        String name     = nameField.getText().trim();
        String email    = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role     = (String) roleBox.getSelectedItem();

        // ── Validation ─────────────────────────────────────────────
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Invalid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Main.emailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email already registered.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ── Create the user ────────────────────────────────────────
        String userId = role.charAt(0) + String.valueOf(System.currentTimeMillis()).substring(8);

        if ("Customer".equals(role)) {
            Customer c = new Customer(userId, name, email, password);
            Main.addCustomer(c);

        } else if ("Seller".equals(role)) {
            String shopName = extraField.getText().trim();
            if (shopName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a shop name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Create a default category and shop for the new seller
            ShopCategory cat = new ShopCategory("General");
            Main.addCategory(cat);

            int shopId = Main.getShops().size() + 1;
            Shop shop = new Shop(shopId, shopName, cat);
            Main.addShop(shop);

            Seller s = new Seller(userId, name, email, password, shop, 0.0);
            Main.addSeller(s);
        }

        JOptionPane.showMessageDialog(this, "Account created! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();   // close this dialog
    }
}