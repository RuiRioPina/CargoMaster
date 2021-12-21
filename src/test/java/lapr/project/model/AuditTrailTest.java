package lapr.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AuditTrailTest {
    @Test
    void testConstructor() {
        assertEquals(
                "AuditTrail - Author = 'JaneDoe' Date = '2020-03-01' nrContainer = 'Nr Container' Action = 'Action'"
                        + " idCargo = 'Id Cargo' idManifest = 'Id Manifest",
                (new AuditTrail("JaneDoe", "2020-03-01", "Nr Container", "Action", "Id Cargo", "Id Manifest")).toString());
    }
}

