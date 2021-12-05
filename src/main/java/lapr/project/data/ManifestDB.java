package lapr.project.data;

import oracle.jdbc.internal.OracleTypes;


import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

public class ManifestDB implements Persistable {
    DatabaseConnection databaseConnection;
    public ManifestDB() {
        databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
    }

    public int getContainerManifestsYear(DatabaseConnection connection,int shipCaptainID, int year){
        int res = 0;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FUNC_SHIPCAPTAINMANIFESTYEAR(?,?,?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, shipCaptainID);
            Date startDate= new Date(year,01,01);
            Date endDate= new Date(year,12,31);
            callStmt.setDate(3,startDate);
            callStmt.setDate(4,endDate);

            callStmt.execute();
            res  = (Integer) callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public double getAverageContainersForManifestYear(DatabaseConnection connection,int shipCaptainID, int year){
        double res = 0;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FUNC_GETAVERAGESCONTAINERSHIPCAPTAINYEAR(?,?,?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, shipCaptainID);
            Date startDate= new Date(year,01,01);
            Date endDate= new Date(year,12,31);
            callStmt.setDate(3,startDate);
            callStmt.setDate(4,endDate);

            callStmt.execute();
            res  = (Double) callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        return false;
    }

    @Override
    public boolean delete(DatabaseConnection databaseConnection, Object object) {
        return false;
    }
}

