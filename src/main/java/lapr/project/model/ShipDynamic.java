package lapr.project.model;

import lapr.project.utils.ShipValidation;

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

    @Override
    public String toString() {
        return "ShipDynamic{" +
                "baseDateTime='" + baseDateTime + '\'' +
                ", location=" + location +
                ", cargo='" + cargo + '\'' +
                ", movement=" + movement +
                ", transceiverClass='" + transceiverClass + '\'' +
                '}';
    }
}
