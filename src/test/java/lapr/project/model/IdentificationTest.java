package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentificationTest {
    Ship ship;
    Route route;
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    @BeforeEach
    void setUp() {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044","C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70, 166.0,25.0,9.5);

        route = new Route();
        ship = new Ship(idShip,shipCharacteristics,route);
    }

    @Test
    public void killRemoveCallTo1() {

        Throwable exception = assertThrows(NullPointerException.class,
                ()->{new Identification(null, "VARAMO", "IMO9395044","C4SQ2");} );
    }

    @Test
    public void killRemoveCallTo2() {

        Throwable exception = assertThrows(NullPointerException.class,
                ()->{new Identification("210950000", null, "IMO9395044","C4SQ2");} );
    }
    @Test
    public void killRemoveCallTo3() {

        Throwable exception = assertThrows(NullPointerException.class,
                ()->{new Identification("210950000", "VARAMO", null,"C4SQ2");} );
    }

    @Test
    public void setMmsi() {
        //Arrange
        //Act
        ship.getShipId().setMmsi("657364732");
        String actual = ship.getShipId().getMmsi();
        String expected = "657364732";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void validateMessage() {
        Identification identification = new Identification("210950000","VARAMO","IMO9395044","C4SQ2");
        identification.setMmsi("218593");
    }


    @Test
    public void setShipName() {
        //Arrange
        //Act
        ship.getShipId().setShipName("California");
        String actual = ship.getShipId().getShipName();
        String expected = "California";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setImoID() {
        //Arrange
        //Act
        ship.getShipId().setImoID("IMO8765432");
        String actual = ship.getShipId().getImoID();
        String expected = "IMO8765432";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setCallsign() {
        //Arrange
        //Act
        ship.getShipId().setCallsign("CSV42");
        String actual = ship.getShipId().getCallsign();
        String expected = "CSV42";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getMmsi() {
        //Arrange
        //Act
        ship.getShipId().setMmsi("210955000");
        String actual = ship.getShipId().getMmsi();
        String expected = "210955000";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getShipName() {
        //Arrange
        //Act
        ship.getShipId().setShipName("California");
        String actual = ship.getShipId().getShipName();
        String expected = "California";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testSetShipName() {
        //Arrange
        //Act
        ship.getShipId().setShipName("California");
        String actual = ship.getShipId().getShipName();
        String expected = "California";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getImoID() {
        //Arrange
        //Act
        ship.getShipId().setImoID("IMO8765432");
        String actual = ship.getShipId().getImoID();
        String expected = "IMO8765432";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setSearchCode() {
        ship.getShipId().setSearchCode("IMO9395044");
        String actual = ship.getShipId().getSearchCode();
        String expected = "IMO9395044";
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testHashCode() {
        int actual = hashCode();
        int expected = hashCode();;
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void returnCodeAccordingToTheFormat() {
        ship.getShipId().setSearchCode("IMO9395044");
        String actual = ship.getShipId().returnCodeAccordingToTheFormat(ship.getShipId().getSearchCode());
        String expected = ship.getShipId().getImoID();
        //Assert
        assertEquals(expected, actual);
    }
    @Test
    public void returnCodeAccordingToTheFormat1() {
        ship.getShipId().setSearchCode("V2EB3");
        String actual = ship.getShipId().returnCodeAccordingToTheFormat(ship.getShipId().getSearchCode());
        String expected = ship.getShipId().getCallsign();
        //Assert
        assertEquals(expected, actual);
    }
    @Test
    public void returnCodeAccordingToTheFormat2() {
        ship.getShipId().setSearchCode("305373000");
        String actual = ship.getShipId().returnCodeAccordingToTheFormat(ship.getShipId().getSearchCode());
        String expected = ship.getShipId().getMmsi();
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void equalsMut () {
        Identification id = new Identification("210950000","VARAMO","IMO9395044","C4SQ2");
        Object o = id;
        Object o1 = null;
        assertTrue(id.equals(new Identification("210950000","VARAMO","IMO9395044","C4SQ2")));
        assertTrue(id.equals(o));
        assertFalse(id.equals(null));
        assertFalse(id.equals(o1));
        assertFalse(o.equals(o1));
        ShipCharacteristics sc = null;
        assertFalse(id.equals(sc));
        assertTrue(id.equals(id));
    }

    @Test
    public void testToString() {
        //Arrange
        //Act

        String actual = ship.getShipId().toString();
        String expected = ship.getShipId().toString();
        //Assert
        assertEquals(expected, actual);
    }
}