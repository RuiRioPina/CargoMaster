package lapr.project.utils;

import lapr.project.controller.App;
import lapr.project.data.*;
import lapr.project.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class will import the ships using an csv provided by the User
 */

public class ImportShips {
    private static final Logger LOGGER = Logger.getLogger("MainLog");
    StringBuilder sout = new StringBuilder("");
    final String fileToBeWrittenTo = "linesNotImported.txt";
    PositionShipDB positionShipDB = new PositionShipDB();
    ShipStoreDB shipStoreDB = new ShipStoreDB();

    String url = System.getProperty("database.url");
    String username = System.getProperty("database.username");
    String password = System.getProperty("database.password");
    DatabaseConnection databaseConnection;
    /**
     * Construtor that will not be used since this class is not to be instantiated
     */

    public ImportShips() {
        databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
    }

    /**
     * This method will go through the csv lines one by one and will instantiate all the members of the ship class, and then it will add the ships to the AVL
     *
     * @param fileName the name of csv where the ships will be imported from
     * @return a list of ships that will be useful for some US
     */

    public List<Ship> importShips(String fileName) {

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
                        sout.append("Failed to import line ").append(size);
                        sout.append('\n');
                    }
                }
                if (ship != null) {
                    try {
                        shipDynamic = createShipDynamic(elements);
                        route.add(shipDynamic);
                        ship.setRoute(route);
                    } catch (Exception e) {
                        sout.append("Failed to import line ").append(size);
                        sout.append('\n');
                    }
                }
            }
        } catch (NullPointerException | IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, "An unexpected error occured. Please check the name of the csv file to import the data.");
        } finally {
            try {
                PrintToFile.printB(sout, fileToBeWrittenTo);
                if (br != null) {
                    br.close();
                }
            } catch (IOException | IllegalArgumentException e) {
                LOGGER.log(Level.INFO, "-");
            }

        }
        for (Ship ship1 : ships) {
            store.addShipToAVL(ship1);
        }
        return ships;
    }

    public List<Ship> importShipsAndSaveToDatabase(String fileName) {
        final String fileToBeWrittenTo = "linesNotImported.txt";
        ShipStore store = App.getInstance().getCompany().getShipStore();
        List<Ship> ships = new ArrayList<>();
        Identification idShip;
        ShipCharacteristics characteristics;
        String line;
        String splitBy = ",";
        BufferedReader br = null;
        Ship ship = null;
        int size = 0;


        try {
            br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while ((line = br.readLine()) != null) {
                size++;
                String[] elements = line.split(splitBy);
                if (ship == null || !ship.getShipId().getMmsi().equals(elements[0])) {

                    try {
                        idShip = createIdentification(elements);
                        characteristics = createCharacteristics(elements);
                        ship = new Ship(idShip, characteristics, null);
                        ships.add(ship);
                        shipStoreDB.save(databaseConnection, ship);
                    } catch (Exception e) {
                        ship = null;
                        sout.append("Failed to import line ").append(size);
                        sout.append('\n');
                    }
                }
            }
        } catch (NullPointerException | IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, "An unexpected error occured. Please check the name of the csv file to import the data.");
        } finally {
            try {
                PrintToFile.printB(sout, fileToBeWrittenTo);
                if (br != null) {
                    br.close();
                }
            } catch (IOException | IllegalArgumentException e) {
                LOGGER.log(Level.INFO, "-");
            }

        }

        return ships;
    }

    public void importShipPositionsAndSaveToDatabase(String fileName) {
        List<ShipDynamic> shipsDynamic = new ArrayList<>();
        ShipDynamic shipDynamic;
        String line;
        String splitBy = ",";
        BufferedReader br = null;
        int size = 0;


        try {
            br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while ((line = br.readLine()) != null) {
                size++;
                String[] elements = line.split(splitBy);

                    try {
                        shipDynamic = createShipDynamic(elements);
                        positionShipDB.save(databaseConnection, shipDynamic);
                        shipsDynamic.add(shipDynamic);
                        } catch (Exception e) {
                        sout.append("Failed to import line ").append(size);
                        sout.append('\n');
                    }

            }
        } catch (NullPointerException | IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, "An unexpected error occured. Please check the name of the csv file to import the data.");
        } finally {
            try {
                PrintToFile.printB(sout, fileToBeWrittenTo);
                if (br != null) {
                    br.close();
                }
            } catch (IOException | IllegalArgumentException e) {
                LOGGER.log(Level.INFO, "-");
            }

        }

    }

    public void importShipEnergyAndSaveToDatabase(String fileName) {

        ShipEnergyDB shipEnergyDB = new ShipEnergyDB();
        ShipEnergy shipEnergy;
        String line;
        String splitBy = ",";
        BufferedReader br = null;
        int size = 0;


        try {
            br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            while ((line = br.readLine()) != null) {
                size++;
                String[] elements = line.split(splitBy);

                try {
                    shipEnergy = createShipEnergy(elements);
                    shipEnergyDB.save(databaseConnection, shipEnergy);
                } catch (Exception e) {
                    sout.append("Failed to import line ").append(size);
                    sout.append('\n');
                }

            }
        } catch (NullPointerException | IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, "An unexpected error occured. Please check the name of the csv file to import the data.");
        } finally {
            try {
                PrintToFile.printB(sout, fileToBeWrittenTo);
                if (br != null) {
                    br.close();
                }
            } catch (IOException | IllegalArgumentException e) {
                LOGGER.log(Level.INFO, "-");
            }

        }

    }


    /**
     * Class for instantiating the Identification of the Ship using the result of the import of the ships
     *
     * @param elements the attributes related to the Identification
     * @return the instance of Identification to be later used in the Ship
     */

    private static Identification createIdentification(String[] elements) {

        String mmsi = elements[0];
        String vesselName = elements[7];
        String imo = elements[8];
        String callsign = elements[9];
        return new Identification(mmsi, vesselName, imo, callsign);
    }

    /**
     * Class for instantiating the ShipCharacteristics of the Ship using the result of the import of the ships
     *
     * @param elements the attributes related to the ShipCharacteristics
     * @return the instance of ShipCharacteristics to be later used in the Ship
     */

    private static ShipCharacteristics createCharacteristics(String[] elements) {
        int vesselType = Integer.parseInt(elements[10]);
        double length = Double.parseDouble(elements[11]);
        double width = Double.parseDouble(elements[12]);
        double draft = Double.parseDouble(elements[13]);

        return new ShipCharacteristics(vesselType, length, width, draft);
    }

    /**
     * Class for instantiating the ShipDynamic of the Ship using the result of the import of the ships
     *
     * @param elements the attributes related to the ShipDynamic
     * @return the instance of ShipDynamic to be later used in the Ship
     */

    private static ShipDynamic createShipDynamic(String[] elements) {
        String mmsi = elements[0];
        String baseDateTime = elements[1];
        String lat = elements[2];
        String lon = elements[3];
        double sog = Double.parseDouble(elements[4]);
        double cog = Double.parseDouble(elements[5]);
        String heading = elements[6];

        String cargo = elements[14];
        String transceiverClass = elements[15];
        return new ShipDynamic(mmsi, baseDateTime, new Location(lat, lon), new Movement(sog, cog, heading), cargo, transceiverClass);
    }

    private static ShipEnergy createShipEnergy(String[] elements) {
        int nrGenerator = Integer.parseInt(elements[10]);
        int power = Integer.parseInt(elements[10]);
        return new ShipEnergy(nrGenerator, power);
    }



}  