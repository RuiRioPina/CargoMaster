package lapr.project.utils;

import lapr.project.controller.App;
import lapr.project.data.DatabaseConnection;
import lapr.project.data.PortStoreDB;
import lapr.project.model.*;
import lapr.project.utils.PrintToFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportPorts {

    private static final Logger LOGGER = Logger.getLogger("MainLog");

    public static void importPorts(String fileName) {
        PortStore store = App.getInstance().getCompany().getPortStore();
        String continent;
        String country;
        int code;
        String namePort;
        Location location;
        String line;
        String splitBy = ",";
        BufferedReader br = null;
        Port port = null;
        int size = 0;
        try {
            br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while ((line = br.readLine()) != null) {
                size++;
                String[] elements = line.split(splitBy);
                if (port == null || port.getCode() != Integer.parseInt(elements[2])) {
                    try {
                        continent = elements[0];
                        country = elements[1];
                        code = Integer.parseInt(elements[2]);
                        namePort = elements[3];
                        location = createLocation(elements);
                        port = new Port(continent, country, code, namePort, location);
                        store.addToList(port, Double.parseDouble(port.getLocation().getLongitude()), Double.parseDouble(port.getLocation().getLatitude()));
                    } catch (Exception e) {
                        port = null;
                    }
                }
            }
            store.insert();
        } catch (NullPointerException | IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, "It was not possible to import this Port, due to domain restrictions.");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException | IllegalArgumentException e) {
                LOGGER.log(Level.INFO, "It was not possible to import this Port, due to domain restrictions.");
            }

        }

    }

    private static Location createLocation(String[] elements) {
        String lat = elements[4];
        String lon = elements[5];
        return new Location(lat, lon);
    }

    public static void importPortsAndSaveToDatabase(String fileName) {
        DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");

        PortStore store = App.getInstance().getCompany().getPortStore();
        String continent;
        String country;
        int code;
        String namePort;
        Location location;
        String line;
        String splitBy = ",";
        BufferedReader br = null;
        Port port = null;
        int size = 0;
        PortStoreDB  portStoreDB = new PortStoreDB();
        try {
            br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while ((line = br.readLine()) != null) {
                size++;
                String[] elements = line.split(splitBy);
                if (port == null || port.getCode() != Integer.parseInt(elements[2])) {
                    try {
                        continent = elements[0];
                        country = elements[1];
                        code = Integer.parseInt(elements[2]);
                        namePort = elements[3];
                        location = createLocation(elements);
                        port = new Port(continent, country, code, namePort, location);
                        portStoreDB.save(databaseConnection,port);
                    } catch (Exception e) {
                        port = null;
                    }
                }
            }
        } catch (NullPointerException | IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, "It was not possible to import this Port, due to domain restrictions.");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException | IllegalArgumentException e) {
                LOGGER.log(Level.INFO, "It was not possible to import this Port, due to domain restrictions.");
            }

        }

    }
}
