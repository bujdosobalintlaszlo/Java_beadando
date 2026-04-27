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
    private final ArrayList<WorkflowStep> steps= new ArrayList<>();
    
    public String getName() {return name;}
    public void setName(String name) {this.name=name;}

    
    public List<WorkflowStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    public Agent(String name){
        if(name == null){
            throw new IllegalArgumentException("a név nem lehet `null`, üres vagy csak szóközökből álló.");
        }
        this.name=name;
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

    public void run(){
        for(WorkflowStep step : steps){
            System.out.println(step.getName() + " " + step.getStructuredOutput());
        }
    }

    public static Agent loadAgent(String filename){
        Agent a;
        try (
            BufferedReader br = new BufferedReader(new FileReader(filename));
        ) {

            String name = br.readLine();
            if(name == null) return null;
            String[] splitName = name.strip().split(":");
            a = new Agent(splitName[1]);
            
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
