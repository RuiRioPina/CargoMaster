package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
public class ManifestControllerTest {
    @Test
    void getContainerManifestsYear() throws SQLException {
        ManifestController mc = new ManifestController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        int res= mc.getContainerManifestsYear(connection,4,2022);
            System.out.println(res);

    }
    @Test
    void getAverageContainersForManifestYear(){
        ManifestController mc = new ManifestController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        Double res= mc.getAverageContainersForManifestYear(connection,4,2022);
        System.out.println(res);
    }

    @Test

    void getoccupancyRate(){
        ManifestController mc = new ManifestController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        Double res= mc.getoccupancyRate(connection,3);
        System.out.println(res);
    }

}
