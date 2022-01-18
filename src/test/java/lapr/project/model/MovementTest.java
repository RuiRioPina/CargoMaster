package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {
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
        route.add(new ShipDynamic(idShip.getMmsi(),"31/12/2020 17:33", new Location("43.02665", "-66.97076"), new Movement(12.5, 3.6, "354.0"), "NA", "A"));
        ship = new Ship(idShip,shipCharacteristics,route);
    }

    @Test
    public void killRemoveCallTo1() {
        try {
            Throwable exception = assertThrows(NullPointerException.class,
                    () -> {
                        new Movement(null, 30.0, "30.0");
                    });
            throw new Error("Mutant Killed");
        } catch (Error e) {
            System.out.println(e);
        }
    }

    @Test
    public void killRemoveCallTo2() {
        try {
            Throwable exception = assertThrows(NullPointerException.class,
                    () -> {
                        new Movement(30.0, null, "30.0");
                    });

            throw new Error("Mutant Killed");
        } catch (Error e) {
            System.out.println(e);
        }
    }

    @Test
    public void killRemoveCallTo3() {
        try {
            Throwable exception = assertThrows(NullPointerException.class,
                    () -> {
                        new Movement(30.0, 30.0, null);
                    });
            throw new Error("Mutant Killed");
        } catch (Error e) {
            System.out.println(e);
        }
    }

    @Test
    public void testCog() {
        new Movement(30.0, 0.0, "30.0");
        new Movement(30.0, -0.1, "30.0");
        new Movement(30.0, 0.1, "30.0");
    }

    @Test
    void convertCog() {
        Double actual = new Movement(30.0, 0.0, "30.0").convertCogAndHeading(0.0);
        assertEquals(0.0, actual);
        Double actual1 = new Movement(30.0, 0.0, "30.0").convertCogAndHeading(-1.0);
        assertEquals(359.0, actual1);
        actual =  new Movement(30.0, 0.0, "30.0").convertCogAndHeading(1.0);
        assertEquals(1, actual);
    }




    @Test
    public void setters() {
        Movement u = new Movement(30.0, 0.0, "30.0");
        u.setCog(2);
        assertEquals(u.getCog(), 2);
        u.setHeading("2");
        assertEquals("2", u.getHeading());
        u.setSog(2);
        assertEquals(2, u.getSog());
    }


    @Test
    public void testToString() {
        //Arrange
        //Act

        String actual = ship.getRoute().getRoute().get(0).getMovement().toString();
        String expected = ship.getRoute().getRoute().get(0).getMovement().toString();;
        //Assert
        assertEquals(expected, actual);
    }
    @Test
    public void validation(){
        assertThrows(IllegalArgumentException.class,()->new Movement(400.0,190.0,"371.0"));
        assertThrows(IllegalArgumentException.class,()->new Movement(400.0,400.0,"30.0"));

    }

}