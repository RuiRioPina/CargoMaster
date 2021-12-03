package lapr.project.data;

import lapr.project.model.Port;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryContinentDB implements Persistable {

    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        Port port = (Port) object;
        boolean returnValue = false;

        try {
            savePortToDatabase(databaseConnection, port);
            //Save changes.
            returnValue = true;

        } catch (SQLException ex) {
            Logger.getLogger(lapr.project.data.PortStoreDB.class.getName())
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
            sqlCommand = "delete from COUNTRY where COUNTRY = ?";
            try (PreparedStatement deleteCoutnriesPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteCoutnriesPreparedStatement.setString(1,
                        port.getCountry());
                deleteCoutnriesPreparedStatement.executeUpdate();
            }

            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(lapr.project.data.CountryContinentDB.class.getName())
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

        String sqlCommand = "select * from COUNTRY where COUNTRY = ?";

        PreparedStatement getPortsPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getPortsPreparedStatement.setString(1, port.getCountry());

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
                "insert into COUNTRY(COUNTRY) values (?)";

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
        String sqlCommand =
                "update country set country = ? where COUNTRY = ?";

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

        try (PreparedStatement savePortPreparedStatement = connection.prepareStatement(
                sqlCommand)) {
            savePortPreparedStatement.setString(1, port.getCountry());
            //savePortPreparedStatement.setString(2, port.getContinent());

            savePortPreparedStatement.executeUpdate();
            savePortPreparedStatement.close();
        }
    }
}


