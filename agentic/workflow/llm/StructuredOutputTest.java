package agentic.workflow.llm;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class StructuredOutputTest {

    //muszaly megtalalnia
    @Test
    public void sizeTest(){
        SchemaType[] sctTest = {SchemaType.BOOLEAN,SchemaType.INT,SchemaType.LIST_INT,SchemaType.LIST_STRING,SchemaType.MAP_STRING_STRING,SchemaType.STRING};
        StructuredOutput sc = new StructuredOutput(sctTest);
        assertEquals(sc.size(),8);
    }
}
