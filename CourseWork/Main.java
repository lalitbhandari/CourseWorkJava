package CourseWork;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("AI Module Management System");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Title Panel ──────────────────────────────────────────
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 30, 60));
        JLabel titleLabel = new JLabel("AI Module Management System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // ── Tabbed Pane ───────────────────────────────────────────
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Base Module",    createBaseModulePanel());
        tabbedPane.addTab("Personal Plan",  createPersonalPlanPanel());
        tabbedPane.addTab("Pro Plan",       createProPlanPanel());
        add(tabbedPane, BorderLayout.CENTER);

        // ── Footer ────────────────────────────────────────────────
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(30, 30, 60));
        JLabel footerLabel = new JLabel("AI Module System © 2026");
        footerLabel.setForeground(Color.LIGHT_GRAY);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ── Tab 1: Base Module ────────────────────────────────────────
    private JPanel createBaseModulePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addField(panel, gbc, 0, "Model Name:",     new JTextField(20));
        addField(panel, gbc, 1, "Price (NPR):",    new JTextField(20));
        addField(panel, gbc, 2, "Parameters (B):", new JTextField(20));
        addField(panel, gbc, 3, "Context Window:", new JTextField(20));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveBtn = createButton("Save", new Color(70, 130, 180));
        panel.add(saveBtn, gbc);

        return panel;
    }

    // ── Tab 2: Personal Plan ──────────────────────────────────────
    private JPanel createPersonalPlanPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addField(panel, gbc, 0, "Model Name:",        new JTextField(20));
        addField(panel, gbc, 1, "Price (NPR):",       new JTextField(20));
        addField(panel, gbc, 2, "Parameters (B):",    new JTextField(20));
        addField(panel, gbc, 3, "Context Window:",    new JTextField(20));
        addField(panel, gbc, 4, "Remaining Prompts:", new JTextField(20));
        addField(panel, gbc, 5, "Enter Prompt:",      new JTextField(20));
        addField(panel, gbc, 6, "Token Length:",      new JTextField(20));
        addField(panel, gbc, 7, "Purchase Prompts:",  new JTextField(20));

        // Buttons row
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(createButton("Submit Prompt",    new Color(60, 160, 100)));
        btnPanel.add(createButton("Purchase Prompts", new Color(70, 130, 180)));
        btnPanel.add(createButton("Save",             new Color(100, 100, 100)));

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        return panel;
    }

    // ── Tab 3: Pro Plan ───────────────────────────────────────────
    private JPanel createProPlanPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addField(panel, gbc, 0, "Model Name:",       new JTextField(20));
        addField(panel, gbc, 1, "Price (NPR):",      new JTextField(20));
        addField(panel, gbc, 2, "Parameters (B):",   new JTextField(20));
        addField(panel, gbc, 3, "Context Window:",   new JTextField(20));
        addField(panel, gbc, 4, "Available Slots:",  new JTextField(20));
        addField(panel, gbc, 5, "Member Name:",      new JTextField(20));

        // Buttons row
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(createButton("Add Member",    new Color(60, 160, 100)));
        btnPanel.add(createButton("Remove Member", new Color(180, 80, 80)));
        btnPanel.add(createButton("Save",          new Color(100, 100, 100)));

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        return panel;
    }

    // ── Helper: adds a label + field row ─────────────────────────
    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(field, gbc);
    }

    // ── Helper: styled button ─────────────────────────────────────
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
    }

    public static void main(String[] args) {
        new Main();
    }
}