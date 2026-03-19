package CourseWork;


/**
 * Write a description of class personalPlan here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

// Child Classes
public class PersonalPlan extends AiModule {
    int remainingPrompts;

    public PersonalPlan(String modelName, double price, int parameterCount, String contextWindow, int remainingPrompts){
        super(modelName, price, parameterCount, contextWindow);
        this.remainingPrompts = remainingPrompts;
    }

    // getter setter method
    public int getRemainingPrompts(){
        return remainingPrompts;
    }

    public void setRemainingPrompts(int newRemainingPrompts){
        this.remainingPrompts = newRemainingPrompts;
    }

    // PersonalPlan User can purchasePrompt when prompts is Zero
    public String purchasePrompts(int prompts){
        if(prompts < 0){
            return "Error: You must enter a positive value or upgrade to Pro Plan.";
        }
        remainingPrompts += prompts;
        return "Successfully added " + prompts + " prompts. New quota: " + remainingPrompts;
    }

    // for prompt text
    public String enterPrompt(String promptText, int tokenLength) {
        if (remainingPrompts > 0) {
            remainingPrompts--;
            return "Prompt submitted successfully!\n" +
                   "Prompt: " + promptText + "\n" +
                   "Expected output length: " + tokenLength + " tokens\n" +
                   "Remaining prompts: " + remainingPrompts;
        } else {
            return "Error: Monthly prompt limit reached. Please purchase additional prompts or upgrade to Pro Plan.";
        }
    }

    // toString method
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Plan Type: Personal Plan\n" +
               "Remaining Prompts: " + remainingPrompts;
    }
}
