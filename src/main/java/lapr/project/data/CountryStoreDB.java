package lapr.project.data;

import lapr.project.controller.App;
import lapr.project.model.*;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryStoreDB {
    DatabaseConnection databaseConnection;
    public CountryStoreDB(){
        databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");

    }
    public List<Country> getAllCountries(DatabaseConnection connection){
        List<Country> countryList = new ArrayList<>();
        ResultSet rSet;
        try(CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call fnc_getAllCountries()}");){
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while(rSet.next()){
                Country country = new Country(new Continent(rSet.getString(2)),new Location(rSet.getString(7),rSet.getString(8)),rSet.getString(1),rSet.getString(3),rSet.getString((4)),Double.parseDouble(rSet.getString(5)),rSet.getString(6));
                countryList.add(country);
            }
        }catch(SQLException ignored) {
            ignored.printStackTrace();
        }
        return countryList;
    }




}
