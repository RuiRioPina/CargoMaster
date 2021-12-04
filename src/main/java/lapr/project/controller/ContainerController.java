package lapr.project.controller;

import lapr.project.data.ContainerDB;
import lapr.project.data.DatabaseConnection;
import lapr.project.model.Container;
import lapr.project.model.ShipStore;

import java.sql.SQLException;
import java.util.List;

public class ContainerController {

    private ContainerDB cDB;

    public ContainerController () {
    cDB = new ContainerDB();
    }

    public List<Container> getContainersToOffloadInNextPort (DatabaseConnection connection, int idShipCap, String portCode) throws SQLException {
        return cDB.getContainersToOffloadInNextPort(connection,idShipCap,portCode);
    }

    public List<Container> getContainersToLoadInNextPort (DatabaseConnection connection, int idShipCap, String portCode) throws SQLException {
        return cDB.getContainersToLoadInNextPort(connection,idShipCap,portCode);
    }

}
