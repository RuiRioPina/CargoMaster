package lapr.project.model;

import lapr.project.utils.ShipValidation;

public class Location {
    public Location(double latitude, double longitude) {
        ShipValidation.validateLongitude(longitude);
        ShipValidation.validateLatitude(latitude);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Location() {
    }

    private double longitude;

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    private double latitude;
}
