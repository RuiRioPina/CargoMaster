package lapr.project.model;

import lapr.project.utils.ShipValidation;

import java.util.Objects;

/**
 * This class will contain the identification related attributes of a ship
 */

public class Identification {
    private String mmsi;
    private String shipName;
    private String imoID;
    private String callsign;

    private String searchCode;

    /**
     * The empty public constructor
     */
    public Identification() {
    }

    /**
     * Public constructor with all its attributes being initialized and verified.
     */

    public Identification(String mmsi, String shipName, String imoID, String callsign) {
        ShipValidation.validateMMSI(mmsi);
        ShipValidation.validateShipName(shipName);
        ShipValidation.validateIMO(imoID);
        this.mmsi = mmsi;
        this.shipName = shipName;
        this.imoID = imoID;
        this.callsign = callsign;
    }

    /**
     * Getter method for the mmsi
     * @return mmsi
     */
    public String getMmsi() {
        return mmsi;
    }

    /**
     * Setter method for the mmsi
     * @param mmsi The mmsi of the ship
     */
    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }
    /**
     * Getter method for the shipName
     * @return shipName
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * Setter method for the shipname
     * @param shipName the name of the ship
     */
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    /**
     * Getter method for the imoID
     * @return imoID
     */
    public String getImoID() {
        return imoID;
    }

    /**
     * Setter method for the imoID
     * @param imoID the imo of the ship
     */
    public void setImoID(String imoID) {
        this.imoID = imoID;
    }
    /**
     * Getter method for the Callsign
     * @return Callsign
     */
    public String getCallsign() {
        return callsign;
    }
    /**
     * Setter method for the callsign
     * @param callsign the callsign of the ship
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    /**
     * Overriding the toString method for the Identification
     * @return the String containing the informations of a Identification
     */
    @Override
    public String toString() {
        return
                "mmsi='" + mmsi + '\'' +
                ", shipName='" + shipName + '\'' +
                ", imoID='" + imoID + '\'' +
                ", callsign='" + callsign;
    }

    /**
     * Overriding the equals method for the Identification
     * @param o the object being compared to
     * @return a boolean indicating whether the two Identifications are the same
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identification that = (Identification) o;
        return Objects.equals(mmsi, that.mmsi) && Objects.equals(shipName, that.shipName) && Objects.equals(imoID, that.imoID) && Objects.equals(callsign, that.callsign);
    }

    /**
     * Overriding the hashCode method for the Identification
     * @return a int with the code for the Identification
     */
    @Override
    public int hashCode() {
        return Objects.hash(mmsi, shipName, imoID, callsign);
    }

    /**
     * Getter method for the SearchCode
     * @return SearchCode
     */
    public String getSearchCode() {
        return searchCode;
    }

    /**
     * Setter method for the SearchCode
     * @param searchCode the searchCode for the Ship
     */

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    /**
     * Will take a searchCode and will check what type of searchCode it is byusing the validators methods
     * @param searchCode the searchCode of the ship
     * @return the type of Code
     */
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
