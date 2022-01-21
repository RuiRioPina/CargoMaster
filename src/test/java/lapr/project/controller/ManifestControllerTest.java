package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
public class ManifestControllerTest {
       /* @Test
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

    @Test
    void getOffcontainer(){
        ManifestController mc = new ManifestController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        Integer res= mc.getOffcontainer(connection,3);
        System.out.println(res);
    }

    @Test
    void insertCargoManifestTrip(){
        try {
            DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
            PreparedStatement prepareStatement = connection.getConnection().prepareStatement("INSERT INTO TRIP(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip,ShipCaptainNrIdentification   ,FleetManagerNrIdentification  ,ChiefEletricalNrIdentificatio, startVehicle ) VALUES(12,3,5,TO_DATE('2022/04/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2022/05/05 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),345,459,5,5)");
            prepareStatement.execute();
        } catch (SQLException e) {
            System.out.println("It was not possible to allow a cargo manifest for the trip because the ship is already in transit.");
            System.out.println(e.getMessage());
        }

    }*/
    /*
    @Test
    void getOffLoadLoadMapTruck(){
        ManifestController mc = new ManifestController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsrvbd1.dei.isep.ipp.pt:1521/pdborcl?oracle.net.disableOob=true", "LAPR3_G076_C", "qwerty");
        int codeFacility = 1;
        System.out.println("Facility " + codeFacility + " next week's operations: \n");
        System.out.println("Containers transported by ship");
        System.out.println("-------------------------------------------------");
        mc.getOffLoadLoadMapShip(connection,codeFacility);
        System.out.println("-------------------------------------------------");
        System.out.println();
        System.out.println("Containers transported by truck");
        System.out.println("-------------------------------------------------");
        mc.getOffLoadLoadMapTruck(connection,codeFacility);
        System.out.println("-------------------------------------------------");


    }
*/

}
