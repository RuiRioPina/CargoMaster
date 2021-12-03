package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.data.PortStoreDB;
import lapr.project.data.PositionShipDB;
import lapr.project.data.ShipStoreDB;
import lapr.project.model.Company;

import lapr.project.model.Ship;
import lapr.project.model.shared.Constants;
import lapr.project.utils.ImportPorts;
import lapr.project.utils.ImportShips;

import java.util.*;


/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class App {

    private Company company;
    private boolean doBootStrap;
    DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
    private List<Ship> ships;


    private App() {

        Properties props = getProperties();

        Company cmp = null;


        this.doBootStrap = false;
        cmp = new Company(props.getProperty(Constants.PARAMS_COMPANY_DESIGNATION));
        this.doBootStrap = true;

        this.company = cmp;
    }

    public Company getCompany() {
        return this.company;
    }


    private Properties getProperties() {
        Properties props = new Properties();

        // Add default properties and values
        props.setProperty(Constants.PARAMS_COMPANY_DESIGNATION, "Ship Company");


        // Read configured values

        return props;
    }


    private void bootstrap() {
        ShipStoreDB shipStoreDB = new ShipStoreDB();
        PortStoreDB portStoreDB = new PortStoreDB();
        ImportShips importShips = new ImportShips();
        String fileName = "csvFiles/sships.csv";
        String fileName1 = "csvFiles/bships.csv";
        String fileName2 = "csvFiles/sports.csv";
//        importShips.importShips(fileName1);
        ImportPorts.importPorts(fileName2);
//        ImportPorts.importPortsAndSaveToDatabase(fileName2);
//        importShips.importShipEnergyAndSaveToDatabase(fileName);
//        importShips.importShipsAndSaveToDatabase(fileName);
//        importShips.importShipPositionsAndSaveToDatabase(fileName);
//        shipStoreDB.getShips(databaseConnection);
//        portStoreDB.getPorts(databaseConnection);
    }


    // Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static App singleton = null;

    public static App getInstance() {
        if (singleton == null) {
            synchronized (App.class) {
                singleton = new App();
                if (singleton.doBootStrap) {
                    singleton.bootstrap();
                    singleton.doBootStrap = false;
                }
            }
        }
        return singleton;
    }

}
