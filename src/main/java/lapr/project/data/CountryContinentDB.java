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
        Port country = (Port) object;
        boolean returnValue = false;

        try {
            saveCountryToDatabase(databaseConnection, country);
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
        Port country = (Port) object;

        try {
            String sqlCommand;
            sqlCommand = "delete from COUNTRY where COUNTRY = ?";
            try (PreparedStatement deleteCountriesPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteCountriesPreparedStatement.setString(1,
                        country.getCountry());
                deleteCountriesPreparedStatement.executeUpdate();
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
     * Checks is a Country is already registered on the datase. If the client
     * is registered, it updates it. If it is not, it inserts a new one.
     *
     * @param databaseConnection
     * @param port
     * @throws SQLException
     */
    private void saveCountryToDatabase(DatabaseConnection databaseConnection,
                                       Port port) throws SQLException {

        if (isCountryOnDatabase(databaseConnection, port)) {
            updateCountryOnDatabase(databaseConnection, port);
        } else {
            insertCountryOnDatabase(databaseConnection, port);
        }

    }

    /**
     * Checks if a country is registered on the Database by its ID.
     *
     * @param databaseConnection
     * @param country
     * @return True if the country is registered, False if otherwise.
     * @throws SQLException
     */
    private boolean isCountryOnDatabase(DatabaseConnection databaseConnection,
                                        Port country) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isCountryOnDatabase = false;

        String sqlCommand = "select * from COUNTRY where COUNTRY = ?";

        PreparedStatement getCountriesPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getCountriesPreparedStatement.setString(1, country.getCountry());

        try (ResultSet portsResultSet = getCountriesPreparedStatement.executeQuery()) {

            if (portsResultSet.next()) {
                // if country already exists in the database
                isCountryOnDatabase = true;
            } else {

                // if country does not exist in the database
                isCountryOnDatabase = false;
            }
        }
        getCountriesPreparedStatement.close();
        return isCountryOnDatabase;
    }

    /**
     * Adds a new Country record to the database.
     *
     * @param databaseConnection
     * @param country
     * @throws SQLException
     */
    private void insertCountryOnDatabase(DatabaseConnection databaseConnection,
                                      Port country) throws SQLException {
        String sqlCommand =
                "insert into COUNTRY(COUNTRY) values (?)";

        executeShipStatementOnDatabase(databaseConnection, country,
                sqlCommand);
    }

    /**
     * Updates an existing client record on the database.
     *
     * @param databaseConnection
     * @param country
     * @throws SQLException
     */
    private void updateCountryOnDatabase(DatabaseConnection databaseConnection,
                                         Port country) throws SQLException {
        String sqlCommand =
                "update country set country = ? where COUNTRY = ?";

        executeShipStatementOnDatabase(databaseConnection, country,
                sqlCommand);
    }

    /**
     * Executes the save Country Statement.
     *
     * @param databaseConnection
     * @param country
     * @throws SQLException
     */
    private void executeShipStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Port country, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        try (PreparedStatement saveCountryPreparedStatement = connection.prepareStatement(
                sqlCommand)) {
            saveCountryPreparedStatement.setString(1, country.getCountry());
            //savePortPreparedStatement.setString(2, country.getContinent());

            saveCountryPreparedStatement.executeUpdate();
            saveCountryPreparedStatement.close();
        }
    }
}


