package CourseWork;
/**
 * Child Class: PersonalPlan
 * User plan with limited monthly prompts.
 */
class PersonalPlan extends AiModule {

    private int remainingPrompts;

    public PersonalPlan(String modelName, double price, int parameterCount, String contextWindow, int remainingPrompts) {
        super(modelName, price, parameterCount, contextWindow);
        this.remainingPrompts = remainingPrompts;
    }

    // Getter and Setter
    public int getRemainingPrompts() {
        return remainingPrompts;
    }

    public void setRemainingPrompts(int newRemainingPrompts) {
        if (newRemainingPrompts >= 0) {
            this.remainingPrompts = newRemainingPrompts;
        }
    }

    // Purchase more prompts
    public String purchasePrompts(int prompts) {
        if (prompts <= 0) {
            return "Error: You must enter a positive value or upgrade to Pro Plan.";
        }
        remainingPrompts += prompts;
        return "Successfully added " + prompts + " prompts. New quota: " + remainingPrompts;
    }

    // Enter a prompt
    public String enterPrompt(String promptText, int tokenLength) {
        if (promptText == null || promptText.isEmpty()) {
            return "Error: Prompt text cannot be empty.";
        }

        if (tokenLength <= 0) {
            return "Error: Token length must be greater than 0.";
        }

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

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Plan Type: Personal Plan\n" +
               "Remaining Prompts: " + remainingPrompts;
    }
}