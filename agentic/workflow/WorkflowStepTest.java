package agentic.workflow;

import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class WorkflowStepTest {
    public void testExpectsStructuredOutput(){
        WorkflowStep step = new WorkflowStep("testStepName1", "testPrompt1", "testSystemPrompt1", new StructuredOutput(new SchemaType[]{SchemaType.BOOLEAN}));
        assertEquals(true,step.expectsStructuredOutput());
    }
}
