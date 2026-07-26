package CourseWork.courseWork;




// ─────────────────────────────────────────────────────────────
//  CHILD CLASS: PersonalPlan  (extends AiModule)
//  Extra field  : remainingPrompts
//  Extra methods: purchasePrompts(), enterPrompt()
// ─────────────────────────────────────────────────────────────
public class PersonalPlan extends AiModule {

    private int remainingPrompts;

    // ── Constructor ──────────────────────────────────────────
    public PersonalPlan(String modelName, double price,
                        int parameterCount, String contextWindow,
                        int remainingPrompts) {
        super(modelName, price, parameterCount, contextWindow);
        this.remainingPrompts = remainingPrompts;
    }

    // ── Getter / Setter ──────────────────────────────────────
    public int  getRemainingPrompts()              { return remainingPrompts;  }
    public void setRemainingPrompts(int newPrompts){ this.remainingPrompts = newPrompts; }

    // ── Buy more prompts ──────────────────────────────────────
    public String purchasePrompts(int prompts) {
        if (prompts < 0)
            return "Error: You must enter a positive value or upgrade to Pro Plan.";
        remainingPrompts += prompts;
        return "Successfully added " + prompts + " prompts.\n"
             + "New quota: " + remainingPrompts;
    }

    // ── Send a prompt (uses one quota unit) ───────────────────
    public String enterPrompt(String promptText, int tokenLength) {
        if (remainingPrompts > 0) {
            remainingPrompts--;
            return "Prompt submitted successfully!\n"
                 + "Prompt: "                  + promptText  + "\n"
                 + "Expected output length: "  + tokenLength + " tokens\n"
                 + "Remaining prompts: "       + remainingPrompts;
        }
        return "Error: Monthly prompt limit reached.\n"
             + "Please purchase additional prompts or upgrade to Pro Plan.";
    }

    // ── Abstract method implementation ────────────────────────
    @Override
    public String getPlanDescription() {
        return "Personal Plan | Model: " + getModelName()
             + " | Remaining Prompts: "  + remainingPrompts;
    }

    // ── toString ─────────────────────────────────────────────
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Plan Type: Personal Plan\n"
             + "Remaining Prompts: " + remainingPrompts;
    }
}
