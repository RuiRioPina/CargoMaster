package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipIdleDaysTest {

    @Test
    void toString1() {
        ShipIdleDays one = new ShipIdleDays("12345",2);
        assertEquals(one.toString(),"MMSI = 12345  Idle Time = 2 days");
    }
}