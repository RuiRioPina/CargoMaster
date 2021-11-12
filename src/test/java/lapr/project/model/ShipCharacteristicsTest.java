package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipCharacteristicsTest {

    @Test
    void testEquals() {
        ShipCharacteristics actual = new ShipCharacteristics(30,30.0,30.0,30.0);
        ShipCharacteristics expect = new ShipCharacteristics(30,30.0,30.0,30.0);
        boolean result = actual.equals(expect);
        assertTrue(result);
    }

    @Test
    void testEquals1() {
        ShipCharacteristics actual = new ShipCharacteristics(30,30.0,30.0,30.0);
        ShipCharacteristics expect = null;
        boolean result = actual.equals(expect);
        assertFalse(result);
    }

    @Test
    void testEquals2() {
        ShipCharacteristics actual = new ShipCharacteristics(30,30.0,30.0,30.0);
        ShipCharacteristics expect = new ShipCharacteristics(30,23.0,30.0,30.0);
        boolean result = actual.equals(expect);
        assertFalse(result);
    }
}