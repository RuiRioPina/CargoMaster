package lapr.project.data;

import lapr.project.model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Addresses can only be manipulated by its parent object - Client -
 * therefore save, update and delete actions are only accessible from
 * inside ClientsStore class.
 *
 * @author nunocastro
 */
public class PositionShipDB implements Persistable {
    @Override
    public boolean save(DatabaseConnection databaseConnection,
                        Object object) {

        Connection connection = databaseConnection.getConnection();
        ShipDynamic shipDynamic = (ShipDynamic) object;

        String sqlCommand = "select * from positionship where IDPOSITIONSHIP = ?";
        boolean returnValue = false;
        try (PreparedStatement getPositionShipPreparedStatement = connection.prepareStatement(
                sqlCommand)) {
            getPositionShipPreparedStatement.setString(1, shipDynamic.getMmsi());
            try (ResultSet positionShipResultSet = getPositionShipPreparedStatement.executeQuery()) {

                if (positionShipResultSet.next()) {
                    sqlCommand =
                            "update positionship set basedatetime = ?, latitude = ?, longitude = ?, cog = ?, sog = ?, heading = ?, cargo = ? where MMSI = ?";
                } else {
                    sqlCommand =
                            "insert into positionship(basedatetime, latitude, longitude, cog, sog, heading,cargo, mmsi) values (?, ?, ?, ?, ?, ?, ?, ?)";
                }

                try (PreparedStatement saveShipPositionPreparedStatement = connection.prepareStatement(
                        sqlCommand)) {
                    String myDate = shipDynamic.getBaseDateTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    java.util.Date date = null;
                    try {
                        date = sdf.parse(myDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    assert date != null;
                    long millis = date.getTime();
                    Date date1 = new Date(millis);

                    saveShipPositionPreparedStatement.setDate(1, date1);
                    saveShipPositionPreparedStatement.setDouble(2, Double.parseDouble(shipDynamic.getLocation().getLatitude()));
                    saveShipPositionPreparedStatement.setDouble(3, Double.parseDouble(shipDynamic.getLocation().getLongitude()));
                    saveShipPositionPreparedStatement.setDouble(4, shipDynamic.getCog());
                    saveShipPositionPreparedStatement.setDouble(5, shipDynamic.getSog());
                    saveShipPositionPreparedStatement.setDouble(6, Double.parseDouble(shipDynamic.getMovement().getHeading()));
                    saveShipPositionPreparedStatement.setString(7, shipDynamic.getCargo());
                    saveShipPositionPreparedStatement.setInt(8, Integer.parseInt(shipDynamic.getMmsi()));

                    saveShipPositionPreparedStatement.executeUpdate();
                    saveShipPositionPreparedStatement.close();
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
        ShipDynamic shipDynamic = (ShipDynamic) object;

        boolean returnValue = false;
        try {
            String sqlCommand;
            sqlCommand = "delete from positionship where mmsi = ?";
            try (PreparedStatement deleteShipPositionPreparedStatement = conn.prepareStatement(
                    sqlCommand)) {
                deleteShipPositionPreparedStatement.setString(1, shipDynamic.getMmsi());
                deleteShipPositionPreparedStatement.executeUpdate();
                deleteShipPositionPreparedStatement.close();
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