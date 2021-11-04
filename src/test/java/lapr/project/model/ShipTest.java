package lapr.project.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipTest {
    Ship ship;
    Route route = new Route();
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    ShipDynamic dynamic;

    @org.junit.Before
    public void setUp() throws Exception {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);

        dynamic = (new ShipDynamic("31/12/2020 16:12", new Location(42.73879, -66.97726), new Movement(13.4, 3.4, 357), "NA", "A"));
        route.add(dynamic);
        ship = new Ship(idShip, shipCharacteristics, route);
    }

    @Test
    public void getMMSI() {
        String expected = "210950000";

        String actual = ship.getShipId().getMmsi();

        assertEquals(expected, actual);

    }

    @Test
    public void getShipName() {
        String expected = "VARAMO";

        String actual = ship.getShipId().getShipName();

        assertEquals(expected, actual);

    }

    @Test
    public void getImoId() {
        String expected = "IMO9395044";

        String actual = ship.getShipId().getImoID();

        assertEquals(expected, actual);

    }

    @Test
    public void getCallsign() {
        String expected = "C4SQ2";

        String actual = ship.getShipId().getCallsign();

        assertEquals(expected, actual);

    }

    @Test
    public void getVesselType() {
        int expected = 70;

        int actual = ship.getCharacteristics().getVesselType();

        assertEquals(expected, actual);

    }

    @Test
    public void getLength() {
        double expected = 166;

        double actual = ship.getCharacteristics().getLength();

        assertEquals(expected, actual, 0.01);

    }

    @Test
    public void getWidth() {
        double expected = 25;

        double actual = ship.getCharacteristics().getWidth();

        assertEquals(expected, actual, 0.001);

    }

    @Test
    public void getDraft() {
        double expected = 9.5;

        double actual = ship.getCharacteristics().getDraft();

        assertEquals(expected, actual, 0.01);

    }

    @Test
    public void getRoute() {
        Route expected = route;

        Route actual = ship.getRoute();

        assertEquals(expected, actual);

    }

    @Test
    public void getShipId() {
        Identification expected = idShip;

        Identification actual = ship.getShipId();

        assertEquals(expected, actual);
    }

    @Test
    public void getCharacteristics() {
        ShipCharacteristics expected = shipCharacteristics;

        ShipCharacteristics actual = ship.getCharacteristics();

        assertEquals(expected, actual);
    }

    @Test
    public void setShipId() {
        Identification identification = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        ship.setShipId(identification);
        Identification expected = identification;

        Identification actual = ship.getShipId();

        assertEquals(expected, actual);
    }

    @Test
    public void setCharacteristics() {
        ShipCharacteristics characteristics = new ShipCharacteristics(70, 166, 25, 9.5);
        ship.setCharacteristics(characteristics);
        ShipCharacteristics expected = characteristics;

        ShipCharacteristics actual = ship.getCharacteristics();

        assertEquals(expected, actual);
    }

    @Test
    public void setRoute() {
        ShipDynamic dynamic = (new ShipDynamic("31/12/2020 16:12", new Location(42.73879, -66.97726), new Movement(13.4, 3.4, 357), "NA", "A"));
        Route expected = new Route();
        expected.add(dynamic);
        ship.setRoute(expected);


        Route actual = ship.getRoute();

        assertEquals(expected, actual);
    }

    @Test
    public void compareTo() {
        Identification identificationShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);

        int expected = 0;
        int actual = this.ship.compareTo(ship);
        assertEquals(expected, actual);
    }

    @Test
    public void compareToGreaterThan() {
        Identification identificationShip = new Identification("210000000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);

        boolean expected = true;
        boolean actual = this.ship.compareTo(ship) > 0;
        assertEquals(expected, actual);
    }

    @Test
    public void compareToLesserThan() {
        Identification identificationShip = new Identification("510000000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);

        boolean expected = true;
        boolean actual = this.ship.compareTo(ship) < 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals() {
        Identification identificationShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipsCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);
        Route route1 = new Route();
        route1.add(dynamic);
        Ship ship = new Ship(identificationShip, shipsCharacteristics, route1);

        boolean expected = true;
        boolean actual = this.ship.equals(ship);
        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsNotCompliant() {
        Identification identificationShip = new Identification("510000000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipsCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);
        Route route1 = new Route();
        route1.add(dynamic);
        Ship ship = new Ship(identificationShip, shipsCharacteristics, route1);

        boolean expected = false;
        boolean actual = this.ship.equals(ship);
        assertEquals(expected, actual);
    }

    @Test
    public void testHashCode() {
        Identification identificationShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipsCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);
        Route route1 = new Route();
        route1.add(dynamic);
        Ship shipLocal = new Ship(identificationShip, shipsCharacteristics, route1);
        int expected = shipLocal.hashCode();
        int actual = ship.hashCode();
        assertEquals(expected,actual);
    }

    @Test
    public void testToString() {
        String expected = "Ship{shipId=Identification{mmsi='210950000', shipName='VARAMO', imoID='IMO9395044', callsign='C4SQ2'}, characteristics=ShipCharacteristics{vesselType=70, length=166.0, width=25.0, draft=9.5}, route=Route{route=[ShipDynamic{baseDateTime='31/12/2020 16:12', location=Location{longitude=-66.97726, latitude=42.73879}, cargo='NA', movement=Movement{sog=13.4, cog=3.4, heading=357.0}, transceiverClass='A'}]}}";
        String actual = ship.toString();
        assertEquals(expected,actual);
    }
}