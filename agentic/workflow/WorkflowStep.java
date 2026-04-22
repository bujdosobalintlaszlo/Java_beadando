package agentic.workflow;
import agentic.workflow.llm.*;
public class WorkflowStep {
    private String name;
    private String prompt;
    private String systemPrompt;
    private StructuredOutput structuredOutput;

    public String getName() {return name;}
    public void setName(String name) {
        if(name.isEmpty()){
            throw new IllegalArgumentException("Nem lehet ures nevet megadni!");
        }
        this.name=name;
    }

    public String getPrompt() {return prompt;}
    public void setPrompt(String prompt) {
        if (prompt.isEmpty()) {
            throw new IllegalArgumentException("Nem lehet ures promtot megadni!");
        }
        this.prompt=prompt;
    }

    public String getSystemPrompt() {return systemPrompt;}
    public void setSystemPrompt(String systemPrompt){
        if (systemPrompt.isEmpty()) {
            throw new IllegalArgumentException("Nem lehet ures promtot megadni!");
        }
        this.systemPrompt=systemPrompt;

    }

    public StructuredOutput getStructuredOutput() {return structuredOutput;}

    public void setStructuredOutput(StructuredOutput structuredOutput){
        if(structuredOutput == null){
            throw new IllegalArgumentException("Nem lehet structuredOutput null!");
        }
        this.structuredOutput=structuredOutput;
    }

    public WorkflowStep(String name,String prompt,String systemPrompt,StructuredOutput structuredOutput){
        if(name.isEmpty() || prompt.isEmpty() || systemPrompt.isEmpty() || structuredOutput == null){
            throw new IllegalArgumentException("Hibas ures adat a constructorbam!");
        }
        this.name=name;
        this.prompt=prompt;
        this.systemPrompt=systemPrompt;
        this.structuredOutput=structuredOutput;
    }

    public boolean expectsStructuredOutput(){
        return structuredOutput != null;
    }

    public String methodSimulateResponse(){
        SchemaType s = structuredOutput.getSchemaTypes().getFirst();
        
    }
}
