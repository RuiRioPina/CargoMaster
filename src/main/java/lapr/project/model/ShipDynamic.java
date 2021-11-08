package lapr.project.model;

import lapr.project.utils.ShipValidation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShipDynamic {
    private String baseDateTime;
    private Location location;
    private String cargo;
    private Movement movement;
    private String transceiverClass;

    public ShipDynamic() {
    }

    public ShipDynamic(String baseDateTime, Location location, Movement movement, String cargo, String transceiverClass) {
        ShipValidation.validateBaseDateTime(baseDateTime);
        //ShipValidation.validateCargo(baseDateTime);
        ShipValidation.validateTransceiverClass(transceiverClass);
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

    public LocalDateTime getBaseDateTimeLDT(){
        DateTimeFormatter format= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime= LocalDateTime.parse(baseDateTime,format);
        return dateTime;
    }
    public double getSog(){
        return this.movement.getSog();
    }
    public double getCog(){
        return this.movement.getCog();
    }
    public double getLatitude(){return this.location.getLatitude();
    }
    public double getLongitude(){
        return this.location.getLongitude();
    }
    @Override
    public String toString() {
        return '\n' + "Ship Dynamic{" +
                "BaseDateTime ='" + baseDateTime + '\'' + ',' +
                  location +
                ", cargo='" + cargo + '\'' +
                  movement +
                ", transceiverClass='" + transceiverClass + '\'' +
                '}';
    }
}
