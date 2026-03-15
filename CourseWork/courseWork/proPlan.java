package CourseWork.courseWork;


/**
 * Write a description of class proPlan here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
// child Class
public class proPlan extends AiModule
{
     private int availableSlots;
     public proPlan(String modelName, double price, int parameterCount, String contextWindow, int availableSlots){
         super( modelName, price, parameterCount, contextWindow);
         this.availableSlots =availableSlots;
     }
     // gatter setter method 
     public int getAvailableSlots(){
         return availableSlots;
     }
     
     public void setAvailanleSlots( int newAvailableSlots){
         this.availableSlots= newAvailableSlots;
     }
     // method for Proplan user which can add member 
     public String addTeamMember(String memberName) {
         // if  availableSlots is greater than 0 than proplan user can add whithout Not
        if (availableSlots > 0) {
            availableSlots--;
            return "Team member '" + memberName + "' added successfully!\n" +
                   "Available slots remaining: " + availableSlots;
        } else {
            return "Error: No available slots. Cannot add team member '" + memberName + "'.";
        }
    }
    // method for remove group member
    public String removeTeamMember(String memberName) {
        availableSlots++;
        return "Team member '" + memberName + "' removed successfully!\n" +
               "Available slots: " + availableSlots;
    }
    // toString method
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Plan Type: Pro Plan\n" +
               "Available Team Slots: " + availableSlots;
    }
    
}
