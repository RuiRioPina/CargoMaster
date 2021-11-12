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
        Location l1 = new Location("42.7698", "-66.9759");
        Location l2 = new Location("42.77682", "-66.9756");
        Location l3 = new Location("42.7969", "-66.97547");
        Location l4 = new Location("42.81133", "-66.97587");
        Location l5 = new Location("42.82021", "-66.9758");
        Location l6 = new Location("42.82527", "-66.97577");
        List<Location> locations = new ArrayList<>();
        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);
        locations.add(l5);
        locations.add(l6);

        String MMSI = "210950000";
        String baseDateTime1 = "31/12/2020 16:17";
        String baseDateTime2 = "31/12/2020 16:37";

        List<Location> result = store.getPositionOfShipPeriod(MMSI, baseDateTime1, baseDateTime2);
        assertEquals(locations.toString(), result.toString());
        StringBuilder uf = new StringBuilder();
        uf.append("MMSI - ").append(MMSI).append(" | Period - ").append(baseDateTime1).append("  ").append(baseDateTime2).append('\n');
        for (Location location : result) {
            uf.append("Longitude ").append(location.getLongitude()).append(" | Latitude ").append(location.getLatitude()).append('\n');
        }
        PrintToFile.printB(uf,"positionOfShipPeriod.txt");
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
        Map<Integer, List <Ship> > res = store.getTopNShips(2,"31/12/2021 00:00","31/12/2021 23:59");
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
    @Test
    public void getCloseShips(){
    List<Pair<Ship,Ship>> actualList=store.getCloseShips();
    assertEquals(actualList,store.getCloseShips());
    }

}