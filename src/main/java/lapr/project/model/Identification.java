package lapr.project.model;

import lapr.project.utils.ShipValidation;

import java.util.Objects;

public class Identification {
    private String mmsi;
    private String shipName;
    private String imoID;
    private String callsign;

    public Identification() {
    }

    public Identification(String mmsi, String shipName, String imoID, String callsign) {
        ShipValidation.validateMMSI(mmsi);
        ShipValidation.validateShipName(shipName);
        ShipValidation.validateIMO(imoID);
        this.mmsi = mmsi;
        this.shipName = shipName;
        this.imoID = imoID;
        this.callsign = callsign;
    }

    public String getMmsi() {
        return mmsi;
    }

    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getImoID() {
        return imoID;
    }

    public void setImoID(String imoID) {
        this.imoID = imoID;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    @Override
    public String toString() {
        return "Identification{" +
                "mmsi='" + mmsi + '\'' +
                ", shipName='" + shipName + '\'' +
                ", imoID='" + imoID + '\'' +
                ", callsign='" + callsign + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identification that = (Identification) o;
        return Objects.equals(mmsi, that.mmsi) && Objects.equals(shipName, that.shipName) && Objects.equals(imoID, that.imoID) && Objects.equals(callsign, that.callsign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mmsi, shipName, imoID, callsign);
    }
}
