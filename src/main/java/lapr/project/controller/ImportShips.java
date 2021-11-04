package lapr.project.controller;

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

    public static List<Ship> importShips(String fileName) throws IOException {
        List<Ship> ships = new ArrayList<>();
        Identification idShip;
        ShipCharacteristics characteristics;
        ShipDynamic shipDynamic;
        String line = "";
        String splitBy = ",";
        BufferedReader br = null;
        String file = fileName;
        Ship ship = null;
        Route route = null;
        try {
            br = new BufferedReader(new FileReader(file));
            br.readLine();
            while ((line = br.readLine()) != null) {

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
                        if (ship != null) {
                            ships.add(ship);
                        }
                        ship = new Ship(idShip, characteristics, null);
                    } catch (Exception e) {
                        ship = null;
                        System.out.println(mmsi + " " + vesselName + " " + imo + " " + callsign);
                        System.out.println(vesselType + " " + length + " " + width + " " + draft);

                    }
                }

                String baseDateTime = elements[1];
                double lat = Double.parseDouble(elements[2]);
                double lon = Double.parseDouble(elements[3]);
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

                        System.out.println(baseDateTime + " " + lat + " " + lon + " " + sog + " " + cog + " " + heading + " " + cargo + " " + transceiverClass);

                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            br.close();
        }
        return ships;
    }
}  