package CourseWork.courseWork.course;

import java.util.ArrayList;

public class ProPlan extends AiModule {

    public static final int MAX_GROUP_SIZE = 10;
    private ArrayList<String> groupMembers;

    public ProPlan(String modelName, double price,
                   int parameterCount, String contextWindow,
                   int availableTokens) {
        super(modelName, price, parameterCount, contextWindow, availableTokens);
        this.groupMembers = new ArrayList<>();
    }

    public String addMember(String name) {
        if (name == null || name.trim().isEmpty())
            return "ERROR: Name cannot be empty.";
        if (groupMembers.size() >= MAX_GROUP_SIZE)
            return "Group is full! Maximum " + MAX_GROUP_SIZE + " members allowed.";
        if (groupMembers.contains(name.trim()))
            return "'" + name.trim() + "' is already in the group.";
        groupMembers.add(name.trim());
        return "'" + name.trim() + "' added to group.\n"
             + "Members: " + groupMembers.size() + " / " + MAX_GROUP_SIZE;
    }

    public String removeMember(String name) {
        if (name == null || name.trim().isEmpty())
            return "ERROR: Name cannot be empty.";
        boolean removed = groupMembers.remove(name.trim());
        if (removed)
            return "'" + name.trim() + "' removed from group.\n"
                 + "Members: " + groupMembers.size() + " / " + MAX_GROUP_SIZE;
        return "'" + name.trim() + "' was not found in the group.";
    }

    public ArrayList<String> getGroupMembers() { 
        return groupMembers;          }
    public int getMemberCount()                 { 
        return groupMembers.size();   }
    public int getSlotsLeft()                   { 
        return MAX_GROUP_SIZE - groupMembers.size(); }

    @Override
    public String getPlanDescription() {
        return "Pro Plan | Model: " + getModelName()
             + " | Members: " + getMemberCount() + "/" + MAX_GROUP_SIZE;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPlan Type: Pro Plan"
             + "\nGroup Members: " + groupMembers;
    }
}
