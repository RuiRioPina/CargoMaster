package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeContainerTest {

    @Test
    void testToString() {
        TypeContainer t = new TypeContainer("Refrigerated");
        String exp = " Type = Refrigerated" + '\'';
        assertEquals(exp,t.toString());
    }
}