package lapr.project.data;

import oracle.jdbc.internal.OracleTypes;


import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
            String stringDate1=year+"/01/01 00:01";
            String stringDate2=year+"/12/31 23:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            java.util.Date date1 = null;
            java.util.Date date2 = null;
            try {
                date1 = sdf.parse(stringDate1);
                date2= sdf.parse(stringDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date1!= null;
            assert date2!= null;
            long millis1 = date1.getTime();
            long millis2 = date2.getTime();
            Date startDate = new Date(millis1);
            Date endDate= new Date(millis2);
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
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FUNC_GETAVERAGECONTAINERSHIPCAPTAINYEAR(?,?,?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
            callStmt.setInt(2, shipCaptainID);
            String stringDate1=year+"/01/01 00:01";
            String stringDate2=year+"/12/31 23:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            java.util.Date date1 = null;
            java.util.Date date2 = null;
            try {
                date1 = sdf.parse(stringDate1);
                date2= sdf.parse(stringDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date1!= null;
            assert date2!= null;
            long millis1 = date1.getTime();
            long millis2 = date2.getTime();
            Date startDate = new Date(millis1);
            Date endDate= new Date(millis2);
            callStmt.setDate(3,startDate);
            callStmt.setDate(4,endDate);

            callStmt.execute();
            res  = (Double) callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public double getoccupancyRate(DatabaseConnection connection, Integer codFacility)  {
        Double res = 0.0;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call fnc_occupancyRate(?)}")) {
            callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
            callStmt.setInt(2, codFacility);

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

