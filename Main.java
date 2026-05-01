import agentic.workflow.Agent;

public class Main {
    public static void main(String[] args) {
        try {
            Agent a = Agent.loadAgent("noname.agent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
