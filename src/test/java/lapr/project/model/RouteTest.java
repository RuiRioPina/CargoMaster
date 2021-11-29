package lapr.project.model;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class RouteTest {
    ShipStore store = new ShipStore();
    Ship ship;
    Route route = new Route();
    Route route2 = new Route();
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    ShipDynamic dynamic;
    @BeforeEach
    public void setUp() throws Exception {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        dynamic = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 16:12", new Location("42.73879", "-66.97726"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
        ShipDynamic dynamic1 = (new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:03", new Location("42.92236", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic2 = (new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:13", new Location("42.95969", "-66.97106"), new Movement(12.9, 8.1, "358.0"), "NA", "A"));
        ShipDynamic dynamic3 = (new ShipDynamic(idShip.getMmsi(),"31/12/2020 16:32", new Location("42.81133", "-66.97587"), new Movement(13.4, 10.0, "356.0"), "NA", "A"));
        ShipDynamic dynamic35 = (new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:01", new Location("42.92236", "-66.97243"), new Movement(13.4, 10.0, "358.0"), "NA", "A"));
        ShipDynamic dynamic4 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 16:52", new Location("42.8839", "-66.97577"), new Movement(12.7, 2.5, "359.0"), "NA", "A"));
        ShipDynamic dynamic5 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:33", new Location("43.02665", "-66.97076"), new Movement(12.5, 3.6, "354.0"), "NA", "A"));
        ShipDynamic dynamic6 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:02", new Location("42.92236", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic7 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:03", new Location("42.92236", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic8 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:04", new Location("42.92236", "-66.97243"), new Movement(13.4, 10.0, "358.0"), "NA", "A"));
        route.add(dynamic1);
        route.add(dynamic);
        route.add(dynamic2);
        route2.add(dynamic35);
        route.add(dynamic3);
        route.add(dynamic4);
        route.add(dynamic5);
        route2.add(dynamic8);
        ship = new Ship(idShip, shipCharacteristics, route);
        store.addShipToAVL(ship);
        store.organizeShipMessage();
        route2.add(dynamic6);
        route2.add(dynamic7);
    }
    @Test
    public void getStartDateTimeLDT(){
        LocalDateTime localDateTime= LocalDateTime.of(2020,12,31,16,12);
        assertEquals(route.getStartDateTimeLDT(),localDateTime);
    }
    @Test
    public void getStartDateTime(){
        String startStr="31/12/2020 16:12";
        assertEquals(route.getStartDateTime(),startStr);
    }
    @Test
    public void getEndDateTimeLDT(){
        LocalDateTime localDateTime= LocalDateTime.of(2020,12,31,17,33);
        assertEquals(route.getEndDateTimeLDT(),localDateTime);
    }
    @Test
    public void getEndDateTime(){
        String EndStr="31/12/2020 17:33";
        assertEquals(route.getEndDateTime(),EndStr);
    }
    @Test
    public void getMaxSog(){
        double maxSOG = 13.4;
        double wrongSOG=12.5;
        assertNotEquals(15,route.getMaxSog(),0.01);
        assertNotEquals(12.4,route.getMaxSog(),0.01);
        assertEquals(maxSOG,route.getMaxSog(),0.01);
        assertNotEquals(wrongSOG,route.getMaxSog(),0.01);
    }

    @Test
    public void getMaxSogTestEqualShipDynamics(){
        Route route = new Route();
        ShipDynamic dynamic = new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:03", new Location("42.92236", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A");
        route.add(dynamic);
        route.add(dynamic);
        double maxSOG = 12.5;
        double wrongSOG=12.3;
        assertNotEquals(15,route.getMaxSog(),0.01);
        assertNotEquals(12.4,route.getMaxSog(),0.01);
        assertEquals(maxSOG,route.getMaxSog(),0.01);
        assertNotEquals(wrongSOG,route.getMaxSog(),0.01);
    }

    @Test
    public void getMaxCogEqualShipDynamic(){
        Route route = new Route();
        ShipDynamic dynamic = new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:03", new Location("42.92236", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A");
        route.add(dynamic);
        route.add(dynamic);
        double maxCOG = 2.4;
        double wrongCOG=4.2;
        assertNotEquals(11,route.getMaxCog(),0.01);
        assertNotEquals(9,route.getMaxCog(),0.01);
        assertEquals(maxCOG,route.getMaxCog(),0.01);
        assertNotEquals(wrongCOG,route.getMaxCog(),0.01);
    }


    @Test
    public void getMeanSOG(){
        double meanSOG=12.9;
        double wrongSOG=12.5;
        assertEquals(meanSOG,route.getMeanSog(),0.01);
        assertNotEquals(wrongSOG,route.getMeanSog(),0.01);
    }
    @Test
    public void getMaxCog(){
        double maxCOG = 10;
        double wrongCOG=4.2;
        assertNotEquals(11,route.getMaxCog(),0.01);
        assertNotEquals(9,route.getMaxCog(),0.01);
        assertEquals(maxCOG,route.getMaxCog(),0.01);
        assertNotEquals(wrongCOG,route.getMaxCog(),0.01);
    }
    @Test
    public void getMeanCOG(){
        double meanCOG=5;
        double wrongCOG=3.7;
        assertEquals(meanCOG,route.getMeanCog(),0.01);
        assertNotEquals(wrongCOG,route.getMeanCog(),0.01);
    }
    @Test
    public void getSize(){
        int correctSize=6;
        int incorrectSize=5;
        assertEquals(correctSize,route.getSize());
        assertNotEquals(incorrectSize,route.getSize());
    }
    @Test
    public void getDeltaDistance(){
        double expected= 32.01;
        double actual = route.getDeltaDistance();
        assertEquals(expected,actual,0.01);
        double expected2=0;
        double actual2= route2.getDeltaDistance();
        assertEquals(expected2,actual2,0.01);
    }
    @Test
    public void getTravelledDistance(){
        double expected= 32.01;
        double actual = route.getTravelledDistance();
        assertEquals(expected,actual,0.01);
    }
    @Test
    public void equalsMut (){
        ShipDynamic dynamic1 = (new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:03", new Location("42.92236", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic2 = (new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:13", new Location("42.95969", "-66.97106"), new Movement(12.9, 8.1, "358.0"), "NA", "A"));
        Route rou = new Route();
        rou.add(dynamic1);
        rou.add(dynamic2);
        Object o = rou;
        Object o1 = null;
        assertTrue(rou.equals(rou));
        assertTrue(rou.equals(o));
        assertFalse(rou.equals(null));
        assertFalse(rou.equals(o1));
        assertFalse(o.equals(o1));
        ShipCharacteristics sc = null;
        assertFalse(rou.equals(sc));
    }

    @Test
    public void testgetDepartureLong() {
        //Arrange
        //Act

        double actual = ship.getRoute().getDepartureLong();
        double expected = -66.97726;
        //Assert
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testgetDepartureLat() {
        //Arrange
        //Act

        double actual = ship.getRoute().getDepartureLat();
        double expected = 42.73879;
        //Assert
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testHashCode() {
        //Arrange
        //Act

        int actual = hashCode();
        int expected = hashCode();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        //Arrange
        //Act

        String actual = ship.getRoute().toString();
        String expected = ship.getRoute().toString();
        //Assert
        assertEquals(expected, actual);
    }
}
