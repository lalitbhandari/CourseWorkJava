package CourseWork.courseWork;

public class PersonalPlan extends AIModel {
    private int dailyLimit;
    private int availableTokens;

    public PersonalPlan(String modelName, double price, String parameters, int dailyLimit, int availableTokens) {
        super(modelName, price, parameters);
        this.dailyLimit = dailyLimit;
        this.availableTokens = availableTokens;
    }

    public int getDailyLimit() { return dailyLimit; }
    public int getAvailableTokens() { return availableTokens; }

    @Override
    public String getPlanType() {
        return "Personal Plan";
    }

    @Override
    public String getExtraInfo() {
        return "Daily Limit: " + dailyLimit + " | Tokens: " + availableTokens;
    }
}