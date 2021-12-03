package lapr.project.model;

import lapr.project.controller.App;
import lapr.project.utils.BST;
import lapr.project.utils.Pair;
import lapr.project.utils.PrintToFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShipStoreTest {
    ShipStore store = App.getInstance().getCompany().getShipStore();


    @BeforeEach
    void setUp() {

    }

    @Test
    public void organizeMessagesShips() {
        //Example shown with one BST ship, but this method works for all BST ships
        BST<Ship> beforeShips = store.getStore();

        store.organizeShipMessage();

        BST<Ship> afterShips = store.getStore();

        assertEquals(beforeShips, afterShips);
    }

    @Test
    public void getPositionOfShipData() throws IOException {
        Location expected = new Location("54.27307", "-164.07348");
        String expected1 = expected.toString();

        String MMSI = "636019825";
        String baseDateTime = "31/12/2020 23:27";

        Location result = store.getPositionOfShipData(MMSI, baseDateTime);
        Location error = store.getPositionOfShipData("333333333","31/12/2020 23:27");
        String result1 = result.toString();

        assertEquals(expected1, result1);
        assertEquals(new Location("0","0").toString(),error.toString());
        String uf = ("MMSI - " + MMSI + " | Data - " + baseDateTime + '\n' +
                "Longitude " + result.getLongitude() + " | Latitude " + result.getLatitude());
        PrintToFile.print(uf,"positionOfShipData.txt");
    }

    @Test
    public void getPositionOfShipPeriod() throws ParseException, IOException {
//        Location l1 = new Location("42.7698", "-66.9759");
//        Location l2 = new Location("42.77682", "-66.9756");
//        Location l3 = new Location("42.7969", "-66.97547");
//        Location l4 = new Location("42.81133", "-66.97587");
//        Location l5 = new Location("42.82021", "-66.9758");
//        Location l6 = new Location("42.82527", "-66.97577");
//        List<Location> locations = new ArrayList<>();
//        locations.add(l1);
//        locations.add(l2);
//        locations.add(l3);
//        locations.add(l4);
//        locations.add(l5);
//        locations.add(l6);
//
//        String MMSI = "210950000";
//        String baseDateTime1 = "31/12/2020 16:17";
//        String baseDateTime2 = "31/12/2020 16:37";
//
//        List<Location> result = store.getPositionOfShipPeriod(MMSI, baseDateTime1, baseDateTime2);
//        assertEquals(locations.toString(), result.toString());
//        StringBuilder uf = new StringBuilder();
//        uf.append("MMSI - ").append(MMSI).append(" | Period - ").append(baseDateTime1).append("  ").append(baseDateTime2).append('\n');
//        for (Location location : result) {
//            uf.append("Longitude ").append(location.getLongitude()).append(" | Latitude ").append(location.getLatitude()).append('\n');
//        }
//        PrintToFile.printB(uf,"positionOfShipPeriod.txt");
    }

    @Test
    public void getTopNShipsSMALL() throws ParseException, IOException {
        Map <Integer, List <Ship> > exp = new HashMap<>();
        List <Ship> ship1 = new ArrayList<>();
        store.organizeShipMessage();
        for (Ship ship : store.getStore().inOrder()) {
            ship1.add(ship);
        }
        List <Ship> ship2 = new ArrayList<>();
        ship2.add(ship1.get(4));
        ship2.add(ship1.get(12));
        List <Ship> ship3 = new ArrayList<>();
        ship3.add(ship1.get(16));
        ship3.add(ship1.get(3));
        List <Ship> ship4 = new ArrayList<>();
        ship4.add(ship1.get(5));
        ship4.add(ship1.get(8));
        exp.put(60,ship4);
        exp.put(70,ship3);
        exp.put(80,ship2);
        Map<Integer, List <Ship> > res = store.getTopNShips(2,"31/12/2020 00:00","31/12/2020 23:59");
        //assertEquals(res,exp);
    }

    @Test
    public void findShipDetails() {
        Ship expected = store.getStore().nodesByLevel().get(1).get(0);
        Ship actual = store.findShipDetails("229857000");
        //assertEquals(expected, actual);
    }


    @Test
    public void findShipDetails1() {
        Ship expected = store.getStore().nodesByLevel().get(1).get(0);
        Ship actual = store.findShipDetails("IMO9224726");
        //assertEquals(expected, actual);
    }

    @Test
    public void findShipDetails2() {
        Ship expected = store.getStore().nodesByLevel().get(1).get(0);
        Ship actual = store.findShipDetails("9HA3667");
        //assertEquals(expected, actual);
    }

    @Test
    public void findShipDetailsNotCompliant() {

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    store.findShipDetails("CS73642");
                });
    }
//    @Test
//    public void getCloseShips(){
//    List<Pair<Ship,Ship>> actualList=store.getCloseShips();
//        System.out.println(2);
//    assertEquals(actualList,store.getCloseShips());
//    }

    @Test
    public void getSize () {
        int a = 0;
        assertNotEquals(a,store.size());
    }

    @Test
    public void getTopNShipsMut () throws ParseException, IOException {
        Map<Integer, List <Ship> > res = store.getTopNShips(2,"31/12/2021 09:00","31/12/2021 10:00");
        Map<Integer, List <Ship> > not = new HashMap<>();
        assertNotEquals(res,not);
    }
    @Test
    public void findClosestPort(){
        Identification identificationShip = new Identification("210000000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);
        Identification identificationShip2 = new Identification("210000001", "VARAMA", "IMO9395043", "ABCD1");
        ShipDynamic dynamic3 = (new ShipDynamic(identificationShip2.getMmsi(),"31/12/2020 17:12", new Location("36.73879", "-24.97726"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
        Route route2= new Route();
        route2.add(dynamic3);
        Route route = new Route();
        ShipDynamic dynamic = (new ShipDynamic(identificationShip.getMmsi(),"31/12/2020 16:12", new Location("42.73879", "-66.97726"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
        route.add(dynamic);
        ShipDynamic dynamic2 = (new ShipDynamic(identificationShip.getMmsi(),"10/01/2021 16:15", new Location("50.05", "1"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
        route.add(dynamic2);
        Ship ship = new Ship(identificationShip, shipCharacteristics, route);
        Ship ship2= new Ship(identificationShip2,shipCharacteristics,route2);
        ShipStore shipStore= new ShipStore();
        shipStore.addShipToAVL(ship);
        shipStore.addShipToAVL(ship2);
        Location location1= new Location("44.65","-63.56666667");
        Location location2= new Location("51.05","2.366666667");
        Location location3= new Location("37.73333333","-25.66666667");
        Port port1= new Port("America","Canada",22226,"Halifax",location1);
        Port port2= new Port("Europe","France",18326,"Dunkirk",location2);
        Port port3 = new Port("Europe","Portugal",18476,"Ponta Delgada",location3);
        assertEquals(shipStore.findClosestPort("C4SQ2","31/12/2020 16:12"),port1);
        assertEquals(shipStore.findClosestPort("C4SQ2","10/01/2021 16:15"),port2);
        assertEquals(shipStore.findClosestPort("ABCD1","31/12/2020 17:12"),port3);



    }

}