package agentic.workflow;

public class WorkflowStep {
    private String name;
    private String prompt;
    private String systemPrompt;

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
}
