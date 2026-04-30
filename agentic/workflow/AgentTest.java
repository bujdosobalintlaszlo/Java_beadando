package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test; 

public class AgentTest {
    //sima hozzaadas tesztje
    @Test 
    public void addStepTest1(){
        Agent testAgent = new Agent("testAgent");
        int stepsBefore=testAgent.getStepCount();
        testAgent.addStep(new WorkflowStep("testStepName1", "testPrompt1", "testSystemPrompt1", new StructuredOutput(new SchemaType[]{SchemaType.BOOLEAN})));
        int stepsAfter = testAgent.getStepCount();
        int out = stepsAfter - stepsBefore;
        assertEquals(1,out); 
    }

    //duplikalt elem hozzaadasa
    @Test 
    public void addStepTest2(){
        Agent testAgent = new Agent("testAgent");
        int stepsBefore=testAgent.getStepCount();
        WorkflowStep step = new WorkflowStep("testStepName1", "testPrompt1", "testSystemPrompt1", new StructuredOutput(new SchemaType[]{SchemaType.BOOLEAN}));
        testAgent.addStep(step);
        try {
            testAgent.addStep(step);
            fail("HIBA ADDSTEPBEN!");
        } catch (IllegalArgumentException e) {
            assertEquals("a lépés nem lehet `null`, és nem létezhet már másik lépés ugyanazzal a névvel.", e.getMessage());
        }
    }
}
