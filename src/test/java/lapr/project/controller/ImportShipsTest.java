package lapr.project.controller;

import lapr.project.data.ShipStore;
import lapr.project.model.Identification;
import lapr.project.model.Ship;
import lapr.project.model.ShipCharacteristics;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ImportShipsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void importShips() throws IOException {
        System.out.println("baseDateTime" + " " + "lat" + " " + "lon" + " " + "sog" + " " + "cog" + " " +  "heading" + " " + "cargo" + " " + "transceiverClass");
        ShipStore store = new ShipStore();
        String fileName = "C:\\Users\\ruiri\\IdeaProjects\\lapr3-2021-g076\\csvFiles\\sships.csv";
        List<Ship> ship = ImportShips.importShips(fileName);
        for (Ship ships : ship) {
            store.addShipToBST(ships);
        }
        System.out.println("Amounts of ships in the store:" + store.size());
    }


}