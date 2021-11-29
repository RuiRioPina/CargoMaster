package lapr.project.utils;

import lapr.project.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class ShipValidationTest {
    Ship ship;
    Route route = new Route();
    ShipCharacteristics characteristics;
    Identification shipID;
    ShipDynamic dynamic;
    Movement movement;
    Location location;
    @BeforeEach
    void setUp() {
        shipID = new Identification();
        dynamic = new ShipDynamic();
        route = new Route();
        location = new Location();
        movement = new Movement();
        characteristics = new ShipCharacteristics();
        ship = new Ship(shipID, characteristics, route);
    }
    @Test
    public void validateMMSIWorkingExample() {

        shipID.setMmsi("210950000");
        ship.setShipId(shipID);
        ShipValidation.validateMMSI(ship.getShipId().getMmsi());
    }

    @Test
    public void validateMMSINotCompliant() {
        shipID.setMmsi("-210950000");
        ship.setShipId(shipID);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateMMSI(ship.getShipId().getMmsi());} );
    }

    @Test
    public void validateMMSINotCompliantMinus() {
        shipID.setMmsi("-99999999");
        ship.setShipId(shipID);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateMMSI(ship.getShipId().getMmsi());} );
    }

    @Test
    public void validateShipName() {
        shipID.setShipName("4125");
        ship.setShipId(shipID);
        Throwable exception = assertThrows(InputMismatchException.class,
                ()->{ShipValidation.validateShipName(ship.getShipId().getShipName());} );
    }

    @Test
    public void validateIMOWorkingExample() {
        shipID.setImoID("IMO9395044");
        ship.setShipId(shipID);
        ShipValidation.validateShipName(ship.getShipId().getImoID());

    }

    @Test
    public void validateIMONotCompliant() {
        shipID.setImoID("IMB9395044");
        ship.setShipId(shipID);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateIMO(ship.getShipId().getImoID());} );
    }

    @Test
    public void validateIMONotCompliant1() {
        shipID.setImoID("IMB939504");
        ship.setShipId(shipID);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateIMO(ship.getShipId().getImoID());} );
    }

    @Test
    public void validateIMONotCompliant2() {
        shipID.setImoID("IMO9395S44");
        ship.setShipId(shipID);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateIMO(ship.getShipId().getImoID());} );
    }

    @Test
    public void validateIMONotCompliant3() {
        shipID.setImoID("IMO3");
        ship.setShipId(shipID);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateIMO(ship.getShipId().getImoID());} );
    }


    @Test
    public void validateBaseDateTime() {

        dynamic.setBaseDateTime("31/12/2020 17:19");
        ShipValidation.validateBaseDateTime(dynamic.getBaseDateTime());
    }

    @Test
    public void validateBaseDateTimeNotCorrect() {
        dynamic.setBaseDateTime("J");
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateBaseDateTime(dynamic.getBaseDateTime());} );
    }

    @Test
    public void validateBaseDateTimeNotCorrect2() {
        dynamic.setBaseDateTime("31/12/2020 25:19");
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateBaseDateTime(dynamic.getBaseDateTime());} );

    }

    @Test
    public void validateBaseDateTimeNotCorrect3() {
        dynamic.setBaseDateTime("70/12/2020 22:19");
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateBaseDateTime(dynamic.getBaseDateTime());} );
    }

    @Test
    public void testValidateVesselTypes() {
        characteristics.setVesselType(0);
        ShipValidation.validateVesselType(characteristics.getVesselType());
        characteristics.setVesselType(10);
        ShipValidation.validateVesselType(characteristics.getVesselType());
    }

    @Test
    public void testValidateVesselTypesNotCorrect() {
        characteristics.setVesselType(0);

        characteristics.setVesselType(-30);
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateVesselType(characteristics.getVesselType());} );
    }

    @Test
    public void testValidateVesselTypes1() {
        characteristics.setVesselType(30);
        ShipValidation.validateVesselType(characteristics.getVesselType());

    }

    @Test
    public void testValidateShipSizes2() {
        characteristics.setVesselType(0);


        ShipValidation.validateVesselType(characteristics.getVesselType());
    }

    @Test
    public void testValidateShipSizesNotCompliant2() {
        characteristics.setVesselType(-30);

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateVesselType(characteristics.getVesselType());} );
    }

    @Test
    public void validateLatitude() {
        location.setLatitude("-70");
        ShipValidation.validateLatitude(location.getLatitude());
    }

    @Test
    public void validateLatitude1() {
        location.setLatitude("-90");
        ShipValidation.validateLatitude(location.getLatitude());
        location.setLatitude("90");
        ShipValidation.validateLatitude(location.getLatitude());
    }

    @Test
    public void validateLatitude2() {
        location.setLatitude("-30");
        ShipValidation.validateLatitude(location.getLatitude());
        location.setLatitude("20");
        ShipValidation.validateLatitude(location.getLatitude());
    }

    @Test
    public void validateLatitude3() {
        location.setLatitude("-190");

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateLatitude(location.getLatitude());} );
        location.setLatitude("200");
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateLatitude(location.getLatitude());} );

    }

    @Test
    public void validateLatitudeNotCompliant() {
        location.setLatitude("-100");
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateLatitude(location.getLatitude());} );
    }

    @Test
    public void validateLongitude() {

        location.setLongitude("170");
        ShipValidation.validateLongitude(location.getLongitude());
    }

    @Test
    public void validateLongitude1() {

        location.setLongitude("180");
        ShipValidation.validateLongitude(location.getLongitude());
        location.setLongitude("-180");
        ShipValidation.validateLongitude(location.getLongitude());
    }

    @Test
    public void validateLongitudeNotCompliant() {
        location.setLongitude("190");

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateLongitude(location.getLongitude());} );
    }

    @Test
    public void validateCog() {
        movement.setCog(300);
        ShipValidation.validateCog(movement.getCog());
    }

    @Test
    public void validateCog1() {
        movement.setCog(0);
        ShipValidation.validateCog(movement.getCog());
        movement.setCog(359);
        ShipValidation.validateCog(movement.getCog());
    }

    @Test
    public void validateCogNotCompliant() {
        movement.setCog(380);
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateCog(movement.getCog());} );
    }

    @Test
    public void validateHeading() {

        movement.setHeading("320");
        ShipValidation.validateHeading(movement.getHeading());
    }

    @Test
    public void validateHeading1() {

        movement.setHeading("360");
        ShipValidation.validateHeading(movement.getHeading());
        movement.setHeading("0");
        ShipValidation.validateHeading(movement.getHeading());
    }

    @Test
    public void validateHeadingNotCompliant() {
        movement.setHeading("380");

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateHeading(movement.getHeading());} );
    }

    @Test
    public void testValidateVesselType() {
        characteristics.setVesselType(30);
        ShipValidation.validateVesselType(characteristics.getVesselType());
    }

    @Test
    public void testValidateVesselTypeNotCompliant() {
        characteristics.setVesselType(-30);
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateVesselType(characteristics.getVesselType());} );
    }

    @Test
    public void testValidateShipSizes() {
        characteristics.setDraft(30);
        characteristics.setLength(30);
        characteristics.setWidth(30);
        ShipValidation.validateShipSizes(characteristics.getDraft());
        ShipValidation.validateShipSizes(characteristics.getLength());
        ShipValidation.validateShipSizes(characteristics.getWidth());
    }

    @Test
    public void testValidateShipSizes1() {
        characteristics.setDraft(0.0);
        characteristics.setLength(0.0);
        characteristics.setWidth(0.0);
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateShipSizes(characteristics.getDraft());} );
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateShipSizes(characteristics.getLength());} );
        Throwable exception2 = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateShipSizes(characteristics.getWidth());} );
    }

    @Test
    public void testValidateShipSizesNotCompliant() {
        characteristics.setDraft(-30);
        characteristics.setLength(-30);
        characteristics.setWidth(-30);
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateShipSizes(characteristics.getDraft());} );
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateShipSizes(characteristics.getLength());} );
        Throwable exception2 = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateShipSizes(characteristics.getWidth());} );
    }
    @Test
    public void testValidateTransceiverClass() {
        dynamic.setTransceiverClass("A");
        ShipValidation.validateTransceiverClass(dynamic.getTransceiverClass());
    }

    @Test
    public void testValidateTransceiverClassNotCompliant() {
        dynamic.setTransceiverClass("D");

        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{ShipValidation.validateTransceiverClass(dynamic.getTransceiverClass());} );
    }
}