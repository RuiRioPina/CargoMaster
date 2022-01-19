package lapr.project.controller;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.DatabaseConnection;
import lapr.project.model.Port;
import lapr.project.model.PortStore;
import lapr.project.model.Ship;
import lapr.project.model.ShipStore;
import lapr.project.utils.ImportPorts;
import org.junit.jupiter.api.Test;

public class ImportPortsTest {
    PortStore portStore = App.getInstance().getCompany().getPortStore();
    ShipStore shipStore = App.getInstance().getCompany().getShipStore();

    @Test
    void importPorts() {
        ImportPorts importPorts = new ImportPorts();
        String fileName = "csvFiles/sports.csv";
        importPorts.importPorts(fileName);
    }

    @Test
    void importPorts1() {
        ImportPorts importPorts = new ImportPorts();

        String fileName = "csvFiles/sports.csv";
        importPorts.importPortsAndSaveToDatabase(fileName);

    }


}
