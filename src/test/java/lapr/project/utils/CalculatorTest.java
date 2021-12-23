package lapr.project.utils;

import lapr.project.model.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void testCalculateLocationDifference() throws Exception {
        Location location1= new Location("2.45","4.32");
        Location location2 = new Location("20.3","0.3");
        Location location3 = new Location("2.45","4.32");
        assertEquals(0,Calculator.calculateLocationDifference(location1,location3));
        assertEquals(2032.1026112670418,Calculator.calculateLocationDifference(location1,location2));
    }
}
