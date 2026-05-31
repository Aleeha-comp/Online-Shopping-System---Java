package onlineshoppingsystem;

import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {

    private Customer customer;
    private JPanel productPanel;
    private JLabel totalLabel;

    public CustomerDashboard() {
        customer = Main.getCurrentCustomer();

        setTitle("Online Shopping System");
        setSize(900, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

    // ------------------ TITLE ------------------

        JLabel title = new JLabel("Online Shopping System", JLabel.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

    // ------------------ CATEGORY PANEL -------------

        JPanel categoryPanel = new JPanel();

        categoryPanel.setLayout(new GridLayout(0, 1, 5, 5));

        categoryPanel.setPreferredSize(new Dimension(140, 0));

        JButton allButton = new JButton("All");

        categoryPanel.add(allButton);

        for (ShopCategory category : Main.getCategories()) {

            JButton button = new JButton(category.getName());

            categoryPanel.add(button);

            button.addActionListener(e -> showProducts(category.getName()));
        }
        
        allButton.addActionListener(e -> showProducts("All"));

        add(categoryPanel, BorderLayout.WEST);

    // ------------------ PRODUCT PANEL -------------------

        productPanel = new JPanel();

        productPanel.setLayout(new GridLayout(0, 2, 10, 10));

        JScrollPane scrollPane = new JScrollPane(productPanel);

        add(scrollPane, BorderLayout.CENTER);

    // ------------------ BOTTOM PANEL -------------------

        JPanel bottomPanel = new JPanel();

        totalLabel = new JLabel("Cart Total: Rs. 0.00");

        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton viewCartButton = new JButton("View Cart");

        JButton removeButton = new JButton("Remove Item");

        JButton checkoutButton = new JButton("Checkout");

        JButton logoutButton = new JButton("Logout");

        bottomPanel.add(totalLabel);

        bottomPanel.add(viewCartButton);

        bottomPanel.add(removeButton);

        bottomPanel.add(checkoutButton);

        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);


     // ----------------- BUTTON ACTIONS ----------------

        viewCartButton.addActionListener( e -> viewCart());

        removeButton.addActionListener( e -> removeFromCart());

        checkoutButton.addActionListener( e -> checkout());

        logoutButton.addActionListener( e -> logout());


    // ------------------ LOAD PRODUCTS ----------------

        showProducts("All");

        updateTotal();
    }

    // ------------------ SHOW PRODUCTS ----------------

        private void showProducts(String selectedCategory) {

        productPanel.removeAll();

        for (Shop shop : Main.getShops()) {

                String categoryName = shop.getShopCategory().getName();

                if (selectedCategory.equals("All") || selectedCategory.equals(categoryName)) {

                for (Product product : shop.getProducts()) {

                    JPanel card = new JPanel();

                    card.setLayout(new GridLayout(7, 1));  

                    card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                    JLabel nameLabel = new JLabel(product.getName(), JLabel.CENTER);

                    nameLabel.setFont(new Font("Arial", Font.BOLD, 18));

                    JLabel categoryLabel = new JLabel( "Category: " + categoryName, JLabel.CENTER );

                    JLabel shopLabel = new JLabel( "Shop: " + shop.getShopName(), JLabel.CENTER );

                    JLabel priceLabel = new JLabel( "Rs. " + product.getDiscountedPrice(), JLabel.CENTER );

                    JLabel stockLabel =new JLabel("Stock: " + product.getStock(), JLabel.CENTER );


                //  NEW: Rating label added
                    JLabel ratingLabel = new JLabel( "Rating: " + String.format("%.1f", product.getAverageRating()) + "/5", JLabel.CENTER );

                    JButton addButton = new JButton("Add To Cart");

                    JButton rateButton = new JButton("Rate Product");

                    card.add(nameLabel);
                    card.add(categoryLabel);
                    card.add(shopLabel);
                    card.add(priceLabel);
                    card.add(stockLabel);


                //  add rating BEFORE button
                    card.add(ratingLabel);
                    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));

                    buttonPanel.add(addButton);
                    buttonPanel.add(rateButton);

                    card.add(buttonPanel);

                    addButton.addActionListener( e -> addToCart(product));

                    rateButton.addActionListener(e -> { String input = JOptionPane.showInputDialog( this, "Enter rating (1 to 5):" );

                    if (input == null || input.isEmpty()) {
                        JOptionPane.showMessageDialog(this,"Rating cannot be empty");
                        return;
                    }

                    try {
                        int rating = Integer.parseInt(input);

                    if (rating < 1 || rating > 5) {
                        JOptionPane.showMessageDialog( this, "Rating must be between 1 and 5" );
                        
                    return;
                    }

                    

                //  IMPORTANT: update product rating
                    product.addRating( rating, customer.getName());

                    JOptionPane.showMessageDialog(this,"Thank you for your rating!");

                // refresh UI to update average rating label
                    showProducts(selectedCategory);

                    } catch (NumberFormatException ex) {
                    
                    JOptionPane.showMessageDialog(this,"Please enter a valid number");
                }
            });
                    productPanel.add(card);
                }
    }
        }

                productPanel.revalidate();
                productPanel.repaint();
        }

        
    // ================= ADD TO CART =================

    private void addToCart(Product product) {

        String input =JOptionPane.showInputDialog(this,"Enter quantity:");

        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Quantity cannot be empty");
        }

        if (!input.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,"Please enter a valid number");
            return;
        }

        int quantity = Integer.parseInt(input);

        if (!product.isInStock(quantity)) {
            JOptionPane.showMessageDialog(this,"Not enough stock available");
            return;
        }

        customer.getCart().addItem(product,quantity);

        updateTotal();

        JOptionPane.showMessageDialog(this,"Added to cart");
    }

    // ================= VIEW CART =================

        private void viewCart() {

            if (customer.getCart().getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Cart is empty");
                return;
            }

            String cartText = "";

            for (CartItem item : customer.getCart().getItems()) {

                cartText += item.getProduct().getName()
                        + " x "
                        + item.getQuantity()
                        + " = Rs. "
                        + item.getSubtotal()
                        + "\n";
            }

            cartText +="\n--------------------------";

            cartText += "\nCart Total: Rs. " + customer.getCart().getTotal();

            JOptionPane.showMessageDialog(this, cartText,"My Cart",JOptionPane.INFORMATION_MESSAGE);
        }

    // ================= REMOVE FROM CART =================

        private void removeFromCart() {

            if (customer.getCart().getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Cart is empty");
                return;
            }

            String[] productNames =new String[customer.getCart().getItems().size()];

            for (int i = 0; i < customer.getCart().getItems().size();i++) {

                CartItem item =customer.getCart().getItems().get(i);

                productNames[i] = item.getProduct().getName() + " x " + item.getQuantity();
            }

            String selected =
                    (String) JOptionPane.showInputDialog(this,"Select product to remove:","Remove Item",
                    JOptionPane.INFORMATION_MESSAGE,null,productNames,productNames[0]);

            if (selected == null) {
                return;
            }

            CartItem selectedItem = null;

            for (CartItem item : customer.getCart().getItems()) {

                String text =item.getProduct().getName() + " x "+ item.getQuantity();

                if (text.equals(selected)) {
                    selectedItem = item;
                    break;
                }
            }

            if (selectedItem != null) {
                String qtyText =JOptionPane.showInputDialog(this,"Enter quantity to remove:");

                if (qtyText == null || qtyText.isEmpty()) {
                    JOptionPane.showMessageDialog(this,"Quantity cannot be empty");
                    return;
                }

                int removeQty =Integer.parseInt(qtyText);

                int currentQty = selectedItem.getQuantity();

                if (removeQty <= 0 ) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0");
                    return;
                }

                if (removeQty > currentQty) {
                    JOptionPane.showMessageDialog(this,"You only have " + currentQty + " in cart");
                    return;
                }

                if (removeQty >= currentQty) {
                    customer.getCart().removeItem(selectedItem.getProduct());

                } else {
                    selectedItem.updateQty(currentQty - removeQty);
                }

                updateTotal();

                JOptionPane.showMessageDialog(this,"Cart updated");
            }
        }
            

    // ================= CHECKOUT =================

        private void checkout() {

            if (customer.getCart().getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this,"Cart is empty" );
                return;
            }

                JDialog dialog =new JDialog( this, "Checkout", true);

                dialog.setSize(400, 430);

                dialog.setLocationRelativeTo(this);

                JPanel panel = new JPanel();

                panel.setLayout(null);

                dialog.add(panel);

                JLabel total =new JLabel("Total Bill: Rs. " + customer.getCart().getTotal());

                total.setBounds(120, 10, 200, 25);

                panel.add(total);

                JLabel streetLabel = new JLabel("Street:");

                streetLabel.setBounds(40, 50, 100, 25);

                panel.add(streetLabel);

                JTextField streetField = new JTextField();

                streetField.setBounds(160, 50, 180, 25);

                panel.add(streetField);

                JLabel cityLabel = new JLabel("City:");

                cityLabel.setBounds(40, 90, 100, 25);

                panel.add(cityLabel);

                JTextField cityField = new JTextField();

                cityField.setBounds(160, 90, 180, 25);

                panel.add(cityField);

                JLabel provinceLabel = new JLabel("Province:");

                provinceLabel.setBounds(40, 130, 100, 25);

                panel.add(provinceLabel);

                JTextField provinceField = new JTextField();

                provinceField.setBounds(160, 130, 180, 25);

                panel.add(provinceField);

                JLabel countryLabel = new JLabel("Country:");

                countryLabel.setBounds(40, 170, 100, 25);

                panel.add(countryLabel);

                JTextField countryField = new JTextField();

                countryField.setBounds(160, 170, 180, 25);

                panel.add(countryField);

                JLabel zipLabel = new JLabel("Zip Code:");

                zipLabel.setBounds(40, 210, 100, 25);

                panel.add(zipLabel);

                JTextField zipField = new JTextField();

                zipField.setBounds(160, 210, 180, 25);

                panel.add(zipField);

                JLabel paymentLabel = new JLabel("Payment:");

                paymentLabel.setBounds(40, 250, 100, 25);

                panel.add(paymentLabel);

                JComboBox<String> paymentBox = new JComboBox<>();

                paymentBox.addItem("Cash on Delivery");

                paymentBox.addItem("Credit Card");

                paymentBox.addItem("EasyPaisa");

                paymentBox.setBounds(160, 250, 180, 25);

                panel.add(paymentBox);

                    
                JLabel extraLabel =new JLabel();

                extraLabel.setBounds(40, 290, 100, 25);

                panel.add(extraLabel);

                JTextField extraField =new JTextField();

                extraField.setBounds(160, 290, 180, 25);

                panel.add(extraField);

                JLabel cvvLabel = new JLabel("CVV:");

                cvvLabel.setBounds(40, 320, 100, 25);

                panel.add(cvvLabel);

                JTextField cvvField = new JTextField();

                cvvField.setBounds(160, 320, 180, 25);

                panel.add(cvvField);

        // ================= HIDE FIELDS INITIALLY =================

                extraLabel.setVisible(false);

                extraField.setVisible(false);

                cvvLabel.setVisible(false);

                cvvField.setVisible(false);

        // ================= PAYMENT TYPE CHANGE =================

            paymentBox.addActionListener(e -> {

                String method = (String) paymentBox.getSelectedItem();

                if (method.equals("Credit Card")) {

                    extraLabel.setText("Card Number:");

                    extraLabel.setVisible(true);

                    extraField.setVisible(true);

                    cvvLabel.setVisible(true);

                    cvvField.setVisible(true);
                }

                else if (method.equals("EasyPaisa")) {

                    extraLabel.setText("Phone Number:");

                    extraLabel.setVisible(true);

                    extraField.setVisible(true);

                    cvvLabel.setVisible(false);

                    cvvField.setVisible(false);
                }

                else {

                    extraLabel.setVisible(false);

                    extraField.setVisible(false);

                    cvvLabel.setVisible(false);

                    cvvField.setVisible(false);
                }
            });

        JButton placeButton = new JButton("Place Order");

        placeButton.setBounds(120, 360, 150, 30);

        panel.add(placeButton);

        placeButton.addActionListener(e -> {

        String zipCode = zipField.getText().trim();

        // ZIP CODE VALIDATION
        if (!zipCode.matches("\\d{5}")) {
            JOptionPane.showMessageDialog(dialog, "Zip Code must contain exactly 5 digits");
            return;
        }
            
        Address address = new Address(streetField.getText(), cityField.getText(), provinceField.getText(), countryField.getText(), zipCode, "Home");

        customer.addAddress(address);

        double amount = customer.getCart().getTotal();

        int paymentId = customer.getOrders().size() + 1;

        Payment payment;

        String method = (String) paymentBox.getSelectedItem();

        if (method.equals("Credit Card")) {

            payment =new CreditCardPayment(paymentId,amount,extraField.getText(),cvvField.getText());
        
        } else if (method.equals("EasyPaisa")) {

            if (amount > 50000) {
                JOptionPane.showMessageDialog(dialog, "EasyPaisa transaction cannot exceed Rs. 50,000" );
                return;
            }

            payment =new EasyPaisaPayment(paymentId,amount,extraField.getText());

        } else {
            payment =new CashOnDelivery(paymentId,amount,true);
        }

        Order order =customer.placeOrder(address,payment);

        if (order != null) {

            String receipt = "========== ORDER RECEIPT ==========\n\n";

            receipt += "Customer: " + customer.getName() + "\n";

            receipt += "Order ID: " + order.getOrderId() + "\n\n";

            receipt += "Items:\n";

                for (CartItem item : order.getItems()) {

                    receipt += item.getProduct().getName()
                            + " x "
                            + item.getQuantity()
                            + " = Rs. "
                            + item.getSubtotal()
                            + "\n";
                }

            receipt += "\n----------------------------\n";

            receipt += "Total Bill: Rs. " + order.getTotal() + "\n\n";

            receipt += "Address:\n" + address.getFullAddress() + "\n\n";

            receipt += "Payment Method: " + method+ "\n";
 
            receipt += "Order Status: Confirmed";

            JOptionPane.showMessageDialog(dialog,receipt,"Order Receipt", JOptionPane.INFORMATION_MESSAGE);
            
            dialog.dispose();

            updateTotal();

            showProducts("All");

            Main.saveAllData();
        }
        });

        dialog.setVisible(true);
    }

    // ================= UPDATE TOTAL =================

    private void updateTotal() {

        totalLabel.setText("Cart Total: Rs. "  + customer.getCart().getTotal());
    }

    // ================= LOGOUT =================

    private void logout() {

        Main.saveAllData();

        Main.logout();

        new LoginFrame().setVisible(true);

        dispose();
    }
     
}