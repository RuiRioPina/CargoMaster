package lapr.project.model;

import lapr.project.utils.ShipValidation;

public class Location {
    public Location(Double latitude, Double longitude) {
        ShipValidation.validateLongitude(longitude);
        ShipValidation.validateLatitude(latitude);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Location() {
    }

    private Double longitude;

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    private Double latitude;
}
