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
}