package onlineshoppingsystem;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;   // dropdown: Customer / Seller / Admin

    // Constructor
    public LoginFrame() {

        //Creating Window
        setTitle("Online Shopping System Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             // Closes program when window closes
        setLocationRelativeTo(null);                             // center window on screen
        setResizable(false);                            // Prevents resizing

        // Main panel
        JPanel panel = new JPanel();                                // Creates container for components.
        panel.setLayout(null);                                 // absolute positioning. Coordinates will be set manually
        panel.setBackground(new Color(245, 245, 245));
        add(panel);

        // Labelling
        // Title label 
        JLabel title = new JLabel("Online Shopping System");
        title.setFont(new Font("Arial", Font.BOLD, 16));            // Manually setting
        title.setBounds(70, 15, 300, 30);
        panel.add(title);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 65, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(140, 65, 200, 25);
        panel.add(emailField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 105, 80, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 105, 200, 25);
        panel.add(passwordField);

        // Role dropdown list
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 145, 80, 25);
        panel.add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"Customer", "Seller", "Admin"});
        roleBox.setBounds(140, 145, 200, 25);
        panel.add(roleBox);

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(80, 195, 100, 35);
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        panel.add(loginBtn);

        // Register button
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(210, 195, 100, 35);
        registerBtn.setBackground(new Color(60, 179, 113));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        panel.add(registerBtn);

        // Button actions
        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> openRegister());
    }

    // Called when Login button is clicked
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        // validation
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter email and password.", "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Try to find the user based on selected role
        if ("Customer".equals(role)) {
            Customer c = Main.findCustomer(email, password);
            if (c != null) {
                Main.setCurrentUser(c);
                new CustomerDashboard().setVisible(true);   // open customer window
                dispose();                                   // close login window
            } 
            
            else {
                JOptionPane.showMessageDialog(this, "Invalid customer credentials.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            }

        } 
        
        else if ("Seller".equals(role)) {
            Seller s = Main.findSeller(email, password);
            if (s != null) {
                Main.setCurrentUser(s);
                new SellerDashboard().setVisible(true);
                dispose();
            } 
            
            else {
                JOptionPane.showMessageDialog(this,"Invalid seller credentials.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            }

        } 
        
        else {   // Admin
            Admin a = Main.findAdmin(email, password);
            if (a != null) {
                Main.setCurrentUser(a);
                new AdminDashboard().setVisible(true);
                dispose();
            } 
            
            else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Open the registration dialog
    private void openRegister() {
        new RegisterFrame(this).setVisible(true);
    }
}
