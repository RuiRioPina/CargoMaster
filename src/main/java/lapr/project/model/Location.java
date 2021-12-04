package lapr.project.model;

import lapr.project.utils.ShipValidation;

import java.util.Objects;

/**
 * This class will contain the Location related attributes of a ship
 */


public class Location {
    private String latitude;
    private String longitude;

    /**
     * Public constructor with all its attributes being initialized and verified.
     * @param latitude
     * @param longitude
     */
    public Location(String latitude, String longitude) {
        ShipValidation.validateLongitude(longitude);
        if(longitude.equals("181")) {
            longitude = "not available";
        }
        ShipValidation.validateLatitude(latitude);
        if(latitude.equals("91")) {
            latitude = "not available";
        }
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * The empty public constructor
     */

    public Location() {
    }

    /**
     * Getter method for the longitude
     * @return longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Setter method for the longitude
     * @param longitude the longitude of the ship
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    /**
     * Getter method for the latitude
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Setter method for the latitude
     * @param latitude the latitude of the ship
     */

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Overriding the toString method for the Location
     * @return the String containing the informations of a Location
     */
    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }



    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
