package lapr.project.controller;

import lapr.project.model.Ship;
import lapr.project.data.ShipStore;
import lapr.project.utils.PrintToFile;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SearchShipsTest {
    ShipStore store = App.getInstance().getCompany().getShipStore();
    @Test
    public void searchShipDetails() throws IOException {
        final String fileToBeWrittenTo = "resultSearchDetails.txt";
        try {
            Ship ship = store.findShipDetails("DHBN");
            System.out.println(ship);
            PrintToFile.print(ship.toString(), fileToBeWrittenTo);
        } catch (IllegalArgumentException e) {
            System.out.println("The vessel to be searched does not exist. Please introduce a valid code!");
        }
    }

    @Test
    public void searchShipDetais1() throws IOException {
        final String fileToBeWrittenTo = "resultSearchDetails.txt";

        try {
            Ship ship = store.findShipDetails("367008090");
            System.out.println(ship);
            PrintToFile.print(ship.toString(), fileToBeWrittenTo);
        } catch (IllegalArgumentException e) {
            System.out.println("The vessel to be searched does not exist. Please introduce a valid code!");
        }
    }

    @Test
    public void searchShipDetails2() throws IOException {
        final String fileToBeWrittenTo = "resultSearchDetails.txt";

        try {
            Ship ship = store.findShipDetails("IMO7437068");
            System.out.println(ship);
            PrintToFile.print(ship.toString(), fileToBeWrittenTo);
        } catch (IllegalArgumentException e) {
            System.out.println("The vessel to be searched does not exist. Please introduce a valid code!");
        }
    }
}
