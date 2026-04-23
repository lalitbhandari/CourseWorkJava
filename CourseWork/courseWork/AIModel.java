package CourseWork.courseWork;

import java.io.Serializable;

public abstract class AIModel implements Serializable {
    protected String modelName;
    protected double price;
    protected String parameters;

    public AIModel(String modelName, double price, String parameters) {
        this.modelName = modelName;
        this.price = price;
        this.parameters = parameters;
    }

    public String getModelName() { 
        return modelName; }
    public double getPrice() {
        return price; }
    public String getParameters() { 
        return parameters; }

    public abstract String getPlanType();
    public abstract String getExtraInfo();

    @Override
    public String toString() {
        return getPlanType() + " | " + modelName + " | NPR " + price + " | " + parameters;
    }
}