
 


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;



public class AISubscriptionManagementSystem extends JFrame {

    //COLOUR PALETTE 
    private static final Color CLR_NAV_BG = new Color(15, 21, 46);
    private static final Color CLR_NAV_HOVER = new Color(35, 47, 88);
    private static final Color CLR_NAV_SELECTED = new Color(67, 97, 238);
    private static final Color CLR_HDR_LEFT = new Color(67, 97, 238);
    private static final Color CLR_HDR_RIGHT = new Color(114, 9, 183);
    private static final Color CLR_CONTENT_BG = new Color(238, 240, 250);
    private static final Color CLR_CARD = Color.WHITE;
    private static final Color CLR_BORDER = new Color(210, 215, 235);
    private static final Color CLR_PRIMARY = new Color(67, 97, 238);
    private static final Color CLR_SUCCESS = new Color(6, 214, 160);
    private static final Color CLR_DANGER = new Color(239, 71, 111);
    private static final Color CLR_WARNING = new Color(255, 158, 64);
    private static final Color CLR_INFO = new Color(76, 201, 240);
    private static final Color CLR_TBL_HEADER = new Color(67, 97, 238);
    private static final Color CLR_TBL_ROW_ALT = new Color(244, 246, 255);
    private static final Color CLR_TXT_DARK = new Color(22, 30, 60);
    private static final Color CLR_TXT_MUTED = new Color(110, 120, 160);
    private static final Color CLR_ACCENT_CYAN = new Color(76, 201, 240);

    // ==================== FONTS ====================
    private static final Font FNT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FNT_HEADING = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FNT_SUBHEAD = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FNT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FNT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FNT_MONO = new Font("Consolas", Font.PLAIN, 13);
    private static final Font FNT_BTN = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FNT_NAV = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FNT_STAT = new Font("Segoe UI", Font.BOLD, 28);

    // ==================== UI COMPONENTS ====================
    private CardLayout cardLayout;
    private JPanel contentArea;
    private String currentPage = "Home";
    private final ArrayList<JButton> navButtons = new ArrayList<>();

    // Header Buttons
    private JButton btnHdrHome, btnHdrSave, btnHdrReload, btnHdrExit;

    // Home Page
    private JTable homeTable;
    private JComboBox<String> homeFilterCombo;
    private JTextField homeSearchField;

    // Add Plan Page
    private JComboBox<String> addTypeCombo;
    private JTextField addModelField, addPriceField, addParamField, addContextField, addTokensField;
    private JTextField addDailyLimitField, addSupportField, addSlotsField;
    private JCheckBox addApiCheckBox;
    private JPanel extraFieldsPanel;

    // Personal, Pro, Team Pages
    private JTable personalTable, proTable, teamTable;

    // Prompt Page
    private JTextField promptIdField, promptTokenField;
    private JTextArea promptInputArea, promptOutputArea;

    // Team Page
    private JTextField teamMemberNameField;
    private JTextArea teamMembersDisplay;

    // Reports Page
    private JTextArea reportArea;
    private JComboBox<String> reportTypeCombo;

    // ===================================================================
    // CONSTRUCTOR
    // ===================================================================
    public AISubscriptionManagementSystem() {
        setTitle("AI Subscription Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 780);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(buildContent(), BorderLayout.CENTER);

        showPage("Home");
    }

    // ==================== HEADER ====================
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, CLR_HDR_LEFT, getWidth(), 0, CLR_HDR_RIGHT);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 66));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 16));

        // Left Title
        JPanel titleSide = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titleSide.setOpaque(false);
        JLabel iconLbl = new JLabel("◈");
        iconLbl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        iconLbl.setForeground(CLR_ACCENT_CYAN);
        JLabel titleLbl = new JLabel("AI Subscription Management System");
        titleLbl.setFont(FNT_TITLE);
        titleLbl.setForeground(Color.WHITE);
        titleSide.add(iconLbl);
        titleSide.add(titleLbl);

        // Right Buttons
        JPanel btnSide = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnSide.setOpaque(false);

        btnHdrHome = makeHeaderBtn("⌂ Home", new Color(255, 255, 255, 45));
        btnHdrSave = makeHeaderBtn("💾 Save", new Color(255, 255, 255, 45));
        btnHdrReload = makeHeaderBtn("⟳ Reload", new Color(255, 255, 255, 45));
        btnHdrExit = makeHeaderBtn("✕ Exit", new Color(239, 71, 111, 200));

        btnSide.add(btnHdrHome);
        btnSide.add(btnHdrSave);
        btnSide.add(btnHdrReload);
        btnSide.add(btnHdrExit);

        header.add(titleSide, BorderLayout.WEST);
        header.add(btnSide, BorderLayout.EAST);
        return header;
    }

    private JButton makeHeaderBtn(String text, Color baseBg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(FNT_BTN);
        b.setForeground(Color.WHITE);
        b.setBackground(baseBg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Color hoverBg = new Color(255, 255, 255, 80);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(hoverBg); b.repaint(); }
            public void mouseExited(MouseEvent e) { b.setBackground(baseBg); b.repaint(); }
        });
        return b;
    }

    // ==================== SIDEBAR ====================
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(CLR_NAV_BG);
        sidebar.setPreferredSize(new Dimension(218, 0));

        // Brand
        JPanel brandBlock = new JPanel(new BorderLayout(10, 0));
        brandBlock.setBackground(new Color(10, 14, 36));
        brandBlock.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        JLabel brandIcon = new JLabel("🤖");
        brandIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        JLabel brandText = new JLabel("<html><b style='font-size:12px'>AI SubManager</b><br><span style='color:#6878a8;font-size:10px'>v1.0.0</span></html>");
        brandText.setForeground(new Color(200, 210, 235));
        brandText.setFont(FNT_BODY);
        brandBlock.add(brandIcon, BorderLayout.WEST);
        brandBlock.add(brandText, BorderLayout.CENTER);
        sidebar.add(brandBlock);

        // Navigation Label
        JLabel menuLabel = new JLabel(" NAVIGATION");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        menuLabel.setForeground(new Color(80, 100, 150));
        menuLabel.setBorder(BorderFactory.createEmptyBorder(16, 12, 6, 0));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(menuLabel);

        // Nav Items
        String[][] navItems = {
            { "Home", "Home", "🏠" },
            { "Add Plan", "AddPlan", "➕" },
            { "Personal Plans", "Personal", "👤" },
            { "Pro Plans", "Pro", "⭐" },
            { "Prompt Operations", "Prompt", "💬" },
            { "Team Operations", "Team", "👥" },
            { "Reports", "Reports", "📊" },
            { "About", "About", "ℹ" }
        };

        for (String[] item : navItems) {
            JButton btn = makeNavBtn(item[0], item[1], item[2]);
            navButtons.add(btn);
            sidebar.add(btn);
        }

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel(" © 2025 AI SubManager");
        footer.setFont(FNT_SMALL);
        footer.setForeground(new Color(60, 80, 130));
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(footer);

        return sidebar;
    }

    private JButton makeNavBtn(String label, String page, String icon) {
        JButton b = new JButton(icon + " " + label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (page.equals(currentPage)) {
                    g2.setColor(CLR_NAV_SELECTED);
                    g2.fillRoundRect(8, 3, getWidth() - 16, getHeight() - 6, 10, 10);
                    g2.setColor(CLR_ACCENT_CYAN);
                    g2.fillRoundRect(2, 10, 4, getHeight() - 20, 4, 4);
                } else if (getModel().isRollover()) {
                    g2.setColor(CLR_NAV_HOVER);
                    g2.fillRoundRect(8, 3, getWidth() - 16, getHeight() - 6, 10, 10);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(FNT_NAV);
        b.setForeground(new Color(200, 210, 235));
        b.setBackground(CLR_NAV_BG);
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(BorderFactory.createEmptyBorder(13, 22, 13, 12));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        String finalPage = page;
        b.addActionListener(e -> showPage(finalPage));

        return b;
    }

    // ==================== CONTENT AREA ====================
    private JPanel buildContent() {
        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(CLR_CONTENT_BG);

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
        currentPage = page;
        cardLayout.show(contentArea, page);
        navButtons.forEach(JComponent::repaint);
        refreshCurrentPage();
    }

    private void refreshCurrentPage() {
        switch (currentPage) {
            case "Home": refreshHomeTable(); break;
            case "Personal": refreshPersonalTable(); break;
            case "Pro": refreshProTable(); break;
            case "Team": refreshTeamTable(); break;
        }
    }

    // ==================== HOME PAGE ====================
    private JPanel buildHomePage() {
        JPanel p = makePage();
        p.add(makePageHeader("🏠 Home Dashboard", "Overview of all AI subscription plans"), BorderLayout.NORTH);

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 14, 0));
        statsRow.setOpaque(false);
        statsRow.add(makeStatCard("Total Plans", "6", CLR_PRIMARY));
        statsRow.add(makeStatCard("Personal Plans", "2", CLR_SUCCESS));
        statsRow.add(makeStatCard("Pro Plans", "2", CLR_INFO));
        statsRow.add(makeStatCard("Team Plans", "1", CLR_WARNING));

        // Toolbar
        JPanel toolLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        toolLeft.setOpaque(false);
        homeFilterCombo = new JComboBox<>(new String[]{"All Plans", "Personal Plan", "Pro Plan", "Team Plan"});
        styleCombo(homeFilterCombo);
        homeSearchField = makeField("🔍 Search by model or type…");
        homeSearchField.setPreferredSize(new Dimension(220, 34));

        toolLeft.add(new JLabel("Filter: ")); toolLeft.add(homeFilterCombo);
        toolLeft.add(homeSearchField);

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(12, 0, 8, 0));
        toolbar.add(toolLeft, BorderLayout.WEST);

        String[] homeCols = {"ID", "Plan Type", "Model Name", "Price (NPR)", "Parameters", "Context Window", "Avail. Tokens", "Team Slots", "Members"};
        homeTable = makeTable(homeCols);

        JPanel center = new JPanel(new BorderLayout(0, 0));
        center.setOpaque(false);
        center.add(statsRow, BorderLayout.NORTH);
        center.add(toolbar, BorderLayout.CENTER);
        center.add(makeScrollPane(homeTable), BorderLayout.SOUTH);

        p.add(center, BorderLayout.CENTER);
        return p;
    }

    private void refreshHomeTable() {
        DefaultTableModel m = (DefaultTableModel) homeTable.getModel();
        m.setRowCount(0);
        // Static sample data (no real logic)
        m.addRow(new Object[]{1, "Personal Plan", "GPT-4o", "2999.00", "175B", "128,000", "50,000", "—", "—"});
        m.addRow(new Object[]{2, "Personal Plan", "Gemini-2", "1999.00", "70B", "32,000", "30,000", "—", "—"});
        m.addRow(new Object[]{3, "Pro Plan", "Claude-3", "6999.00", "200B", "200,000", "150,000", "—", "—"});
        m.addRow(new Object[]{4, "Pro Plan", "GPT-4o", "5999.00", "175B", "128,000", "100,000", "—", "—"});
        m.addRow(new Object[]{5, "Team Plan", "Claude-3", "19999.00", "200B", "200,000", "500,000", "20", "0"});
    }

    // ==================== ADD PLAN PAGE ====================
    private JPanel buildAddPlanPage() {
        JPanel p = makePage();
        p.add(makePageHeader("➕ Add New Plan", "Create a new AI subscription plan"), BorderLayout.NORTH);

        JPanel card = makeCard();
        card.setLayout(new BorderLayout(0, 16));

        JPanel formGrid = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        addTypeCombo = new JComboBox<>(new String[]{"Personal Plan", "Pro Plan", "Team Plan"});
        styleCombo(addTypeCombo);
        addTypeCombo.addActionListener(e -> updateExtraFields());

        addModelField = makeField("e.g. GPT-4o, Claude-3");
        addPriceField = makeField("e.g. 2999.00");
        addParamField = makeField("e.g. 175 (billions)");
        addContextField = makeField("e.g. 128000");
        addTokensField = makeField("e.g. 50000");

        int row = 0;
        formRow(formGrid, gbc, row++, "Plan Type:", addTypeCombo);
        formRow(formGrid, gbc, row++, "Model Name:", addModelField);
        formRow(formGrid, gbc, row++, "Price (NPR):", addPriceField);
        formRow(formGrid, gbc, row++, "Parameter Count:", addParamField);
        formRow(formGrid, gbc, row++, "Context Window:", addContextField);
        formRow(formGrid, gbc, row++, "Available Tokens:", addTokensField);

        extraFieldsPanel = new JPanel(new GridBagLayout());
        extraFieldsPanel.setOpaque(false);
        updateExtraFields();

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAddClear = makeBtn("✕ Clear Form", new Color(140, 140, 160));
        JButton btnAddSubmit = makeBtn("✓ Add Plan", CLR_SUCCESS);
        btnBar.add(btnAddClear);
        btnBar.add(btnAddSubmit);

        JPanel bottom = new JPanel(new BorderLayout(0, 12));
        bottom.setOpaque(false);
        bottom.add(extraFieldsPanel, BorderLayout.CENTER);
        bottom.add(btnBar, BorderLayout.SOUTH);

        card.add(new JLabel("Plan Details") {{ setFont(FNT_HEADING); setForeground(CLR_TXT_DARK); }}, BorderLayout.NORTH);
        card.add(formGrid, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        p.add(new JScrollPane(card), BorderLayout.CENTER);
        return p;
    }

    private void updateExtraFields() {
        extraFieldsPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 8, 6, 8);

        String type = (String) addTypeCombo.getSelectedItem();
        if ("Personal Plan".equals(type)) {
            addDailyLimitField = makeField("e.g. 100");
            formRow(extraFieldsPanel, gbc, 0, "Daily Prompt Limit:", addDailyLimitField);
        } else if ("Pro Plan".equals(type)) {
            addSupportField = makeField("e.g. 24/7 Priority");
            addApiCheckBox = new JCheckBox(" API Access Included");
            addApiCheckBox.setFont(FNT_BODY);
            addApiCheckBox.setOpaque(false);
            formRow(extraFieldsPanel, gbc, 0, "Support Level:", addSupportField);
            gbc.gridy = 1; gbc.gridx = 1;
            extraFieldsPanel.add(addApiCheckBox, gbc);
        } else if ("Team Plan".equals(type)) {
            addSlotsField = makeField("e.g. 20");
            formRow(extraFieldsPanel, gbc, 0, "Team Slots:", addSlotsField);
        }
        extraFieldsPanel.revalidate();
        extraFieldsPanel.repaint();
    }

    // ==================== OTHER PAGES (Static) ====================
    private JPanel buildPersonalPage() {
        JPanel p = makePage();
        p.add(makePageHeader("👤 Personal Plans", "Manage individual user subscriptions"), BorderLayout.NORTH);

        String[] cols = {"ID", "Model Name", "Price (NPR)", "Parameters", "Context Window", "Avail. Tokens", "Daily Limit"};
        personalTable = makeTable(cols);
        refreshPersonalTable();

        p.add(makeScrollPane(personalTable), BorderLayout.CENTER);
        return p;
    }

    private void refreshPersonalTable() {
        DefaultTableModel m = (DefaultTableModel) personalTable.getModel();
        m.setRowCount(0);
        m.addRow(new Object[]{1, "GPT-4o", "2999.00", "175B", "128,000", "50,000", "100"});
        m.addRow(new Object[]{2, "Gemini-2", "1999.00", "70B", "32,000", "30,000", "60"});
    }

    private JPanel buildProPage() {
        JPanel p = makePage();
        p.add(makePageHeader("⭐ Pro Plans", "Manage professional-tier subscriptions"), BorderLayout.NORTH);

        String[] cols = {"ID", "Model Name", "Price (NPR)", "Parameters", "Context Window", "Avail. Tokens", "API Access", "Support Level"};
        proTable = makeTable(cols);
        refreshProTable();

        p.add(makeScrollPane(proTable), BorderLayout.CENTER);
        return p;
    }

    private void refreshProTable() {
        DefaultTableModel m = (DefaultTableModel) proTable.getModel();
        m.setRowCount(0);
        m.addRow(new Object[]{3, "Claude-3", "6999.00", "200B", "200,000", "150,000", "✓ Yes", "24/7 Priority"});
        m.addRow(new Object[]{4, "GPT-4o", "5999.00", "175B", "128,000", "100,000", "✓ Yes", "Priority"});
    }

    private JPanel buildPromptPage() {
        JPanel p = makePage();
        p.add(makePageHeader("💬 Prompt Operations", "Simulate sending prompts to a subscription plan"), BorderLayout.NORTH);

        promptIdField = makeField("Enter plan ID");
        promptTokenField = makeField("Expected output token count");
        promptInputArea = new JTextArea(8, 40);
        promptOutputArea = new JTextArea(8, 40);
        promptOutputArea.setEditable(false);
        promptOutputArea.setBackground(new Color(248, 250, 255));

        JPanel fields = new JPanel(new GridLayout(2, 2, 10, 10));
        fields.add(new JLabel("Plan ID:")); fields.add(promptIdField);
        fields.add(new JLabel("Expected Tokens:")); fields.add(promptTokenField);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(promptInputArea), new JScrollPane(promptOutputArea));
        split.setResizeWeight(0.5);

        JButton runBtn = makeBtn("▶ Run Prompt", CLR_SUCCESS);
        JButton clearBtn = makeBtn("✕ Clear", new Color(140, 140, 160));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(clearBtn);
        bottom.add(runBtn);

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.add(fields, BorderLayout.NORTH);
        center.add(split, BorderLayout.CENTER);
        center.add(bottom, BorderLayout.SOUTH);

        p.add(center, BorderLayout.CENTER);
        return p;
    }

    private JPanel buildTeamPage() {
        JPanel p = makePage();
        p.add(makePageHeader("👥 Team Operations", "Manage team subscriptions and member access"), BorderLayout.NORTH);

        String[] cols = {"ID", "Model Name", "Price (NPR)", "Team Slots", "Members Used"};
        teamTable = makeTable(cols);
        refreshTeamTable();

        teamMemberNameField = makeField("Member name…");
        teamMembersDisplay = new JTextArea();
        teamMembersDisplay.setEditable(false);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                makeScrollPane(teamTable),
                new JPanel(new BorderLayout()) {{
                    add(new JLabel("Add Team Member"), BorderLayout.NORTH);
                    add(teamMemberNameField, BorderLayout.CENTER);
                    add(new JScrollPane(teamMembersDisplay), BorderLayout.SOUTH);
                    add(makeBtn("➕ Add Member", CLR_SUCCESS), BorderLayout.SOUTH);
                }});

        p.add(split, BorderLayout.CENTER);
        return p;
    }

    private void refreshTeamTable() {
        DefaultTableModel m = (DefaultTableModel) teamTable.getModel();
        m.setRowCount(0);
        m.addRow(new Object[]{5, "Claude-3", "19999.00", "20", "0"});
    }

    private JPanel buildReportsPage() {
        JPanel p = makePage();
        p.add(makePageHeader("📊 Reports", "Generate analytics summaries"), BorderLayout.NORTH);

        reportTypeCombo = new JComboBox<>(new String[]{"Full Summary", "Personal Plans Only", "Pro Plans Only", "Team Plans Only"});
        styleCombo(reportTypeCombo);

        reportArea = new JTextArea("Click 'Generate Report' to view subscription analytics.");
        reportArea.setEditable(false);
        reportArea.setFont(FNT_MONO);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Report Type: "));
        top.add(reportTypeCombo);
        top.add(makeBtn("📊 Generate Report", CLR_PRIMARY));

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildAboutPage() {
        JPanel p = makePage();
        p.add(makePageHeader("ℹ About", "Application information and class hierarchy"), BorderLayout.NORTH);

        JPanel card = makeCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("🤖 AI Subscription Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(CLR_PRIMARY);
        card.add(title);

        card.add(new JLabel("Version: 1.0.0 (GUI Only Version)"));
        card.add(new JLabel("Architecture: OOP with Inheritance"));
        card.add(new JLabel(" "));
        card.add(new JLabel("Class Hierarchy:"));
        card.add(new JLabel(" AIModule (abstract)"));
        card.add(new JLabel(" ├── PersonalPlan"));
        card.add(new JLabel(" ├── ProPlan"));
        card.add(new JLabel(" └── TeamPlan"));

        p.add(new JScrollPane(card), BorderLayout.CENTER);
        return p;
    }

    // ==================== HELPER METHODS ====================
    private JPanel makePage() {
        JPanel p = new JPanel(new BorderLayout(0, 16));
        p.setBackground(CLR_CONTENT_BG);
        p.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        return p;
    }

    private JPanel makeCard() {
        JPanel card = new JPanel();
        card.setBackground(CLR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER, 1),
            BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        return card;
    }

    private JPanel makePageHeader(String title, String subtitle) {
        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JPanel bar = new JPanel();
        bar.setBackground(CLR_PRIMARY);
        bar.setPreferredSize(new Dimension(5, 44));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(FNT_TITLE);
        titleLbl.setForeground(CLR_TXT_DARK);
        JLabel subLbl = new JLabel(subtitle);
        subLbl.setFont(FNT_BODY);
        subLbl.setForeground(CLR_TXT_MUTED);

        JPanel textStack = new JPanel(new BorderLayout(0, 2));
        textStack.setOpaque(false);
        textStack.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        textStack.add(titleLbl, BorderLayout.NORTH);
        textStack.add(subLbl, BorderLayout.SOUTH);

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.add(bar, BorderLayout.WEST);
        left.add(textStack, BorderLayout.CENTER);

        header.add(left, BorderLayout.CENTER);
        header.add(new JSeparator(), BorderLayout.SOUTH);
        return header;
    }

    private JPanel makeStatCard(String label, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 6)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(accent);
                g.fillRect(0, 0, getWidth(), 5);
            }
        };
        card.setBackground(CLR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)));

        JLabel valLbl = new JLabel(value);
        valLbl.setFont(FNT_STAT);
        valLbl.setForeground(accent);
        JLabel lblLbl = new JLabel(label);
        lblLbl.setFont(FNT_SMALL);
        lblLbl.setForeground(CLR_TXT_MUTED);

        card.add(valLbl, BorderLayout.CENTER);
        card.add(lblLbl, BorderLayout.SOUTH);
        return card;
    }

    private JButton makeBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(FNT_BTN);
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JTable makeTable(String[] cols) {
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(FNT_BODY);
        table.setGridColor(CLR_BORDER);
        table.setShowGrid(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(CLR_TBL_HEADER);
        header.setForeground(Color.WHITE);

        return table;
    }

    private JScrollPane makeScrollPane(JComponent comp) {
        JScrollPane sp = new JScrollPane(comp);
        sp.setBorder(BorderFactory.createLineBorder(CLR_BORDER));
        sp.getViewport().setBackground(CLR_CARD);
        return sp;
    }

    private JTextField makeField(String placeholder) {
        JTextField f = new JTextField();
        f.setFont(FNT_BODY);
        f.setForeground(CLR_TXT_DARK);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER),
            BorderFactory.createEmptyBorder(7, 9, 7, 9)));
        f.setPreferredSize(new Dimension(220, 34));
        return f;
    }

    private void styleCombo(JComboBox<?> c) {
        c.setFont(FNT_BODY);
        c.setBackground(CLR_CARD);
        c.setForeground(CLR_TXT_DARK);
        c.setPreferredSize(new Dimension(170, 34));
    }

    private void formRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.28;
        panel.add(new JLabel(labelText) {{ setFont(FNT_BODY); setForeground(CLR_TXT_DARK); }}, gbc);
        gbc.gridx = 1; gbc.weightx = 0.72;
        panel.add(field, gbc);
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new AISubscriptionManagementSystem().setVisible(true);
        });
    }
}
