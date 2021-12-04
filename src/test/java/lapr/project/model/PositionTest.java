package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testToString() {
        Position pos = new Position(1,1,1);
       String exp =  "[X = 1, Y = 1, Z = 1]";
       assertEquals(exp, pos.toString());

    }
}