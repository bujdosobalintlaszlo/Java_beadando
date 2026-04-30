package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.beans.Transient;

import org.junit.jupiter.api.Test; 

public class AgentTest {
    //sima hozzaadas tesztje
    @Test 
    public void testStepCount(){
        Agent testAgent = new Agent("testAgent");
        int stepsBefore=testAgent.getStepCount();
        testAgent.addStep(new WorkflowStep("testStepName1", "testPrompt1", "testSystemPrompt1", new StructuredOutput(new SchemaType[]{SchemaType.BOOLEAN})));
        int stepsAfter = testAgent.getStepCount();
        int out = stepsAfter - stepsBefore;
        assertEquals(1,out); 
    }

    //duplikalt elem hozzaadasa
    @Test 
    public void testAddDuplicateStepRejected(){
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

    @Test 
    public void findStepByName(){
        Agent testAgent = new Agent("testAgent");;
        WorkflowStep step = new WorkflowStep("testStepName1", "testPrompt1", "testSystemPrompt1", new StructuredOutput(new SchemaType[]{SchemaType.BOOLEAN}));
        testAgent.addStep(step);
        assertEquals(step,testAgent.findStepByName(step.getName()));
    }

    @Test 
    public void findStepByNameMissing(){
        Agent testAgent = new Agent("testAgent");
        WorkflowStep step = new WorkflowStep("testStepName1", "testPrompt1", "testSystemPrompt1", new StructuredOutput(new SchemaType[]{SchemaType.BOOLEAN}));
        testAgent.addStep(step);
        assertEquals(null,testAgent.findStepByName("ezbiztosanhianyzik"));
    }
}
