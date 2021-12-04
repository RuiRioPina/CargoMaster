package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortTest {

    @Test
    void testToString() {
        Port p = new Port ("Leixões");
        String port = "Port = Leixões";
    }
}