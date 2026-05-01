import agentic.workflow.Agent;

public class Main {
    public static void main(String[] args) {
        try {
            Agent a = Agent.loadAgent("calculator.agent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
