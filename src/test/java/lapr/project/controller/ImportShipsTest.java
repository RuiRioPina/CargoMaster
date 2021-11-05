package lapr.project.controller;

import lapr.project.data.ShipStore;
import lapr.project.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ImportShipsTest {
    Ship ship;
    Route route = new Route();
    ShipCharacteristics shipCharacteristics;
    Identification idShip;
    ShipDynamic dynamic;
    @Before
    public void setUp() throws Exception {
        idShip = new Identification("210950000", "VARAMO", "IMO9395044", "C4SQ2");
        shipCharacteristics = new ShipCharacteristics(70, 166, 25, 9.5);

        dynamic = (new ShipDynamic("31/12/2020 16:12", new Location(42.73879, -66.97726), new Movement(13.4, 3.4, 357), "NA", "A"));
        route.add(dynamic);
        ship = new Ship(idShip, shipCharacteristics, route);
    }



}