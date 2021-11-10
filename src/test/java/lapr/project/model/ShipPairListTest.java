package lapr.project.model;

import lapr.project.controller.ImportShips;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ShipPairListTest {
ShipStore shipStore;
ShipPairList shipPairList;
    @BeforeEach
    void setUp() {
        shipStore = new ShipStore();
        String fileName = "csvFiles/bships.csv";
        List<Ship> shipsList = ImportShips.importShips(fileName);
        for (Ship ships : shipsList) {
            shipStore.addShipToAVL(ships);
        }
         shipPairList= new ShipPairList(shipStore);

    }
    @Test
    public void getShipPairList(){
        List<Pair<Ship,Ship>> pairList= shipPairList.getShipPairList();
        assertEquals(pairList,shipPairList.getShipPairList());

    }
    @Test
    public void getTravelledDistanceList(){
        List<Double> travelledDistanceList= shipPairList.getTravelledDistanceList();
        assertEquals(travelledDistanceList,shipPairList.getTravelledDistanceList());
    }

}
