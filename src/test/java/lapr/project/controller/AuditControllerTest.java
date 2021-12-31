package lapr.project.controller;

import lapr.project.data.DatabaseConnection;
import lapr.project.model.AuditTrail;
import lapr.project.model.Container;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuditControllerTest {

    @Test
    void getAudit() throws SQLException {
        AuditController ac = new AuditController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        List<AuditTrail> audit = ac.getAudit(connection,4,"JORU1234563");
        for (int i = 0; i < audit.size(); i++) {
            System.out.println(audit.get(i));
        }
        String a = "[AuditTrail - Author = 'LAPR3_G076' Date = '2021-12-21 00:06:27' nrContainer = '4' Action = 'LAPR3_G076' idCargo = 'INSERT' idManifest = '4, AuditTrail - Author = 'LAPR3_G076' Date = '2021-12-21 00:06:27' nrContainer = '6' Action = 'LAPR3_G076' idCargo = 'INSERT' idManifest = '4]";
        assertNotEquals(a,audit.toString());
    }
}