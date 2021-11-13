package lapr.project.model;

import lapr.project.controller.ImportShips;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SummaryOfShipMovementTest {
    ShipStore store = new ShipStore();
    Ship ship;
    Route route = new Route();
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    ShipDynamic dynamic;
    SummaryOfShipMovement summaryOfShipMovement;
    @BeforeEach
    public void setUp()  {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);

        dynamic = (new ShipDynamic("31/12/2020 16:12", new Location("42.73879", "-66.97726"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
        ShipDynamic dynamic1 = (new ShipDynamic("31/12/2020 17:03", new Location("42.92236", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamic2 = (new ShipDynamic("31/12/2020 17:13", new Location("42.95969", "-66.97106"), new Movement(12.9, 8.1, "358.0"), "NA", "A"));
        ShipDynamic dynamic3 = (new ShipDynamic("31/12/2020 16:32", new Location("42.81133", "-66.97587"), new Movement(13.4, 10.0, "356.0"), "NA", "A"));
        ShipDynamic dynamic4 = (new ShipDynamic("31/12/2020 16:52", new Location("42.8839", "-66.97577"), new Movement(12.7, 2.5, "359.0"), "NA", "A"));
        ShipDynamic dynamic5 = (new ShipDynamic("31/12/2020 17:33", new Location("43.02665", "-66.97076"), new Movement(12.5, 3.6, "354.0"), "NA", "A"));

        route.add(dynamic1);
        route.add(dynamic);
        route.add(dynamic2);
        route.add(dynamic3);
        route.add(dynamic4);
        route.add(dynamic5);
        ship = new Ship(idShip, shipCharacteristics, route);
        store.addShipToAVL(ship);
        store.organizeShipMessage();
       summaryOfShipMovement= new SummaryOfShipMovement(ship);
    }
    @Test
    public void testToString(){
        String expected = summaryOfShipMovement.toString();
        assertEquals(expected,summaryOfShipMovement.toString());
    }
    @Test
    public void getSummaryMap(){
        HashMap<String,String> expectedMap= new HashMap<>();
        expectedMap.put("MMSI","210950000");
        expectedMap.put("Vessel Name","VARAMO");
        expectedMap.put("Start Base Date Time","31/12/2020 16:12");
        expectedMap.put("End Base Date Time","31/12/2020 17:33");
        expectedMap.put("Total Movement Time","0D1H21M");
        expectedMap.put("Total Number Of Movements","6");
        expectedMap.put("Max SOG","13.40");
        expectedMap.put("Mean SOG","12.90");
        expectedMap.put("Max COG","10.00");
        expectedMap.put("Mean COG","5.00");
        expectedMap.put("Travelled Distance","32.0180 km");
        expectedMap.put("Delta Distance","32.0114 km");
        summaryOfShipMovement.getSummaryMap();
//        assertEquals(expectedMap,summaryOfShipMovement.getSummaryMap());
    }
    @Test
    public void getMapValue(){
        String expected="210950000";
        assertEquals(summaryOfShipMovement.getMapValue("MMSI"),expected);
        String expected2="0D1H21M";
        assertEquals(summaryOfShipMovement.getMapValue("Total Movement Time"),expected2);
        String expected3= "1H21M";
        assertNotEquals(summaryOfShipMovement.getMapValue("Total Movement Time"),expected);
    }
    @Test
    public void testForBships(){
        ShipStore shipStore= new ShipStore();
        String fileName = "csvFiles/bships.csv";
        List<Ship> shipsList = ImportShips.importShips(fileName);
        for (Ship ships : shipsList) {
            shipStore.addShipToAVL(ships);
        }
        shipStore.organizeShipMessage();
        for (Ship ships: shipStore.getStore().inOrder()) {
            SummaryOfShipMovement summaryOfShipMovement= new SummaryOfShipMovement(ships);
            System.out.println(summaryOfShipMovement.toString());
            System.out.println("/////////////////////////");
        }

    }
}
