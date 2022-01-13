package lapr.project.data;

import lapr.project.model.*;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AuditTrailDB {

    DatabaseConnection databaseConnection;

    /**
     * Construtor that will not be used since this class is not to be instantiated
     */

    public AuditTrailDB() {
        databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
    }

    public List<AuditTrail> getAuditTrail (DatabaseConnection connection, int cargo, String nrContainer) throws SQLException {
        ResultSet rSet;

        List<AuditTrail> audit = new LinkedList<>();

        try (CallableStatement c = connection.getConnection().prepareCall("{ ? = call fnc_getAudit(?,?)}");) {
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.setInt(2, cargo);
            c.setString(3, nrContainer);
            c.execute();
            rSet = (ResultSet) c.getObject(1);
            while (rSet.next()) {
                audit.add(new AuditTrail(rSet.getString(3),rSet.getString(2),rSet.getString(7),
                rSet.getString(4),rSet.getString(5),rSet.getString(6)));

            }
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
        return audit;
    }

    public List<ShipIdleDays> getShipIdleDays (DatabaseConnection connection, int fleetId) throws SQLException {
        ResultSet r1Set;

        List<String> mmsis = new ArrayList<>();

        try (CallableStatement c = connection.getConnection().prepareCall("{ ? = call fnc_getMmsi(?)}")) {
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.setInt(2, fleetId);
            c.execute();
            r1Set = (ResultSet) c.getObject(1);
            while (r1Set.next()) {
                mmsis.add(r1Set.getString(1));
            }
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }

        List <Integer> idle = new ArrayList<>();

        for (int i = 0; i < mmsis.size(); i++) {
            try (CallableStatement c = connection.getConnection().prepareCall("{ ? = call fnc_shipIdleDays(?,?)}")) {
                c.registerOutParameter(1, OracleTypes.INTEGER);
                c.setInt(2, fleetId);
                c.setString(3,mmsis.get(i));
                c.execute();
                idle.add((Integer) c.getObject(1));

            } catch (SQLException ignored) {
                ignored.printStackTrace();
            }
        }
        List <ShipIdleDays> shipIdleDays = new ArrayList<>();
        for (int i = 0; i < idle.size(); i++) {
            shipIdleDays.add(new ShipIdleDays(mmsis.get(i),idle.get(i)));
        }
        return shipIdleDays;
    }

}


