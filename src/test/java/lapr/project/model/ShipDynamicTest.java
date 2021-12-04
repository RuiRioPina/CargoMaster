package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipDynamicTest {
    Ship ship;
    Route route = new Route();
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    ShipDynamic dynamic;

    @BeforeEach
    void setUp() {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        dynamic = (new ShipDynamic(idShip.getMmsi(),"31/12/2020 16:12", new Location("42.73879", "-66.97726"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
        route.add(dynamic);
        ship = new Ship(idShip, shipCharacteristics, route);
    }

    @Test
    public void killRemoveCallTo2() {

        Throwable exception = assertThrows(NullPointerException.class,
                () -> {
                    new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:03", new Location("32.0", "32.0"), new Movement(30.0, 30.0, "30.0"), "30", null);
                });
    }

    @Test
    public void killRemoveCallTo1() {

        Throwable exception = assertThrows(NullPointerException.class,
                () -> {
                    new ShipDynamic(idShip.getMmsi(),null, new Location("32.0", "32.0"), new Movement(30.0, 30.0, "30.0"), "30", "A");
                });
    }

    @Test
    public void getLocation() {
        List<ShipDynamic> ships = ship.getRoute().getRoute();
        String actual = ships.get(0).getLocation().getLatitude();
        String expected = "42.73879";
        assertEquals(expected, actual);
    }

    @Test
    public void setLocation() {
        List<ShipDynamic> ships = ship.getRoute().getRoute();
        Location location = new Location("30.0", "30.0");
        ships.get(0).setLocation(location);
        Location actual = ships.get(0).getLocation();
        Location expected = location;
        assertEquals(expected, actual);
    }

    @Test
    public void getCargo() {
        List<ShipDynamic> ships = ship.getRoute().getRoute();
        String actual = ships.get(0).getCargo();
        String expected = "NA";
        assertEquals(expected, actual);
    }

    @Test
    public void setCargo() {

        List<ShipDynamic> ships = ship.getRoute().getRoute();
        ships.get(0).setCargo("30");
        String actual = ships.get(0).getCargo();
        String expected = "30";
        assertEquals(expected, actual);
    }

    @Test
    public void getMovement() {
        List<ShipDynamic> ships = ship.getRoute().getRoute();
        ships.get(0).setCargo("30");
        String actual = ships.get(0).getCargo();
        String expected = "30";
        assertEquals(expected, actual);
    }

    @Test
    public void setMovement() {
        List<ShipDynamic> ships = ship.getRoute().getRoute();
        Movement movement = new Movement(30.0, 30.0, "300.0");
        ships.get(0).setMovement(movement);
        Movement actual = ships.get(0).getMovement();
        Movement expected = movement;
        assertEquals(expected, actual);
    }

    @Test
    public void getClosestPort(){
        Location location1 = new Location("10.41666667","-75.53333333");
        Location location2 = new Location("43.2","10.2");
        Location location22 = new Location("43.2","10.2");

        Location location3= new Location("44.65","-63.56666667");
        Location location4= new Location("-20.00","40.00");

        Port port1= new Port("America","Colombia",28313,"Cartagena",location1);
        Port port2= new Port("Europe","Italy",98732,"port2",location2);
        Port port22= new Port("Europe","Italy",98732,"port2",location2);
        Port port3= new Port("America","Canada",22226,"Halifax",location3);
        Port port4= new Port("Europe","Spain",98734,"port4",location4);
        assertEquals(dynamic.getClosestPort().toString(),port3.toString());
        assertNotEquals(dynamic.getClosestPort().toString(),port1.toString());
    }
}