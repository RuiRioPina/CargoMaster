package lapr.project.model;

import lapr.project.controller.App;
import lapr.project.controller.ImportShips;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ShipPairListTest {
ShipStore shipStore;
ShipPairList shipPairList;
Ship ship;
Ship ship2;
Ship ship3;
Ship ship4;
    List<Pair<Ship,Ship>> shipPairList2;
    @BeforeEach
    void setUp() {
        shipStore= new ShipStore();
        Identification idShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        ShipCharacteristics shipCharacteristics = new ShipCharacteristics(70, 166.0, 25.0, 9.5);
        Identification idShip2 = new Identification("210950001", "VARAMO", "IMO9395044", "C4SQ2");
        Identification idShip3 = new Identification("210950002", "VARAMO", "IMO9395044", "C4SQ2");
        Identification idShip4 = new Identification("210950003", "VARAMO", "IMO9395044", "C4SQ2");
        ShipDynamic dynamic = (new ShipDynamic("31/12/2020 16:12", new Location("42.73879", "-66.97726"), new Movement(13.4, 3.4, "357.0"), "NA", "A"));
        ShipDynamic dynamic1 = (new ShipDynamic("31/12/2020 17:03", new Location("42.92236", "-66.97243"), new Movement(12.5, 2.4,"358.0"), "NA", "A"));
        ShipDynamic dynamic2 = (new ShipDynamic("31/12/2020 17:13", new Location("42.95969", "-66.97106"), new Movement(12.9, 8.1,"358.0"), "NA", "A"));
        ShipDynamic dynamic3 = (new ShipDynamic("31/12/2020 16:32", new Location("42.81133", "-66.97587"), new Movement(13.4, 10.0, "356.0"), "NA", "A"));
        ShipDynamic dynamic4 = (new ShipDynamic("31/12/2020 16:52", new Location("42.8839", "-66.97577"), new Movement(12.7, 2.5, "359.0"), "NA", "A"));
        ShipDynamic dynamic5 = (new ShipDynamic("31/12/2020 17:33", new Location("43.02665", "-66.97076"), new Movement(12.5, 3.6,"354.0"), "NA", "A"));
        Route route = new Route();
        route.add(dynamic1);
        route.add(dynamic);
        route.add(dynamic2);
        route.add(dynamic3);
        route.add(dynamic4);
        route.add(dynamic5);
       ship = new Ship(idShip, shipCharacteristics, route);
        shipStore.addShipToAVL(ship);
        shipStore.organizeShipMessage();
        ShipDynamic dynamicEx1 = (new ShipDynamic("31/12/2020 17:03", new Location(
                "12.81133", "-66.97587"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamicEx3 = (new ShipDynamic("31/12/2020 16:32", new Location("42.72234", "-66.97726"), new Movement(13.4, 10.0, "356.0"), "NA", "A"));
        ShipDynamic dynamicEx5 = (new ShipDynamic("31/12/2020 17:33", new Location("43.04665", "-66.97075"), new Movement(12.5, 3.6, "354.0"), "NA", "A"));
        Route route2 = new Route();
        route2.add(dynamicEx1);
        route2.add(dynamicEx3);
        route2.add(dynamicEx5);


        shipStore.organizeShipMessage();
        ShipDynamic dynamicEx11 = (new ShipDynamic("31/12/2020 16:32", new Location("42.70233", "-66.97726"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamicEx31 = (new ShipDynamic("31/12/2020 17:03", new Location("32.81133", "-66.97587"), new Movement(13.4, 10.0, "356.0"), "NA", "A"));
        ShipDynamic dynamicEx51 = (new ShipDynamic("31/12/2020 17:33", new Location("43.02664", "-66.97072"), new Movement(12.5, 3.6, "354.0"), "NA", "A"));
        Route route3 = new Route();
        route3.add(dynamicEx11);
        route3.add(dynamicEx31);
        route3.add(dynamicEx51);
        ship2 = new Ship(idShip2, shipCharacteristics, route3);
         ship3 = new Ship(idShip3, shipCharacteristics, route2);
       shipStore.addShipToAVL(ship3);
       shipStore.organizeShipMessage();
        ShipDynamic dynamicEx12 = (new ShipDynamic("31/12/2020 17:03", new Location("02.92235", "-66.97243"), new Movement(12.5, 2.4, "358.0"), "NA", "A"));
        ShipDynamic dynamicEx32 = (new ShipDynamic("31/12/2020 16:32", new Location("32.81133", "-66.97587"), new Movement(13.4, 10.0, "356.0"), "NA", "A"));
        ShipDynamic dynamicEx52 = (new ShipDynamic("31/12/2020 17:33", new Location("43.02664", "-66.97072"), new Movement(12.5, 3.6, "354.0"), "NA", "A"));
        Route route4 = new Route();
        route4.add(dynamicEx12);
        route4.add(dynamicEx32);
        route4.add(dynamicEx52);
         ship4 = new Ship(idShip4, shipCharacteristics, route4);
        shipStore.addShipToAVL(ship4);
        shipStore.addShipToAVL(ship2);
        shipStore.organizeShipMessage();
        List<Pair<Ship,Ship>> shipPairList2=shipStore.getCloseShips();
        shipPairList= new ShipPairList(shipStore);





    }
    @Test
    public void getShipPairList(){
        List<Pair<Ship,Ship>> pairList0=new ArrayList<>();
        Pair<Ship,Ship> shipPair1= new Pair<>(ship,ship2);
        Pair<Ship,Ship> shipPair2= new Pair<>(ship,ship3);
        Pair<Ship,Ship> shipPair3= new Pair<>(ship2,ship3);

        pairList0.add(shipPair2);
        pairList0.add(shipPair1);
        pairList0.add(shipPair3);
        List<Pair<Ship,Ship>> pairList1=new ArrayList<>();
        Pair<Ship,Ship> shipPair4= new Pair<>(ship4,ship);
        pairList1.add(shipPair4);
        List<Pair<Ship,Ship>> pairList= shipPairList.getShipPairList();
        assertEquals(pairList,shipPairList.getShipPairList());
        assertNotEquals(pairList1,pairList);
        assertEquals(pairList0,pairList);
        ShipStore shipStore1= App.getInstance().getCompany().getShipStore();

        shipStore1.organizeShipMessage();
        ShipPairList shipPairList= new ShipPairList(shipStore1);
        List<Pair<Ship,Ship>> shipl= shipPairList.getShipPairList();
       assertNotEquals(shipl,App.getInstance().getCompany().getShipStore().getCloseShips());
       assertEquals(shipl,shipPairList.getShipPairList());

    }
    @Test
    public void getTravelledDistanceList(){

        Pair<Ship,Ship> shipPair1= new Pair<>(ship,ship2);
        Pair<Ship,Ship> shipPair2= new Pair<>(ship,ship3);
        Pair<Ship,Ship> shipPair3= new Pair<>(ship2,ship3);
        Pair<Ship,Ship> shipPair4= new Pair<>(ship4,ship);
        List<Double> doubles= new ArrayList<>();
        doubles.add(220.3);
        List< Double> doubleList0= new ArrayList<>();
        doubleList0.add(6655.627012149574);
        doubleList0.add(2203.594184088364);
        doubleList0.add(4452.032828061209);
        List<Double> travelledDistanceList= shipPairList.getTravelledDistanceList();
        assertEquals(travelledDistanceList,shipPairList.getTravelledDistanceList());
        assertNotEquals(doubleList0,doubles);
        assertEquals(doubleList0.get(0),travelledDistanceList.get(0),0.0001);
        assertEquals(doubleList0.get(1),travelledDistanceList.get(1),0.0001);
        assertEquals(doubleList0.get(2),travelledDistanceList.get(2),0.0001);
    }

}
