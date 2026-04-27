package agentic.workflow;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
public class Agent {
    private String name;
    private List<WorkflowStep> steps;
    
    public String getName() {return name;}
    public void setName(String name) {this.name=name;}

    public List<WorkflowStep> getSteps() {return Collections.unmodifiableList(steps);}
    public void setName(List<WorkflowStep> steps) {this.steps=steps;}

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

    public Agent loadAgent(String filename){
        try(
            BufferedReader r = new BufferedReader(new FileReader(filename));
        ){
            Agent a;

        }
        return a;
    }

}
