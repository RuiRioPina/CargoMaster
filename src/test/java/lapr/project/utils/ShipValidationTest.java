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
        ship = new Ship(shipID,characteristics,route);
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

    public static void validateVesselType(int vesselType){
        if (vesselType<0){
            throw new IllegalArgumentException();
        }
    }
    public static void validateShipSizes(int measure){
        if (measure<0){
            throw new IllegalArgumentException();
        }
    }
    public static void validateTransceiverClass(String transceiverClass){
        if (!(transceiverClass.equals("A")||transceiverClass.equals("B"))){
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void validateLatitude() {
        location.setLatitude(-70);
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

    @Test(expected = IllegalArgumentException.class)
    public void validateCogNotCompliant() {
        movement.setCog(-30);
        ShipValidation.validateCog(movement.getCog());
    }
    @Test
    public void validateHeading() {

        movement.setHeading(320);
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