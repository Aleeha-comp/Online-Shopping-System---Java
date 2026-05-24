package onlineshoppingsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/*
 * CustomerDashboard — shown after a customer logs in.
 *
 * Viva points:
 * - JTabbedPane: groups related screens into tabs (Browse, Cart, Orders)
 * - JTable + DefaultTableModel: displays data in rows and columns
 * - JScrollPane: adds scrollbar to JTable when rows overflow
 * - Calls existing business-logic methods (no logic duplicated here)
 *   e.g. cart.addItem(), customer.placeOrder()
 */
public class CustomerDashboard extends JFrame {

    private Customer customer;   // the logged-in customer

    // ── Browse tab components ──────────────────────────────────────
    private JTable       productTable;
    private DefaultTableModel productModel;

    // ── Cart tab components ────────────────────────────────────────
    private JTable       cartTable;
    private DefaultTableModel cartModel;
    private JLabel       totalLabel;

    // ── Orders tab components ──────────────────────────────────────
    private JTable       orderTable;
    private DefaultTableModel orderModel;

    // ── Constructor ────────────────────────────────────────────────
    public CustomerDashboard() {
        customer = Main.getCurrentCustomer();

        setTitle("Customer Dashboard – " + customer.getName());
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ── Top bar ────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(70, 130, 180));
        topBar.setPreferredSize(new Dimension(750, 45));

        JLabel welcomeLabel = new JLabel("  Welcome, " + customer.getName() + "  |  Role: Customer");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 13));
        topBar.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> logout());
        topBar.add(logoutBtn, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // ── Tabbed pane ────────────────────────────────────────────
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("🛍  Browse Products", buildBrowseTab());
        tabs.addTab("🛒  My Cart",          buildCartTab());
        tabs.addTab("📦  My Orders",        buildOrdersTab());

        add(tabs, BorderLayout.CENTER);

        // Load products into the browse table when the window opens
        loadProducts();
    }

    // ══════════════════════════════════════════════════════════════
    //  TAB 1 – Browse Products
    // ══════════════════════════════════════════════════════════════
    private JPanel buildBrowseTab() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table columns
        String[] columns = {"Product ID", "Name", "Shop", "Price (Rs.)", "Discount %", "Stock"};
        productModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; } // read-only
        };
        productTable = new JTable(productModel);
        productTable.setRowHeight(24);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(productTable);
        panel.add(scroll, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        qtySpinner.setPreferredSize(new Dimension(60, 28));

        JButton addCartBtn = new JButton("Add to Cart");
        addCartBtn.setBackground(new Color(70, 130, 180));
        addCartBtn.setForeground(Color.WHITE);
        addCartBtn.setFocusPainted(false);

        bottom.add(new JLabel("Qty:"));
        bottom.add(qtySpinner);
        bottom.add(addCartBtn);
        panel.add(bottom, BorderLayout.SOUTH);

        // Add to cart action
        addCartBtn.addActionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product first.");
                return;
            }
            // Get product from the selected row
            int productId = (int) productModel.getValueAt(row, 0);
            Product product = findProductById(productId);
            int qty = (int) qtySpinner.getValue();

            if (product == null) return;

            if (!product.isInStock(qty)) {
                JOptionPane.showMessageDialog(this, "Not enough stock! Available: " + product.getStock());
                return;
            }

            customer.getCart().addItem(product, qty);
            JOptionPane.showMessageDialog(this, product.getName() + " x" + qty + " added to cart!");
            refreshCart();   // update cart tab
        });

        return panel;
    }

    // Load all products from all shops into the table
    private void loadProducts() {
        productModel.setRowCount(0);   // clear old rows
        for (Shop shop : Main.getShops()) {
            for (Product p : shop.getProducts()) {
                productModel.addRow(new Object[]{
                    p.getProductId(),
                    p.getName(),
                    shop.getShopName(),
                    String.format("%.2f", p.getDiscountedPrice()),
                    (int) p.getDiscountPct() + "%",
                    p.getStock()
                });
            }
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  TAB 2 – My Cart
    // ══════════════════════════════════════════════════════════════
    private JPanel buildCartTab() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cart table
        String[] columns = {"Product", "Price (Rs.)", "Qty", "Subtotal (Rs.)"};
        cartModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        cartTable = new JTable(cartModel);
        cartTable.setRowHeight(24);

        panel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Bottom: total + buttons
        JPanel bottom = new JPanel(new BorderLayout());

        totalLabel = new JLabel("  Total: Rs. 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottom.add(totalLabel, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton removeBtn = new JButton("Remove Item");
        removeBtn.setBackground(new Color(220, 53, 69));
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setFocusPainted(false);

        JButton checkoutBtn = new JButton("Checkout →");
        checkoutBtn.setBackground(new Color(40, 167, 69));
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFocusPainted(false);

        btnPanel.add(removeBtn);
        btnPanel.add(checkoutBtn);
        bottom.add(btnPanel, BorderLayout.EAST);
        panel.add(bottom, BorderLayout.SOUTH);

        // Remove item action
        removeBtn.addActionListener(e -> {
            int row = cartTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select an item to remove."); return; }
            String name = (String) cartModel.getValueAt(row, 0);
            Product p = findProductByName(name);
            if (p != null) {
                customer.getCart().removeItem(p);
                refreshCart();
            }
        });
