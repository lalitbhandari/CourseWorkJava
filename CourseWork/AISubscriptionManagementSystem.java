package CourseWork;


// ╔══════════════════════════════════════════════════════════════════════════╗
// ║           AI SUBSCRIPTION MANAGEMENT SYSTEM — Full Source                ║
// ║                                                                          ║
// ║  OOP Hierarchy:                                                          ║
// ║    AIModule          ← Abstract Parent Class (base for all plans)        ║
// ║    ├── PersonalPlan  ← Child Class 1 (single-user plans)                 ║
// ║    ├── ProPlan       ← Child Class 2 (professional plans)                ║
// ║    └── TeamPlan      ← Child Class 3 (multi-user team plans)             ║
// ║                                                                          ║
// ║  GUI:  AISubscriptionManagementSystem extends JFrame                     ║
// ║                                          implements ActionListener       ║
// ╚══════════════════════════════════════════════════════════════════════════╝



import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;


// ════════════════════════════════════════════════════════════════════════════
//  ABSTRACT PARENT CLASS: AIModule
//  ▸ Base class for ALL plan types (Personal, Pro, Team)
//  ▸ Holds every field that is COMMON across all subscription plans
//  ▸ Uses ENCAPSULATION: all fields are private, accessed via getters/setters
//  ▸ Declares one abstract method that each child MUST implement
// ════════════════════════════════════════════════════════════════════════════
abstract class AIModule {

    // ── Unique numeric identifier assigned at creation ──────────────────────
    private int id;

    // ── Name/label of the plan tier: "Personal Plan", "Pro Plan", "Team Plan"
    private String planType;

    // ── Name of the underlying AI model (e.g. "GPT-4o", "Claude-3") ─────────
    private String modelName;

    // ── Monthly subscription price in NPR ────────────────────────────────────
    private double price;

    // ── Size of the AI model in billions of parameters (e.g. 175 = 175B) ────
    private int paramCount;

    // ── Maximum tokens the model can hold in a single conversation ───────────
    private int contextWindow;

    // ── Monthly token budget available to the subscriber ─────────────────────
    private int availableTokens;

    // ════════════════════════════════════════════════════════════════════════
    //  CONSTRUCTOR — Initialises every shared field for any plan subtype
    // ════════════════════════════════════════════════════════════════════════
    public AIModule(int id, String planType, String modelName, double price,
                    int paramCount, int contextWindow, int availableTokens) {
        this.id              = id;
        this.planType        = planType;
        this.modelName       = modelName;
        this.price           = price;
        this.paramCount      = paramCount;
        this.contextWindow   = contextWindow;
        this.availableTokens = availableTokens;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  ABSTRACT METHOD — Every subclass MUST override this to describe itself
    // ════════════════════════════════════════════════════════════════════════
    public abstract String getPlanDescription();

    // ════════════════════════════════════════════════════════════════════════
    //  GETTERS — Read-only access to private fields
    // ════════════════════════════════════════════════════════════════════════
    public int    getId()              { return id; }
    public String getPlanType()        { return planType; }
    public String getModelName()       { return modelName; }
    public double getPrice()           { return price; }
    public int    getParamCount()      { return paramCount; }
    public int    getContextWindow()   { return contextWindow; }
    public int    getAvailableTokens() { return availableTokens; }

    // ════════════════════════════════════════════════════════════════════════
    //  SETTERS — Controlled write access (only fields that may be edited)
    // ════════════════════════════════════════════════════════════════════════
    public void setModelName(String modelName)          { this.modelName       = modelName;       }
    public void setPrice(double price)                  { this.price           = price;           }
    public void setAvailableTokens(int availableTokens) { this.availableTokens = availableTokens; }
    public void setPlanType(String planType)            { this.planType        = planType;        }
}


// ════════════════════════════════════════════════════════════════════════════
//  CHILD CLASS 1: PersonalPlan  (extends AIModule)
//  ▸ Designed for a SINGLE user
//  ▸ Adds: dailyPromptLimit — max prompts the user may send per day
// ════════════════════════════════════════════════════════════════════════════
class PersonalPlan extends AIModule {

    // ── Maximum number of prompts this user can run per day ──────────────────
    private int dailyPromptLimit;

    // ════════════════════════════════════════════════════════════════════════
    //  CONSTRUCTOR — Calls super() for common fields, then stores personal field
    // ════════════════════════════════════════════════════════════════════════
    public PersonalPlan(int id, String modelName, double price,
                        int paramCount, int contextWindow,
                        int availableTokens, int dailyPromptLimit) {
        // Pass "Personal Plan" as the planType to the parent constructor
        super(id, "Personal Plan", modelName, price, paramCount, contextWindow, availableTokens);
        this.dailyPromptLimit = dailyPromptLimit;
    }

    // ── Getter and setter for the personal-specific field ────────────────────
    public int  getDailyPromptLimit()          { return dailyPromptLimit; }
    public void setDailyPromptLimit(int limit) { this.dailyPromptLimit = limit; }

    // ════════════════════════════════════════════════════════════════════════
    //  OVERRIDE — Concrete description for a PersonalPlan
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public String getPlanDescription() {
        return "Personal Plan | Model: " + getModelName()
             + " | Tokens: " + getAvailableTokens()
             + " | Daily Limit: " + dailyPromptLimit + " prompts";
    }
}


// ════════════════════════════════════════════════════════════════════════════
//  CHILD CLASS 2: ProPlan  (extends AIModule)
//  ▸ Designed for PROFESSIONAL individual users
//  ▸ Adds: apiAccess (boolean) and supportLevel (priority tier string)
// ════════════════════════════════════════════════════════════════════════════
class ProPlan extends AIModule {

    // ── Whether this plan includes programmatic API access ───────────────────
    private boolean apiAccess;

    // ── Customer support tier: e.g. "Standard", "Priority", "24/7 Priority" ──
    private String  supportLevel;

    // ════════════════════════════════════════════════════════════════════════
    //  CONSTRUCTOR — Calls super() for common fields, stores pro-specific fields
    // ════════════════════════════════════════════════════════════════════════
    public ProPlan(int id, String modelName, double price,
                   int paramCount, int contextWindow, int availableTokens,
                   boolean apiAccess, String supportLevel) {
        // Pass "Pro Plan" as the planType to the parent constructor
        super(id, "Pro Plan", modelName, price, paramCount, contextWindow, availableTokens);
        this.apiAccess    = apiAccess;
        this.supportLevel = supportLevel;
    }

    // ── Getters and setters for Pro-specific fields ───────────────────────────
    public boolean isApiAccess()                        { return apiAccess;    }
    public String  getSupportLevel()                    { return supportLevel; }
    public void    setApiAccess(boolean apiAccess)      { this.apiAccess    = apiAccess;    }
    public void    setSupportLevel(String supportLevel) { this.supportLevel = supportLevel; }

    // ════════════════════════════════════════════════════════════════════════
    //  OVERRIDE — Concrete description for a ProPlan
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public String getPlanDescription() {
        return "Pro Plan | Model: " + getModelName()
             + " | API: " + (apiAccess ? "Yes" : "No")
             + " | Support: " + supportLevel;
    }
}


// ════════════════════════════════════════════════════════════════════════════
//  CHILD CLASS 3: TeamPlan  (extends AIModule)
//  ▸ Designed for MULTIPLE users sharing one subscription
//  ▸ Adds: teamSlots, memberCount, memberNames list
//  ▸ Provides addMember() which enforces the slot limit
// ════════════════════════════════════════════════════════════════════════════
class TeamPlan extends AIModule {

    // ── Total seats available in this team subscription ───────────────────────
    private int teamSlots;

    // ── How many members have been added so far ───────────────────────────────
    private int memberCount;

    // ── Actual names of the team members (grows as members are added) ─────────
    private ArrayList<String> memberNames;

    // ════════════════════════════════════════════════════════════════════════
    //  CONSTRUCTOR — Calls super(), initialises team-specific state
    // ════════════════════════════════════════════════════════════════════════
    public TeamPlan(int id, String modelName, double price,
                    int paramCount, int contextWindow,
                    int availableTokens, int teamSlots) {
        // Pass "Team Plan" as the planType to the parent constructor
        super(id, "Team Plan", modelName, price, paramCount, contextWindow, availableTokens);
        this.teamSlots   = teamSlots;
        this.memberCount = 0;               // Starts with zero members
        this.memberNames = new ArrayList<>();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  TEAM-SPECIFIC METHOD: addMember
    //  ▸ Returns true if member was added successfully
    //  ▸ Returns false if the team is already at capacity
    // ════════════════════════════════════════════════════════════════════════
    public boolean addMember(String name) {
        if (memberCount < teamSlots) {
            memberNames.add(name);
            memberCount++;
            return true;   // Member added
        }
        return false;      // Team is full — caller should show an error
    }

    // ── Getters and setters for team-specific fields ──────────────────────────
    public int               getTeamSlots()            { return teamSlots;   }
    public int               getMemberCount()          { return memberCount; }
    public ArrayList<String> getMemberNames()          { return memberNames; }
    public void              setTeamSlots(int slots)   { this.teamSlots = slots; }

    // ════════════════════════════════════════════════════════════════════════
    //  OVERRIDE — Concrete description for a TeamPlan
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public String getPlanDescription() {
        return "Team Plan | Model: " + getModelName()
             + " | Slots: " + teamSlots
             + " | Members: " + memberCount;
    }
}


// ════════════════════════════════════════════════════════════════════════════
//  MAIN GUI CLASS
//  ▸ extends JFrame            → makes this class a resizable application window
//  ▸ implements ActionListener → one central actionPerformed() handles ALL buttons
// ════════════════════════════════════════════════════════════════════════════
public class AISubscriptionManagementSystem extends JFrame implements ActionListener {

    // ════════════════════════════════════════════════════════════════════════
    //  COLOUR PALETTE — All colours declared as private static constants
    //  Using a deep navy/indigo theme with vivid accent colours
    // ════════════════════════════════════════════════════════════════════════
    private static final Color CLR_NAV_BG        = new Color(15,  21,  46);   // Very dark navy sidebar
    private static final Color CLR_NAV_HOVER     = new Color(35,  47,  88);   // Sidebar hover state
    private static final Color CLR_NAV_SELECTED  = new Color(67,  97,  238);  // Active nav item
    private static final Color CLR_HDR_LEFT      = new Color(67,  97,  238);  // Header gradient start
    private static final Color CLR_HDR_RIGHT     = new Color(114, 9,   183);  // Header gradient end
    private static final Color CLR_CONTENT_BG    = new Color(238, 240, 250);  // Main page background
    private static final Color CLR_CARD          = Color.WHITE;               // White cards/panels
    private static final Color CLR_BORDER        = new Color(210, 215, 235);  // Subtle grey border
    private static final Color CLR_PRIMARY       = new Color(67,  97,  238);  // Primary blue (buttons, accents)
    private static final Color CLR_SUCCESS       = new Color(6,   214, 160);  // Green (add / success)
    private static final Color CLR_DANGER        = new Color(239, 71,  111);  // Red/pink (delete)
    private static final Color CLR_WARNING       = new Color(255, 158, 64);   // Orange (edit / warn)
    private static final Color CLR_INFO          = new Color(76,  201, 240);  // Cyan (info / stat)
    private static final Color CLR_TBL_HEADER    = new Color(67,  97,  238);  // Table header row
    private static final Color CLR_TBL_ROW_ALT   = new Color(244, 246, 255);  // Alternating table row
    private static final Color CLR_TXT_DARK      = new Color(22,  30,  60);   // Dark body text
    private static final Color CLR_TXT_MUTED     = new Color(110, 120, 160);  // Muted / label text
    private static final Color CLR_ACCENT_CYAN   = new Color(76,  201, 240);  // Cyan accent (logo, badges)

    // ════════════════════════════════════════════════════════════════════════
    //  FONTS — All fonts declared as private static constants
    // ════════════════════════════════════════════════════════════════════════
    private static final Font FNT_TITLE   = new Font("Segoe UI", Font.BOLD,  20);
    private static final Font FNT_HEADING = new Font("Segoe UI", Font.BOLD,  16);
    private static final Font FNT_SUBHEAD = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FNT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FNT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FNT_MONO    = new Font("Consolas",  Font.PLAIN, 13);
    private static final Font FNT_BTN     = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FNT_NAV     = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FNT_STAT    = new Font("Segoe UI", Font.BOLD,  28);

    // ════════════════════════════════════════════════════════════════════════
    //  DATA MODEL
    //  ▸ planList holds ALL plans polymorphically using the parent type AIModule
    //  ▸ nextId auto-increments so each plan gets a unique ID
    // ════════════════════════════════════════════════════════════════════════
    private final ArrayList<AIModule> planList = new ArrayList<>(); // Master plan list
    private       int                 nextId   = 1;                 // Auto-increment ID counter

    // ════════════════════════════════════════════════════════════════════════
    //  LAYOUT STATE
    //  ▸ cardLayout   → switches which "page" is visible in contentArea
    //  ▸ contentArea  → the central JPanel holding every page as a card
    //  ▸ currentPage  → tracks which page is currently shown (for nav highlight)
    //  ▸ navButtons   → list of sidebar buttons kept for re-painting on page change
    // ════════════════════════════════════════════════════════════════════════
    private CardLayout          cardLayout;
    private JPanel              contentArea;
    private String              currentPage = "Home";
    private final ArrayList<JButton> navButtons = new ArrayList<>();

    // ════════════════════════════════════════════════════════════════════════
    //  HEADER BUTTONS (top-right of the window)
    // ════════════════════════════════════════════════════════════════════════
    private JButton btnHdrHome;    // Navigate to Home page
    private JButton btnHdrSave;    // Save (simulated)
    private JButton btnHdrReload;  // Reload current page's table data
    private JButton btnHdrExit;    // Terminate the application

    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: HOME DASHBOARD
    // ════════════════════════════════════════════════════════════════════════
    private JTable            homeTable;        // Table showing ALL plans
    private JComboBox<String> homeFilterCombo;  // Dropdown: filter by plan type
    private JTextField        homeSearchField;  // Live-search text box
    private JButton           btnHomeRefresh;   // Reload the home table
    private JButton           btnHomeEdit;      // Open edit dialog for selected row
    private JButton           btnHomeDelete;    // Delete the selected plan

    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: ADD PLAN (the data-entry form)
    // ════════════════════════════════════════════════════════════════════════
    private JComboBox<String> addTypeCombo;       // Plan type selector (Personal/Pro/Team)
    private JTextField        addModelField;      // Model name text input
    private JTextField        addPriceField;      // Price text input
    private JTextField        addParamField;      // Parameter count text input
    private JTextField        addContextField;    // Context window size text input
    private JTextField        addTokensField;     // Available tokens text input
    private JTextField        addDailyLimitField; // PersonalPlan — daily prompt limit
    private JTextField        addSupportField;    // ProPlan      — support level
    private JCheckBox         addApiCheckBox;     // ProPlan      — API access toggle
    private JTextField        addSlotsField;      // TeamPlan     — team slot count
    private JPanel            extraFieldsPanel;   // Dynamic sub-panel (changes with plan type)
    private JButton           btnAddSubmit;       // Submit the new plan form
    private JButton           btnAddClear;        // Reset all form fields

    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: PERSONAL PLANS
    // ════════════════════════════════════════════════════════════════════════
    private JTable  personalTable;      // Table of PersonalPlan rows
    private JButton btnPersonalPrompt;  // Send selected plan to Prompt page
    private JButton btnPersonalEdit;    // Edit selected personal plan
    private JButton btnPersonalDelete;  // Delete selected personal plan

    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: PRO PLANS
    // ════════════════════════════════════════════════════════════════════════
    private JTable  proTable;       // Table of ProPlan rows
    private JButton btnProPrompt;   // Send selected plan to Prompt page
    private JButton btnProEdit;     // Edit selected pro plan
    private JButton btnProDelete;   // Delete selected pro plan

    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: PROMPT OPERATIONS
    // ════════════════════════════════════════════════════════════════════════
    private JTextField promptIdField;      // Plan ID to run the prompt against
    private JTextField promptTokenField;   // Expected output token count (user's estimate)
    private JTextArea  promptInputArea;    // User types their prompt here
    private JTextArea  promptOutputArea;   // Simulated AI response appears here
    private JButton    btnRunPrompt;       // Executes the prompt simulation
    private JButton    btnClearPrompt;     // Clears both text areas and fields

    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: TEAM OPERATIONS
    // ════════════════════════════════════════════════════════════════════════
    private JTable     teamTable;            // Table of TeamPlan rows
    private JTextField teamMemberNameField;  // Name of the member to be added
    private JTextArea  teamMembersDisplay;   // Shows names of all added members
    private JButton    btnAddMember;         // Adds the typed member to selected plan
    private JButton    btnTeamRefresh;       // Refreshes the team table

    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: REPORTS
    // ════════════════════════════════════════════════════════════════════════
    private JTextArea         reportArea;          // Large read-only text area for report output
    private JComboBox<String> reportTypeCombo;     // Selector: which kind of report to generate
    private JButton           btnGenerateReport;   // Triggers report generation


    // ════════════════════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    //  ▸ Seeds sample data using the three subclass constructors
    //  ▸ Configures JFrame properties
    //  ▸ Assembles the full UI (header, sidebar, content area)
    //  ▸ Shows the Home page on launch
    // ════════════════════════════════════════════════════════════════════════
    public AISubscriptionManagementSystem() {

        // ── Seed initial sample data using polymorphism ──────────────────────
        // PersonalPlan(id, modelName, price, paramCount, contextWindow, availableTokens, dailyLimit)
        planList.add(new PersonalPlan(nextId++, "GPT-4o",    2999.0, 175, 128000, 50000, 100));
        planList.add(new PersonalPlan(nextId++, "Gemini-2",  1999.0,  70,  32000, 30000,  60));

        // ProPlan(id, modelName, price, paramCount, contextWindow, availableTokens, apiAccess, supportLevel)
        planList.add(new ProPlan(nextId++, "Claude-3",  6999.0, 200, 200000, 150000, true,  "24/7 Priority"));
        planList.add(new ProPlan(nextId++, "GPT-4o",    5999.0, 175, 128000, 100000, true,  "Priority"));

        // TeamPlan(id, modelName, price, paramCount, contextWindow, availableTokens, teamSlots)
        planList.add(new TeamPlan(nextId++,  "Claude-3", 19999.0, 200, 200000, 500000, 20));

        // ── JFrame base configuration ────────────────────────────────────────
        setTitle("AI Subscription Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 780);
        setMinimumSize(new Dimension(1000, 620));
        setLocationRelativeTo(null);                // Centre on screen
        setLayout(new BorderLayout(0, 0));

        // ── Assemble the three structural areas ──────────────────────────────
        add(buildHeader(),  BorderLayout.NORTH);    // Top gradient bar + buttons
        add(buildSidebar(), BorderLayout.WEST);     // Left navigation menu
        add(buildContent(), BorderLayout.CENTER);   // Main card-panel with pages

        // ── Display the first page ───────────────────────────────────────────
        showPage("Home");
    }


    // ════════════════════════════════════════════════════════════════════════
    //  BUILD HEADER
    //  A gradient bar (blue → purple) across the top.
    //  Left side: app icon + title.
    //  Right side: Home / Save / Reload / Exit buttons.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildHeader() {

        // Custom JPanel that paints a left-to-right gradient background
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Horizontal gradient: CLR_HDR_LEFT → CLR_HDR_RIGHT
                GradientPaint gp = new GradientPaint(
                    0, 0, CLR_HDR_LEFT,
                    getWidth(), 0, CLR_HDR_RIGHT);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 66));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 16));

        // ── Left: decorative icon + application title ────────────────────────
        JPanel titleSide = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titleSide.setOpaque(false);

        JLabel iconLbl = new JLabel("◈");                          // Decorative glyph
        iconLbl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        iconLbl.setForeground(CLR_ACCENT_CYAN);

        JLabel titleLbl = new JLabel("AI Subscription Management System");
        titleLbl.setFont(FNT_TITLE);
        titleLbl.setForeground(Color.WHITE);

        titleSide.add(iconLbl);
        titleSide.add(titleLbl);
        header.add(titleSide, BorderLayout.WEST);

        // ── Right: quick-action header buttons ──────────────────────────────
        JPanel btnSide = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnSide.setOpaque(false);

        // Create buttons and assign them to private fields
        btnHdrHome   = makeHeaderBtn("⌂ Home",    new Color(255, 255, 255, 45));
        btnHdrSave   = makeHeaderBtn("💾 Save",    new Color(255, 255, 255, 45));
        btnHdrReload = makeHeaderBtn("⟳ Reload",  new Color(255, 255, 255, 45));
        btnHdrExit   = makeHeaderBtn("✕ Exit",    new Color(239, 71,  111, 200));

        // Register this class as ActionListener for each header button
        btnHdrHome.addActionListener(this);
        btnHdrSave.addActionListener(this);
        btnHdrReload.addActionListener(this);
        btnHdrExit.addActionListener(this);

        btnSide.add(btnHdrHome);
        btnSide.add(btnHdrSave);
        btnSide.add(btnHdrReload);
        btnSide.add(btnHdrExit);
        header.add(btnSide, BorderLayout.EAST);

        return header;
    }

    // ── Factory: glass-style header button with rounded rect paint ───────────
    private JButton makeHeaderBtn(String text, Color baseBg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);  // Let Swing draw the label
            }
        };
        b.setFont(FNT_BTN);
        b.setForeground(Color.WHITE);
        b.setBackground(baseBg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);   // We paint manually above
        b.setOpaque(false);
        b.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover: lighten the background slightly
        Color hoverBg = new Color(255, 255, 255, 80);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(hoverBg); b.repaint(); }
            public void mouseExited (MouseEvent e) { b.setBackground(baseBg);  b.repaint(); }
        });
        return b;
    }


    // ════════════════════════════════════════════════════════════════════════
    //  BUILD SIDEBAR
    //  Dark navy panel on the left.
    //  Top: brand block.  Middle: nav buttons.  Bottom: version text.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(CLR_NAV_BG);
        sidebar.setPreferredSize(new Dimension(218, 0));   // Fixed width

        // ── Brand / logo block at the top of the sidebar ────────────────────
        JPanel brandBlock = new JPanel(new BorderLayout(10, 0));
        brandBlock.setBackground(new Color(10, 14, 36));
        brandBlock.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JLabel brandIcon = new JLabel("🤖");
        brandIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JLabel brandText = new JLabel("<html><b style='font-size:12px'>AI SubManager</b>"
                                    + "<br><span style='color:#6878a8;font-size:10px'>v1.0.0</span></html>");
        brandText.setForeground(new Color(200, 210, 235));
        brandText.setFont(FNT_BODY);

        brandBlock.add(brandIcon, BorderLayout.WEST);
        brandBlock.add(brandText, BorderLayout.CENTER);
        sidebar.add(brandBlock);

        // ── Section label above the nav items ───────────────────────────────
        JLabel menuLabel = new JLabel("   NAVIGATION");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        menuLabel.setForeground(new Color(80, 100, 150));
        menuLabel.setBorder(BorderFactory.createEmptyBorder(16, 12, 6, 0));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(menuLabel);

        // ── Navigation items: {display label, card name, emoji} ─────────────
        String[][] navItems = {
            { "Home",              "Home",     "🏠" },
            { "Add Plan",          "AddPlan",  "➕" },
            { "Personal Plans",    "Personal", "👤" },
            { "Pro Plans",         "Pro",      "⭐" },
            { "Prompt Operations", "Prompt",   "💬" },
            { "Team Operations",   "Team",     "👥" },
            { "Reports",           "Reports",  "📊" },
            { "About",             "About",    "ℹ"  }
        };

        for (String[] item : navItems) {
            JButton btn = makeNavBtn(item[0], item[1], item[2]);
            navButtons.add(btn);   // Keep reference for highlight updates
            sidebar.add(btn);
        }

        sidebar.add(Box.createVerticalGlue());  // Push content up

        // ── Copyright strip at the very bottom ──────────────────────────────
        JLabel footer = new JLabel("  © 2025 AI SubManager");
        footer.setFont(FNT_SMALL);
        footer.setForeground(new Color(60, 80, 130));
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(footer);

        return sidebar;
    }

    // ── Factory: sidebar navigation button ───────────────────────────────────
    private JButton makeNavBtn(String label, String page, String icon) {
        JButton b = new JButton(icon + "  " + label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (page.equals(currentPage)) {
                    // Selected: filled rounded rect + cyan left accent bar
                    g2.setColor(CLR_NAV_SELECTED);
                    g2.fillRoundRect(8, 3, getWidth() - 16, getHeight() - 6, 10, 10);
                    g2.setColor(CLR_ACCENT_CYAN);
                    g2.fillRoundRect(2, 10, 4, getHeight() - 20, 4, 4);
                } else if (getModel().isRollover()) {
                    // Hover: subtle dark fill
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
        b.addActionListener(e -> showPage(page));
        return b;
    }


    // ════════════════════════════════════════════════════════════════════════
    //  BUILD CONTENT AREA
    //  A CardLayout panel that holds every page as a named "card".
    //  showPage() switches the visible card.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildContent() {
        cardLayout  = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(CLR_CONTENT_BG);

        // Each card name must match the name passed to showPage()
        contentArea.add(buildHomePage(),     "Home");
        contentArea.add(buildAddPlanPage(),  "AddPlan");
        contentArea.add(buildPersonalPage(), "Personal");
        contentArea.add(buildProPage(),      "Pro");
        contentArea.add(buildPromptPage(),   "Prompt");
        contentArea.add(buildTeamPage(),     "Team");
        contentArea.add(buildReportsPage(),  "Reports");
        contentArea.add(buildAboutPage(),    "About");

        return contentArea;
    }

    // ── Switches to a named card and refreshes its data ─────────────────────
    private void showPage(String page) {
        currentPage = page;
        cardLayout.show(contentArea, page);
        navButtons.forEach(JComponent::repaint);  // Update sidebar highlights
        refreshCurrentPage();
    }

    // ── Refreshes the data-bound components on the active page ──────────────
    private void refreshCurrentPage() {
        switch (currentPage) {
            case "Home":     refreshHomeTable();     break;
            case "Personal": refreshPersonalTable(); break;
            case "Pro":      refreshProTable();      break;
            case "Team":     refreshTeamTable();     break;
        }
    }


    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: HOME DASHBOARD
    //  Shows stat cards + a filterable / searchable table of all plans.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildHomePage() {
        JPanel p = makePage();

        // ── Page header strip ────────────────────────────────────────────────
        p.add(makePageHeader("🏠  Home Dashboard",
                             "Overview of all AI subscription plans"), BorderLayout.NORTH);

        // ── Four summary stat cards ──────────────────────────────────────────
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 14, 0));
        statsRow.setOpaque(false);
        statsRow.add(makeStatCard("Total Plans",    String.valueOf(planList.size()), CLR_PRIMARY));
        statsRow.add(makeStatCard("Personal Plans", countType("Personal Plan"),      CLR_SUCCESS));
        statsRow.add(makeStatCard("Pro Plans",      countType("Pro Plan"),           CLR_INFO));
        statsRow.add(makeStatCard("Team Plans",     countType("Team Plan"),          CLR_WARNING));

        // ── Toolbar: filter combo, search field, action buttons ──────────────
        JPanel toolLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        toolLeft.setOpaque(false);

        homeFilterCombo = new JComboBox<>(
            new String[]{"All Plans", "Personal Plan", "Pro Plan", "Team Plan"});
        styleCombo(homeFilterCombo);
        homeFilterCombo.addActionListener(e -> refreshHomeTable()); // Filter on combo change

        homeSearchField = makeField("🔍  Search by model or type…");
        homeSearchField.setPreferredSize(new Dimension(220, 34));
        // Live search: refresh table on every keystroke
        homeSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { refreshHomeTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { refreshHomeTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refreshHomeTable(); }
        });

        toolLeft.add(new JLabel("Filter: ")); toolLeft.add(homeFilterCombo);
        toolLeft.add(homeSearchField);

        JPanel toolRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolRight.setOpaque(false);

        btnHomeRefresh = makeBtn("⟳  Refresh", CLR_PRIMARY);
        btnHomeEdit    = makeBtn("✎  Edit",    CLR_WARNING);
        btnHomeDelete  = makeBtn("🗑  Delete",  CLR_DANGER);

        // Register with ActionListener (handled in actionPerformed)
        btnHomeRefresh.addActionListener(this);
        btnHomeEdit.addActionListener(this);
        btnHomeDelete.addActionListener(this);

        toolRight.add(btnHomeRefresh);
        toolRight.add(btnHomeEdit);
        toolRight.add(btnHomeDelete);

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(12, 0, 8, 0));
        toolbar.add(toolLeft,  BorderLayout.WEST);
        toolbar.add(toolRight, BorderLayout.EAST);

        // ── Table for all plans ──────────────────────────────────────────────
        String[] homeCols = {
            "ID", "Plan Type", "Model Name", "Price (NPR)",
            "Parameters", "Context Window", "Avail. Tokens",
            "Team Slots", "Members"
        };
        homeTable = makeTable(homeCols);

        // ── Assemble center section ──────────────────────────────────────────
        JPanel aboveTable = new JPanel(new BorderLayout(0, 0));
        aboveTable.setOpaque(false);
        aboveTable.add(statsRow, BorderLayout.NORTH);
        aboveTable.add(toolbar,  BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout(0, 0));
        center.setOpaque(false);
        center.add(aboveTable,                   BorderLayout.NORTH);
        center.add(makeScrollPane(homeTable),    BorderLayout.CENTER);
        p.add(center, BorderLayout.CENTER);

        return p;
    }

    // ── Repopulates the home table applying current filter and search text ───
    private void refreshHomeTable() {
        if (homeTable == null) return;
        DefaultTableModel m = (DefaultTableModel) homeTable.getModel();
        m.setRowCount(0);   // Clear all rows

        String filter = (String) homeFilterCombo.getSelectedItem();
        String search = homeSearchField.getText().toLowerCase().trim();

        for (AIModule plan : planList) {
            // Apply plan-type filter drop-down
            if (!"All Plans".equals(filter) && !filter.equals(plan.getPlanType())) continue;
            // Apply live search (matches model name or plan type)
            if (!search.isEmpty()
                && !plan.getModelName().toLowerCase().contains(search)
                && !plan.getPlanType().toLowerCase().contains(search)) continue;

            // Team-specific columns: show value only for TeamPlan, otherwise em-dash
            Object slots   = (plan instanceof TeamPlan) ? ((TeamPlan) plan).getTeamSlots()   : "—";
            Object members = (plan instanceof TeamPlan) ? ((TeamPlan) plan).getMemberCount() : "—";

            m.addRow(new Object[]{
                plan.getId(),
                plan.getPlanType(),
                plan.getModelName(),
                String.format("%.2f", plan.getPrice()),
                plan.getParamCount() + "B",
                String.format("%,d", plan.getContextWindow()),
                String.format("%,d", plan.getAvailableTokens()),
                slots, members
            });
        }
    }


    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: ADD PLAN
    //  A data-entry form.  The "extra fields" panel changes dynamically
    //  based on which plan type the user selects.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildAddPlanPage() {
        JPanel p = makePage();
        p.add(makePageHeader("➕  Add New Plan",
                             "Create a new AI subscription plan"), BorderLayout.NORTH);

        // ── White card containing the entire form ────────────────────────────
        JPanel card = makeCard();
        card.setLayout(new BorderLayout(0, 16));

        JLabel cardTitle = new JLabel("Plan Details");
        cardTitle.setFont(FNT_HEADING);
        cardTitle.setForeground(CLR_TXT_DARK);
        cardTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        // ── Common fields grid ───────────────────────────────────────────────
        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        int row = 0;

        // Plan Type: JComboBox (not a JTextField)
        addTypeCombo = new JComboBox<>(new String[]{"Personal Plan", "Pro Plan", "Team Plan"});
        styleCombo(addTypeCombo);
        addTypeCombo.addActionListener(e -> updateExtraFields()); // Rebuild extras on change
        formRow(formGrid, gbc, row++, "Plan Type:", addTypeCombo);

        // Remaining common fields (all JTextFields)
        addModelField   = makeField("e.g. GPT-4o, Claude-3");
        addPriceField   = makeField("e.g. 2999.00");
        addParamField   = makeField("e.g. 175 (billions)");
        addContextField = makeField("e.g. 128000");
        addTokensField  = makeField("e.g. 50000");

        formRow(formGrid, gbc, row++, "Model Name:",        addModelField);
        formRow(formGrid, gbc, row++, "Price (NPR):",       addPriceField);
        formRow(formGrid, gbc, row++, "Parameter Count:",   addParamField);
        formRow(formGrid, gbc, row++, "Context Window:",    addContextField);
        formRow(formGrid, gbc, row++, "Available Tokens:",  addTokensField);

        // ── Dynamic extra fields (Personal / Pro / Team specific) ────────────
        extraFieldsPanel = new JPanel(new GridBagLayout());
        extraFieldsPanel.setOpaque(false);
        extraFieldsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CLR_BORDER),
            "  Plan-Specific Settings  ",
            TitledBorder.LEFT, TitledBorder.TOP,
            FNT_SUBHEAD, CLR_PRIMARY));

        // Initialise all plan-specific input widgets here (they are reused each time)
        addDailyLimitField  = makeField("e.g. 100");
        addSupportField     = makeField("e.g. 24/7 Priority");
        addApiCheckBox      = new JCheckBox("  API Access Included");
        addApiCheckBox.setFont(FNT_BODY);
        addApiCheckBox.setOpaque(false);
        addApiCheckBox.setForeground(CLR_TXT_DARK);
        addSlotsField       = makeField("e.g. 20");

        updateExtraFields();  // Populate for the default "Personal Plan" selection

        // ── Form action buttons ──────────────────────────────────────────────
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnBar.setOpaque(false);

        btnAddClear  = makeBtn("✕  Clear Form",  new Color(140, 140, 160));
        btnAddSubmit = makeBtn("✓  Add Plan",    CLR_SUCCESS);
        btnAddSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnAddClear.addActionListener(this);
        btnAddSubmit.addActionListener(this);

        btnBar.add(btnAddClear);
        btnBar.add(btnAddSubmit);

        // ── Assemble card ────────────────────────────────────────────────────
        JPanel bottomSection = new JPanel(new BorderLayout(0, 12));
        bottomSection.setOpaque(false);
        bottomSection.add(extraFieldsPanel, BorderLayout.CENTER);
        bottomSection.add(btnBar,           BorderLayout.SOUTH);

        card.add(cardTitle,    BorderLayout.NORTH);
        card.add(formGrid,     BorderLayout.CENTER);
        card.add(bottomSection, BorderLayout.SOUTH);

        JScrollPane sp = new JScrollPane(card);
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        p.add(sp, BorderLayout.CENTER);

        return p;
    }

    // ── Rebuilds extraFieldsPanel based on the selected plan type ────────────
    private void updateExtraFields() {
        extraFieldsPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 8, 6, 8);

        String type = (String) addTypeCombo.getSelectedItem();

        if ("Personal Plan".equals(type)) {
            // Show only: daily prompt limit
            formRow(extraFieldsPanel, gbc, 0, "Daily Prompt Limit:", addDailyLimitField);

        } else if ("Pro Plan".equals(type)) {
            // Show: support level + API access checkbox
            formRow(extraFieldsPanel, gbc, 0, "Support Level:", addSupportField);
            gbc.gridx = 1; gbc.gridy = 1;
            extraFieldsPanel.add(addApiCheckBox, gbc);

        } else if ("Team Plan".equals(type)) {
            // Show: team slot count
            formRow(extraFieldsPanel, gbc, 0, "Team Slots:", addSlotsField);
        }

        extraFieldsPanel.revalidate();
        extraFieldsPanel.repaint();
    }

    // ── Reads the form and creates the correct AIModule subclass ─────────────
    private void doAddPlan() {
        try {
            // Read common fields (all plans share these)
            String type   = (String) addTypeCombo.getSelectedItem();
            String model  = addModelField.getText().trim();
            double price  = Double.parseDouble(addPriceField.getText().trim());
            int    param  = Integer.parseInt(addParamField.getText().trim());
            int    ctx    = Integer.parseInt(addContextField.getText().trim());
            int    tokens = Integer.parseInt(addTokensField.getText().trim());

            if (model.isEmpty()) throw new Exception("Model name cannot be empty.");

            AIModule newPlan = null;

            if ("Personal Plan".equals(type)) {
                // Read PersonalPlan-specific field
                int limit = Integer.parseInt(addDailyLimitField.getText().trim());
                newPlan = new PersonalPlan(nextId++, model, price, param, ctx, tokens, limit);

            } else if ("Pro Plan".equals(type)) {
                // Read ProPlan-specific fields
                String  support   = addSupportField.getText().trim();
                boolean apiAccess = addApiCheckBox.isSelected();
                newPlan = new ProPlan(nextId++, model, price, param, ctx, tokens, apiAccess, support);

            } else if ("Team Plan".equals(type)) {
                // Read TeamPlan-specific field
                int slots = Integer.parseInt(addSlotsField.getText().trim());
                newPlan = new TeamPlan(nextId++, model, price, param, ctx, tokens, slots);
            }

            if (newPlan != null) {
                planList.add(newPlan);                           // Add to master list
                dlgSuccess("Plan '" + model + "' added successfully!");
                clearAddForm();
            }

        } catch (NumberFormatException ex) {
            dlgError("Please enter valid numbers in all numeric fields.");
        } catch (Exception ex) {
            dlgError("Error: " + ex.getMessage());
        }
    }

    // ── Resets every field in the Add Plan form ──────────────────────────────
    private void clearAddForm() {
        addModelField.setText("");
        addPriceField.setText("");
        addParamField.setText("");
        addContextField.setText("");
        addTokensField.setText("");
        addDailyLimitField.setText("");
        addSupportField.setText("");
        addApiCheckBox.setSelected(false);
        addSlotsField.setText("");
    }


    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: PERSONAL PLANS
    //  Displays only PersonalPlan instances; extra column for daily limit.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildPersonalPage() {
        JPanel p = makePage();
        p.add(makePageHeader("👤  Personal Plans",
                             "Manage individual user subscriptions"), BorderLayout.NORTH);

        String[] cols = {
            "ID", "Model Name", "Price (NPR)", "Parameters",
            "Context Window", "Avail. Tokens", "Daily Limit"
        };
        personalTable = makeTable(cols);

        // ── Action buttons ───────────────────────────────────────────────────
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        btnPersonalPrompt = makeBtn("💬  Give Prompt", CLR_PRIMARY);
        btnPersonalEdit   = makeBtn("✎  Edit",         CLR_WARNING);
        btnPersonalDelete = makeBtn("🗑  Delete",       CLR_DANGER);

        btnPersonalPrompt.addActionListener(this);
        btnPersonalEdit.addActionListener(this);
        btnPersonalDelete.addActionListener(this);

        toolbar.add(btnPersonalPrompt);
        toolbar.add(btnPersonalEdit);
        toolbar.add(btnPersonalDelete);

        JPanel center = new JPanel(new BorderLayout(0, 0));
        center.setOpaque(false);
        center.add(toolbar,                       BorderLayout.NORTH);
        center.add(makeScrollPane(personalTable), BorderLayout.CENTER);
        p.add(center, BorderLayout.CENTER);

        return p;
    }

    // ── Loads only PersonalPlan instances into the personal table ────────────
    private void refreshPersonalTable() {
        if (personalTable == null) return;
        DefaultTableModel m = (DefaultTableModel) personalTable.getModel();
        m.setRowCount(0);
        for (AIModule plan : planList) {
            if (plan instanceof PersonalPlan) {                      // Runtime type check
                PersonalPlan pp = (PersonalPlan) plan;               // Safe downcast
                m.addRow(new Object[]{
                    pp.getId(),
                    pp.getModelName(),
                    String.format("%.2f", pp.getPrice()),
                    pp.getParamCount() + "B",
                    String.format("%,d", pp.getContextWindow()),
                    String.format("%,d", pp.getAvailableTokens()),
                    pp.getDailyPromptLimit()                          // PersonalPlan-specific getter
                });
            }
        }
    }


    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: PRO PLANS
    //  Displays only ProPlan instances; extra columns for API access and support.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildProPage() {
        JPanel p = makePage();
        p.add(makePageHeader("⭐  Pro Plans",
                             "Manage professional-tier subscriptions"), BorderLayout.NORTH);

        String[] cols = {
            "ID", "Model Name", "Price (NPR)", "Parameters",
            "Context Window", "Avail. Tokens", "API Access", "Support Level"
        };
        proTable = makeTable(cols);

        // ── Action buttons ───────────────────────────────────────────────────
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        btnProPrompt = makeBtn("💬  Give Prompt", CLR_PRIMARY);
        btnProEdit   = makeBtn("✎  Edit",         CLR_WARNING);
        btnProDelete = makeBtn("🗑  Delete",       CLR_DANGER);

        btnProPrompt.addActionListener(this);
        btnProEdit.addActionListener(this);
        btnProDelete.addActionListener(this);

        toolbar.add(btnProPrompt);
        toolbar.add(btnProEdit);
        toolbar.add(btnProDelete);

        JPanel center = new JPanel(new BorderLayout(0, 0));
        center.setOpaque(false);
        center.add(toolbar,                  BorderLayout.NORTH);
        center.add(makeScrollPane(proTable), BorderLayout.CENTER);
        p.add(center, BorderLayout.CENTER);

        return p;
    }

    // ── Loads only ProPlan instances into the pro table ──────────────────────
    private void refreshProTable() {
        if (proTable == null) return;
        DefaultTableModel m = (DefaultTableModel) proTable.getModel();
        m.setRowCount(0);
        for (AIModule plan : planList) {
            if (plan instanceof ProPlan) {                           // Runtime type check
                ProPlan pp = (ProPlan) plan;                         // Safe downcast
                m.addRow(new Object[]{
                    pp.getId(),
                    pp.getModelName(),
                    String.format("%.2f", pp.getPrice()),
                    pp.getParamCount() + "B",
                    String.format("%,d", pp.getContextWindow()),
                    String.format("%,d", pp.getAvailableTokens()),
                    pp.isApiAccess() ? "✓ Yes" : "✗ No",             // ProPlan-specific getter
                    pp.getSupportLevel()                              // ProPlan-specific getter
                });
            }
        }
    }


    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: PROMPT OPERATIONS
    //  User enters a Plan ID + prompt text, clicks Run → simulated response.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildPromptPage() {
        JPanel p = makePage();
        p.add(makePageHeader("💬  Prompt Operations",
                             "Simulate sending prompts to a subscription plan"), BorderLayout.NORTH);

        // ── Two input fields at the top ──────────────────────────────────────
        JPanel fieldsCard = makeCard();
        fieldsCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 8, 6, 8);

        promptIdField    = makeField("Enter plan ID");
        promptTokenField = makeField("Expected output token count");

        formRow(fieldsCard, gbc, 0, "Plan ID:",         promptIdField);
        formRow(fieldsCard, gbc, 1, "Expected Tokens:", promptTokenField);

        // ── Two side-by-side text areas (input | output) ─────────────────────
        promptInputArea  = new JTextArea();
        promptOutputArea = new JTextArea();
        promptOutputArea.setEditable(false);
        promptOutputArea.setBackground(new Color(248, 250, 255));

        promptInputArea.setFont(FNT_MONO);
        promptOutputArea.setFont(FNT_MONO);
        promptInputArea.setLineWrap(true);
        promptOutputArea.setLineWrap(true);
        promptInputArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        promptOutputArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JScrollPane inSp  = makeScrollPane(promptInputArea);
        JScrollPane outSp = makeScrollPane(promptOutputArea);
        inSp.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CLR_BORDER),
            "  ✏️  Prompt Input  ",
            TitledBorder.LEFT, TitledBorder.TOP, FNT_BTN, CLR_PRIMARY));
        outSp.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CLR_BORDER),
            "  🤖  AI Response (Simulated)  ",
            TitledBorder.LEFT, TitledBorder.TOP, FNT_BTN, CLR_SUCCESS));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inSp, outSp);
        split.setResizeWeight(0.5);
        split.setBorder(null);
        split.setDividerSize(6);

        // ── Run / Clear buttons ──────────────────────────────────────────────
        btnClearPrompt = makeBtn("✕  Clear",       new Color(140, 140, 160));
        btnRunPrompt   = makeBtn("▶  Run Prompt",  CLR_SUCCESS);
        btnRunPrompt.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnClearPrompt.addActionListener(this);
        btnRunPrompt.addActionListener(this);

        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnBar.setOpaque(false);
        btnBar.add(btnClearPrompt);
        btnBar.add(btnRunPrompt);

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setOpaque(false);
        center.add(fieldsCard, BorderLayout.NORTH);
        center.add(split,      BorderLayout.CENTER);
        center.add(btnBar,     BorderLayout.SOUTH);
        p.add(center, BorderLayout.CENTER);

        return p;
    }

    // ── Simulates running a prompt against a plan ────────────────────────────
    private void doRunPrompt() {
        String idStr  = promptIdField.getText().trim();
        String prompt = promptInputArea.getText().trim();

        if (idStr.isEmpty() || prompt.isEmpty()) {
            dlgWarn("Please enter both a Plan ID and a prompt.");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            // Find the plan by ID — uses parent type for polymorphic search
            AIModule found = null;
            for (AIModule pl : planList) {
                if (pl.getId() == id) { found = pl; break; }
            }

            if (found == null) { dlgError("Plan ID " + id + " not found."); return; }

            int wordCount = prompt.trim().split("\\s+").length;
            int estTokens = wordCount * 4;                   // ~4 chars per token estimate
            String expTok = promptTokenField.getText().trim();

            // Build a richly formatted simulated response
            String out =
                "╔═══════════════════════════════════════════════╗\n"
              + "║       AI SUBSCRIPTION — PROMPT RESPONSE       ║\n"
              + "╚═══════════════════════════════════════════════╝\n\n"
              + "📋  Plan ID      : " + found.getId()        + "\n"
              + "🤖  Model        : " + found.getModelName() + "\n"
              + "📦  Plan Type    : " + found.getPlanType()  + "\n"
              + "ℹ   Description  : " + found.getPlanDescription() + "\n\n"  // Polymorphic call
              + "══════════════ Prompt Statistics ══════════════\n"
              + "📝  Word Count        : " + wordCount + " words\n"
              + "🔢  Estimated Tokens  : ~" + estTokens + " tokens\n"
              + "🎯  Expected Output   : " + (expTok.isEmpty() ? "N/A" : expTok + " tokens") + "\n"
              + "💳  Tokens Remaining  : ~" + String.format("%,d", found.getAvailableTokens() - estTokens) + "\n\n"
              + "══════════════ Simulated Response ═════════════\n"
              + "Processing your prompt through " + found.getModelName() + "…\n\n"
              + "Response: This is a simulated AI response. In a real deployment\n"
              + "this would call the " + found.getModelName() + " API and return\n"
              + "a real completion result based on your prompt.\n\n"
              + "✅  Prompt processed successfully.\n"
              + "═══════════════════════════════════════════════\n";

            promptOutputArea.setText(out);
            promptOutputArea.setCaretPosition(0);

        } catch (NumberFormatException ex) {
            dlgError("Plan ID must be a valid integer.");
        }
    }


    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: TEAM OPERATIONS
    //  Table of TeamPlan rows (left) + member-add panel (right).
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildTeamPage() {
        JPanel p = makePage();
        p.add(makePageHeader("👥  Team Operations",
                             "Manage team subscriptions and member access"), BorderLayout.NORTH);

        // ── Left: team plans table ───────────────────────────────────────────
        String[] cols = {"ID", "Model Name", "Price (NPR)", "Team Slots", "Members Used"};
        teamTable = makeTable(cols);

        JPanel tableCard = makeCard();
        tableCard.setLayout(new BorderLayout(0, 8));

        JLabel tTitle = new JLabel("Team Plans");
        tTitle.setFont(FNT_HEADING);
        tTitle.setForeground(CLR_TXT_DARK);

        btnTeamRefresh = makeBtn("⟳  Refresh", CLR_PRIMARY);
        btnTeamRefresh.addActionListener(this);

        JPanel tTopRow = new JPanel(new BorderLayout());
        tTopRow.setOpaque(false);
        tTopRow.add(tTitle,        BorderLayout.WEST);
        tTopRow.add(btnTeamRefresh, BorderLayout.EAST);

        tableCard.add(tTopRow,                  BorderLayout.NORTH);
        tableCard.add(makeScrollPane(teamTable), BorderLayout.CENTER);

        // ── Right: add-member panel ──────────────────────────────────────────
        JPanel memberCard = makeCard();
        memberCard.setPreferredSize(new Dimension(275, 0));
        memberCard.setLayout(new BorderLayout(0, 10));

        JLabel mTitle = new JLabel("Add Team Member");
        mTitle.setFont(FNT_HEADING);
        mTitle.setForeground(CLR_TXT_DARK);

        JLabel mHint = new JLabel("<html>Select a team plan above,<br>then type a name below.</html>");
        mHint.setFont(FNT_SMALL);
        mHint.setForeground(CLR_TXT_MUTED);

        teamMemberNameField = makeField("Member name…");

        teamMembersDisplay = new JTextArea();
        teamMembersDisplay.setEditable(false);
        teamMembersDisplay.setFont(FNT_BODY);
        teamMembersDisplay.setBackground(new Color(248, 250, 255));
        teamMembersDisplay.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JScrollPane mScroll = makeScrollPane(teamMembersDisplay);
        mScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(CLR_BORDER),
            "  Current Members  ",
            TitledBorder.LEFT, TitledBorder.TOP, FNT_BODY, CLR_TXT_DARK));

        btnAddMember = makeBtn("➕  Add Member", CLR_SUCCESS);
        btnAddMember.addActionListener(this);

        // Assemble the top section of the member panel
        JPanel mTop = new JPanel(new BorderLayout(0, 6));
        mTop.setOpaque(false);
        mTop.add(mTitle,             BorderLayout.NORTH);
        mTop.add(mHint,              BorderLayout.CENTER);
        mTop.add(teamMemberNameField, BorderLayout.SOUTH);

        memberCard.add(mTop,     BorderLayout.NORTH);
        memberCard.add(mScroll,  BorderLayout.CENTER);
        memberCard.add(btnAddMember, BorderLayout.SOUTH);

        // ── Split: table | member panel ──────────────────────────────────────
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableCard, memberCard);
        split.setResizeWeight(0.70);
        split.setBorder(null);
        split.setDividerSize(6);

        p.add(split, BorderLayout.CENTER);
        return p;
    }

    // ── Loads only TeamPlan instances into the team table ────────────────────
    private void refreshTeamTable() {
        if (teamTable == null) return;
        DefaultTableModel m = (DefaultTableModel) teamTable.getModel();
        m.setRowCount(0);
        for (AIModule plan : planList) {
            if (plan instanceof TeamPlan) {                          // Runtime type check
                TeamPlan tp = (TeamPlan) plan;                       // Safe downcast
                m.addRow(new Object[]{
                    tp.getId(),
                    tp.getModelName(),
                    String.format("%.2f", tp.getPrice()),
                    tp.getTeamSlots(),                               // TeamPlan-specific getter
                    tp.getMemberCount()                              // TeamPlan-specific getter
                });
            }
        }
    }

    // ── Adds a member to the currently selected TeamPlan ─────────────────────
    private void doAddMember() {
        String name = teamMemberNameField.getText().trim();
        if (name.isEmpty()) { dlgWarn("Please enter a member name."); return; }

        int row = teamTable.getSelectedRow();
        if (row < 0) { dlgWarn("Please select a Team Plan from the table first."); return; }

        int id = Integer.parseInt(teamTable.getValueAt(row, 0).toString());

        for (AIModule plan : planList) {
            if (plan.getId() == id && plan instanceof TeamPlan) {
                TeamPlan tp = (TeamPlan) plan;
                if (tp.addMember(name)) {                    // Slot-enforced via TeamPlan method
                    teamMembersDisplay.append("✓  " + name + "\n");
                    teamMemberNameField.setText("");
                    refreshTeamTable();                      // Refresh member count in table
                } else {
                    dlgError("Team is full! Maximum slots: " + tp.getTeamSlots());
                }
                break;
            }
        }
    }


    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: REPORTS
    //  Generates a formatted text report in a large monospaced text area.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildReportsPage() {
        JPanel p = makePage();
        p.add(makePageHeader("📊  Reports",
                             "Generate analytics summaries for all subscription plans"), BorderLayout.NORTH);

        // ── Report type selector + generate button ───────────────────────────
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        reportTypeCombo = new JComboBox<>(new String[]{
            "Full Summary", "Personal Plans Only", "Pro Plans Only", "Team Plans Only"
        });
        styleCombo(reportTypeCombo);

        btnGenerateReport = makeBtn("📊  Generate Report", CLR_PRIMARY);
        btnGenerateReport.addActionListener(this);

        toolbar.add(new JLabel("Report Type: "));
        toolbar.add(reportTypeCombo);
        toolbar.add(btnGenerateReport);

        // ── Report output area ───────────────────────────────────────────────
        reportArea = new JTextArea("Click 'Generate Report' to view subscription analytics.");
        reportArea.setEditable(false);
        reportArea.setFont(FNT_MONO);
        reportArea.setBackground(new Color(248, 250, 255));
        reportArea.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JPanel north = new JPanel(new BorderLayout(0, 8));
        north.setOpaque(false);
        north.add(toolbar, BorderLayout.CENTER);

        p.add(north,                          BorderLayout.NORTH);
        p.add(makeScrollPane(reportArea),     BorderLayout.CENTER);

        return p;
    }

    // ── Builds and displays the report text based on selected type ───────────
    private void doGenerateReport() {
        String type = (String) reportTypeCombo.getSelectedItem();
        StringBuilder sb = new StringBuilder();

        // Header block
        sb.append("╔══════════════════════════════════════════════════════╗\n");
        sb.append("║       AI SUBSCRIPTION MANAGEMENT — REPORT            ║\n");
        sb.append("╚══════════════════════════════════════════════════════╝\n\n");
        sb.append("Generated : ").append(new java.util.Date()).append("\n\n");

        // Summary counts using instanceof for type-safe counting
        long personal = planList.stream().filter(pl -> pl instanceof PersonalPlan).count();
        long pro      = planList.stream().filter(pl -> pl instanceof ProPlan).count();
        long team     = planList.stream().filter(pl -> pl instanceof TeamPlan).count();
        double total  = planList.stream().mapToDouble(AIModule::getPrice).sum();

        sb.append("══ SUMMARY ══════════════════════════════════════════\n");
        sb.append(String.format("  Total Plans      : %d\n", planList.size()));
        sb.append(String.format("  Personal Plans   : %d\n", personal));
        sb.append(String.format("  Pro Plans        : %d\n", pro));
        sb.append(String.format("  Team Plans       : %d\n", team));
        sb.append(String.format("  Total Revenue    : NPR %,.2f/month\n\n", total));

        // Personal plans section
        if ("Full Summary".equals(type) || "Personal Plans Only".equals(type)) {
            sb.append("══ PERSONAL PLANS ═══════════════════════════════════\n");
            for (AIModule plan : planList) {
                if (plan instanceof PersonalPlan) {
                    PersonalPlan pp = (PersonalPlan) plan;
                    sb.append(String.format(
                        "  [ID:%2d] %-12s | NPR %,8.2f | Tokens: %,8d | Daily Limit: %3d\n",
                        pp.getId(), pp.getModelName(), pp.getPrice(),
                        pp.getAvailableTokens(), pp.getDailyPromptLimit()));
                    sb.append("         ↳ ").append(pp.getPlanDescription()).append("\n");  // Polymorphic call
                }
            }
            sb.append("\n");
        }

        // Pro plans section
        if ("Full Summary".equals(type) || "Pro Plans Only".equals(type)) {
            sb.append("══ PRO PLANS ════════════════════════════════════════\n");
            for (AIModule plan : planList) {
                if (plan instanceof ProPlan) {
                    ProPlan pp = (ProPlan) plan;
                    sb.append(String.format(
                        "  [ID:%2d] %-12s | NPR %,8.2f | API: %-3s | Support: %s\n",
                        pp.getId(), pp.getModelName(), pp.getPrice(),
                        pp.isApiAccess() ? "Yes" : "No", pp.getSupportLevel()));
                    sb.append("         ↳ ").append(pp.getPlanDescription()).append("\n");  // Polymorphic call
                }
            }
            sb.append("\n");
        }

        // Team plans section
        if ("Full Summary".equals(type) || "Team Plans Only".equals(type)) {
            sb.append("══ TEAM PLANS ═══════════════════════════════════════\n");
            for (AIModule plan : planList) {
                if (plan instanceof TeamPlan) {
                    TeamPlan tp = (TeamPlan) plan;
                    sb.append(String.format(
                        "  [ID:%2d] %-12s | NPR %,8.2f | Slots: %2d | Members: %2d\n",
                        tp.getId(), tp.getModelName(), tp.getPrice(),
                        tp.getTeamSlots(), tp.getMemberCount()));
                    if (!tp.getMemberNames().isEmpty())
                        sb.append("         Members: ").append(String.join(", ", tp.getMemberNames())).append("\n");
                    sb.append("         ↳ ").append(tp.getPlanDescription()).append("\n");  // Polymorphic call
                }
            }
        }

        sb.append("\n══════════════════════════════════════════════════════\n");
        sb.append("                    END OF REPORT                    \n");
        sb.append("══════════════════════════════════════════════════════\n");

        reportArea.setText(sb.toString());
        reportArea.setCaretPosition(0);
    }


    // ════════════════════════════════════════════════════════════════════════
    //  PAGE: ABOUT
    //  Static card with application metadata and feature list.
    // ════════════════════════════════════════════════════════════════════════
    private JPanel buildAboutPage() {
        JPanel p = makePage();
        p.add(makePageHeader("ℹ  About",
                             "Application information and class hierarchy overview"), BorderLayout.NORTH);

        JPanel card = makeCard();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill  = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets  = new Insets(5, 0, 5, 0);

        // App title row
        JLabel appTitle = new JLabel("🤖  AI Subscription Management System");
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        appTitle.setForeground(CLR_PRIMARY);
        gbc.gridy = 0; card.add(appTitle, gbc);

        // Separator
        gbc.gridy++; card.add(makeSep(), gbc);

        // Key info rows
        String[][] info = {
            { "Version",       "1.0.0" },
            { "Platform",      "Java Swing (JDK 8+)" },
            { "Author",        "Course Work — AI SubManager" },
            { "Architecture",  "OOP with Inheritance + ActionListener pattern" },
        };
        for (String[] row : info) {
            gbc.gridy++;
            JPanel rowPanel = new JPanel(new BorderLayout(16, 0));
            rowPanel.setOpaque(false);
            JLabel key = new JLabel(row[0] + ":"); key.setFont(FNT_SUBHEAD);
            key.setForeground(CLR_TXT_DARK); key.setPreferredSize(new Dimension(180, 22));
            JLabel val = new JLabel(row[1]);  val.setFont(FNT_BODY);
            val.setForeground(CLR_TXT_MUTED);
            rowPanel.add(key, BorderLayout.WEST);
            rowPanel.add(val, BorderLayout.CENTER);
            card.add(rowPanel, gbc);
        }

        gbc.gridy++; card.add(makeSep(), gbc);

        // Class hierarchy block
        gbc.gridy++;
        JLabel hierTitle = new JLabel("📐  Class Hierarchy");
        hierTitle.setFont(FNT_HEADING); hierTitle.setForeground(CLR_PRIMARY);
        card.add(hierTitle, gbc);

        String[] hier = {
            "  AIModule  (abstract parent class)",
            "  ├── PersonalPlan  → adds dailyPromptLimit",
            "  ├── ProPlan       → adds apiAccess, supportLevel",
            "  └── TeamPlan      → adds teamSlots, memberNames, addMember()",
            "",
            "  AISubscriptionManagementSystem extends JFrame implements ActionListener"
        };
        for (String line : hier) {
            gbc.gridy++;
            JLabel l = new JLabel(line);
            l.setFont(FNT_MONO);
            l.setForeground(new Color(55, 80, 140));
            card.add(l, gbc);
        }

        gbc.gridy++; card.add(makeSep(), gbc);

        // Features block
        gbc.gridy++;
        JLabel featTitle = new JLabel("✨  Features");
        featTitle.setFont(FNT_HEADING); featTitle.setForeground(CLR_PRIMARY);
        card.add(featTitle, gbc);

        String[] feats = {
            "•  Polymorphic plan list — all plans stored as ArrayList<AIModule>",
            "•  Filter & live-search on the Home Dashboard",
            "•  Dynamic Add Plan form (extra fields adapt to chosen plan type)",
            "•  Team member management with slot-enforcement via addMember()",
            "•  Prompt simulation with token usage estimation",
            "•  Filterable report generation (Personal / Pro / Team / All)",
            "•  Single ActionListener handles all 20+ buttons centrally"
        };
        for (String feat : feats) {
            gbc.gridy++;
            JLabel fl = new JLabel(feat);
            fl.setFont(FNT_BODY);
            fl.setForeground(new Color(55, 70, 110));
            card.add(fl, gbc);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card, BorderLayout.NORTH);
        p.add(new JScrollPane(wrapper), BorderLayout.CENTER);
        return p;
    }


    // ════════════════════════════════════════════════════════════════════════
    //  ACTION LISTENER — Central dispatcher  (implements ActionListener)
    //  Every button in the app is registered with `addActionListener(this)`.
    //  This single method routes each event to the appropriate handler.
    // ════════════════════════════════════════════════════════════════════════
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // ── Header buttons ───────────────────────────────────────────────────
        if      (src == btnHdrHome)   showPage("Home");
        else if (src == btnHdrSave)   dlgSuccess("Data saved! (Simulated — no persistent storage in this demo)");
        else if (src == btnHdrReload) refreshCurrentPage();
        else if (src == btnHdrExit)   System.exit(0);

        // ── Home Dashboard ───────────────────────────────────────────────────
        else if (src == btnHomeRefresh) refreshHomeTable();
        else if (src == btnHomeDelete)  doDeleteFrom(homeTable);
        else if (src == btnHomeEdit)    doEditFrom(homeTable);

        // ── Add Plan ─────────────────────────────────────────────────────────
        else if (src == btnAddSubmit) doAddPlan();
        else if (src == btnAddClear)  clearAddForm();

        // ── Personal Plans ───────────────────────────────────────────────────
        else if (src == btnPersonalDelete)  doDeleteFrom(personalTable);
        else if (src == btnPersonalEdit)    doEditFrom(personalTable);
        else if (src == btnPersonalPrompt)  goToPrompt(personalTable);

        // ── Pro Plans ────────────────────────────────────────────────────────
        else if (src == btnProDelete)  doDeleteFrom(proTable);
        else if (src == btnProEdit)    doEditFrom(proTable);
        else if (src == btnProPrompt)  goToPrompt(proTable);

        // ── Prompt Operations ────────────────────────────────────────────────
        else if (src == btnRunPrompt)   doRunPrompt();
        else if (src == btnClearPrompt) {
            promptInputArea.setText("");
            promptOutputArea.setText("");
            promptIdField.setText("");
            promptTokenField.setText("");
        }

        // ── Team Operations ──────────────────────────────────────────────────
        else if (src == btnAddMember)  doAddMember();
        else if (src == btnTeamRefresh) refreshTeamTable();

        // ── Reports ──────────────────────────────────────────────────────────
        else if (src == btnGenerateReport) doGenerateReport();
    }


    // ════════════════════════════════════════════════════════════════════════
    //  SHARED HELPERS — Called from actionPerformed or from individual pages
    // ════════════════════════════════════════════════════════════════════════

    // ── Deletes the plan whose ID is in column 0 of the selected row ─────────
    private void doDeleteFrom(JTable table) {
        int row = table.getSelectedRow();
        if (row < 0) { dlgWarn("Please select a row to delete."); return; }

        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        int ok = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete Plan ID " + id + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (ok == JOptionPane.YES_OPTION) {
            planList.removeIf(pl -> pl.getId() == id);  // Remove from master list
            refreshCurrentPage();
            dlgSuccess("Plan ID " + id + " deleted.");
        }
    }

    // ── Opens an edit dialog for the plan whose ID is in column 0 ────────────
    private void doEditFrom(JTable table) {
        int row = table.getSelectedRow();
        if (row < 0) { dlgWarn("Please select a plan to edit."); return; }

        int id = Integer.parseInt(table.getValueAt(row, 0).toString());

        // Find the plan in the list using the parent type (polymorphism)
        for (AIModule plan : planList) {
            if (plan.getId() == id) {
                // Pre-populate dialog fields with current values
                JTextField modelF = makeField(""); modelF.setText(plan.getModelName());
                JTextField priceF = makeField(""); priceF.setText(String.valueOf(plan.getPrice()));
                JTextField tokF   = makeField(""); tokF.setText(String.valueOf(plan.getAvailableTokens()));

                JPanel dlg = new JPanel(new GridLayout(6, 1, 5, 5));
                dlg.add(new JLabel("Model Name:"));   dlg.add(modelF);
                dlg.add(new JLabel("Price (NPR):"));  dlg.add(priceF);
                dlg.add(new JLabel("Avail. Tokens:")); dlg.add(tokF);

                int res = JOptionPane.showConfirmDialog(this, dlg,
                    "Edit Plan ID " + id, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        // Use setters inherited from AIModule (parent class)
                        plan.setModelName(modelF.getText().trim());
                        plan.setPrice(Double.parseDouble(priceF.getText().trim()));
                        plan.setAvailableTokens(Integer.parseInt(tokF.getText().trim()));
                        refreshCurrentPage();
                        dlgSuccess("Plan ID " + id + " updated.");
                    } catch (NumberFormatException ex) {
                        dlgError("Invalid number format. Please enter valid numeric values.");
                    }
                }
                break;
            }
        }
    }

    // ── Copies selected row's ID to the Prompt page and navigates there ──────
    private void goToPrompt(JTable table) {
        int row = table.getSelectedRow();
        if (row < 0) { dlgWarn("Please select a plan first."); return; }
        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        promptIdField.setText(String.valueOf(id));   // Pre-fill Plan ID field on Prompt page
        showPage("Prompt");
    }

    // ── Counts plans matching a given type string ─────────────────────────────
    private String countType(String type) {
        long count = 0;
        for (AIModule pl : planList) if (pl.getPlanType().equals(type)) count++;
        return String.valueOf(count);
    }


    // ════════════════════════════════════════════════════════════════════════
    //  UI COMPONENT FACTORIES
    //  All helpers that create consistently styled Swing components.
    //  Returning a new component each call keeps pages independent.
    // ════════════════════════════════════════════════════════════════════════

    // ── Blank page panel (BorderLayout + padding + content background) ────────
    private JPanel makePage() {
        JPanel p = new JPanel(new BorderLayout(0, 16));
        p.setBackground(CLR_CONTENT_BG);
        p.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        return p;
    }

    // ── White card with a subtle border and internal padding ──────────────────
    private JPanel makeCard() {
        JPanel card = new JPanel();
        card.setBackground(CLR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CLR_BORDER, 1),
            BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        return card;
    }

    // ── Page header: left colour bar + title + subtitle + bottom separator ────
    private JPanel makePageHeader(String title, String subtitle) {
        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        // Vertical accent bar on the left
        JPanel bar = new JPanel();
        bar.setBackground(CLR_PRIMARY);
        bar.setPreferredSize(new Dimension(5, 44));

        // Title and subtitle stacked
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
        textStack.add(subLbl,   BorderLayout.SOUTH);

        JPanel leftSide = new JPanel(new BorderLayout());
        leftSide.setOpaque(false);
        leftSide.add(bar,       BorderLayout.WEST);
        leftSide.add(textStack, BorderLayout.CENTER);

        JSeparator sep = new JSeparator();
        sep.setForeground(CLR_BORDER);

        header.add(leftSide, BorderLayout.CENTER);
        header.add(sep,      BorderLayout.SOUTH);
        return header;
    }

    // ── Coloured summary stat card (used on Home Dashboard) ───────────────────
    private JPanel makeStatCard(String label, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 6)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Paint a thin colour stripe at the very top
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

    // ── Rounded action button with hover darkening ─────────────────────────
    private JButton makeBtn(String text, Color bg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 9, 9);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(FNT_BTN);
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Color darker = bg.darker();
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(darker); b.repaint(); }
            public void mouseExited (MouseEvent e) { b.setBackground(bg);     b.repaint(); }
        });
        return b;
    }

    // ── Styled JTable with coloured header and alternating row colours ─────────
    private JTable makeTable(String[] cols) {
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; } // Read-only table
        };

        JTable table = new JTable(model) {
            // Alternating row backgrounds painted in prepareRenderer
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row))
                    c.setBackground(row % 2 == 0 ? CLR_CARD : CLR_TBL_ROW_ALT);
                return c;
            }
        };

        table.setRowHeight(32);
        table.setFont(FNT_BODY);
        table.setGridColor(CLR_BORDER);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(200, 218, 255));
        table.setSelectionForeground(CLR_TXT_DARK);
        table.setFillsViewportHeight(true);

        // Styled column header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(CLR_TBL_HEADER);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 38));

        // Centre-align every cell
        DefaultTableCellRenderer centre = new DefaultTableCellRenderer();
        centre.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < cols.length; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(centre);

        return table;
    }

    // ── JScrollPane with consistent border ────────────────────────────────────
    private JScrollPane makeScrollPane(JComponent comp) {
        JScrollPane sp = new JScrollPane(comp);
        sp.setBorder(BorderFactory.createLineBorder(CLR_BORDER));
        sp.getViewport().setBackground(CLR_CARD);
        return sp;
    }

    // ── Styled JTextField ────────────────────────────────────────────────────
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

    // ── Styles an existing JComboBox to match the palette ────────────────────
    private void styleCombo(JComboBox<?> c) {
        c.setFont(FNT_BODY);
        c.setBackground(CLR_CARD);
        c.setForeground(CLR_TXT_DARK);
        c.setPreferredSize(new Dimension(170, 34));
    }

    // ── Adds a label + component pair as a GridBagLayout row ─────────────────
    private void formRow(JPanel panel, GridBagConstraints gbc,
                         int row, String labelText, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.28;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(FNT_BODY);
        lbl.setForeground(CLR_TXT_DARK);
        panel.add(lbl, gbc);
        gbc.gridx = 1; gbc.weightx = 0.72;
        panel.add(field, gbc);
    }

    // ── Thin horizontal rule ──────────────────────────────────────────────────
    private JSeparator makeSep() {
        JSeparator sep = new JSeparator();
        sep.setForeground(CLR_BORDER);
        return sep;
    }


    // ════════════════════════════════════════════════════════════════════════
    //  DIALOG HELPERS — Convenience wrappers around JOptionPane
    // ════════════════════════════════════════════════════════════════════════

    // Success: green information icon
    private void dlgSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "✓ Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Error: red error icon
    private void dlgError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "✕ Error", JOptionPane.ERROR_MESSAGE);
    }

    // Warning: orange warning icon
    private void dlgWarn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "⚠ Warning", JOptionPane.WARNING_MESSAGE);
    }


    // ════════════════════════════════════════════════════════════════════════
    //  MAIN METHOD — Application entry point
    //  Uses invokeLater() to start the GUI on the Event Dispatch Thread (EDT),
    //  which is the thread-safe way to create Swing windows.
    // ════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        // Attempt to use the platform's native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Falls back to the default cross-platform Metal look and feel
        }

        // Schedule GUI creation on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            AISubscriptionManagementSystem app = new AISubscriptionManagementSystem();
            app.setVisible(true);
        });
    }
}
