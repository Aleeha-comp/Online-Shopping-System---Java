package onlineshoppingsystem;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public LoginFrame() {

        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        add(panel);

        JLabel title = new JLabel("Online Shopping System");
        title.setBounds(100, 20, 200, 30);
        panel.add(title);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 70, 100, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 70, 150, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 150, 25);
        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 150, 100, 25);
        panel.add(roleLabel);

        roleBox = new JComboBox<>();

        roleBox.addItem("Customer");
        roleBox.addItem("Seller");
        roleBox.addItem("Admin");

        roleBox.setBounds(150, 150, 150, 25);

        panel.add(roleBox);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(80, 210, 100, 30);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(200, 210, 100, 30);
        panel.add(registerButton);

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> openRegister());
    }

    private void login() {

        String email = emailField.getText();

        String password = new String(passwordField.getPassword());

        String role = (String) roleBox.getSelectedItem();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields");
            return;
        }

        if (role.equals("Customer")) {
            Customer customer = Main.findCustomer(email, password);

            if (customer != null) {

                Main.setCurrentUser(customer);

                new CustomerDashboard().setVisible(true);

                dispose();

            } 
            
            else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } 
        
        else if (role.equals("Seller")) {

            Seller seller = Main.findSeller(email, password);

            if (seller != null) {

                Main.setCurrentUser(seller);
                new SellerDashboard().setVisible(true);

                dispose();

            } 
            
            else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } 
        
        else {

            Admin admin = Main.findAdmin(email, password);

            if (admin != null) {

                Main.setCurrentUser(admin);
                new AdminDashboard().setVisible(true);

                dispose();

            } 
            
            else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        }
    }

    private void openRegister() {

        new RegisterFrame().setVisible(true);
    }
}