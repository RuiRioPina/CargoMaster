package lapr.project.model;

import lapr.project.utils.Utils;
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

    @Test
    public void testCalculateShipCapacity_Success(){
        double length = Utils.convertFeetToMeters(64);
        double width = Utils.convertFeetToMeters(64);
        int expected = 4;
        ShipCharacteristics underTest = new ShipCharacteristics();
        int actual = underTest.calculateShipCapacity(length, width);

        assertNotEquals(expected, actual);
    }

    @Test
    public void testCalculateShipCapacity_NegativeLength(){
        double length = -1;
        double width = 1;
        int expected = 0;
        ShipCharacteristics underTest = new ShipCharacteristics();
        int actual = underTest.calculateShipCapacity(length, width);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateShipCapacity_NegativeWidth(){
        double length = Utils.convertFeetToMeters(100);
        double width = -1;
        int expected = -16;
        ShipCharacteristics underTest = new ShipCharacteristics();
        int actual = underTest.calculateShipCapacity(length, width);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateShipCapacity_NegativeHeight(){
        double length = Utils.convertFeetToMeters(20);
        double width = Utils.convertFeetToMeters(20);
        double localHeight = Utils.convertFeetToMeters(-1);
        ShipCharacteristics underTest = new ShipCharacteristics();
        int result = underTest.calculateShipCapacity(length, width);

        assertEquals(20, result);
    }

    @Test
    public void testCalculateShipCapacity_ZeroLength(){
        double length = 0;
        double width = 10;
        int expected = 0;
        ShipCharacteristics underTest = new ShipCharacteristics();
        int actual = underTest.calculateShipCapacity(length, width);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateShipCapacity_ZeroWidth(){
        double length = Utils.convertFeetToMeters(64);
        double width = 0;
        int expected = 1;
        ShipCharacteristics underTest = new ShipCharacteristics();
        int actual = underTest.calculateShipCapacity(length, width);

        assertNotEquals(expected, actual);
    }

    @Test
    public void testCalculateShipCapacity_ZeroHeight(){
        double length = 10;
        double width = 10;
        double localHeight = 0;
        ShipCharacteristics underTest = new ShipCharacteristics();
        int result = underTest.calculateShipCapacity(length, width);

        assertEquals(53, result);
    }

    @Test
    public void testCalculateShipCapacity_ZeroVolumeContainer(){
        double length = 10;
        double width = 10;
        double localHeight = Utils.convertFeetToMeters(64);
        ShipCharacteristics underTest = new ShipCharacteristics();
        int result = underTest.calculateShipCapacity(length, width);

        assertEquals(53, result);
    }

    @Test
    public void testCalculateShipCapacity_ZeroVolumeShip(){
        double length = 0;
        double width = 0;
        double localHeight = Utils.convertFeetToMeters(64);
        ShipCharacteristics underTest = new ShipCharacteristics();
        int result = underTest.calculateShipCapacity(length, width);

        assertEquals(0, result);
    }

    @Test
    public void testEquals_Success(){
        ShipCharacteristics ship1 = new ShipCharacteristics(10, 100.0, 100.0, 100.0);
        ShipCharacteristics ship2 = new ShipCharacteristics(10, 100.0, 100.0, 100.0);

        assertEquals(ship1, ship2);
    }

    @Test
    public void testShipCharacteristics_Success(){
        ShipCharacteristics ship = new ShipCharacteristics(10, 100.0, 100.0, 100.0);
        assertEquals(10, ship.getVesselType());
        assertEquals(100.0, ship.getLength(), 0.0);
        assertEquals(100.0, ship.getWidth(), 0.0);
        assertEquals(100.0, ship.getDraft(), 0.0);
        assertEquals(5374.0, ship.getCapacity(), 0.0);
    }
}