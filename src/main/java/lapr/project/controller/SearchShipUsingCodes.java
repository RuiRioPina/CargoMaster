package lapr.project.controller;

import lapr.project.model.ShipStore;
import lapr.project.model.Ship;
import lapr.project.utils.AVL;

import java.io.IOException;
import java.util.List;

public class SearchShipUsingCodes {

    AVL<Ship> shipMmsiAVL = new AVL<>();
    AVL<Ship> shipIMOAVL = new AVL<>();
    AVL<Ship> shipCallsignAVL = new AVL<>();

    public static void main(String[] args) throws IOException {
        searchShipDetails("IMO9222285");
    }

    public static void searchShipDetails(String code) throws IOException {
        ShipStore store = new ShipStore();
        String fileName = "csvFiles/bships.csv";
        List<Ship> shipsList = ImportShips.importShips(fileName);
        for (Ship ships : shipsList) {
            ships.getShipId().setSearchCode(code);
            store.addShipToBST(ships);
        }


        store.findShipDetails(code);

    }

}
