package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.model.Container;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContainerControllerTest {

    @Test
    void getContainersToOffloadInNextPort() throws SQLException {
        ContainerController cc = new ContainerController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        List<Container> contOffload = cc.getContainersToOffloadInNextPort(connection,1,"2");
        for (int i = 0; i < contOffload.size(); i++) {
            System.out.println(contOffload.get(i));
        }
    }

    @Test
    void getContainersToLoadInNextPort() throws SQLException {
        ContainerController cc = new ContainerController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        List<Container> contLoad = cc.getContainersToLoadInNextPort(connection,1,"3");
        for (int i = 0; i < contLoad.size(); i++) {
            System.out.println(contLoad.get(i));
        }
    }

    @Test
    void getOccupancyRateFromDate() throws SQLException {
        ContainerController cc = new ContainerController();
        String mmsi = "987654321";
        String date = "2021/12/30 23:45";
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        int res = cc.getOccupancyRateFromDate(connection,mmsi,date);
            System.out.println("The occupancy rate on the " + date + " was " + res + "%");
    }

    @Test
    void getOccupancyRateFromCertainManifest() throws SQLException {
        String mmsi = "987654321";
        int idManifest = 3;
        ContainerController cc = new ContainerController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        double res = cc.getOccupancyRateFromCertainManifest(connection,mmsi,idManifest);
        System.out.println("The occupancy rate of the ship with the mmsi "+ mmsi +" on the manifest " + idManifest + " was " + res + "%");
    }


}