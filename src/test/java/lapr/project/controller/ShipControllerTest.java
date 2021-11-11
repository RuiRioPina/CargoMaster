package lapr.project.controller;


import lapr.project.model.*;
import lapr.project.utils.AVL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShipControllerTest {


    @Test
    void getPositionOfShipData() {
        ShipController sc = new ShipController();
        Identification id = new Identification("366980250","BRIAN","IMO8841943","WCP9410");
        ShipCharacteristics ch = new ShipCharacteristics(31,30.0,8.0,3.5);
        Route ro = new Route();
        Movement mo = new Movement(0.0,195.0,174.0);
        Location lo = new Location("22.34538", "47.57392");
        ShipDynamic s = new ShipDynamic("31/12/2020 13:11",lo,mo,"21","A");
        ro.add(s);
        Ship ship = new Ship(id,ch,ro);
        sc.addShip(ship);
        Location a = new Location("22.34538", "47.57392");
        Location b = sc.getPositionOfShipData("366980250","31/12/2020 13:11");
        sc.organizeShipMessage();
        assertEquals(a.toString(),b.toString());
    }

    @Test
    void getPositionOfShipPeriod() throws ParseException {
        ShipController sc = new ShipController();
        Identification id = new Identification("366980250","BRIAN","IMO8841943","WCP9410");
        ShipCharacteristics ch = new ShipCharacteristics(31,30.0,8.0,3.5);
        Route ro = new Route();
        Movement mo = new Movement(0.0,195.0,174.0);
        Location lo = new Location("22.34538", "47.57392");
        ShipDynamic s = new ShipDynamic("31/12/2020 13:11",lo,mo,"21","A");
        ro.add(s);
        Ship ship = new Ship(id,ch,ro);
        sc.addShip(ship);
        Location a = new Location("22.34538", "47.57392");
        List<Location> b = sc.getPositionOfShipPeriod("366980250","31/12/2020 13:10","31/12/2020 13:13");
        assertEquals(a.toString(),b.get(0).toString());
    }

    @Test
    void getTopNShips() throws ParseException, IOException {
        ShipController sc = new ShipController();
        Identification id = new Identification("366980250","BRIAN","IMO8841943","WCP9410");
        ShipCharacteristics ch = new ShipCharacteristics(31,30.0,8.0,3.5);
        Route ro = new Route();
        Movement mo = new Movement(0.0,195.0,174.0);
        Location lo = new Location("22.34538", "47.57392");
        ShipDynamic s = new ShipDynamic("31/12/2020 13:11",lo,mo,"21","A");
        ro.add(s);
        Ship ship = new Ship(id,ch,ro);
        sc.addShip(ship);
        List <Ship> sh = new ArrayList<>();
        sh.add(ship);
        Map<Integer,List <Ship>> map = new HashMap<>();
        map.put(31,sh);
        assertEquals(map, sc.getTopNShips(1,"30/12/2020 00:00","30/12/2020 23:59"));
    }
}