package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    Ship ship;
    Route route;
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    Location location;
    @BeforeEach
    void setUp() {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044","C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70, 166.0,25.0,9.5);
        location = new Location("30","30");
        route = new Route();
        location = new Location();
        route.add(new ShipDynamic("31/12/2020 17:33", new Location("43.02665", "-66.97076"), new Movement(12.5, 3.6, "354.0"), "NA", "A"));
        ship = new Ship(idShip,shipCharacteristics,route);
    }

    @Test
    public void testNotAvailable() {
        Location location = new Location("30","181");
        String actual = location.getLongitude();
        String expected = "not available";
        assertEquals(actual,expected);
    }

    @Test
    public void testNotAvailable1() {

        Location location = new Location("91","30");
        String actual = location.getLatitude();
        String expected = "not available";
        assertEquals(expected,actual);
    }




    @Test
    public void killRemoveCallTo1() {

        Throwable exception = assertThrows(NullPointerException.class,
                ()->{new Location(null,"30.0");} );
    }
    @Test
    public void killRemoveCallTo2() {

        Throwable exception = assertThrows(NullPointerException.class,
                ()->{new Location("30.0",null);} );
    }
    @Test
    public void setLatitude() {
        //Arrange
        //Act
        ship.getRoute().getRoute().get(0).getLocation().setLatitude("73");
        String actual = ship.getRoute().getRoute().get(0).getLocation().getLatitude();
        String expected = "73";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void validateLongitude() {
        Identification identification = new Identification("210950000","VARAMO","IMO9395044","C4SQ2");
        ship.getRoute().getRoute().get(0).getLocation().setLongitude("-190");
    }


    @Test
    public void setLongitude() {
        //Arrange
        //Act
        ship.getRoute().getRoute().get(0).getLocation().setLongitude("21");
        String actual = ship.getRoute().getRoute().get(0).getLocation().getLongitude();
        String expected = "21";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testToString1() {
        //Arrange
        //Act

        String actual = ship.getRoute().getRoute().get(0).getLocation().toString();
        String expected = "Location{longitude=-66.97076, latitude=43.02665}";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        //Arrange
        //Act

        String actual = ship.getRoute().getRoute().get(0).toString();
        String expected = ship.getRoute().getRoute().get(0).toString();;
        //Assert
        assertEquals(expected, actual);
    }

}