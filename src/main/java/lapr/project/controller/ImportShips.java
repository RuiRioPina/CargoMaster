package lapr.project.controller;

import lapr.project.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportShips {
    private static final Logger LOGGER = Logger.getLogger("MainLog");
    private ImportShips() {
        //This class is not expected to be instantiated
    }
    public static List<Ship> importShips(String fileName) {
        ShipStore store = App.getInstance().getCompany().getShipStore();
        List<Ship> ships = new ArrayList<>();
        Identification idShip;
        ShipCharacteristics characteristics;
        ShipDynamic shipDynamic;
        String line;
        String splitBy = ",";
        BufferedReader br = null;
        Ship ship = null;
        Route route = null;
        int size = 0;
        try {
            br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while ((line = br.readLine()) != null) {
                size++;
                String[] elements = line.split(splitBy);
                if (ship == null || !ship.getShipId().getMmsi().equals(elements[0])) {

                    try {
                        route = new Route();
                        idShip = createIdentification(elements);
                        characteristics = createCharacteristics(elements);
                        ship = new Ship(idShip, characteristics, null);
                        ships.add(ship);
                    } catch (Exception e) {
                        ship = null;
                        LOGGER.log(Level.INFO, String.format("Failed to import line %d", size));
                    }
                }
                if (ship != null) {
                    try {
                        shipDynamic = createShipDynamic(elements);
                        route.add(shipDynamic);
                        ship.setRoute(route);
                    } catch (Exception e) {
                        LOGGER.log(Level.INFO, String.format("Failed to import line %d", size));
                    }
                }
            }
        } catch (NullPointerException | IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, "An unexpected error occured. Please check the name of the csv file to import the data.");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (Ship ship1 : ships) {
            store.addShipToAVL(ship1);
        }
        return ships;
    }

    private static Identification createIdentification(String[] elements) {

        String mmsi = elements[0];
        String vesselName = elements[7];
        String imo = elements[8];
        String callsign = elements[9];
        return new Identification(mmsi, vesselName, imo, callsign);
    }

    private static ShipCharacteristics createCharacteristics(String[] elements) {
        int vesselType = Integer.parseInt(elements[10]);
        double length = Double.parseDouble(elements[11]);
        double width = Double.parseDouble(elements[12]);
        double draft = Double.parseDouble(elements[13]);

        return new ShipCharacteristics(vesselType, length, width, draft);
    }

    private static ShipDynamic createShipDynamic(String[] elements) {
        String baseDateTime = elements[1];
        String lat = elements[2];
        String lon = elements[3];
        double sog = Double.parseDouble(elements[4]);
        double cog = Double.parseDouble(elements[5]);
        String heading = elements[6];

        String cargo = elements[14];
        String transceiverClass = elements[15];
        return new ShipDynamic(baseDateTime, new Location(lat, lon), new Movement(sog, cog, heading), cargo, transceiverClass);
    }


}  