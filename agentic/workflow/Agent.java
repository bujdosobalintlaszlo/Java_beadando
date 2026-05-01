package agentic.workflow;
import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Agent {
    private String name;
    private final List<WorkflowStep> steps;
    
    public String getName() {return name;}
    public void setName(String name) {this.name=name;}

    public List<WorkflowStep> getSteps() {return Collections.unmodifiableList(steps);}

    public Agent(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("A név nem lehet null, üres vagy csak szóközökből álló.");
        }
        this.name = name;
        this.steps = new ArrayList<>();
    }
    public void addStep(WorkflowStep s) throws IllegalArgumentException{
        if(s == null || steps.contains(s)) throw new IllegalArgumentException("a lépés nem lehet `null`, és nem létezhet már másik lépés ugyanazzal a névvel.");
        this.steps.add(s);
    }
    public int getStepCount() {return steps.size();}

    public WorkflowStep findStepByName(String stepName){
        if(stepName == null){
            throw new IllegalArgumentException("a lépés neve nem lehet `null`, üres vagy csak szóközökből álló.");
        }
        for(WorkflowStep step : steps){
            if(step.getName().equals(stepName)){
                return step;
            }
        }
        return null;
    }

    private static boolean checkName(String name) {
        return !(name.trim().isEmpty());
    }

    public void run(){
        for(WorkflowStep step : steps){
            System.out.println(step.getName() + " " + step.getStructuredOutput());
        }
    }
    public static Agent loadAgent(String filename) throws IOException, WorkflowFormatException {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("A fájlnév nem lehet null vagy üres.");
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String tempLine = br.readLine();
            if (tempLine == null) throw new WorkflowFormatException("A fájl üres.");
    
            String[] splitTempLine = tempLine.split(":");
            if (!splitTempLine[0].equals("AGENT")) {
                throw new WorkflowFormatException("Fájl tartalma hibás formátumú: hiányzó AGENT fejléc.");
            }

            String agentName = splitTempLine[1].trim();
            if(agentName.isEmpty()){
                throw new WorkflowFormatException("UresNev");
            }
            Agent a = new Agent(agentName);
    
            WorkflowStep step = null;
            while (true) {
                step = parseStep(br);
                if(step == null) break;
                a.addStep(step);
            }
            
            return a;
            
        } catch (IOException e) {
            throw e;
        }
    }
    private static WorkflowStep parseStep(BufferedReader reader) throws IOException,WorkflowFormatException{
        String tempLine = reader.readLine();
        if (tempLine == null || !tempLine.equals("STEP")) {
            return null; 
        }
        HashMap<String,String> a = new HashMap<>();
        tempLine = reader.readLine();
        while(!tempLine.equals("ENDSTEP")){
            String[] splitTemp = tempLine.split("=");
            a.put(splitTemp[0], splitTemp[1]);
            tempLine = reader.readLine();
        }
        String name = null;
        String prompt = null;
        String systemPrompt = null;
        SchemaType output = null;

        for(Map.Entry<String, String> rec : a.entrySet()){
            String key = rec.getKey();
            String val = rec.getValue();
            switch (key) {
                case "name":
                    name = val;
                    break;
                case "prompt":
                    prompt=val;
                    break;
                case "systemPrompt":
                    systemPrompt=val;
                    break;
                case "output":
                    output=getSchemaType(val);
                    break;
                default:
                    throw new WorkflowFormatException("hibas adat(ok)");
            }
        }
        SchemaType[] sc = {output};
        StructuredOutput sot = new StructuredOutput(sc);
        WorkflowStep w = new WorkflowStep(name, prompt, systemPrompt,sot);
        return w;
    }
    private static SchemaType getSchemaType(String name){
        switch(name){
            case "INT":
            return SchemaType.INT;
        case "STRING":
            return SchemaType.STRING;
        case "BOOLEAN":
            return SchemaType.BOOLEAN;
        case "LIST_INT":
            return SchemaType.LIST_INT;
        case "LIST_STRING":
            return SchemaType.LIST_STRING;
        case "MAP_STRING_STRING":
            return SchemaType.MAP_STRING_STRING;
        default: 
            return null;
        }
    }
    
}
