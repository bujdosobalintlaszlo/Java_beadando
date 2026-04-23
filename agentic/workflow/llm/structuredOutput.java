package agentic.workflow.llm;

public class StructuredOutput {
    private final SchemaType[] schemaTypes;

    public StructuredOutput(SchemaType[] schemaTypes) {
        if (schemaTypes == null || schemaTypes.length <= 0) {
            throw new IllegalArgumentException("Ures schemaType jott parameterben!");
        }
        this.schemaTypes = schemaTypes.clone();
    }

    public SchemaType[] getSchemaTypes() {
        return schemaTypes.clone();
    }

    public boolean contains(SchemaType schemaType) {
        if (schemaType == null) return false;
        
        for (SchemaType s : schemaTypes) {
            if (s == schemaType) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return schemaTypes.length;
    }
}