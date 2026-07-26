package CourseWork.courseWork.course;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AISubscriptionGUI extends JFrame {

    //  Model objects
    private final PersonalPlan personal = new PersonalPlan("GPT-4o", 2999.0, 175, "128K", 200);
    private final ProPlan      pro      = new ProPlan("Claude-3", 6999.0, 200, "200K", 500000);

    //  Colours 
    private static final Color backgroundColor = new Color(240, 242, 250);
    private static final Color primaryBlue     = new Color(67,  97, 238);
    private static final Color successGreen    = new Color(6,  214, 160);
    private static final Color dangerRed       = new Color(239, 71, 111);
    private static final Color goldAccent      = new Color(230, 170,  20);
    private static final Color cardWhite       = Color.WHITE;
    private static final Color mutedText       = new Color(110, 120, 160);
    private static final Color purpleAccent    = new Color(114,   9, 183);

    // Fonts
    private static final Font titleFont  = new Font("Segoe UI", Font.BOLD,  15);
    private static final Font bodyFont   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font buttonFont = new Font("Segoe UI", Font.BOLD,  12);
    private static final Font monoFont   = new Font("Consolas",  Font.PLAIN, 12);

    //  Widgets
    private JTabbedPane tabs;

    // Personal tab
    private JLabel    lblTokens;
    private JTextArea txtPersonalIn, txtPersonalOut;

    // Pro tab
    private JLabel                   lblProInfo;
    private JLabel                   lblSlots;
    private DefaultListModel<String> memberModel;
    private JList<String>            memberList;
    private JTextField               fldMember;
    private JTextArea                txtProIn, txtProOut;

    // Constructor
    public AISubscriptionGUI() {
        try {
            setTitle("AI Subscription Manager");
            setSize(820, 560);
            setMinimumSize(new Dimension(700, 480));
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            getContentPane().setBackground(backgroundColor);
            setLayout(new BorderLayout(0, 0));

            add(buildHeader(), BorderLayout.NORTH);

            tabs = new JTabbedPane();
            tabs.setFont(bodyFont);
            tabs.setBackground(backgroundColor);
            tabs.addTab("Home",     buildHomeTab());
            tabs.addTab("Personal", buildPersonalTab());
            tabs.addTab("Pro Plan", buildProTab());
            add(tabs, BorderLayout.CENTER);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Failed to build UI:\n" + ex.getMessage(),
                "Startup Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Header 
    private JPanel buildHeader() {
        JPanel h = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, primaryBlue,getWidth(), 0, purpleAccent);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
};
        h.setPreferredSize(new Dimension(0, 52));
        h.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 14));

        JLabel title = new JLabel("  AI Subscription Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 17));
        title.setForeground(Color.WHITE);
        h.add(title, BorderLayout.WEST);

        JButton btnExit = plainBtn("Exit", dangerRed);
        btnExit.addActionListener(e -> System.exit(0));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 10));
        right.setOpaque(false);
        right.add(btnExit);
        h.add(right, BorderLayout.EAST);
        return h;
    }

    // Home Tab 
    private JPanel buildHomeTab() {
        JPanel p = padded(new BorderLayout(0, 16));

        try {
            JLabel heading = new JLabel("Overview");
            heading.setFont(titleFont);
            heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
            p.add(heading, BorderLayout.NORTH);

            JPanel cards = new JPanel(new GridLayout(1, 2, 18, 0));
            cards.setOpaque(false);

            // Personal card
            JPanel persCard = card("Personal Plan — " + personal.getModelName(), primaryBlue);
            persCard.add(label("Price: NPR " + (int) personal.getPrice() + "/month", mutedText));
            persCard.add(Box.createVerticalStrut(4));
            persCard.add(label("Context: " + personal.getContextWindow()
                             + "  |  Params: " + personal.getParameterCount() + "B", mutedText));
            persCard.add(Box.createVerticalStrut(8));
            persCard.add(label("Tokens are deducted per prompt word.", mutedText));
            persCard.add(Box.createVerticalStrut(10));
            JButton goPersonal = plainBtn("Open Personal Plan ->", primaryBlue);
            goPersonal.addActionListener(e -> tabs.setSelectedIndex(1));
            persCard.add(goPersonal);
            cards.add(persCard);

            // Pro card
            JPanel proCard = card("Pro Plan — " + pro.getModelName(), goldAccent);
            proCard.add(label("Price: NPR " + (int) pro.getPrice() + "/month", mutedText));
            proCard.add(Box.createVerticalStrut(4));
            proCard.add(label("Context: " + pro.getContextWindow()
                            + "  |  Params: " + pro.getParameterCount() + "B", mutedText));
            proCard.add(Box.createVerticalStrut(8));
            proCard.add(label("Unlimited tokens · Up to " + ProPlan.MAX_GROUP_SIZE + " members.", mutedText));
            proCard.add(Box.createVerticalStrut(10));
            JButton goPro = plainBtn("Open Pro Plan ->", goldAccent);
            goPro.addActionListener(e -> tabs.setSelectedIndex(2));
            proCard.add(goPro);
            cards.add(proCard);

            p.add(cards, BorderLayout.CENTER);

            JLabel foot = new JLabel("Select a tab above or click a button to get started.");
            foot.setFont(bodyFont);
            foot.setForeground(mutedText);
            foot.setHorizontalAlignment(SwingConstants.CENTER);
            p.add(foot, BorderLayout.SOUTH);

        } catch (Exception ex) {
            p.add(new JLabel("Error loading Home tab: " + ex.getMessage()), BorderLayout.CENTER);
            ex.printStackTrace();
        }
        return p;
    }

    //  Personal Tab 
    private JPanel buildPersonalTab() {
        JPanel p = padded(new BorderLayout(0, 10));

        try {
            JPanel top = new JPanel(new BorderLayout(10, 0));
            top.setOpaque(false);

            lblTokens = new JLabel(tokenText());
            lblTokens.setFont(titleFont);
            lblTokens.setForeground(primaryBlue);
            top.add(lblTokens, BorderLayout.WEST);

            JButton btnBuy = plainBtn("Buy Tokens", successGreen);
            btnBuy.addActionListener(e -> doBuyTokens());
            top.add(btnBuy, BorderLayout.EAST);
            p.add(top, BorderLayout.NORTH);

            txtPersonalIn = new JTextArea(5, 0);
            txtPersonalIn.setFont(monoFont);
            txtPersonalIn.setLineWrap(true);
            txtPersonalIn.setWrapStyleWord(true);
            JScrollPane inSp = scroll(txtPersonalIn, "Your Prompt  (1 word = 1 token)");

            txtPersonalOut = new JTextArea(5, 0);
            txtPersonalOut.setFont(monoFont);
            txtPersonalOut.setEditable(false);
            txtPersonalOut.setBackground(new Color(248, 250, 255));
            txtPersonalOut.setLineWrap(true);
            txtPersonalOut.setWrapStyleWord(true);
            JScrollPane outSp = scroll(txtPersonalOut, "AI Response (Simulated)");

            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inSp, outSp);
            split.setResizeWeight(0.5);
            split.setBorder(null);
            split.setDividerSize(5);
            p.add(split, BorderLayout.CENTER);

            JButton btnSubmit = plainBtn("Submit Prompt", successGreen);
            JButton btnClear  = plainBtn("Clear", mutedText);
            btnSubmit.addActionListener(e -> doPersSubmit());
            btnClear.addActionListener(e -> {
                txtPersonalIn.setText("");
                txtPersonalOut.setText("");
            });

            JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            btnRow.setOpaque(false);
            btnRow.add(btnClear);
            btnRow.add(btnSubmit);
            p.add(btnRow, BorderLayout.SOUTH);

        } catch (Exception ex) {
            p.add(new JLabel("Error loading Personal tab: " + ex.getMessage()), BorderLayout.CENTER);
            ex.printStackTrace();
        }
        return p;
    }

    //  Pro Tab 
    private JPanel buildProTab() {
        JPanel p = padded(new BorderLayout(0, 10));

        try {
            // Top bar with info + Buy Pro Plan button
            JPanel proTop = new JPanel(new BorderLayout(10, 0));
            proTop.setOpaque(false);

            lblProInfo = new JLabel(proInfoText());
            lblProInfo.setFont(bodyFont);
            lblProInfo.setForeground(new Color(0, 140, 90));
            proTop.add(lblProInfo, BorderLayout.WEST);

            JButton btnBuyPro = plainBtn("Buy Pro Plan", purpleAccent);
            btnBuyPro.addActionListener(e -> doBuyProPlan());
            proTop.add(btnBuyPro, BorderLayout.EAST);
            p.add(proTop, BorderLayout.NORTH);

            // Prompt area
            txtProIn = new JTextArea(4, 0);
            txtProIn.setFont(monoFont);
            txtProIn.setLineWrap(true);
            txtProIn.setWrapStyleWord(true);
            JScrollPane proInSp = scroll(txtProIn, "Prompt (free — unlimited tokens)");

            txtProOut = new JTextArea(4, 0);
            txtProOut.setFont(monoFont);
            txtProOut.setEditable(false);
            txtProOut.setBackground(new Color(248, 252, 248));
            txtProOut.setLineWrap(true);
            txtProOut.setWrapStyleWord(true);
            JScrollPane proOutSp = scroll(txtProOut, "AI Response (Simulated)");

            JSplitPane promptSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, proInSp, proOutSp);
            promptSplit.setResizeWeight(0.5);
            promptSplit.setBorder(null);
            promptSplit.setDividerSize(4);

            JButton btnRun   = plainBtn("Run Prompt (Free)", goldAccent);
            JButton btnClear = plainBtn("Clear", mutedText);
            btnRun.addActionListener(e -> doProSubmit());
            btnClear.addActionListener(e -> {
                txtProIn.setText("");
                txtProOut.setText("");
            });

            JPanel promptBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
            promptBtns.setOpaque(false);
            promptBtns.add(btnClear);
            promptBtns.add(btnRun);

            JPanel promptPanel = new JPanel(new BorderLayout(0, 4));
            promptPanel.setOpaque(false);
            promptPanel.add(promptSplit, BorderLayout.CENTER);
            promptPanel.add(promptBtns, BorderLayout.SOUTH);

            // Members panel
            memberModel = new DefaultListModel<>();
            memberList  = new JList<>(memberModel);
            memberList.setFont(bodyFont);
            memberList.setFixedCellHeight(28);
            memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            lblSlots = new JLabel(slotText());
            lblSlots.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblSlots.setForeground(primaryBlue);

            fldMember = new JTextField();
            fldMember.setFont(bodyFont);
            fldMember.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JButton btnAdd    = plainBtn("Add",    successGreen);
            JButton btnRemove = plainBtn("Remove", dangerRed);
            btnAdd.addActionListener(e -> doAddMember());
            btnRemove.addActionListener(e -> doRemoveMember());

            JPanel memberBtns = new JPanel(new GridLayout(1, 2, 6, 0));
            memberBtns.setOpaque(false);
            memberBtns.add(btnAdd);
            memberBtns.add(btnRemove);

            JPanel mHead = new JPanel(new BorderLayout());
            mHead.setOpaque(false);
            JLabel mTitle = new JLabel("Group Members");
            mTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
            mHead.add(mTitle,    BorderLayout.WEST);
            mHead.add(lblSlots,  BorderLayout.EAST);

            JPanel mBot = new JPanel(new BorderLayout(0, 4));
            mBot.setOpaque(false);
            mBot.add(fldMember,  BorderLayout.NORTH);
            mBot.add(memberBtns, BorderLayout.SOUTH);

            JPanel memberPanel = new JPanel(new BorderLayout(0, 6));
            memberPanel.setBackground(cardWhite);
            memberPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 235)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            memberPanel.add(mHead,                      BorderLayout.NORTH);
            memberPanel.add(new JScrollPane(memberList), BorderLayout.CENTER);
            memberPanel.add(mBot,                        BorderLayout.SOUTH);
            memberPanel.setPreferredSize(new Dimension(240, 0));

            JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, promptPanel, memberPanel);
            main.setResizeWeight(0.65);
            main.setBorder(null);
            main.setDividerSize(5);
            p.add(main, BorderLayout.CENTER);

        } catch (Exception ex) {
            p.add(new JLabel("Error loading Pro tab: " + ex.getMessage()), BorderLayout.CENTER);
            ex.printStackTrace();
        }
        return p;
    }

    // Actions 

    private void doPersSubmit() {
        try {
            String text = txtPersonalIn.getText().trim();
            if (text.isEmpty()) { warn("Type a prompt first."); return; }

            String result = personal.enterPrompt(text);  // PersonalPlan.enterPrompt()
            txtPersonalOut.setText(result);

            lblTokens.setText(tokenText());
            lblTokens.setForeground(personal.getAvailableTokens() < 20 ? dangerRed : primaryBlue);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error submitting prompt:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void doProSubmit() {
        try {
            String text = txtProIn.getText().trim();
            if (text.isEmpty()) { warn("Type a prompt first."); return; }

            int words = text.split("\\s+").length;
            txtProOut.setText(
                "[ Pro Plan — " + pro.getModelName() + " | Simulated Response ]\n\n"
              + "Token cost : " + (words * 4) + " estimated — NOT deducted (Pro = unlimited)\n"
              + "Members    : " + pro.getMemberCount() + " / " + ProPlan.MAX_GROUP_SIZE + "\n\n"
              + pro.getPlanDescription() + "\n\n"
              + "This is a simulated AI response.\n"
              + "Pro subscribers enjoy unlimited token usage — no caps, ever.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error submitting Pro prompt:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void doBuyTokens() {
        try {
            String[] options = {
                "Starter   —  10,000 tokens  (NPR    499)",
                "Standard  —  50,000 tokens  (NPR  1,999)",
                "Premium   — 200,000 tokens  (NPR  6,999)",
                "Mega      — 500,000 tokens  (NPR 14,999)"
            };
            int[] amounts = { 10_000, 50_000, 200_000, 500_000 };

            int choice = JOptionPane.showOptionDialog(this,
                "Select a token pack:", "Buy Tokens — Personal Plan",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
            if (choice < 0) return;

            personal.buyTokens(amounts[choice]);   // AiModule.buyTokens()
            lblTokens.setText(tokenText());
            lblTokens.setForeground(primaryBlue);

            JOptionPane.showMessageDialog(this,
                String.format("%,d tokens added!\nNew balance: %,d",
                    amounts[choice], personal.getAvailableTokens()),
                "Tokens Added", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error buying tokens:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void doBuyProPlan() {
        try {
            String[] modelNames    = { "Claude-3", "GPT-4o", "Gemini-Ultra", "Llama-3-Pro" };
            double[] modelPrices   = { 6999,        7499,     8999,           4999          };
            int[]    modelParams   = { 200,          175,      540,            70            };
            String[] modelContexts = { "200K",      "128K",   "100K",         "64K"         };

            String[] options = {
                "Claude-3       — 200B params · 200K context  (NPR  6,999/mo)",
                "GPT-4o         — 175B params · 128K context  (NPR  7,499/mo)",
                "Gemini-Ultra   — 540B params · 100K context  (NPR  8,999/mo)",
                "Llama-3-Pro    —  70B params ·  64K context  (NPR  4,999/mo)"
            };

            int choice = JOptionPane.showOptionDialog(this,
                "Select a Pro Plan model to activate:",
                "Buy / Upgrade Pro Plan",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
            if (choice < 0) return;

            // AiModule setters update the ProPlan object
            pro.setModelName(modelNames[choice]);
            pro.setPrice(modelPrices[choice]);
            pro.setParameterCount(modelParams[choice]);
            pro.setContextWindow(modelContexts[choice]);

            lblProInfo.setText(proInfoText());

            JOptionPane.showMessageDialog(this,
                "Pro Plan activated!\n\n"
              + "Model   : " + pro.getModelName()        + "\n"
              + "Price   : NPR " + (int) pro.getPrice()  + "/month\n"
              + "Context : " + pro.getContextWindow()    + "\n"
              + "Params  : " + pro.getParameterCount()   + "B\n"
              + "Tokens  : Unlimited",
                "Pro Plan Updated", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error buying Pro Plan:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void doAddMember() {
        try {
            String name   = fldMember.getText().trim();
            String result = pro.addMember(name);   // ProPlan.addMember()

            if (pro.getGroupMembers().contains(name)) {
                memberModel.addElement(name);
                lblSlots.setText(slotText());
                fldMember.setText("");
            } else {
                warn(result);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error adding member:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void doRemoveMember() {
        try {
            int idx = memberList.getSelectedIndex();
            if (idx < 0) { warn("Select a member to remove."); return; }

            String name = memberModel.getElementAt(idx);
            int ok = JOptionPane.showConfirmDialog(this,
                "Remove \"" + name + "\" from the Pro Plan?",
                "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok != JOptionPane.YES_OPTION) return;

            String result = pro.removeMember(name);   // ProPlan.removeMember()
            if (!pro.getGroupMembers().contains(name)) {
                memberModel.remove(idx);
                lblSlots.setText(slotText());
            } else {
                warn(result);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error removing member:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Helpers 

    private String proInfoText() {
        return "Active: " + pro.getModelName()
             + "  |  NPR " + (int) pro.getPrice() + "/month"
             + "  |  Unlimited tokens";
    }

    private String tokenText() {
        int t = personal.getAvailableTokens();
        return "Tokens: " + String.format("%,d", t) + (t < 20 ? "  Warning: Low!" : "");
    }

    private String slotText() {
        return pro.getMemberCount() + " / " + ProPlan.MAX_GROUP_SIZE + " members";
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    // UI Factories 

    private JPanel padded(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        return p;
    }

    private JPanel card(String title, Color accent) {
        JPanel c = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                try {
                    super.paintComponent(g);
                    g.setColor(accent);
                    g.fillRect(0, 0, getWidth(), 4);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setBackground(cardWhite);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 215, 235)),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        JLabel t = new JLabel(title);
        t.setFont(titleFont);
        t.setForeground(accent);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        c.add(t);
        c.add(Box.createVerticalStrut(6));
        return c;
    }

    private JLabel label(String text, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(bodyFont);
        l.setForeground(color);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JScrollPane scroll(JComponent c, String title) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(210, 215, 235)),
            "  " + title + "  ",
            TitledBorder.LEFT, TitledBorder.TOP, buttonFont, primaryBlue));
        return sp;
    }

    private JButton plainBtn(String text, Color bg) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                try {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                    super.paintComponent(g);
                } catch (Exception ex) {
                    super.paintComponent(g);
                }
            }
        };
        b.setFont(buttonFont);
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Color darker = bg.darker();
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(darker); b.repaint(); }
            public void mouseExited (MouseEvent e) { b.setBackground(bg);     b.repaint(); }
        });
        return b;
    }

    // Main 
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Could not set system look and feel: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(() -> {
            try {
                new AISubscriptionGUI().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                    "Failed to launch application:\n" + ex.getMessage(),
                    "Launch Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}
