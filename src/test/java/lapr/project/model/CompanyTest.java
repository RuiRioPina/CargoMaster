package lapr.project.model;

import lapr.project.data.ShipStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    Company cmp = new Company("Ships");
    @Test
    void getNumberOfEmployees() {
        assertEquals(0,cmp.getNumberOfEmployees());
    }

    @Test
    void getDesignation() {
        assertEquals("Ships",cmp.getDesignation());
    }

    @Test
    void setNumberOfEmployees() {
        cmp.setNumberOfEmployees(1);
        assertEquals(1,cmp.getNumberOfEmployees());
    }

    @Test
    void getShipStore() {
        assertNotEquals(new ShipStore(),cmp.getShipStore());
    }
}