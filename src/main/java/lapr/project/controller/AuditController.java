package lapr.project.controller;

import lapr.project.data.AuditTrailDB;
import lapr.project.data.ContainerDB;
import lapr.project.data.DatabaseConnection;
import lapr.project.model.AuditTrail;
import lapr.project.model.Container;

import java.sql.SQLException;
import java.util.List;

public class AuditController {


    private AuditTrailDB aDB;

    public AuditController () {
        aDB = new AuditTrailDB();
    }

    public List<AuditTrail> getAudit (DatabaseConnection connection, int cargo, String nrContainer) throws SQLException {
        return aDB.getAuditTrail(connection,cargo,nrContainer);
    }

}
