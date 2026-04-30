//import agentic.*;
import agentic.workflow.Agent;
public class Main {
    public static void main(String[] args) {
        Agent a = new Agent("test1");
        a.loadAgent("calculator.agent"); 
        a.run();
    }
}
