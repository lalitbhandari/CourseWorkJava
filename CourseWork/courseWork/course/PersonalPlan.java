package CourseWork.courseWork.course;

public class PersonalPlan extends AiModule {

    public PersonalPlan(String modelName, double price,
                        int parameterCount, String contextWindow,
                        int availableTokens) {
        super(modelName, price, parameterCount, contextWindow, availableTokens);
    }

    public String enterPrompt(String text) {
        if (text == null || text.trim().isEmpty())
            return "ERROR: Prompt cannot be empty.";

        String[] words = text.trim().split("\\s+");
        int cost = words.length;

        if (deductTokens(cost)) {
            return "Prompt accepted!\n"
                 + "Words entered  : " + cost + "\n"
                 + "Tokens used    : " + cost + "\n"
                 + "Tokens left    : " + getAvailableTokens() + "\n\n"
                 + "Simulated response from " + getModelName() + ":\n"
                 + "\"This is a simulated AI reply to your prompt.\"";
        } else {
            return "Not enough tokens!\n"
                 + "Tokens needed  : " + cost + "\n"
                 + "Tokens left    : " + getAvailableTokens() + "\n"
                 + "Please buy more tokens to continue.";
        }
    }

    @Override
    public String getPlanDescription() {
        return "Personal Plan | Model: " + getModelName()
             + " | Tokens left: " + getAvailableTokens();
    }

    @Override
    public String toString() {
        return super.toString() + "\nPlan Type: Personal Plan";
    }
}
