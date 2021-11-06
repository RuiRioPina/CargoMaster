package lapr.project.model;

import lapr.project.controller.ImportShips;
import lapr.project.utils.AVL;
import lapr.project.utils.BST;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipStoreTest {
    ShipStore store = new ShipStore();
    AVL<Ship> expected1 = new AVL<>();

    @Before
    public void Setup() throws IOException {
        String fileName = "csvFiles/bships.csv";
        List<Ship> ship = ImportShips.importShips(fileName);
        for (Ship ships : ship) {
            store.addShipToBST(ships);
            expected1.insert(ships);
        }
    }

    @Test
    public void organizeMessagesShips() {
        //Example shown with one BST ship, but this method works for all BST ships
        System.out.println("Before organize temporally");
        BST<Ship> beforeShips = store.getStore();
        System.out.println(beforeShips.nodesByLevel().get(0).toString());
        store.organizeShipMessage();

        System.out.println("---------------------------");
        System.out.println("After organize temporally");
        BST<Ship> afterShips = store.getStore();
        System.out.println(afterShips.nodesByLevel().get(0).toString());

        assertEquals(beforeShips, afterShips);
    }

    @Test
    public void getPositionOfShipData() {
        Location expected = new Location(42.7698, -66.9759);
        String expected1 = expected.toString();
        String MMSI = "210950000";
        String baseDateTime = "31/12/2020 16:20";
        Location result = store.getPositionOfShipData(MMSI, baseDateTime);
        String result1 = result.toString();
        assertEquals(result1, expected1);
        System.out.println("MMSI - " + MMSI + " | Data - " + baseDateTime + '\n' +
                "Longitude " + result.getLongitude() + " | Latitude " + result.getLatitude());
    }

    @Test
    public void getPositionOfShipPeriod() throws ParseException {
        Location l1 = new Location(42.7698, -66.9759);
        Location l2 = new Location(42.77682, -66.9756);
        Location l3 = new Location(42.7969, -66.97547);
        Location l4 = new Location(42.81133, -66.97587);
        Location l5 = new Location(42.82021, -66.9758);
        Location l6 = new Location(42.82527, -66.97577);
        Location l7 = new Location(42.84366, -66.97571);
        Location l8 = new Location(42.8839, -66.97409);
        Location l9 = new Location(42.90195, -66.97326);

        List<Location> locations = new ArrayList<>();
        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);
        locations.add(l5);
        locations.add(l6);
        locations.add(l7);
        locations.add(l8);
        locations.add(l9);
        String MMSI = "210950000";
        String baseDateTime1 = "31/12/2020 16:17";
        String baseDateTime2 = "31/12/2020 16:59";

        List<Location> result = store.getPositionOfShipPeriod(MMSI, baseDateTime1, baseDateTime2);
        assertEquals(locations.toString(), result.toString());
        System.out.println("MMSI - " + MMSI + " | Period - " + baseDateTime1 + "  " + baseDateTime2);
        for (int i = 0; i < result.size(); i++) {
            System.out.println("Longitude " + result.get(i).getLongitude() + " | Latitude " + result.get(i).getLatitude());
        }

    }

    @Test
    public void convertSortingMethod() {
        AVL<Ship> actual = store.convertSortingMethod("IMO9488645");

        assertEquals(actual,expected1);

    }

    @Test
    public void convertSortingMethod1() {
        AVL<Ship> actual = store.convertSortingMethod("VJC33");

        assertEquals(actual,expected1);

    }

    @Test
    public void convertSortingMethod2() {
        AVL<Ship> actual = store.convertSortingMethod("636019825");

        assertEquals(actual,expected1);

    }

    @Test
    public void findShipDetails() {
        Ship expected=store.getStore().nodesByLevel().get(1).get(0);
        Ship actual = store.findShipDetails("366062000");
        assertEquals(expected,actual);
    }



    @Test
    public void findShipDetails1() {
        Ship expected=store.getStore().nodesByLevel().get(1).get(0);
        Ship actual = store.findShipDetails("IMO8030489");
        assertEquals(expected,actual);
    }

    @Test
    public void findShipDetails2() {
        Ship expected=store.getStore().nodesByLevel().get(1).get(0);
        Ship actual = store.findShipDetails("KUCE");
        assertEquals(expected,actual);
    }

    @Test
    public void findShipDetailsNotCompliant() {
        Ship actual = store.findShipDetails("CS73642");
        assertNull(actual);
    }
}