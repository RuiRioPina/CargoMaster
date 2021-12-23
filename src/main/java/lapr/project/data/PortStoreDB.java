package lapr.project.data;

import lapr.project.controller.App;
import lapr.project.model.*;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortStoreDB implements Persistable {
    CountryContinentDB countryContinentDB = new CountryContinentDB();
    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        Port port = (Port) object;
        boolean returnValue = false;

        try {
            countryContinentDB.save(databaseConnection,port);
            savePortToDatabase(databaseConnection, port);

            //Save changes.
            returnValue = true;

        } catch (SQLException ex) {
            Logger.getLogger(PortStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }

    @Override
    public boolean delete(DatabaseConnection databaseConnection,
                          Object object) {
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        Port port = (Port) object;

        try {
            String sqlCommand;
            sqlCommand = "delete from FACILITY where CODEFACILITY = ?";
            try (PreparedStatement deletePortsPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deletePortsPreparedStatement.setInt(1,
                        port.getCode());
                deletePortsPreparedStatement.executeUpdate();
            }

            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(PortStoreDB.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);
            returnValue = false;
        }

        return returnValue;
    }

    /**
     * Checks is a cliente is already registered on the datase. If the client
     * is registered, it updates it. If it is not, it inserts a new one.
     *
     * @param databaseConnection
     * @param port
     * @throws SQLException
     */
    private void savePortToDatabase(DatabaseConnection databaseConnection,
                                    Port port) throws SQLException {
        CountryContinentDB countryContinentDB = new CountryContinentDB();
        countryContinentDB.save(databaseConnection, port);
        if (isPortOnDatabase(databaseConnection, port)) {
            updatePortOnDatabase(databaseConnection, port);
        } else {
            insertPortOnDatabase(databaseConnection, port);
        }

    }

    /**
     * Checks if a client is registered on the Database by its ID.
     *
     * @param databaseConnection
     * @param port
     * @return True if the cliente is registered, False if otherwise.
     * @throws SQLException
     */
    private boolean isPortOnDatabase(DatabaseConnection databaseConnection,
                                     Port port) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isPortOnDatabase = false;

        String sqlCommand = "select * from FACILITY where CODEFACILITY = ?";

        PreparedStatement getPortsPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getPortsPreparedStatement.setInt(1, port.getCode());

        try (ResultSet portsResultSet = getPortsPreparedStatement.executeQuery()) {

            if (portsResultSet.next()) {
                // if client already exists in the database
                isPortOnDatabase = true;
            } else {

                // if client does not exist in the database
                isPortOnDatabase = false;
            }
        }
        getPortsPreparedStatement.close();
        return isPortOnDatabase;
    }

    /**
     * Adds a new client record to the database.
     *
     * @param databaseConnection
     * @param port
     * @throws SQLException
     */
    private void insertPortOnDatabase(DatabaseConnection databaseConnection,
                                      Port port) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String sqlCommand =
                "insert into facility(nameFacility,latitude, longitude, typeFacility, country, codeFacility) values (?, ?, ?, ?, ?, ?)";

        executeShipStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }

    /**
     * Updates an existing client record on the database.
     *
     * @param databaseConnection
     * @param port
     * @throws SQLException
     */
    private void updatePortOnDatabase(DatabaseConnection databaseConnection,
                                      Port port) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String sqlCommand =
                "update facility set nameFacility = ?, latitude = ?, longitude = ?, typeFacility = ?, country= ? where codeFacility = ?";

        executeShipStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }

    /**
     * Executes the save Client Statement.
     *
     * @param databaseConnection
     * @param port
     * @throws SQLException
     */
    private void executeShipStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Port port, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(
                        sqlCommand);

        savePortPreparedStatement.setString(1, port.getNamePort());

        savePortPreparedStatement.setDouble(2, Double.parseDouble(port.getLatitude()));

        savePortPreparedStatement.setDouble(3, Double.parseDouble(port.getLongitude()));

        savePortPreparedStatement.setString(4, "port");

        savePortPreparedStatement.setString(5, port.getCountry());

        savePortPreparedStatement.setInt(6, port.getCode());

        savePortPreparedStatement.executeUpdate();
        savePortPreparedStatement.close();
    }

    PortStore portStore = App.getInstance().getCompany().getPortStore();

    public List<Port> getPorts(DatabaseConnection connection) {
        CountryStore countryStore= App.getInstance().getCompany().getCountryStore();
        List<Port> portList = new ArrayList<>();
        ResultSet rSet;
        try(CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call fnc_getAllFacilities()}");){
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while(rSet.next()){
                if (Integer.parseInt(rSet.getString(1))>1000&& rSet.getString(5).equals("port")) {
                    Port port = new Port(countryStore.getContinentByCountry(rSet.getString(7)),rSet.getString(7),Integer.parseInt(rSet.getString(1)),rSet.getString(2),new Location(rSet.getString(3),rSet.getString(4)));
                    portList.add(port);
                }
                }
        }catch(SQLException ignored) {
            ignored.printStackTrace();
        }
return portList;
    }

    public static void main(String[] args) {
        PortStoreDB portStoreDB= new PortStoreDB();
        System.out.println(portStoreDB.getPorts(new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword")));
    }

}
