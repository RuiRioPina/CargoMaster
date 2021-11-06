package lapr.project.model;

import lapr.project.controller.ImportShips;
import lapr.project.model.ShipStore;
import lapr.project.model.Location;
import lapr.project.model.Ship;
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

    @Before
    public void Setup () throws IOException {
        String fileName = "csvFiles/sships.csv";
        List<Ship> ship = ImportShips.importShips(fileName);
        for (Ship ships:ship) {
            store.addShipToBST(ships);
        }
    }

    @Test
    public void organizeMessagesShips() {
        //Example shown with one BST ship, but this method works for all BST ships
        System.out.println("Before organize temporally");
        BST<Ship> beforeShips = store.getShipStore();
        System.out.println(beforeShips.nodesByLevel().get(0).toString());
        store.organizeShipMessage();

        System.out.println("---------------------------");
        System.out.println("After organize temporally");
        BST<Ship> afterShips = store.getShipStore();
        System.out.println(afterShips.nodesByLevel().get(0).toString());

        assertEquals(beforeShips,afterShips);
    }

    @Test
    public void getPositionOfShipData() {
        Location expected = new Location(42.7698,-66.9759);
        String expected1 = expected.toString();
        String MMSI = "210950000";
        String baseDateTime = "31/12/2020 16:20";
        Location result = store.getPositionOfShipData(MMSI,baseDateTime);
        String result1 = result.toString();
        assertEquals(result1,expected1);
        System.out.println("MMSI - " + MMSI + " | Data - " + baseDateTime + '\n' +
                "Longitude " + result.getLongitude() + " | Latitude " + result.getLatitude());
    }

    @Test
    public void getPositionOfShipPeriod() throws ParseException {
        Location l1 = new Location( 42.7698, -66.9759);
        Location l2 = new Location( 42.7969, -66.97547);
        Location l3 = new Location( 42.81133, -66.97587);
        Location l4 = new Location( 42.8839, -66.97409);
        Location l5 = new Location( 42.90195, -66.97326);

        List <Location> locations = new ArrayList<>();
        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);
        locations.add(l5);
        String MMSI = "210950000";
        String baseDateTime1 = "31/12/2020 16:17";
        String baseDateTime2 = "31/12/2020 16:59";

        List <Location> result = store.getPositionOfShipPeriod(MMSI,baseDateTime1,baseDateTime2);
        assertEquals(locations.toString(),result.toString());
        System.out.println("MMSI - " + MMSI + " | Period - " + baseDateTime1 + "  " + baseDateTime2);
        for (int i = 0; i < result.size(); i++){
            System.out.println("Longitude " + result.get(i).getLongitude() + " | Latitude " + result.get(i).getLatitude());
        }

    }
}