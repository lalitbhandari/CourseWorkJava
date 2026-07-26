package CourseWork.courseWork;




// ─────────────────────────────────────────────────────────────
//  ABSTRACT PARENT CLASS: AiModule
//  All plan types (PersonalPlan, ProPlan) must extend this.
//  Abstract method getPlanDescription() must be overridden.
// ─────────────────────────────────────────────────────────────
public abstract class AiModule {

    private String modelName;
    private double price;
    private int    parameterCount;
    private String contextWindow;

    // ── Constructor ──────────────────────────────────────────
    public AiModule(String modelName, double price,
                    int parameterCount, String contextWindow) {
        this.modelName      = modelName;
        this.price          = price;
        this.parameterCount = parameterCount;
        this.contextWindow  = contextWindow;
    }

    // ── Abstract method: every child MUST implement this ─────
    public abstract String getPlanDescription();

    // ── Getters ──────────────────────────────────────────────
    public String getModelName()      { return modelName;      }
    public double getPrice()          { return price;          }
    public int    getParameterCount() { return parameterCount; }
    public String getContextWindow()  { return contextWindow;  }

    // ── Setters ──────────────────────────────────────────────
    public void setModelName(String modelName)         { this.modelName      = modelName;      }
    public void setPrice(double price)                 { this.price          = price;          }
    public void setParameterCount(int parameterCount)  { this.parameterCount = parameterCount; }
    public void setContextWindow(String contextWindow) { this.contextWindow  = contextWindow;  }

    // ── toString ─────────────────────────────────────────────
    @Override
    public String toString() {
        return "Model Name: "     + modelName      + "\n"
             + "Price: NPR "      + price          + " per 1 Lakh tokens\n"
             + "Parameters: "     + parameterCount + " billion\n"
             + "Context Window: " + contextWindow  + " tokens";
    }
}
