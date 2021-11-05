package lapr.project.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentificationTest {
    Ship ship;
    Route route;
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    @org.junit.Before
    public void setUp() throws Exception {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044","C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70,166,25,9.5);

        route = new Route();
        ship = new Ship(idShip,shipCharacteristics,route);
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
    public void testToString() {
        //Arrange
        //Act

        String actual = ship.getShipId().getImoID().toString();
        String expected = "IMO9395044";
        //Assert
        assertEquals(expected, actual);
    }
}