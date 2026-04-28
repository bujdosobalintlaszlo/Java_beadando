package agentic.workflow.llm;

public class StructuredOutput {
    private final SchemaType[] schemaTypes;

    public StructuredOutput(SchemaType[] schemaTypes) {
        if (schemaTypes == null || schemaTypes.length == 0) {
            throw new IllegalArgumentException("legalább egy sématípust meg kell adni.");
        }
    
        for (SchemaType type : schemaTypes) {
            if (type == null) {
                throw new NullPointerException("a megadott sématípusok között nem lehet `null`.");
            }
        }

        this.schemaTypes = schemaTypes.clone();
    }
    public int size() {
        return schemaTypes.length;
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

}