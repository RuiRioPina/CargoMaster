package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.data.ManifestDB;

public class ManifestController {
    private ManifestDB manifestDB;
    public ManifestController(){manifestDB= new ManifestDB();}
    public int getContainerManifestsYear(DatabaseConnection connection, int shipCaptainID, int year){
        return manifestDB.getContainerManifestsYear(connection,shipCaptainID,year);
    }
    public double getAverageContainersForManifestYear(DatabaseConnection connection,int shipCaptainID, int year){
        return manifestDB.getAverageContainersForManifestYear(connection,shipCaptainID,year);
    }
}