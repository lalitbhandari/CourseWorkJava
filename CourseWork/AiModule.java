package CourseWork;

/**
 * Parent Class: AIModule
 * Represents an AI model with pricing and specifications.
 */
public class AiModule {

    private String modelName;
    private double price;
    private int parameterCount;
    private String contextWindow;

    // Constructor
    public AiModule(String modelName, double price, int parameterCount, String contextWindow) {
        setModelName(modelName);
        setPrice(price);
        setParameterCount(parameterCount);
        setContextWindow(contextWindow);
    }

    // Getters and Setters with validation
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String newModelName) {
        if (newModelName != null && !newModelName.isEmpty()) {
            this.modelName = newModelName;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        if (newPrice >= 0) {
            this.price = newPrice;
        }
    }

    public int getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(int newParameterCount) {
        if (newParameterCount >= 0) {
            this.parameterCount = newParameterCount;
        }
    }

    public String getContextWindow() {
        return contextWindow;
    }

    public void setContextWindow(String newContextWindow) {
        if (newContextWindow != null && !newContextWindow.isEmpty()) {
            this.contextWindow = newContextWindow;
        }
    }

    // toString method
    @Override
    public String toString() {
        return "Model Name: " + modelName + "\n" +
               String.format("Price: NPR %.2f per 1 Lakh tokens\n", price) +
               "Parameters: " + parameterCount + " billion\n" +
               "Context Window: " + contextWindow + " tokens";
    }
}
