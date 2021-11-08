package lapr.project.controller;

import lapr.project.model.ShipStore;
import lapr.project.model.Ship;
import lapr.project.utils.AVL;
import lapr.project.utils.PrintToFile;

import java.io.IOException;
import java.util.List;

public class SearchShipUsingCodes {
    private final String fileToBeWrittenTo = "resultSearchDetails.txt";

    public static void main(String[] args) throws IOException {
        searchShipDetails("IMO1139288");
    }

    public static void searchShipDetails(String code) throws IOException {
        final String fileToBeWrittenTo = "resultSearchDetails.txt";
        ShipStore store = new ShipStore();
        String fileName = "csvFiles/bships.csv";
        List<Ship> shipsList = ImportShips.importShips(fileName);
        for (Ship ships : shipsList) {
            ships.getShipId().setSearchCode(code);
            store.addShipToBST(ships);
        }

        try {
            Ship ship = store.findShipDetails(code);
            System.out.println(ship);
            PrintToFile.print(ship.toString(), fileToBeWrittenTo);
        } catch (IllegalArgumentException e) {
            System.out.println("The vessel to be searched does not exist. Please introduce a valid code!");
        }
    }

}
