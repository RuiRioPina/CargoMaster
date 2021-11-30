package lapr.project.controller;

import lapr.project.model.PortStore;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImportPortsTest {
    PortStore portStore = App.getInstance().getCompany().getPortStore();
    @Test
    void importShips3() {
        System.out.println(portStore.getSize());
    }
}
