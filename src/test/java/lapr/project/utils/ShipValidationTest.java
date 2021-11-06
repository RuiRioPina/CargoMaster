package lapr.project.utils;

import lapr.project.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.InputMismatchException;

import static org.junit.Assert.*;

public class ShipValidationTest {
    Ship ship;
    Route route = new Route();
    ShipCharacteristics characteristics;
    Identification shipID;
    ShipDynamic dynamic;
    Movement movement;
    Location location;

    @Before
    public void setUp() throws Exception {

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

    @Test(expected = IllegalArgumentException.class)
    public void validateMMSINotCompliant() {
        shipID.setMmsi("-210950000");
        ship.setShipId(shipID);
        ShipValidation.validateMMSI(ship.getShipId().getMmsi());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateMMSINotCompliantMinus() {
        shipID.setMmsi("-99999999");
        ship.setShipId(shipID);
        ShipValidation.validateMMSI(ship.getShipId().getMmsi());
    }

    @Test(expected = InputMismatchException.class)
    public void validateShipName() {
        shipID.setShipName("4125");
        ship.setShipId(shipID);
        ShipValidation.validateShipName(ship.getShipId().getShipName());
    }

    @Test
    public void validateIMOWorkingExample() {
        shipID.setImoID("IMO9395044");
        ship.setShipId(shipID);
        ShipValidation.validateShipName(ship.getShipId().getImoID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateIMONotCompliant() {
        shipID.setImoID("IMB9395044");
        ship.setShipId(shipID);
        ShipValidation.validateIMO(ship.getShipId().getImoID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateIMONotCompliant1() {
        shipID.setImoID("IMB939504");
        ship.setShipId(shipID);
        ShipValidation.validateIMO(ship.getShipId().getImoID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateIMONotCompliant2() {
        shipID.setImoID("IMO9395S44");
        ship.setShipId(shipID);
        ShipValidation.validateIMO(ship.getShipId().getImoID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateIMONotCompliant3() {
        shipID.setImoID("IMO3");
        ship.setShipId(shipID);
        ShipValidation.validateIMO(ship.getShipId().getImoID());
    }


    @Test
    public void validateBaseDateTime() {

        dynamic.setBaseDateTime("31/12/2020 17:19");
        ShipValidation.validateBaseDateTime(dynamic.getBaseDateTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateBaseDateTimeNotCorrect() {
        dynamic.setBaseDateTime("J");
        ShipValidation.validateBaseDateTime(dynamic.getBaseDateTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateBaseDateTimeNotCorrect2() {
        dynamic.setBaseDateTime("31/12/2020 25:19");
        ShipValidation.validateBaseDateTime(dynamic.getBaseDateTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateBaseDateTimeNotCorrect3() {
        dynamic.setBaseDateTime("70/12/2020 22:19");
        ShipValidation.validateBaseDateTime(dynamic.getBaseDateTime());
    }

    @Test
    public void testValidateVesselTypes() {
        characteristics.setVesselType(0);
        ShipValidation.validateVesselType(characteristics.getVesselType());
        characteristics.setVesselType(10);
        ShipValidation.validateVesselType(characteristics.getVesselType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateVesselTypesNotCorrect() {
        characteristics.setVesselType(0);
        ShipValidation.validateVesselType(characteristics.getVesselType());
        characteristics.setVesselType(-30);
        ShipValidation.validateVesselType(characteristics.getVesselType());
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

    @Test(expected = IllegalArgumentException.class)
    public void testValidateShipSizesNotCompliant2() {
        characteristics.setVesselType(-30);

        ShipValidation.validateVesselType(characteristics.getVesselType());
    }

    @Test
    public void validateLatitude() {
        location.setLatitude(-70);
        ShipValidation.validateLatitude(location.getLatitude());
    }

    @Test
    public void validateLatitude1() {
        location.setLatitude(-90);
        ShipValidation.validateLatitude(location.getLatitude());
        location.setLatitude(90);
        ShipValidation.validateLatitude(location.getLatitude());
    }

    @Test
    public void validateLatitude2() {
        location.setLatitude(-30);
        ShipValidation.validateLatitude(location.getLatitude());
        location.setLatitude(20);
        ShipValidation.validateLatitude(location.getLatitude());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLatitude3() {
        location.setLatitude(-190);
        ShipValidation.validateLatitude(location.getLatitude());
        location.setLatitude(200);
        ShipValidation.validateLatitude(location.getLatitude());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLatitudeNotCompliant() {
        location.setLatitude(-100);
        ShipValidation.validateLatitude(location.getLatitude());
    }

    @Test
    public void validateLongitude() {

        location.setLongitude(170);
        ShipValidation.validateLongitude(location.getLongitude());
    }

    @Test
    public void validateLongitude1() {

        location.setLongitude(180);
        ShipValidation.validateLongitude(location.getLongitude());
        location.setLongitude(-180);
        ShipValidation.validateLongitude(location.getLongitude());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLongitudeNotCompliant() {
        location.setLongitude(190);
        ShipValidation.validateLongitude(location.getLongitude());
    }

    @Test
    public void validateSog() {
        movement.setSog(10);
        ShipValidation.validateSog(movement.getSog());
    }

    @Test
    public void validateSog1() {
        movement.setSog(0);
        ShipValidation.validateSog(movement.getSog());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateSogNotCompliant() {
        movement.setSog(-30);
        ShipValidation.validateSog(movement.getSog());
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

    @Test(expected = IllegalArgumentException.class)
    public void validateCogNotCompliant() {
        movement.setCog(380);
        ShipValidation.validateCog(movement.getCog());
    }

    @Test
    public void validateHeading() {

        movement.setHeading(320);
        ShipValidation.validateHeading(movement.getHeading());
    }

    @Test
    public void validateHeading1() {

        movement.setHeading(359);
        ShipValidation.validateHeading(movement.getHeading());
        movement.setHeading(0);
        ShipValidation.validateHeading(movement.getHeading());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateHeadingNotCompliant() {
        movement.setHeading(380);
        ShipValidation.validateHeading(movement.getHeading());
    }

    @Test
    public void testValidateVesselType() {
        characteristics.setVesselType(30);
        ShipValidation.validateVesselType(characteristics.getVesselType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateVesselTypeNotCompliant() {
        characteristics.setVesselType(-30);
        ShipValidation.validateVesselType(characteristics.getVesselType());
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
        characteristics.setDraft(0);
        characteristics.setLength(0);
        characteristics.setWidth(0);
        ShipValidation.validateShipSizes(characteristics.getDraft());
        ShipValidation.validateShipSizes(characteristics.getLength());
        ShipValidation.validateShipSizes(characteristics.getWidth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateShipSizesNotCompliant() {
        characteristics.setDraft(-30);
        characteristics.setLength(-30);
        characteristics.setWidth(-30);
        ShipValidation.validateShipSizes(characteristics.getDraft());
        ShipValidation.validateShipSizes(characteristics.getLength());
        ShipValidation.validateShipSizes(characteristics.getWidth());
    }

    @Test
    public void testValidateTransceiverClass() {
        dynamic.setTransceiverClass("A");
        ShipValidation.validateTransceiverClass(dynamic.getTransceiverClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateTransceiverClassNotCompliant() {
        dynamic.setTransceiverClass("D");
        ShipValidation.validateTransceiverClass(dynamic.getTransceiverClass());
    }
}