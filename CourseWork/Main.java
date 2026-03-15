package CourseWork;

public class Main {
    public static void main(String[] args) {

        // Parent class
        AiModule ai1 = new AiModule("ChatGPT", 7, 4, "88K");
        System.out.println(ai1); // toString() called automatically
        System.out.println("-----------------------------");

        // Personal Plan
        PersonalPlan ai2 = new PersonalPlan("ChatGPT", 7832.5, 4, "88K", 8);
        System.out.println(ai2);
        System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        //System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        //System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        //System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        //System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        //System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        //System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        //System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        //System.out.println(ai2.enterPrompt("Write a short poem", 5000));
        System.out.println(ai2.purchasePrompts(5));
        System.out.println("----------------------------");

        // Pro Plan
        ProPlan ai3 = new ProPlan("ChatGPT", 7832.5, 422, "88K", 3);
        System.out.println(ai3);
        System.out.println(ai3.addTeamMember("Alice"));
        System.out.println(ai3.addTeamMember("Bob"));
        System.out.println(ai3.removeTeamMember("Alice"));
        System.out.println(ai3.addTeamMember("Charlie"));
        System.out.println("---------------------------");

        // Final state of ProPlan
        System.out.println(ai3);
    }
}