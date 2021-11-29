//package lapr.project.data;
//
//import lapr.project.model.ShipEnergy;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class PortStoreDB implements Persistable {
//    int i = 1;
//    @Override
//    public boolean save(DatabaseConnection databaseConnection,
//                        Object object) {
//        Connection connection = databaseConnection.getConnection();
//        ShipEnergy shipDynamic = (ShipEnergy) object;
//
//        String sqlCommand = "select * from port where IDENERGY = ?";
//        boolean returnValue = false;
//        try (PreparedStatement getPositionShipPreparedStatement = connection.prepareStatement(
//                sqlCommand)) {
//            getPositionShipPreparedStatement.setInt(1, shipDynamic.getIdEnergy());
//            try (ResultSet positionShipResultSet = getPositionShipPreparedStatement.executeQuery()) {
//
//                if (positionShipResultSet.next()) {
//                    sqlCommand =
//                            "update energy set IDENERGY = ?, NRGENERATORS = ?, POWER = ? where IDENERGY = ?";
//                } else {
//                    sqlCommand =
//                            "insert into energy(IDENERGY, NRGENERATORS, POWER) values (?, ?, ?)";
//                }
//
//                try (PreparedStatement saveShipPositionPreparedStatement = connection.prepareStatement(
//                        sqlCommand)) {
//                    saveShipPositionPreparedStatement.setInt(1,i);
//                    saveShipPositionPreparedStatement.setInt(2, shipDynamic.getNrGenerators());
//                    saveShipPositionPreparedStatement.setDouble(3, shipDynamic.getPower());
//
//
//                    i++;
//                    saveShipPositionPreparedStatement.executeUpdate();
//                    saveShipPositionPreparedStatement.close();
//                    returnValue = true;
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ShipStoreDB.class.getName())
//                    .log(Level.SEVERE, null, ex);
//            databaseConnection.registerError(ex);
//            returnValue = false;
//        }
//        return returnValue;
//
//    }
//
//    @Override
//    public boolean delete(DatabaseConnection databaseConnection,
//                          Object object) {
//
//        Connection conn = databaseConnection.getConnection();
//        ShipEnergy shipDynamic = (ShipEnergy) object;
//
//        boolean returnValue = false;
//        try {
//            String sqlCommand;
//            sqlCommand = "delete from energy where idenergy = ?";
//            try (PreparedStatement deleteShipPositionPreparedStatement = conn.prepareStatement(
//                    sqlCommand)) {
//                deleteShipPositionPreparedStatement.setInt(1, shipDynamic.getIdEnergy());
//                deleteShipPositionPreparedStatement.executeUpdate();
//                deleteShipPositionPreparedStatement.close();
//                returnValue = true;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ShipStoreDB.class.getName())
//                    .log(Level.SEVERE, null, ex);
//            databaseConnection.registerError(ex);
//            returnValue = false;
//        }
//        return returnValue;
//    }
//}
