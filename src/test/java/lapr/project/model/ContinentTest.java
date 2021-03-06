package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ContinentTest {

    private Continent continentUnderTest;

    @BeforeEach
    void setUp() throws Exception {
        continentUnderTest = new Continent("continent");
    }

    @Test
    void testToString() throws Exception {
        assertEquals("Continent{continent=continent}", continentUnderTest.toString());
    }
    @Test
    void  getName() throws Exception{
        assertEquals("continent",continentUnderTest.getName());
        assertNotEquals(" continent", continentUnderTest.getName());
    }
}
