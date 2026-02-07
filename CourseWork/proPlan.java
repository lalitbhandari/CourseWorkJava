package CourseWork;


/**
 * Write a description of class proPlan here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class proPlan extends AIModule
{
     private int availableSlots;
     public proPlan(String modelName, double price, int parameterCount, String contextWindow, int availableSlots){
         super( modelName, price, parameterCount, contextWindow);
         this.availableSlots =availableSlots;
     }
     public int getAvailableSlots(){
         return availableSlots;
     }
     public void setAvailanleSlots( int newAvailableSlots){
         this.availableSlots= newAvailableSlots;
     }
     public String addTeamMember(String memberName) {
        if (availableSlots > 0) {
            availableSlots--;
            return "Team member '" + memberName + "' added successfully!\n" +
                   "Available slots remaining: " + availableSlots;
        } else {
            return "Error: No available slots. Cannot add team member '" + memberName + "'.";
        }
    }
    public String removeTeamMember(String memberName) {
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