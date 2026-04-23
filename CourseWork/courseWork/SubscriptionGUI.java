package CourseWork.courseWork;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class SubscriptionGUI extends JFrame {
    private ArrayList<AIModel> plans = new ArrayList<>();
    private CardLayout cardLayout;
    private JPanel contentArea;
    private JTable homeTable, personalTable, proTable;

    // Colors (similar to your screenshot)
    private final Color NAV_BG = new Color(30, 40, 60);
    private final Color NAV_HOVER = new Color(50, 65, 95);
    private final Color HEADER_BG = new Color(25, 50, 110);
    private final Color CONTENT_BG = new Color(245, 245, 250);
    private final Color ACCENT = new Color(40, 80, 160);

    public SubscriptionGUI() {
        setTitle("AI Subscription Management System - Islington College");
        setSize(1150, 700);
        setMinimumSize(new Dimension(950, 580));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(buildContentArea(), BorderLayout.CENTER);

        loadSampleData();
        showPage("Home");
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setPreferredSize(new Dimension(0, 65));
        JLabel title = new JLabel("   AI Subscription Management System");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(NAV_BG);
        sidebar.setPreferredSize(new Dimension(220, 0));

        String[][] menu = {
            {"🏠 Home", "Home"},
            {"➕ Add Plan", "AddPlan"},
            {"👤 Personal Plans", "Personal"},
            {"⭐ Pro Plans", "Pro"},
            {"💬 Prompt", "Prompt"},
            {"👥 Team", "Team"},
            {"📊 Reports", "Reports"},
            {"ℹ About", "About"}
        };

        for (String[] item : menu) {
            JButton btn = createNavButton(item[0], item[1]);
            sidebar.add(btn);
        }
        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JButton createNavButton(String text, String page) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(NAV_BG);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.addActionListener(e -> showPage(page));
        return btn;
    }

    private JPanel buildContentArea() {
        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(CONTENT_BG);

        contentArea.add(buildHomePage(), "Home");
        contentArea.add(buildAddPlanPage(), "AddPlan");
        contentArea.add(buildPersonalPage(), "Personal");
        contentArea.add(buildProPage(), "Pro");
        contentArea.add(buildPromptPage(), "Prompt");
        contentArea.add(buildTeamPage(), "Team");
        contentArea.add(buildReportsPage(), "Reports");
        contentArea.add(buildAboutPage(), "About");

        return contentArea;
    }

    private void showPage(String page) {
        cardLayout.show(contentArea, page);
        refreshTables();
    }

    private void refreshTables() {
        refreshHomeTable();
        refreshPersonalTable();
        refreshProTable();
    }

    // ==================== PAGES ====================

    private JPanel buildHomePage() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(CONTENT_BG);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Home Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        p.add(title, BorderLayout.NORTH);

        homeTable = createTable(new String[]{"ID", "Type", "Model", "Price (NPR)", "Extra Info"});
        p.add(new JScrollPane(homeTable), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildAddPlanPage() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(CONTENT_BG);
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Personal Plan", "Pro Plan"});
        JTextField modelField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField paramField = new JTextField(20);
        JTextField extra1 = new JTextField(20);  // Daily Limit or Support
        JTextField extra2 = new JTextField(20);  // Tokens or Slots

        int row = 0;
        p.add(new JLabel("Plan Type:"), gbc); gbc.gridx=1; p.add(typeCombo, gbc); gbc.gridx=0; row++;
        p.add(new JLabel("Model Name:"), gbc); gbc.gridx=1; p.add(modelField, gbc); gbc.gridx=0; row++;
        p.add(new JLabel("Price (NPR):"), gbc); gbc.gridx=1; p.add(priceField, gbc); gbc.gridx=0; row++;
        p.add(new JLabel("Parameters:"), gbc); gbc.gridx=1; p.add(paramField, gbc); gbc.gridx=0; row++;
        p.add(new JLabel("Extra Field 1:"), gbc); gbc.gridx=1; p.add(extra1, gbc); gbc.gridx=0; row++;
        p.add(new JLabel("Extra Field 2:"), gbc); gbc.gridx=1; p.add(extra2, gbc); gbc.gridx=0; row++;

        JButton addBtn = new JButton("Add Plan");
        addBtn.setBackground(ACCENT);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        addBtn.addActionListener(e -> {
            try {
                String type = (String) typeCombo.getSelectedItem();
                String model = modelField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                String param = paramField.getText().trim();

                if (type.equals("Personal Plan")) {
                    int limit = Integer.parseInt(extra1.getText().trim());
                    int tokens = Integer.parseInt(extra2.getText().trim());
                    plans.add(new PersonalPlan(model, price, param, limit, tokens));
                } else {
                    String support = extra1.getText().trim();
                    int slots = Integer.parseInt(extra2.getText().trim());
                    plans.add(new ProPlan(model, price, param, support, slots));
                }

                JOptionPane.showMessageDialog(this, "Plan Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTables();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 1; gbc.gridy = row; p.add(addBtn, gbc);
        return p;
    }

    private JPanel buildPersonalPage() {
        JPanel p = createPage("Personal Plans");
        personalTable = createTable(new String[]{"ID", "Model", "Price", "Parameters", "Tokens", "Daily Limit"});
        p.add(new JScrollPane(personalTable), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildProPage() {
        JPanel p = createPage("Pro Plans");
        proTable = createTable(new String[]{"ID", "Model", "Price", "Parameters", "Support", "Slots"});
        p.add(new JScrollPane(proTable), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildPromptPage() { /* Simplified */ 
        JPanel p = createPage("Prompt Operations");
        p.add(new JLabel("Prompt feature available (select Personal Plan)", SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildTeamPage() {
        JPanel p = createPage("Team Operations");
        p.add(new JLabel("Team Management for Pro Plans", SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildReportsPage() {
        JPanel p = createPage("Reports");
        p.add(new JLabel("Report Generation Coming Soon...", SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildAboutPage() {
        JPanel p = createPage("About");
        JTextArea ta = new JTextArea("""
                AI Subscription Management System
                Version: 1.0
                Islington College Kathmandu
                Coursework - CS4001NI Programming
                """);
        ta.setEditable(false);
        p.add(new JScrollPane(ta), BorderLayout.CENTER);
        return p;
    }

    private JPanel createPage(String title) {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(CONTENT_BG);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        p.add(lbl, BorderLayout.NORTH);
        return p;
    }

    private JTable createTable(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.getTableHeader().setBackground(new Color(230, 232, 240));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        return table;
    }

    private void loadSampleData() {
        plans.add(new PersonalPlan("GPT-4o", 2999, "175B", 100, 50000));
        plans.add(new ProPlan("Claude-3 Opus", 6999, "200B", "24/7", 15));
    }

    private void refreshHomeTable() { /* Implement similarly as before */ 
        // You can expand this if needed
    }

    private void refreshPersonalTable() {
        // Similar logic as previous version
    }

    private void refreshProTable() {
        // Similar logic
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SubscriptionGUI().setVisible(true));
    }
}