package lapr.project.data;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import lapr.project.controller.App;
import lapr.project.model.*;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nunocastro
 */
public class ShipStoreDB implements Persistable {
    ShipStore shipStore = App.getInstance().getCompany().getShipStore();

    @Override
    public boolean save(DatabaseConnection databaseConnection, Object object) {
        Ship ship = (Ship) object;
        boolean returnValue = false;

        try {
            saveShipToDatabase(databaseConnection, ship);

            //Delete shiPositions.
            deleteShipPositions(databaseConnection, ship);

            //Post new shipPositions.
            addShipPositons(databaseConnection, ship);


            //Save changes.
            returnValue = true;

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
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        Ship ship = (Ship) object;

        try {
            String sqlCommand;
            sqlCommand = "delete from positionShip where mmsi = ?";
            try (PreparedStatement deleteShipPositionsPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteShipPositionsPreparedStatement.setString(1,
                        ship.getShipId().getMmsi());
                deleteShipPositionsPreparedStatement.executeUpdate();
            }

            sqlCommand = "delete from ship where mmsi = ?";
            try (PreparedStatement deleteShipPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteShipPreparedStatement.setString(1, ship.getShipId().getMmsi());
                deleteShipPreparedStatement.executeUpdate();
            }

            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(PositionShipDB.class.getName())
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
     * @param ship
     * @throws SQLException
     */
    private void saveShipToDatabase(DatabaseConnection databaseConnection,
                                    Ship ship) throws SQLException {

        if (isShipOnDatabase(databaseConnection, ship)) {
            updateShipOnDatabase(databaseConnection, ship);
        } else {
            insertShipOnDatabase(databaseConnection, ship);
        }

    }

    /**
     * Checks if a client is registered on the Database by its ID.
     *
     * @param databaseConnection
     * @param client
     * @return True if the cliente is registered, False if otherwise.
     * @throws SQLException
     */
    private boolean isShipOnDatabase(DatabaseConnection databaseConnection,
                                     Ship client) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isShipOnDatabase = false;

        String sqlCommand = "select * from ship where mmsi = ?";

        PreparedStatement getShipsPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getShipsPreparedStatement.setString(1, client.getShipId().getMmsi());

        try (ResultSet shipsResultSet = getShipsPreparedStatement.executeQuery()) {

            if (shipsResultSet.next()) {
                // if client already exists in the database
                isShipOnDatabase = true;
            } else {

                // if client does not exist in the database
                isShipOnDatabase = false;
            }
        }
        getShipsPreparedStatement.close();
        return isShipOnDatabase;
    }

    /**
     * Adds a new client record to the database.
     *
     * @param databaseConnection
     * @param ship
     * @throws SQLException
     */
    private void insertShipOnDatabase(DatabaseConnection databaseConnection,
                                      Ship ship) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String sqlCommand =
                "insert into ship(draft, nameship, imo, typeship, capacity, length, width, callsign, mmsi) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        executeShipStatementOnDatabase(databaseConnection, ship,
                sqlCommand);
    }

    /**
     * Updates an existing client record on the database.
     *
     * @param databaseConnection
     * @param ship
     * @throws SQLException
     */
    private void updateShipOnDatabase(DatabaseConnection databaseConnection,
                                      Ship ship) throws SQLException {
        String sqlCommand =
                "update ship set draft = ?, nameShip = ?, imo = ?, typeShip = ?, capacity = ?, length = ?, width = ?, callsign = ? where mmsi = ?";

        executeShipStatementOnDatabase(databaseConnection, ship,
                sqlCommand);
    }

    /**
     * Executes the save Client Statement.
     *
     * @param databaseConnection
     * @param ship
     * @throws SQLException
     */
    private void executeShipStatementOnDatabase(
            DatabaseConnection databaseConnection,
            Ship ship, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement saveShipPreparedStatement =
                connection.prepareStatement(
                        sqlCommand);

        saveShipPreparedStatement.setDouble(1, ship.getCharacteristics().getDraft());
        System.out.println(ship.getCharacteristics().getDraft());
        saveShipPreparedStatement.setString(2, ship.getShipId().getShipName());
        System.out.println(ship.getShipId().getShipName());

        saveShipPreparedStatement.setString(3, ship.getShipId().getImoID());

        saveShipPreparedStatement.setInt(4, ship.getCharacteristics().getVesselType());

        saveShipPreparedStatement.setInt(5, ship.getCharacteristics().getCapacity());

        saveShipPreparedStatement.setDouble(6, ship.getCharacteristics().getLength());

        saveShipPreparedStatement.setDouble(7, ship.getCharacteristics().getWidth());

        saveShipPreparedStatement.setString(8, ship.getShipId().getCallsign());

        saveShipPreparedStatement.setString(9, ship.getShipId().getMmsi());


        System.out.println(ship.getShipId().getImoID());

        saveShipPreparedStatement.executeUpdate();
        saveShipPreparedStatement.close();
    }

    private void deleteShipPositions(DatabaseConnection databaseConnection,
                                       Ship ship) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String sqlCommand;

        //Delete addresses.
        sqlCommand = "select * from PositionShip where mmsi = ?";
        try (PreparedStatement getShipPositionsPreparedStatement = connection.prepareStatement(
                sqlCommand)) {
            getShipPositionsPreparedStatement.setString(1, ship.getShipId().getMmsi());
            try (ResultSet shipPositionsResultSet = getShipPositionsPreparedStatement.executeQuery()) {
                while (shipPositionsResultSet.next()) {
                    boolean found = false;
                    for (int i = 0;
                         i < ship.getRoute().getRoute().size() && !found;
                         i++) {
                        ShipDynamic shipDynamic =
                                ship.getRoute().getRoute().get(i);

                        Integer position_mmsi =
                                shipPositionsResultSet.getInt(
                                        "mmsi");

                        if (Objects.equals(position_mmsi, shipDynamic.getMmsi())) {
                            found = true;
                        }
                    }
                    if (!found) {
                        sqlCommand =
                                "delete from positionship where mmsi = ?";

                        try (PreparedStatement shipPositionDeletePreparedStatement = connection.prepareStatement(
                                sqlCommand)) {
                            shipPositionDeletePreparedStatement.setString(
                                    1,
                                    ship.getShipId().getMmsi());
                            shipPositionDeletePreparedStatement.executeUpdate();
                            getShipPositionsPreparedStatement.close();
                        }
                    }
                }
            }
        }
    }

    private void addShipPositons(DatabaseConnection databaseConnection,
                                 Ship ship) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String sqlCommand = "";

        PositionShipDB addressesStore = new PositionShipDB();

        for (int i = 0; i < ship.getRoute().getRoute().size(); i++) {
            ShipDynamic shipDynamic = ship.getRoute().getRoute().get(i);
            if (!addressesStore.save(databaseConnection, shipDynamic)) {
                throw databaseConnection.getLastError();
            }

            //Post association between clients and addresses.
            sqlCommand =
                    "select * from positionShip where mmsi = ?";
            try (PreparedStatement shipPositionsPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                shipPositionsPreparedStatement.setString(1,
                        ship.getShipId().getMmsi());

                try (ResultSet shipPositionsResultSet = shipPositionsPreparedStatement.executeQuery()) {
                    if (!shipPositionsResultSet.next()) {
                        sqlCommand =
                                "insert into positionShip(mmsi) values (?)";
                        try (PreparedStatement insertClientAddressPreparedStatement = connection.prepareStatement(
                                sqlCommand)) {
                            insertClientAddressPreparedStatement.setString(
                                    1, ship.getShipId().getMmsi());
                            insertClientAddressPreparedStatement.executeUpdate();
                            shipPositionsPreparedStatement.close();
                        }
                    }
                }
            }
        }
    }

    public void getShips(DatabaseConnection connection) {
        Route route = new Route();

        ResultSet rSet;
        try(CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call fncGetShips()}");){
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while(rSet.next()){
                Ship ship = new Ship(new Identification(rSet.getString(1), rSet.getString(3),rSet.getString(4), rSet.getString(9)
                ),new ShipCharacteristics(rSet.getInt(5),rSet.getDouble(6),rSet.getDouble(7),rSet.getDouble(2)),null);
                getShipPositions(connection, ship);
                shipStore.addShipToAVL(ship);
            }
        }catch(SQLException ignored) {
            ignored.printStackTrace();
        }

    }

    private void getShipPositions(DatabaseConnection connection, Ship ship) {
        Route route = new Route();
        ResultSet rSet;
        try(CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call fncGetShipPositions() }");){
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            String mmsi = "";
            while(rSet.next()){
                Date date = rSet.getDate(7);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String strDate = sdf.format(date);
                ShipDynamic shipDynamic = new ShipDynamic(rSet.getString(9), strDate, new Location(String.valueOf(rSet.getDouble(2)), String.valueOf(rSet.getDouble(3))), new Movement(rSet.getDouble(5), rSet.getDouble(4), rSet.getString(6)), rSet.getString(8), "A");
                if(ship.getShipId().getMmsi().equals(rSet.getString(9))) {
                    route.add(shipDynamic);
                    System.out.println(mmsi);
                    System.out.println(strDate);
                }
                ship.setRoute(route);
            }


        }catch(SQLException ignored) {
        }
    }





    private void associateShipWithRoute(Ship ship, Route route) {
        ship.setRoute(route);
    }





}