package agentic.workflow.llm;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class StructuredOutputTest {

    @Test
    public void sizeTest(){
        SchemaType[] sctTest = {SchemaType.BOOLEAN,SchemaType.INT,SchemaType.LIST_INT,SchemaType.LIST_STRING,SchemaType.MAP_STRING_STRING,SchemaType.STRING};
        StructuredOutput sc = new StructuredOutput(sctTest);
        assertEquals(6,sc.size());
    }

    @Test
    public void containsTest(){
        SchemaType[] sctTest = {SchemaType.BOOLEAN};
        StructuredOutput sc = new StructuredOutput(sctTest);
        
        assertEquals(true,sc.contains(SchemaType.BOOLEAN));
        assertEquals(false,sc.contains(SchemaType.INT));
    }

}
