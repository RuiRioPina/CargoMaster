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
        List<Container> contOffload = cc.getContainersToOffloadInNextPort(connection,345,"4");
        assertEquals(2,contOffload.size());
        //assertEquals(contOffload.toString(),  "[Container{numberContainer='SSDU2124366', typeOfVehicle='null', dimension=null, client=null, type= Type = Refrigerated', iso='null', certificate='null', load='Cash', typeManifest='null', position=[X = 3, Y = 0, Z = 0], nextPort=Port{continent=null, country='null', code=0, namePort='Berlengas Port', location=null, averageCloseness=0.0}, date='2022-04-03 14:30:00', arrivalDate='null', departureDate='null'}, Container{numberContainer='TVCU2124466', typeOfVehicle='null', dimension=null, client=null, type= Type = Refrigerated', iso='null', certificate='null', load='Medicine', typeManifest='null', position=[X = 1, Y = 0, Z = 0], nextPort=Port{continent=null, country='null', code=0, namePort='Berlengas Port', location=null, averageCloseness=0.0}, date='2022-04-03 14:30:00', arrivalDate='null', departureDate='null'}]");
        for (int i = 0; i < contOffload.size(); i++) {
            System.out.println(contOffload.get(i));
        }
    }

    @Test
    void getRouteFromContainer() throws SQLException {
        ContainerController cc = new ContainerController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        List<Container> contLoad = cc.getRoute(connection,1,"ABCU1827364");
        for (Container container : contLoad) {
            System.out.println(container);
        }
    }

    @Test
    void getContainersToLoadInNextPort() throws SQLException {
        ContainerController cc = new ContainerController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        List<Container> contLoad = cc.getContainersToLoadInNextPort(connection,345,"3");
        //assertEquals(4,contLoad.size());
        //assertEquals(contLoad.toString(),"[Container{numberContainer='ABCU1113456', typeOfVehicle='null', dimension=null, client=null, type= Type = Refrigerated', iso='null', certificate='null', load='Bottled Wine', typeManifest='null', position=[X = 0, Y = 0, Z = 0], nextPort=Port{continent=null, country='null', code=0, namePort='Porto de Aveiro', location=null, averageCloseness=0.0}, date='2022-03-01 17:30:00', arrivalDate='null', departureDate='null'}, Container{numberContainer='SSDU2124366', typeOfVehicle='null', dimension=null, client=null, type= Type = Refrigerated', iso='null', certificate='null', load='Cash', typeManifest='null', position=[X = 3, Y = 0, Z = 0], nextPort=Port{continent=null, country='null', code=0, namePort='Porto de Aveiro', location=null, averageCloseness=0.0}, date='2022-03-01 17:30:00', arrivalDate='null', departureDate='null'}, Container{numberContainer='TVCU2124466', typeOfVehicle='null', dimension=null, client=null, type= Type = Refrigerated', iso='null', certificate='null', load='Medicine', typeManifest='null', position=[X = 1, Y = 0, Z = 0], nextPort=Port{continent=null, country='null', code=0, namePort='Porto de Aveiro', location=null, averageCloseness=0.0}, date='2022-03-01 17:30:00', arrivalDate='null', departureDate='null'}, Container{numberContainer='XGCU2123466', typeOfVehicle='null', dimension=null, client=null, type= Type = Refrigerated', iso='null', certificate='null', load='Motorbike', typeManifest='null', position=[X = 2, Y = 0, Z = 0], nextPort=Port{continent=null, country='null', code=0, namePort='Porto de Aveiro', location=null, averageCloseness=0.0}, date='2022-03-01 17:30:00', arrivalDate='null', departureDate='null'}]");
        for (int i = 0; i < contLoad.size(); i++) {
            System.out.println(contLoad.get(i));
        }
    }
    
//    @Test
//    void getOccupancyRateFromDate() throws SQLException {
//        ContainerController cc = new ContainerController();
//        String mmsi = "987654321";
//        String date = "2021/12/14 10:00";
//        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
//        int res = cc.getOccupancyRateFromDate(connection,mmsi,date);
//            System.out.println("The occupancy rate on the " + date + " was " + res + "%");
//    }

//    @Test
//    void getOccupancyRateFromCertainManifest() throws SQLException {
//        String mmsi = "987654321";
//        int idManifest = 3;
//        ContainerController cc = new ContainerController();
//        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
//        double res = cc.getOccupancyRateFromCertainManifest(connection,mmsi,idManifest);
//        System.out.println("The occupancy rate of the ship with the mmsi "+ mmsi +" on the manifest " + idManifest + " was " + res + "%");
//    }
    @Test
    void getContainerStatus() throws SQLException {
        String numberContainer = "JORU1234513";
        int clientid=1;
        ContainerController cc = new ContainerController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        String res = cc.getContainerStatus(connection,numberContainer,clientid);


       //System.out.println(res);
    }



}