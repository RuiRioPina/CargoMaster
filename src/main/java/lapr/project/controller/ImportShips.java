package lapr.project.controller;

import lapr.project.data.utils.auth.app.App;
import lapr.project.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportShips {

    private ImportShips() {
        //This class is not expected to be instantiated
    }

    public static List<Ship> importShips(String fileName) {
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
                    route = new Route();
                    String mmsi = elements[0];
                    String vesselName = elements[7];
                    String imo = elements[8];
                    String callsign = elements[9];
                    int vesselType = Integer.parseInt(elements[10]);
                    double length = Double.parseDouble(elements[11]);
                    double width = Double.parseDouble(elements[12]);
                    double draft = Double.parseDouble(elements[13]);
                    try {
                        idShip = new Identification(mmsi, vesselName, imo, callsign);
                        characteristics = new ShipCharacteristics(vesselType, length, width, draft);
                        ship = new Ship(idShip, characteristics, null);
                        ships.add(ship);
                    } catch (Exception e) {
                        ship = null;
                        System.out.println("Failed to import line " + size);


                    }
                }

                String baseDateTime = elements[1];
                String lat = elements[2];
                String lon = elements[3];
                double sog = Double.parseDouble(elements[4]);
                double cog = Double.parseDouble(elements[5]);
                double heading = Double.parseDouble(elements[6]);

                String cargo = elements[14];
                String transceiverClass = elements[15];
                if (ship != null) {
                    try {

                        shipDynamic = new ShipDynamic(baseDateTime, new Location(lat, lon), new Movement(sog, cog, heading), cargo, transceiverClass);
                        route.add(shipDynamic);
                        ship.setRoute(route);

                    } catch (Exception e) {

                        System.out.println("Failed to import line " + size);

                    }
                }

            }
        } catch (NullPointerException | IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return ships;
    }



}  