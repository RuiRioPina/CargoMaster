package lapr.project.controller;


import lapr.project.data.DatabaseConnection;
import lapr.project.data.PortDB;

import java.util.List;

public class PortController {
    private final PortDB pDB;

    public PortController () {
        pDB = new PortDB();
    }
    public List<String> occupancyRateShips(DatabaseConnection connection, int facilityCode, int year, int month){
        return  pDB.occupancyRateShips(connection,facilityCode,year,month);
    }
    public List<String> occupancyRateContainers(DatabaseConnection connection,int facilityCode,int year,int month){
        return  pDB.occupancyRateContainers(connection,facilityCode,year,month);
    }
}
