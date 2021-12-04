package lapr.project.data;

import lapr.project.model.ShipEnergy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipEnergyDB implements Persistable {
    @Override
    public boolean save(DatabaseConnection databaseConnection,
                        Object object) {
        Connection connection = databaseConnection.getConnection();
        ShipEnergy shipEnergy = (ShipEnergy) object;

        String sqlCommand = "select * from energy where IDENERGY = ?";
        boolean returnValue = false;
        try (PreparedStatement getEnergyShipPreparedStatement = connection.prepareStatement(
                sqlCommand)) {
            getEnergyShipPreparedStatement.setInt(1, shipEnergy.getIdEnergy());
            try (ResultSet shipEnergyResultSet = getEnergyShipPreparedStatement.executeQuery()) {

                if (shipEnergyResultSet.next()) {
                    sqlCommand =
                            "update energy set NRGENERATORS = ?, POWER = ? where IDENERGY = ?";
                } else {
                    sqlCommand =
                            "insert into energy(NRGENERATORS, POWER) values (?, ?)";
                }

                try (PreparedStatement saveShipEnergyPreparedStatement = connection.prepareStatement(
                        sqlCommand)) {

                    saveShipEnergyPreparedStatement.setInt(1, shipEnergy.getNrGenerators());
                    saveShipEnergyPreparedStatement.setDouble(2, shipEnergy.getPower());

                    saveShipEnergyPreparedStatement.executeUpdate();
                    saveShipEnergyPreparedStatement.close();
                    returnValue = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;

    }

    @Override
    public boolean delete(DatabaseConnection databaseConnection,
                          Object object) {

        Connection conn = databaseConnection.getConnection();
        ShipEnergy shipEnergy = (ShipEnergy) object;

        boolean returnValue = false;
        try {
            String sqlCommand;
            sqlCommand = "delete from energy where idenergy = ?";
            try (PreparedStatement deleteShipEnergyPreparedStatement = conn.prepareStatement(
                    sqlCommand)) {
                deleteShipEnergyPreparedStatement.setInt(1, shipEnergy.getIdEnergy());
                deleteShipEnergyPreparedStatement.executeUpdate();
                deleteShipEnergyPreparedStatement.close();
                returnValue = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipStoreDB.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }
}
