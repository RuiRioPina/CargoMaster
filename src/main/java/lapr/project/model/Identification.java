package lapr.project.model;

import lapr.project.utils.ShipValidation;

import java.util.Objects;

public class Identification {
    private String mmsi;
    private String shipName;
    private String imoID;
    private String callsign;

    private String searchCode;

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
        return
                "mmsi='" + mmsi + '\'' +
                ", shipName='" + shipName + '\'' +
                ", imoID='" + imoID + '\'' +
                ", callsign='" + callsign;
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

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String returnCodeAccordingToTheFormat(String searchCode) {
        try {
            ShipValidation.validateIMO(searchCode);
            return imoID;
        } catch (IllegalArgumentException e) {
            //do Nothing
        }
        try {
            ShipValidation.validateMMSI(searchCode);
            return mmsi;
        } catch (IllegalArgumentException e) {
            //do Nothing
        }
        return callsign;
    }
}
