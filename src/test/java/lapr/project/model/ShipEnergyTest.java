package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipEnergyTest {

    @Test
    void getNrGenerators() {
        ShipEnergy se = new ShipEnergy(1,2);
        assertEquals(1,se.getNrGenerators());
    }

    @Test
    void getPower() {
        ShipEnergy se = new ShipEnergy(1,2);
        assertEquals(2,se.getPower());
    }

    @Test
    void getIdEnergy() {
        ShipEnergy se = new ShipEnergy(1,2);
        se.setIdEnergy(1);
        assertEquals(1,se.getIdEnergy());
        se.setIdEnergy(-1);
        assertEquals(-1,se.getIdEnergy());
        assertNotEquals(4,se.getIdEnergy());
    }

}