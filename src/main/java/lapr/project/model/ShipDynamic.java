package lapr.project.model;

import lapr.project.controller.App;
import lapr.project.utils.ShipValidation;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * This class will contain the shipDynamics related attributes of a ship
 */
public class ShipDynamic {
    private String mmsi;
    private String baseDateTime;
    private Location location;
    private String cargo;
    private Movement movement;
    private String transceiverClass;

    /**
     * The empty public constructor
     */
    public ShipDynamic() {
    }

    /**
     * Public constructor with all its attributes being initialized and verified.
     */
    public ShipDynamic(String mmsi, String baseDateTime, Location location, Movement movement, String cargo, String transceiverClass) {
        ShipValidation.validateBaseDateTime(baseDateTime);
        ShipValidation.validateTransceiverClass(transceiverClass);
        this.mmsi = mmsi;
        this.baseDateTime = baseDateTime;
        this.location = location;
        this.movement = movement;
        this.cargo = cargo;
        this.transceiverClass = transceiverClass;
    }

    public String getBaseDateTime() {
        return baseDateTime;
    }

    public void setBaseDateTime(String baseDateTime) {
        this.baseDateTime = baseDateTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public String getTransceiverClass() {
        return transceiverClass;
    }

    public void setTransceiverClass(String transceiverClass) {
        this.transceiverClass = transceiverClass;
    }

    /**
     * get method for a LocalDateTime object created by a string.
     *
     * @return LocalDateTime object with date
     */
    public LocalDateTime getBaseDateTimeLDT() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(baseDateTime, format);
        return dateTime;
    }

    /**
     * get method for the SOG of the message
     *
     * @return double with the SOG value
     */
    public double getSog() {
        return this.movement.getSog();
    }

    /**
     * get method for the COG of the message
     *
     * @return double with the COG value
     */
    public double getCog() {
        return this.movement.getCog();
    }

    /**
     * get method for the latitude of the message
     *
     * @return double with the latitude value
     */
    public String getLatitude() {
        return this.location.getLatitude();
    }


    @Override
    public int hashCode() {
        return Objects.hash(baseDateTime, location, cargo, movement, transceiverClass);
    }

    /**
     * get method for the longitude of the message
     *
     * @return double with the longitude value
     */
    public String getLongitude() {
        return this.location.getLongitude();
    }

    public String getMmsi() {
        return mmsi;
    }

    @Override
    public String toString() {
        return String.format("%nDate Time: %s, %s, Cargo: %s, %s, Transceiver class: %s", baseDateTime, location, cargo, movement, transceiverClass);
    }

    /**
     * finds the closest port in the application's database closest to the location of this message
     * @return closest port
     */
    public Port getClosestPort() {
        return App.getInstance().getCompany().getPortStore().store.findNearestNeigbour(Double.parseDouble(this.location.getLongitude()), Double.parseDouble(this.location.getLatitude()));
    }
}


