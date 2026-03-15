package CourseWork;

/**
 * Child Class: ProPlan
 * User plan with team slots to add/remove members.
 */
class ProPlan extends AiModule {

    private int availableSlots;

    public ProPlan(String modelName, double price, int parameterCount, String contextWindow, int availableSlots) {
        super(modelName, price, parameterCount, contextWindow);
        this.availableSlots = availableSlots;
    }

    // Getter and Setter
    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int newAvailableSlots) {
        if (newAvailableSlots >= 0) {
            this.availableSlots = newAvailableSlots;
        }
    }

    // Add team member
    public String addTeamMember(String memberName) {
        if (memberName == null || memberName.isEmpty()) {
            return "Error: Member name cannot be empty.";
        }

        if (availableSlots > 0) {
            availableSlots--;
            return "Team member '" + memberName + "' added successfully!\n" +
                   "Available slots remaining: " + availableSlots;
        } else {
            return "Error: No available slots. Cannot add team member '" + memberName + "'.";
        }
    }

    // Remove team member
    public String removeTeamMember(String memberName) {
        if (memberName == null || memberName.isEmpty()) {
            return "Error: Member name cannot be empty.";
        }

        availableSlots++;
        return "Team member '" + memberName + "' removed successfully!\n" +
               "Available slots: " + availableSlots;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Plan Type: Pro Plan\n" +
               "Available Team Slots: " + availableSlots;
    }
}