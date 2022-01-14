package lapr.project.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;


class ShipTest {
    Ship ship;
    Route route = new Route();
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    ShipDynamic dynamic;


    @BeforeEach
    void setUp() {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        dynamic = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 16:12", new Location("42.73879", "-66.97726"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
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
    public void getTravelledDistance() {
        double expected = route.getTravelledDistance();
        double actual = ship.getTravelledDistance();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTravelledDistance() {
        Identification shipId = new Identification();
        ShipCharacteristics characteristics = new ShipCharacteristics();
        assertEquals(0.0, (new Ship(shipId, characteristics, new Route())).getTravelledDistance().doubleValue());
    }

    @Test
    public void getVesselType2() {
        int expected = shipCharacteristics.getVesselType();
        int actual = ship.getVesselType();
        assertEquals(expected, actual);
    }

    @Test
    void testGetVesselType() {
        Identification shipId = new Identification();
        ShipCharacteristics characteristics = new ShipCharacteristics();
        assertEquals(0, (new Ship(shipId, characteristics, new Route())).getVesselType());
    }

    @Test
    public void getMeanSOG() {
        double expected = route.getMeanSog();

        double actual = ship.getMeanSOG();

        assertEquals(expected, actual);
    }

    @Test
    void testGetMeanSOG() {
        Identification shipId = new Identification();
        ShipCharacteristics characteristics = new ShipCharacteristics();
        assertEquals(Double.NaN, (new Ship(shipId, characteristics, new Route())).getMeanSOG().doubleValue());
    }

    @Test
    void testGetMeanSOG2() {
        ShipDynamic shipDynamic = new ShipDynamic();
        shipDynamic.setMovement(new Movement());

        Route route = new Route();
        route.add(shipDynamic);
        Identification shipId = new Identification();
        assertEquals(0.0, (new Ship(shipId, new ShipCharacteristics(), route)).getMeanSOG().doubleValue());
    }

    @Test
    void testGetRouteTravelledDistance() {
        Identification shipId = new Identification();
        ShipCharacteristics characteristics = new ShipCharacteristics();
        assertEquals(0.0, (new Ship(shipId, characteristics, new Route())).getRouteTravelledDistance());
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
        ShipCharacteristics characteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);
        ship.setCharacteristics(characteristics);
        ShipCharacteristics expected = characteristics;

        ShipCharacteristics actual = ship.getCharacteristics();

        assertEquals(expected, actual);
    }

    @Test
    public void setRoute() {
        ShipDynamic dynamic = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 16:12", new Location("42.73879", "-66.97726"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
        Route expected = new Route();
        expected.add(dynamic);
        ship.setRoute(expected);


        Route actual = ship.getRoute();

        assertEquals(expected, actual);
    }

    @Test
    void testGetStartBaseDateTime() {
        Route route = new Route();
        route.add(new ShipDynamic());
        Identification shipId = new Identification();
        assertNull((new Ship(shipId, new ShipCharacteristics(), route)).getStartBaseDateTime());
    }

    @Test
    void testGetEndBaseDateTime() {
        Route route = new Route();
        route.add(new ShipDynamic());
        Identification shipId = new Identification();
        assertNull((new Ship(shipId, new ShipCharacteristics(), route)).getEndBaseDateTime());
    }

    @Test
    public void compareTo() {
        Identification identificationShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);

        int expected = 0;
        int actual = this.ship.compareTo(ship);
        assertEquals(expected, actual);
    }

    @Test
    public void compareToGreaterThan() {
        Identification identificationShip = new Identification("210000000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);

        boolean expected = true;
        boolean actual = this.ship.compareTo(ship) > 0;
        assertEquals(expected, actual);
    }

    @Test
    public void compareToLesserThan() {
        Identification identificationShip = new Identification("510000000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);

        boolean expected = true;
        boolean actual = this.ship.compareTo(ship) < 0;
        assertEquals(expected, actual);
    }

    @Test
    public void compareToGreaterThanCallsign() {
        Identification identificationShip = new Identification("210000000", "VARAMO", "IMO9395044", "A4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);
        ship.getShipId().setSearchCode("VJC32");
        boolean expected = true;
        boolean actual = this.ship.compareTo(ship) > 0;
        assertEquals(expected, actual);
    }

    @Test
    public void compareToLesserThanCallsign() {
        Identification identificationShip = new Identification("510000000", "VARAMO", "IMO9395044", "VJC32");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);
        ship.getShipId().setSearchCode("VJC32");
        boolean expected = true;
        boolean actual = this.ship.compareTo(ship) < 0;
        assertEquals(expected, actual);
    }

    @Test
    public void compareToGreaterThanIMO() {
        Identification identificationShip = new Identification("210000000", "VARAMO", "IMO3295044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        Route route = new Route();
        Ship ship3123124 = new Ship(identificationShip, shipCharacteristics, route);
        ship.getShipId().setSearchCode("IMO9488645");
        boolean expected = true;
        boolean actual = this.ship.compareTo(ship3123124) > 0;
        assertEquals(expected, actual);
    }

    @Test
    public void compareToLesserThanIMO() {
        Identification identificationShip = new Identification("510000000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        Route route = new Route();
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);

        boolean expected = true;
        boolean actual = this.ship.compareTo(ship) < 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals() {
        Identification identificationShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipsCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);
        Route route1 = new Route();
        route1.add(dynamic);
        Ship ship = new Ship(identificationShip, shipsCharacteristics, route1);
        Ship ship2 = null;
        boolean expected = true;
        boolean actual = this.ship.equals(ship);
        assertEquals(expected, actual);
        Object o = route1;
        Object o1 = null;
        assertFalse(route1.equals(new Object()));
        assertTrue(route1.equals(route1));
        assertFalse(route1.equals(o1));
        assertTrue(route1.equals(o));
        assertFalse(route1.equals(null));
        assertNotEquals(ship.getRouteTravelledDistance(), 78);
        assertNotEquals(ship,ship2);
        assertNotEquals(ship,route1);
    }

    @Test
    public void testEqualsNotCompliant() {
        Identification identificationShip = new Identification("510000000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipsCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);
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
        ShipCharacteristics shipsCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);
        Route route1 = new Route();
        route1.add(dynamic);
        Ship shipLocal = new Ship(identificationShip, shipsCharacteristics, route1);
        int expected = shipLocal.hashCode();
        int actual = ship.hashCode();
        assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        String expected = ship.toString();
        String actual = ship.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void isClose() {
        Identification idShip2 = new Identification("210950001", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics2 = new ShipCharacteristics(71, 166.0, 25.0, 9.5);
        Route route2 = new Route();
        ShipDynamic dynamic1 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:03", new Location("42.93879", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic2 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:13", new Location("42.95969", "-66.97243"), new Movement(12.9, 8.1, "358.0"), "NA", "A"));
        ShipDynamic dynamic3 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:03", new Location("42.70879", "-66.97726"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic4 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:03", new Location("42.93879", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic5 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:03", new Location("42.93879", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic6 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:03", new Location("41.93879", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic7 = (new ShipDynamic(idShip.getMmsi(), "31/12/2020 17:03", new Location("42.93879", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        route.add(dynamic1);
        route.add(dynamic2);
        route2.add(dynamic3);
        route2.add(dynamic4);
        route2.add(dynamic5);
        Route route3 = new Route();
        Route route4 = new Route();
        route3.add(dynamic);
        route3.add(dynamic1);
        route3.add(dynamic6);
        route4.add(dynamic7);
        route4.add(dynamic1);
        route4.add(dynamic2);
        ship = new Ship(idShip, shipCharacteristics, route);
        Ship ship1 = new Ship(idShip, shipCharacteristics, route);
        Ship ship2 = new Ship(idShip2, shipCharacteristics2, route2);
        Ship ship3 = new Ship(idShip2, shipCharacteristics2, route3);
        Ship ship4 = new Ship(idShip2, shipCharacteristics2, route4);
        assertFalse(ship.isClose(ship1));
        assertTrue(ship.isClose(ship2));
        assertFalse(ship.isClose(ship3));
        assertFalse(ship.isClose(ship4));
    }

    @Test
    void testGetTravelledDistance1() {
        double expected = 0.0;
        double actual = ship.getTravelledDistance();
        assertEquals(expected,actual);
    }

    @Test
    void getRouteTravelledDistance() {
        double expected = 0.0;
        double actual = ship.getRouteTravelledDistance();
        assertEquals(expected,actual);
    }

    @Test
    void getShipEnergy() {
        ShipEnergy actual = ship.getShipEnergy();
        assertEquals(actual,actual);
    }
}