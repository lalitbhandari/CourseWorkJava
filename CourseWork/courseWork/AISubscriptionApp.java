package CourseWork.courseWork;




import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// ─────────────────────────────────────────────────────────────
//  GUI MAIN CLASS: AISubscriptionApp
//  extends JFrame          → is itself a window
//  implements ActionListener → one method handles all buttons
// ─────────────────────────────────────────────────────────────
public class AISubscriptionApp extends JFrame implements ActionListener {

    // ── Sample plan objects (polymorphic parent type) ────────
    private final PersonalPlan personal =
            new PersonalPlan("GPT-4o", 2999.0, 175, "128K", 50);

    private final ProPlan pro =
            new ProPlan("Claude-3", 6999.0, 200, "200K", 5);

    // ── Colours & fonts ──────────────────────────────────────
    private static final Color BLUE    = new Color(67, 97, 238);
    private static final Color GREEN   = new Color(6, 214, 160);
    private static final Color RED     = new Color(239, 71, 111);
    private static final Color BG      = new Color(238, 240, 250);
    private static final Color WHITE   = Color.WHITE;
    private static final Font  BOLD14  = new Font("Segoe UI", Font.BOLD,  14);
    private static final Font  PLAIN13 = new Font("Segoe UI", Font.PLAIN, 13);

    // ── Personal Plan tab widgets ────────────────────────────
    private JLabel    lblPersonalInfo;
    private JTextField fldPromptText, fldTokenLen, fldBuyQty;
    private JTextArea  areaPersonalLog;
    private JButton    btnRunPrompt, btnBuyPrompts;
    private JButton btnBuyTokensPersonal;
    private JButton btnBuyTokens;
    

    // ── Pro Plan tab widgets ─────────────────────────────────
    private JLabel    lblProInfo;
    private JTextField fldMemberName;
    private JTextArea  areaProLog;
    private JButton    btnAddMember, btnRemoveMember;

    // ─────────────────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────────────────
    public AISubscriptionApp() {
        setTitle("AI Subscription Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 550);
        setMinimumSize(new Dimension(600, 480));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        add(buildHeader(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(BOLD14);
        tabs.addTab("👤  Personal Plan", buildPersonalTab());
        tabs.addTab("⭐  Pro Plan",       buildProTab());
        add(tabs, BorderLayout.CENTER);

        add(buildFooter(), BorderLayout.SOUTH);
    }

    // ─────────────────────────────────────────────────────────
    //  HEADER — gradient blue-purple bar
    // ─────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel h = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ((Graphics2D) g).setPaint(new GradientPaint(
                        0, 0, BLUE, getWidth(), 0, new Color(114, 9, 183)));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        h.setPreferredSize(new Dimension(0, 58));
        h.setLayout(new FlowLayout(FlowLayout.LEFT, 18, 12));

        JLabel icon  = new JLabel("🤖");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        JLabel title = new JLabel("AI Subscription Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(WHITE);

        h.add(icon);  h.add(title);
        return h;
    }

    // ─────────────────────────────────────────────────────────
    //  FOOTER
    // ─────────────────────────────────────────────────────────
    private JPanel buildFooter() {
        JPanel f = new JPanel(new FlowLayout(FlowLayout.CENTER));
        f.setBackground(new Color(210, 215, 235));
        JLabel lbl = new JLabel(
            "OOP CourseWork  ·  PersonalPlan & ProPlan extend abstract AiModule");
        lbl.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lbl.setForeground(new Color(100, 110, 150));
        f.add(lbl);
        return f;
    }

    // ─────────────────────────────────────────────────────────
    //  PERSONAL PLAN TAB
    // ─────────────────────────────────────────────────────────
    private JPanel buildPersonalTab() {
        JPanel tab = new JPanel(new BorderLayout(12, 12));
        tab.setBackground(BG);
        tab.setBorder(new EmptyBorder(16, 16, 16, 16));

        lblPersonalInfo = infoLabel(personal);
        tab.add(lblPersonalInfo, BorderLayout.NORTH);

        JPanel controls = card();
        controls.setLayout(new GridBagLayout());
        GridBagConstraints g = gbc();

        // Section: Run a Prompt
        g.gridx=0; g.gridy=0; g.gridwidth=3;
        controls.add(sectionLabel("Run a Prompt"), g);

        g.gridwidth=1; g.gridy=1;
        g.gridx=0; g.weightx=0.25; controls.add(label("Prompt text:"),     g);
        fldPromptText = field("Type your prompt here…");
        g.gridx=1; g.weightx=0.75; g.gridwidth=2; controls.add(fldPromptText, g);

        g.gridwidth=1; g.gridy=2;
        g.gridx=0; g.weightx=0.25; controls.add(label("Expected tokens:"), g);
        fldTokenLen = field("e.g. 200");
        g.gridx=1; g.weightx=0.40; controls.add(fldTokenLen, g);
        btnRunPrompt = btn("▶  Run Prompt", GREEN);
        btnRunPrompt.addActionListener(this);
        g.gridx=2; g.weightx=0.35; controls.add(btnRunPrompt, g);

        g.gridx=0; g.gridy=3; g.gridwidth=3; g.weightx=1.0;
        controls.add(new JSeparator(), g);

        // Section: Buy Prompts
        g.gridy=4;
        controls.add(sectionLabel("Buy More Prompts"), g);

        g.gridwidth=1; g.gridy=5;
        g.gridx=0; g.weightx=0.25; controls.add(label("Quantity to add:"), g);
        fldBuyQty = field("e.g. 20");
        g.gridx=1; g.weightx=0.40; controls.add(fldBuyQty, g);
        btnBuyPrompts = btn("➕  Purchase", BLUE);
        btnBuyPrompts.addActionListener(this);
        g.gridx=2; g.weightx=0.35; controls.add(btnBuyPrompts, g);

        tab.add(controls, BorderLayout.CENTER);

        areaPersonalLog = logArea();
        tab.add(scrollLog(areaPersonalLog), BorderLayout.SOUTH);

        return tab;
    }

    // ─────────────────────────────────────────────────────────
    //  PRO PLAN TAB
    // ─────────────────────────────────────────────────────────
    private JPanel buildProTab() {
        JPanel tab = new JPanel(new BorderLayout(12, 12));
        tab.setBackground(BG);
        tab.setBorder(new EmptyBorder(16, 16, 16, 16));

        lblProInfo = infoLabel(pro);
        tab.add(lblProInfo, BorderLayout.NORTH);

        JPanel controls = card();
        controls.setLayout(new GridBagLayout());
        GridBagConstraints g = gbc();

        g.gridx=0; g.gridy=0; g.gridwidth=3;
        controls.add(sectionLabel("Manage Team Members"), g);

        g.gridwidth=1; g.gridy=1;
        g.gridx=0; g.weightx=0.25; controls.add(label("Member name:"), g);
        fldMemberName = field("e.g. Alice");
        g.gridx=1; g.weightx=0.40; controls.add(fldMemberName, g);

        JPanel btnBox = new JPanel(new GridLayout(1, 2, 8, 0));
        btnBox.setOpaque(false);
        btnAddMember    = btn("➕  Add Member",    GREEN);
        btnRemoveMember = btn("✕  Remove Member", RED);
        btnAddMember.addActionListener(this);
        btnRemoveMember.addActionListener(this);
        btnBox.add(btnAddMember);
        btnBox.add(btnRemoveMember);
        g.gridx=2; g.weightx=0.35; controls.add(btnBox, g);

        tab.add(controls, BorderLayout.CENTER);

        areaProLog = logArea();
        tab.add(scrollLog(areaProLog), BorderLayout.SOUTH);

        return tab;
    }

    // ─────────────────────────────────────────────────────────
    //  ACTION LISTENER — handles all button clicks
    // ─────────────────────────────────────────────────────────
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnRunPrompt) {
            String text = fldPromptText.getText().trim();
            String tokS = fldTokenLen.getText().trim();
            if (text.isEmpty() || tokS.isEmpty()) {
                warn("Fill in both Prompt text and Expected tokens."); return;
            }
            try {
                log(areaPersonalLog, personal.enterPrompt(text, Integer.parseInt(tokS)));
                lblPersonalInfo.setText(infoText(personal));
            } catch (NumberFormatException ex) { warn("Expected tokens must be a number."); }

        } else if (src == btnBuyPrompts) {
            String qtyS = fldBuyQty.getText().trim();
            if (qtyS.isEmpty()) { warn("Enter a quantity to purchase."); return; }
            try {
                log(areaPersonalLog, personal.purchasePrompts(Integer.parseInt(qtyS)));
                lblPersonalInfo.setText(infoText(personal));
            } catch (NumberFormatException ex) { warn("Quantity must be a number."); }

        } else if (src == btnAddMember) {
            String name = fldMemberName.getText().trim();
            if (name.isEmpty()) { warn("Enter a member name."); return; }
            log(areaProLog, pro.addTeamMember(name));
            lblProInfo.setText(infoText(pro));
            fldMemberName.setText("");

        } else if (src == btnRemoveMember) {
            String name = fldMemberName.getText().trim();
            if (name.isEmpty()) { warn("Enter the member name to remove."); return; }
            log(areaProLog, pro.removeTeamMember(name));
            lblProInfo.setText(infoText(pro));
            fldMemberName.setText("");
        }
    }

    // ─────────────────────────────────────────────────────────
    //  HELPERS
    // ─────────────────────────────────────────────────────────

    private String infoText(AiModule m) {
        String extra = "";
        if (m instanceof PersonalPlan)
            extra = " &nbsp;| <b>Remaining Prompts:</b> "
                  + ((PersonalPlan) m).getRemainingPrompts();
        else if (m instanceof ProPlan)
            extra = " &nbsp;| <b>Available Slots:</b> "
                  + ((ProPlan) m).getAvailableSlots();
        return "<html><b>Model:</b> " + m.getModelName()
             + " &nbsp;| <b>Price:</b> NPR " + m.getPrice()
             + " &nbsp;| <b>Params:</b> "    + m.getParameterCount() + "B"
             + " &nbsp;| <b>Context:</b> "   + m.getContextWindow()
             + extra + "</html>";
    }

    private JLabel infoLabel(AiModule m) {
        JLabel l = new JLabel(infoText(m));
        l.setOpaque(true); l.setBackground(WHITE); l.setFont(PLAIN13);
        l.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 210, 235)),
            new EmptyBorder(10, 14, 10, 14)));
        return l;
    }

    private JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(WHITE);
        p.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 210, 235)),
            new EmptyBorder(14, 16, 14, 16)));
        return p;
    }

    private GridBagConstraints gbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 6, 5, 6);
        return g;
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(BOLD14); l.setForeground(BLUE);
        l.setBorder(new EmptyBorder(4, 0, 2, 0));
        return l;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text); l.setFont(PLAIN13); return l;
    }

    private JTextField field(String hint) {
        JTextField f = new JTextField();
        f.setFont(PLAIN13);
        f.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 210, 235)),
            new EmptyBorder(6, 8, 6, 8)));
        return f;
    }

    private JButton btn(String text, Color bg) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(BOLD14); b.setForeground(WHITE); b.setBackground(bg);
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setContentAreaFilled(false); b.setOpaque(false);
        b.setBorder(new EmptyBorder(8, 14, 8, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Color darker = bg.darker();
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(darker); b.repaint(); }
            public void mouseExited (MouseEvent e) { b.setBackground(bg);     b.repaint(); }
        });
        return b;
    }

    private JTextArea logArea() {
        JTextArea a = new JTextArea();
        a.setEditable(false);
        a.setFont(new Font("Consolas", Font.PLAIN, 12));
        a.setBackground(new Color(248, 250, 255));
        a.setBorder(new EmptyBorder(6, 8, 6, 8));
        return a;
    }

    private JScrollPane scrollLog(JTextArea area) {
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(0, 120));
        sp.setBorder(new TitledBorder(
            new LineBorder(new Color(200, 210, 235)), "  Activity Log  ",
            TitledBorder.LEFT, TitledBorder.TOP, PLAIN13, BLUE));
        return sp;
    }

    private void log(JTextArea area, String msg) {
        area.append("──────────────────────────\n" + msg + "\n");
        area.setCaretPosition(area.getDocument().getLength());
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "⚠ Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    // ─────────────────────────────────────────────────────────
    //  MAIN — entry point
    // ─────────────────────────────────────────────────────────
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new AISubscriptionApp().setVisible(true));
    }
}
