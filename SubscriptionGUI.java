import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * SubscriptionGUI - Main GUI class for managing AI subscription plans
 * Provides interface for adding plans, managing prompts, team members, and file operations
 */
public class SubscriptionGUI extends JFrame implements ActionListener {
    // ArrayList to store AI subscription plans
    private ArrayList<AIModel> subscriptionPlans;
    
    // Text Fields
    private JTextField txtModelName;
    private JTextField txtPrice;
    private JTextField txtParameters;
    private JTextField txtContextWindow;
    private JTextField txtPromptQuota;
    private JTextField txtTeamSlots;
    private JTextField txtPromptText;
    private JTextField txtResponseLength;
    private JTextField txtTeamMemberName;
    private JTextField txtIndexNumber;
    
    // Buttons
    private JButton btnAddPersonalPlan;
    private JButton btnAddProPlan;
    private JButton btnDisplayAll;
    private JButton btnClear;
    private JButton btnGivePrompt;
    private JButton btnAddTeamMember;
    private JButton btnRemoveTeamMember;
    private JButton btnCheckPlanType;
    private JButton btnExportToFile;
    private JButton btnLoadFromFile;
    
    // Display Area
    private JTextArea displayArea;
    
    /**
     * Constructor - Initializes the GUI components and layout
     */
    public SubscriptionGUI() {
        subscriptionPlans = new ArrayList<>();
        
        // Set up the frame
        setTitle("AI Subscription Management System - Itahari Tech");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Create panels
        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel bottomPanel = createBottomPanel();
        
        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    /**
     * Create top panel with input fields for model details
     * @return JPanel - Panel containing input fields
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Model Details"));
        
        // Model Name
        panel.add(new JLabel("Model Name:"));
        txtModelName = new JTextField();
        panel.add(txtModelName);
        
        // Price
        panel.add(new JLabel("Price (NPR per 1L tokens):"));
        txtPrice = new JTextField();
        panel.add(txtPrice);
        
        // Parameters
        panel.add(new JLabel("Parameters (billions):"));
        txtParameters = new JTextField();
        panel.add(txtParameters);
        
        // Context Window
        panel.add(new JLabel("Context Window:"));
        txtContextWindow = new JTextField();
        panel.add(txtContextWindow);
        
        // Prompt Quota (for Personal Plan)
        panel.add(new JLabel("Prompt Quota (Personal):"));
        txtPromptQuota = new JTextField();
        panel.add(txtPromptQuota);
        
        // Team Slots (for Pro Plan)
        panel.add(new JLabel("Team Slots (Pro):"));
        txtTeamSlots = new JTextField();
        panel.add(txtTeamSlots);
        
        // Add Plan Buttons
        btnAddPersonalPlan = new JButton("Add Personal Plan");
        btnAddPersonalPlan.addActionListener(this);
        panel.add(btnAddPersonalPlan);
        
        btnAddProPlan = new JButton("Add Pro Plan");
        btnAddProPlan.addActionListener(this);
        panel.add(btnAddProPlan);
        
        // Display and Clear Buttons
        btnDisplayAll = new JButton("Display All");
        btnDisplayAll.addActionListener(this);
        panel.add(btnDisplayAll);
        
        btnClear = new JButton("Clear");
        btnClear.addActionListener(this);
        panel.add(btnClear);
        
        return panel;
    }
    
    /**
     * Create center panel with operations section
     * @return JPanel - Panel containing operation fields and display area
     */
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        
        // Operations Panel
        JPanel operationsPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        operationsPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
        
        // Index Number
        operationsPanel.add(new JLabel("Index Number:"));
        txtIndexNumber = new JTextField();
        operationsPanel.add(txtIndexNumber);
        
        // Prompt Text
        operationsPanel.add(new JLabel("Prompt Text:"));
        txtPromptText = new JTextField();
        operationsPanel.add(txtPromptText);
        
        // Response Length
        operationsPanel.add(new JLabel("Response Length (tokens):"));
        txtResponseLength = new JTextField();
        operationsPanel.add(txtResponseLength);
        
        // Give Prompt Button
        btnGivePrompt = new JButton("Give Prompt");
        btnGivePrompt.addActionListener(this);
        operationsPanel.add(btnGivePrompt);
        
        // Team Member Name
        operationsPanel.add(new JLabel("Team Member Name:"));
        txtTeamMemberName = new JTextField();
        operationsPanel.add(txtTeamMemberName);
        
        // Team Operations
        btnAddTeamMember = new JButton("Add Team Member");
        btnAddTeamMember.addActionListener(this);
        operationsPanel.add(btnAddTeamMember);
        
        btnRemoveTeamMember = new JButton("Remove Team Member");
        btnRemoveTeamMember.addActionListener(this);
        operationsPanel.add(btnRemoveTeamMember);
        
        btnCheckPlanType = new JButton("Check Plan Type");
        btnCheckPlanType.addActionListener(this);
        operationsPanel.add(btnCheckPlanType);
        
        panel.add(operationsPanel, BorderLayout.NORTH);
        
        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Display Area"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create bottom panel with file operations
     * @return JPanel - Panel containing file operation buttons
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        btnExportToFile = new JButton("Export to File");
        btnExportToFile.addActionListener(this);
        panel.add(btnExportToFile);
        
        btnLoadFromFile = new JButton("Load From File");
        btnLoadFromFile.addActionListener(this);
        panel.add(btnLoadFromFile);
        
        return panel;
    }
    
    /**
     * Get and validate index number from GUI
     * @return int - Valid index or -1 if error
     */
    private int getIndexNumber() {
        int index = -1;
        try {
            index = Integer.parseInt(txtIndexNumber.getText().trim());
            if (index < 0 || index >= subscriptionPlans.size()) {
                JOptionPane.showMessageDialog(this, 
                    "Error: Index must be between 0 and " + (subscriptionPlans.size() - 1),
                    "Invalid Index", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Error: Please enter a valid integer for index number.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return index;
    }
    
    /**
     * Check and display the plan type at given index
     * @param index - Index of the plan to check
     */
    private void checkPlanType(int index) {
        if (index == -1) return;
        
        AIModel model = subscriptionPlans.get(index);
        String planType = "";
        
        if (model instanceof PersonalPlan) {
            planType = "Personal Plan";
        } else if (model instanceof ProPlan) {
            planType = "Pro Plan";
        } else {
            planType = "Unknown Plan Type";
        }
        
        JOptionPane.showMessageDialog(this,
            "Plan at index " + index + " is: " + planType,
            "Plan Type Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Action handler for all button clicks
     * @param e - ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAddPersonalPlan) {
            addPersonalPlan();
        } else if (e.getSource() == btnAddProPlan) {
            addProPlan();
        } else if (e.getSource() == btnDisplayAll) {
            displayAll();
        } else if (e.getSource() == btnClear) {
            clearFields();
        } else if (e.getSource() == btnGivePrompt) {
            givePrompt();
        } else if (e.getSource() == btnAddTeamMember) {
            addTeamMember();
        } else if (e.getSource() == btnRemoveTeamMember) {
            removeTeamMember();
        } else if (e.getSource() == btnCheckPlanType) {
            int index = getIndexNumber();
            checkPlanType(index);
        } else if (e.getSource() == btnExportToFile) {
            exportToFile();
        } else if (e.getSource() == btnLoadFromFile) {
            loadFromFile();
        }
    }
    
    /**
     * Add a Personal Plan to the subscription list
     */
    private void addPersonalPlan() {
        try {
            String modelName = txtModelName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int parameters = Integer.parseInt(txtParameters.getText().trim());
            String contextWindow = txtContextWindow.getText().trim();
            int promptQuota = Integer.parseInt(txtPromptQuota.getText().trim());
            
            PersonalPlan plan = new PersonalPlan(modelName, price, parameters, 
                                                  contextWindow, promptQuota);
            subscriptionPlans.add(plan);
            
            JOptionPane.showMessageDialog(this,
                "Personal Plan added successfully!\nIndex: " + (subscriptionPlans.size() - 1),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Error: Please enter valid numeric values for price, parameters, and prompt quota.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Add a Pro Plan to the subscription list
     */
    private void addProPlan() {
        try {
            String modelName = txtModelName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int parameters = Integer.parseInt(txtParameters.getText().trim());
            String contextWindow = txtContextWindow.getText().trim();
            int teamSlots = Integer.parseInt(txtTeamSlots.getText().trim());
            
            ProPlan plan = new ProPlan(modelName, price, parameters, 
                                       contextWindow, teamSlots);
            subscriptionPlans.add(plan);
            
            JOptionPane.showMessageDialog(this,
                "Pro Plan added successfully!\nIndex: " + (subscriptionPlans.size() - 1),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Error: Please enter valid numeric values for price, parameters, and team slots.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Display all subscription plans in the display area
     */
    private void displayAll() {
        if (subscriptionPlans.isEmpty()) {
            displayArea.setText("No subscription plans available.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALL SUBSCRIPTION PLANS ===\n\n");
        
        for (int i = 0; i < subscriptionPlans.size(); i++) {
            sb.append("Index: ").append(i).append("\n");
            sb.append(subscriptionPlans.get(i).display());
            sb.append("\n");
            sb.append("─".repeat(60)).append("\n\n");
        }
        
        displayArea.setText(sb.toString());
    }
    
    /**
     * Clear all text fields
     */
    private void clearFields() {
        txtModelName.setText("");
        txtPrice.setText("");
        txtParameters.setText("");
        txtContextWindow.setText("");
        txtPromptQuota.setText("");
        txtTeamSlots.setText("");
        txtPromptText.setText("");
        txtResponseLength.setText("");
        txtTeamMemberName.setText("");
        txtIndexNumber.setText("");
    }
    
    /**
     * Submit a prompt for Personal Plan
     */
    private void givePrompt() {
        int index = getIndexNumber();
        if (index == -1) return;
        
        AIModel model = subscriptionPlans.get(index);
        
        if (model instanceof PersonalPlan) {
            try {
                String promptText = txtPromptText.getText().trim();
                int responseLength = Integer.parseInt(txtResponseLength.getText().trim());
                
                PersonalPlan personalPlan = (PersonalPlan) model;
                String result = personalPlan.enterPrompt(promptText, responseLength);
                
                JOptionPane.showMessageDialog(this, result,
                    "Prompt Result", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error: Please enter a valid number for response length.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Error: This operation is only available for Personal Plan subscriptions.",
                "Invalid Operation", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Add a team member to Pro Plan
     */
    private void addTeamMember() {
        int index = getIndexNumber();
        if (index == -1) return;
        
        AIModel model = subscriptionPlans.get(index);
        
        if (model instanceof ProPlan) {
            String memberName = txtTeamMemberName.getText().trim();
            ProPlan proPlan = (ProPlan) model;
            String result = proPlan.addTeamMember(memberName);
            
            JOptionPane.showMessageDialog(this, result,
                "Team Member Operation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Error: Team collaboration is only available for Pro Plan subscriptions.",
                "Invalid Operation", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Remove a team member from Pro Plan
     */
    private void removeTeamMember() {
        int index = getIndexNumber();
        if (index == -1) return;
        
        AIModel model = subscriptionPlans.get(index);
        
        if (model instanceof ProPlan) {
            String memberName = txtTeamMemberName.getText().trim();
            ProPlan proPlan = (ProPlan) model;
            String result = proPlan.removeTeamMember(memberName);
            
            JOptionPane.showMessageDialog(this, result,
                "Team Member Operation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Error: Team collaboration is only available for Pro Plan subscriptions.",
                "Invalid Operation", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Export all subscription data to a text file
     */
    private void exportToFile() {
        if (subscriptionPlans.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No data to export. Please add subscription plans first.",
                "Export Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Subscription Data");
        fileChooser.setSelectedFile(new File("subscriptions.txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileToSave))) {
                writer.println("AI SUBSCRIPTION MANAGEMENT SYSTEM - DATA EXPORT");
                writer.println("Total Plans: " + subscriptionPlans.size());
                writer.println("Export Date: " + java.time.LocalDateTime.now());
                writer.println("=".repeat(70));
                writer.println();
                
                for (int i = 0; i < subscriptionPlans.size(); i++) {
                    AIModel model = subscriptionPlans.get(i);
                    writer.println("INDEX: " + i);
                    
                    if (model instanceof PersonalPlan) {
                        PersonalPlan pp = (PersonalPlan) model;
                        writer.println("PLAN_TYPE: PERSONAL");
                        writer.println("MODEL_NAME: " + pp.getModelName());
                        writer.println("PRICE: " + pp.getPrice());
                        writer.println("PARAMETERS: " + pp.getParameterCount());
                        writer.println("CONTEXT_WINDOW: " + pp.getContextWindow());
                        writer.println("REMAINING_PROMPTS: " + pp.getRemainingPrompts());
                    } else if (model instanceof ProPlan) {
                        ProPlan pp = (ProPlan) model;
                        writer.println("PLAN_TYPE: PRO");
                        writer.println("MODEL_NAME: " + pp.getModelName());
                        writer.println("PRICE: " + pp.getPrice());
                        writer.println("PARAMETERS: " + pp.getParameterCount());
                        writer.println("CONTEXT_WINDOW: " + pp.getContextWindow());
                        writer.println("AVAILABLE_SLOTS: " + pp.getAvailableSlots());
                    }
                    
                    writer.println("-".repeat(70));
                    writer.println();
                }
                
                JOptionPane.showMessageDialog(this,
                    "Data exported successfully to:\n" + fileToSave.getAbsolutePath(),
                    "Export Successful", JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error exporting data: " + ex.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Load subscription data from a text file and display
     */
    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Subscription Data");
        
        int userSelection = fileChooser.showOpenDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
                StringBuilder content = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                
                // Display in a new window
                JFrame displayFrame = new JFrame("Loaded Data - " + fileToLoad.getName());
                displayFrame.setSize(700, 500);
                
                JTextArea textArea = new JTextArea(content.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                displayFrame.add(scrollPane);
                displayFrame.setLocationRelativeTo(this);
                displayFrame.setVisible(true);
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error loading data: " + ex.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Main method to launch the application
     * @param args - Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SubscriptionGUI());
    }
}