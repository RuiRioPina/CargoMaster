package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Company;
import lapr.project.model.shared.Constants;
import lapr.project.utils.ImportPorts;
import lapr.project.utils.ImportShips;

import java.util.*;


/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class App {

    private final Company company;
    private boolean doBootStrap;
    DatabaseConnection databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");


    private App() {

        Properties props = getProperties();

        Company cmp;


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
        CountryStore countryStore = new CountryStore();
        ImportShips importShips = new ImportShips();
        String fileName = "csvFiles/sships.csv";
        String fileName1 = "csvFiles/bships.csv";
        String fileName2 = "csvFiles/sports.csv";
        String fileName3 = "csvFiles/countries.csv";
     importShips.importShips(fileName);
       ImportPorts.importPorts(fileName2);
//        IpomrtCountries.importCountries(fileName3);
//        ImportPorts.importPortsAndSaveToDatabase(fileName2);
//        importShips.importShipsAndSaveToDatabase(fileName);
//        importShips.importShipPositionsAndSaveToDatabase(fileName);
//          shipStoreDB.getShips(databaseConnection);
//        portStoreDB.getPorts(databaseConnection);
        countryStore.getCountriesFromDatabase();

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
