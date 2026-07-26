package CourseWork.courseWork;



// ─────────────────────────────────────────────────────────────
//  CHILD CLASS: ProPlan  (extends AiModule)
//  Extra field  : availableSlots
//  Extra methods: addTeamMember(), removeTeamMember()
// ─────────────────────────────────────────────────────────────
public class ProPlan extends AiModule {

    private int availableSlots;

    // ── Constructor ──────────────────────────────────────────
    public ProPlan(String modelName, double price,
                   int parameterCount, String contextWindow,
                   int availableSlots) {
        super(modelName, price, parameterCount, contextWindow);
        this.availableSlots = availableSlots;
    }

    // ── Getter / Setter ──────────────────────────────────────
    public int  getAvailableSlots()              { return availableSlots; }
    public void setAvailableSlots(int newSlots)  { this.availableSlots = newSlots; }

    // ── Add a team member (consumes one slot) ─────────────────
    public String addTeamMember(String memberName) {
        if (availableSlots > 0) {
            availableSlots--;
            return "Team member '" + memberName + "' added successfully!\n"
                 + "Available slots remaining: " + availableSlots;
        }
        return "Error: No available slots.\n"
             + "Cannot add team member '" + memberName + "'.";
    }

    // ── Remove a team member (frees one slot) ─────────────────
    public String removeTeamMember(String memberName) {
        availableSlots++;
        return "Team member '" + memberName + "' removed successfully!\n"
             + "Available slots: " + availableSlots;
    }

    // ── Abstract method implementation ────────────────────────
    @Override
    public String getPlanDescription() {
        return "Pro Plan | Model: "        + getModelName()
             + " | Available Slots: "      + availableSlots;
    }

    // ── toString ─────────────────────────────────────────────
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Plan Type: Pro Plan\n"
             + "Available Team Slots: " + availableSlots;
    }
}
