package lapr.project.controller;

import lapr.project.model.ShipStore;
import lapr.project.model.Ship;
import lapr.project.utils.AVL;

import java.io.IOException;
import java.util.List;

public class SearchShipUsingCodes {

    public static void main(String[] args) throws IOException {
        searchShipDetails("IMO1139288");
    }

    public static void searchShipDetails(String code) throws IOException {
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
        } catch (IllegalArgumentException e) {
            System.out.println("The vessel to be searched does not exist. Please introduce a valid code!");
        }
    }

}
