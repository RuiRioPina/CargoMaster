package lapr.project.data;

import oracle.jdbc.internal.OracleTypes;


import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ManifestDB {
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

    public Integer getOffcontainer(DatabaseConnection connection, Integer codFacility)  {
        Integer res = 0;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call fnc_offcontainer(?)}")) {
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, codFacility);

            callStmt.execute();
            res  = (Integer) callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

    public void getOffLoadLoadMapTruck(DatabaseConnection connection, Integer codFacility)  {
        ResultSet rSet;
        try (CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call func_containersLoadOffLoadInADayTruck(?)}")) {

            callStmtAux.registerOutParameter(1, OracleTypes.REF_CURSOR);
            callStmtAux.setInt(2, codFacility);


            callStmtAux.executeQuery();

            rSet = (ResultSet) callStmtAux.getObject(1);
            System.out.println("Date of Manifest     Number of Container  Type of Manifest  Registration of the Truck  Code Facility");
            while (rSet.next()) {

                System.out.printf("%s    %s            %s                     %s                %s%n",(rSet.getString(1)),
                        rSet.getString(2), rSet.getString(3), rSet.getString(4), rSet.getString(5));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public void getOffLoadLoadMapShip(DatabaseConnection connection, Integer codFacility)  {
        ResultSet rSet;
        try (CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call func_containersLoadOffLoadInADaySHIP(?)}")) {

            callStmtAux.registerOutParameter(1, OracleTypes.REF_CURSOR);
            callStmtAux.setInt(2, codFacility);


            callStmtAux.executeQuery();

            rSet = (ResultSet) callStmtAux.getObject(1);

            System.out.println("Date of Manifest  Number of Container   x   y   z   Type of Manifest Ship MMSI  Code Facility");
            while (rSet.next()) {

                System.out.printf("%s   %s       %d   %d   %d        %s        %d       %d%n",(rSet.getString(1)),
                        rSet.getString(2), rSet.getInt(3), rSet.getInt(4), rSet.getInt(5),
                        rSet.getString(6), rSet.getInt(7), (rSet.getInt(8)));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public String getmanifestOccupancySmaller(DatabaseConnection connection)  {
        String res = null;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FNC_manifestOccupancySmaller1}")) {
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);

            callStmt.executeUpdate();
            res  = callStmt.getString(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

    public Double getManifestOccupancyRate(DatabaseConnection connection, Integer vehicle, String startDate, String endDate)  {
        Double res = 0.0;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FNC_manifestOccupancyRate(?,?,?)}")) {
            callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
            callStmt.setInt(2,vehicle);
            java.sql.Date date1=null;
            java.sql.Date date2=null;
            try {
                SimpleDateFormat formatter  = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                date1 = new java.sql.Date( (formatter.parse(startDate)).getTime() );
                date2 = new java.sql.Date( (formatter.parse(endDate)).getTime() );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            callStmt.setDate(3, date1);
            callStmt.setDate(4, date2);

            callStmt.execute();
            res  = (Double) callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

//    @Override
//    public boolean save(DatabaseConnection databaseConnection, Object object) {
//        return false;
//    }
//
//    @Override
//    public boolean delete(DatabaseConnection databaseConnection, Object object) {
//        return false;
//    }


}

