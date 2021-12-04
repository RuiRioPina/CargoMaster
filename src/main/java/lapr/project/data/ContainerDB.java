package lapr.project.data;


import lapr.project.model.Container;
import lapr.project.model.Port;
import lapr.project.model.Position;
import lapr.project.model.TypeContainer;
import oracle.jdbc.internal.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ContainerDB implements Persistable {

    DatabaseConnection databaseConnection;
    /**
     * Construtor that will not be used since this class is not to be instantiated
     */

    public ContainerDB() {
        databaseConnection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
    }

    public List<Container> getContainersToLoadInNextPort (DatabaseConnection connection, int idShipCap, String portCode) throws SQLException {
        List<Container> l = new LinkedList<>();

        ResultSet rSet;

        try (CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call fnc_getContainersToLoad(?,?)}");){
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.setInt(2,idShipCap);
            callStmtAux.setString(3,portCode);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while(rSet.next()){
                l.add(new Container(rSet.getString(1), new TypeContainer(rSet.getString(2)),
                        rSet.getString(3), new Position(rSet.getInt(4), rSet.getInt(5),
                        rSet.getInt(6)), new Port(rSet.getString(7)),rSet.getString(8)));

            }
        }catch(SQLException ignored) {
        ignored.printStackTrace();
      }
      return l;
      }

    public List<Container> getContainersToOffloadInNextPort (DatabaseConnection connection, int idShipCap, String portCode) throws SQLException {
        List<Container> list = new LinkedList<>();

        ResultSet rSet;

        try (CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call fnc_getContainersToOffload(?,?)}");){
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.setInt(2,idShipCap);
            callStmtAux.setString(3,portCode);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while(rSet.next()){
                list.add(new Container(rSet.getString(1), new TypeContainer(rSet.getString(2)),
                        rSet.getString(3), new Position(rSet.getInt(4), rSet.getInt(5),
                        rSet.getInt(6)), new Port(rSet.getString(7)),rSet.getString(8)));
            }
        }catch(SQLException ignored) {
            ignored.printStackTrace();
        }
        return list;
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

