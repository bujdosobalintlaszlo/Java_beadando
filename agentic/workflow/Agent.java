package agentic.workflow;
import agentic.workflow.llm.SchemaType;
import agentic.workflow.llm.StructuredOutput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        if(s == null) throw new IllegalArgumentException();
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

    public static Agent loadAgent(String filename) throws IOException,WorkflowFormatException{
        Agent a;
        try (
            BufferedReader br = new BufferedReader(new FileReader(filename));
        ) {

            String name = br.readLine();
            if(name == null) return null;
            String[] splitName = name.strip().split(":");
            if(!checkName(splitName[1])) {
                throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
            }
            a = new Agent(splitName[1]);
            while(br.readLine().equals("STEP")){
                WorkflowStep wstp = parseStep(br);
                a.addStep(wstp);
            }
            
        } catch (IOException e) {
            return null;
        }

        return a;
    }

    private static WorkflowStep parseStep(BufferedReader reader) throws IOException,WorkflowFormatException{
        if(reader.readLine().equals("STEP")){
            
            //name
            String[] name = reader.readLine().strip().split("=");
            if(!name[0].equals("name")){
                throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
            }
            
            //prompt
            String[] prompt = reader.readLine().strip().split("=");
            if(!prompt[0].equals("prompt")){
                throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
            }

            //systemPrompt
            String[] systemPrompt = reader.readLine().strip().split("=");
            if(!systemPrompt[0].equals("systemPrompt")){
                throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
            }

            //output
            String[] output = reader.readLine().strip().split("=");
            if(!output[0].equals("output")){
                throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
            }

            String[] stout = output[1].split(" ");
            SchemaType[] s = new SchemaType[stout.length];

            if(!reader.readLine().equals("ENDSTEP")){
                throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
            }
            for (int i = 0; i < stout.length; i++) {
                try {
                    s[i] = SchemaType.valueOf(stout[i]);
                } catch (IllegalArgumentException e) {
                    throw new WorkflowFormatException("Nem létező típus: " + stout[i]);
                }
            }
            WorkflowStep w = new WorkflowStep(name[1], prompt[1], systemPrompt[1], new StructuredOutput(s));
            return w;
        }

        throw new WorkflowFormatException("ha a lépés tartalma hibás vagy hiányos.");
    }


}
