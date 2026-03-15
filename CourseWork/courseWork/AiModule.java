package CourseWork.courseWork;



/**
 * Write a description of class AIModule here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
// parent class
public class AiModule
{
    private String modelName;
    private double price;
    private int parameterCount;
    private String contextWindow;
    
    public AiModule(String modelName, double price, int parameterCount, String contextWindow){
        this.modelName = modelName;
        this.price = price;
        this.parameterCount = parameterCount;
        this.contextWindow = contextWindow;
    }
    // gatter setter method
    public String getModelName(){
        return modelName;
    }
    public void setModelName(String newModelName){
        this.modelName =newModelName;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double newPrice){
        this.price = newPrice;
    }
    public int getParameterCount(){
        return parameterCount;
    }
    public void setParameterCount(int newParameterCount){
        this.parameterCount =newParameterCount;
    }
    public String getContextWindow(){
        return contextWindow;
    }
    public void setContextWindow( String newContextWindow){
        this.contextWindow = newContextWindow;
    }
    // toString method 
    @Override
    public  String toString(){
        return  "Model Name: " + modelName + "\n" +
               "Price: NPR " + price + " per 1 Lakh tokens\n" +
               "Parameters: " + parameterCount + " billion\n" +
               "Context Window: " + contextWindow + " tokens";
    
    }
}
