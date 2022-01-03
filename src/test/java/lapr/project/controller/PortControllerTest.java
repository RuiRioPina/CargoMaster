package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class PortControllerTest {

    @Test
    void occupancyRate() throws SQLException {

        PortController pc = new PortController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        System.out.println("Ship occupancy rate:");
        System.out.println(pc.occupancyRateShips(connection,29002,2022,6));
        System.out.println("Container occupancy rate:");
        System.out.println(pc.occupancyRateContainers(connection,29002,2022,6));


    }
}
