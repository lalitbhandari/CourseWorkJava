package CourseWork.courseWork.course;

public abstract class AiModule {

    private String modelName;
    private double price;
    private int    parameterCount;
    private String contextWindow;
    private int    availableTokens;

    public AiModule(String modelName, double price,
                    int parameterCount, String contextWindow,
                    int availableTokens) {
        this.modelName       = modelName;
        this.price           = price;
        this.parameterCount  = parameterCount;
        this.contextWindow   = contextWindow;
        this.availableTokens = availableTokens;
    }

    public abstract String getPlanDescription();

    // Getters
    public String getModelName()       { 
        return modelName;       }
    public double getPrice()           { 
        return price;           }
    public int    getParameterCount()  { 
        return parameterCount;  }
    public String getContextWindow()   { 
        return contextWindow;   }
    public int    getAvailableTokens() { 
        return availableTokens; }

    // Setters
    public void setModelName(String v)     { 
        this.modelName       = v; }
    public void setPrice(double v)         { 
        this.price           = v; }
    public void setParameterCount(int v)   { 
        this.parameterCount  = v; }
    public void setContextWindow(String v) { 
        this.contextWindow   = v; }
    public void setAvailableTokens(int v)  { 
        this.availableTokens = v; }

    // Deducts tokens; returns false if not enough
    public boolean deductTokens(int amount) {
        if (availableTokens >= amount) {
            availableTokens -= amount;
            return true;
        }
        return false;
    }

    // Adds tokens to available balance
    public void buyTokens(int amount) {
        if (amount > 0) {
            this.availableTokens += amount;
        }
    }

    @Override
    public String toString() {
        return "Model: "     + modelName      + "\n"
             + "Price: NPR " + price          + "\n"
             + "Params: "    + parameterCount + "B\n"
             + "Context: "   + contextWindow  + "\n"
             + "Tokens: "    + availableTokens;
    }
}
