package lapr.project.controller;

import lapr.project.controller.ImportShips;
import lapr.project.model.Company;
import lapr.project.model.Ship;
import lapr.project.model.ShipStore;
import lapr.project.model.shared.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.*;


/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class App  {

    private Company company;
    private boolean doBootStrap;

    private App() {

        Properties props = getProperties();
        
        Company cmp = null;
        try
        {    
            FileInputStream file = new FileInputStream(Constants.PARAMS_FICHEIRO_DADOS);
            ObjectInputStream in = new ObjectInputStream(file); 
              
            cmp = (Company)in.readObject(); 
              
            in.close(); 
            file.close(); 
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) 
        { 
        } 
        
        this.doBootStrap = false;
        if(cmp == null) {
        	cmp = new Company(props.getProperty(Constants.PARAMS_COMPANY_DESIGNATION));
        	this.doBootStrap = true;
        }
        
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
        try {
            InputStream in = new FileInputStream(Constants.PARAMS_FILENAME);
            props.load(in);
            in.close();
        } catch (IOException ex) {

        }
        return props;
    }


    private void bootstrap() {


        this.company.setNumberOfEmployees(0);

        ShipStore store = this.company.getShipStore();
        String fileName = "csvFiles/bships.csv";
        List<Ship> shipsList = ImportShips.importShips(fileName);
        for (Ship ships : shipsList) {
            store.addShipToAVL(ships);
        }
    }


    // Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static App singleton = null;

    public static App getInstance() {
        if (singleton == null) {
            synchronized (App.class) {
                singleton = new App();
                if(singleton.doBootStrap) {
                	singleton.bootstrap();
                	singleton.doBootStrap = false;
                }
            }
        }
        return singleton;
    }

}
