package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
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
    DatabaseConnection databaseConnection = null;
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
        ImportShips importShips = new ImportShips();
        String fileName = "csvFiles/sships.csv";
        String fileName1 = "csvFiles/bships.csv";
        String fileName2 = "csvFiles/sports.csv";
        importShips.importShips(fileName1);
        ImportPorts.importPorts(fileName2);
//        importShips.importShipEnergyAndSaveToDatabase(fileName);
//        importShips.importShipsAndSaveToDatabase(fileName);
//        importShips.importShipPositionsAndSaveToDatabase(fileName);

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
