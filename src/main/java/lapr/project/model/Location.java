package lapr.project.model;

import lapr.project.utils.ShipValidation;

public class Location {
    private String latitude;
    private String longitude;
    public Location(String latitude, String longitude) {
        ShipValidation.validateLongitude(longitude);
        ShipValidation.validateLatitude(latitude);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Location() {
    }



    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }


}
