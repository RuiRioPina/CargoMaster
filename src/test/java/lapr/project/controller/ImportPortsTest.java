package lapr.project.controller;

import lapr.project.model.PortStore;
import lapr.project.model.Ship;
import lapr.project.model.ShipStore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImportPortsTest {
    PortStore portStore = App.getInstance().getCompany().getPortStore();
    ShipStore shipStore = App.getInstance().getCompany().getShipStore();

    @Test
    void importShips3() {
        System.out.println(portStore.getSize());
    }
}
