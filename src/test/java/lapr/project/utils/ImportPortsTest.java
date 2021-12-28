package lapr.project.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




class ImportPortsTest {

    @Test
    void testImportPorts() {
        ImportPorts.importPorts("fileName");
    }

    @Test
    void testImportPortsAndSaveToDatabase() {
        ImportPorts.importPortsAndSaveToDatabase("fileName");
    }
}

