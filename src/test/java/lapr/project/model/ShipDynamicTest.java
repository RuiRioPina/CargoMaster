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
        shipCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);

        dynamic = (new ShipDynamic("31/12/2020 16:12", new Location(42.73879, -66.97726), new Movement(13.4, 3.4, 357), "NA", "A"));
        route.add(dynamic);
        ship = new Ship(idShip, shipCharacteristics, route);
    }
    @Test
    public void getLocation() {
        List<ShipDynamic> ships = ship.getRoute().getRoute();
        double actual = ships.get(0).getLocation().getLatitude();
        double expected = 42.73879;
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void setLocation() {
        List<ShipDynamic> ships = ship.getRoute().getRoute();
        Location location = new Location(30,30);
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
        Movement movement = new Movement(30,30,300);
        ships.get(0).setMovement(movement);
        Movement actual = ships.get(0).getMovement();
        Movement expected = movement;
        assertEquals(expected, actual);
    }
}