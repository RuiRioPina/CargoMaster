package lapr.project.controller;

import lapr.project.data.ContainerDB;
import lapr.project.data.DatabaseConnection;
import lapr.project.model.Container;

import java.sql.SQLException;
import java.util.List;

public class ContainerController {

    private final ContainerDB cDB;

    public ContainerController () {
    cDB = new ContainerDB();
    }

    public List<Container> getContainersToOffloadInNextPort (DatabaseConnection connection, int idShipCap, String portCode) throws SQLException {
        return cDB.getContainersToOffloadInNextPort(connection,idShipCap,portCode);
    }


    public List<Container> getContainersToLoadInNextPort (DatabaseConnection connection, int idShipCap, String portCode) throws SQLException {
        return cDB.getContainersToLoadInNextPort(connection,idShipCap,portCode);
    }

    public List<Container> getRoute (DatabaseConnection connection, int idClient, String numberContainer) throws SQLException {
        return cDB.getRoute(connection,idClient,numberContainer);
    }
    public int getOccupancyRateFromDate(DatabaseConnection connection, String mmsi, String date) throws SQLException {
        return cDB.getOccupancyRateFromDate(connection,mmsi, date);
    }

    public double getOccupancyRateFromCertainManifest(DatabaseConnection connection, String mmsi, int idManifest) throws SQLException {
        return cDB.getOccupancyRateFromCertainManifest(connection,mmsi, idManifest);
    }
    public String getContainerStatus(DatabaseConnection connection, String numberContainer,int clientID)throws SQLException{
        return cDB.getContainerStatus(connection,numberContainer,clientID);
    }


}
