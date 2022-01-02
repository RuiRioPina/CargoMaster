package lapr.project.data;

import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PortDB {
    DatabaseConnection databaseConnection;

    /**
     * Construtor that will not be used since this class is not to be instantiated
     */

    public PortDB() {
        databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
    }
    public List<String> occupancyRateShips(DatabaseConnection connection, int facilityCode, int year, int month){
        List<String> occupRateList= new ArrayList<>();
        String res="";
        for (int i=1;i<monthNumOfDays(month)+1;i++) {
            try (CallableStatement callStmt = connection.getConnection().prepareCall("{? = call func_getShipsOccupInDay(?,?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.NUMBER);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
                java.util.Date date1u = null;
                String date =year+"/"+month+"/"+i;
                try {
                    date1u = sdf.parse(year+"/"+month+"/"+i+" 00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = date1u.getTime();
                Date date1 = new Date(millis);
                callStmt.setInt(2, facilityCode);
                callStmt.setDate(3, date1);

                callStmt.execute();
                res =callStmt.getString(1);
                occupRateList.add(date+" "+res+"%");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return occupRateList;
    }
    public List<String> occupancyRateContainers(DatabaseConnection connection,int facilityCode,int year,int month){
        List<String> occupRateList= new ArrayList<>();
        String res="";
        for (int i=1;i<monthNumOfDays(month)+1;i++) {
            try (CallableStatement callStmt = connection.getConnection().prepareCall("{? = call  func_getContainersOccupRateInDay(?,?) }")) {
                callStmt.registerOutParameter(1,OracleTypes.NUMBER);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
                java.util.Date date1u = null;
                String date =year+"/"+month+"/"+i;
                try {
                    date1u = sdf.parse(year+"/"+month+"/"+i+" 00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = date1u.getTime();
                Date date1 = new Date(millis);
                callStmt.setInt(2, facilityCode);
                callStmt.setDate(3, date1);

                callStmt.execute();
                res =callStmt.getString(1);
                occupRateList.add(date+" "+res+"%");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return occupRateList;
    }
    private int monthNumOfDays(int month){
        switch(month){
            case 1:
                return  31;
            case 2:
                return 28;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                return 0;

        }
    }
}
