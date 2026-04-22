package agentic.workflow.llm;

import java.util.Collections;
import java.util.List;
public class StructuredOutput {
    List<SchemaType>schemaTypes;

    public List<SchemaType> getSchemaTypes(){return Collections.unmodifiableList(schemaTypes);}
    public void setSchemaTypes(List<SchemaType> schemaTypes){
        if(schemaTypes.isEmpty()){
            throw new IllegalArgumentException("Ures schemaType jott paramterben!");
        }
        this.schemaTypes = schemaTypes;
    }

    public StructuredOutput(List<SchemaType> schemaTypes){
        if(schemaTypes.isEmpty()){
            throw new IllegalArgumentException("Ures schemaType jott paramterben!");
        }
        this.schemaTypes=schemaTypes;
    }

    public boolean contains(SchemaType schemaType){
        return schemaTypes.contains(schemaType);
    }

    public int methodSize(){return schemaTypes.size();}
}
