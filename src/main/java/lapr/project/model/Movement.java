package lapr.project.model;

import lapr.project.utils.ShipValidation;

public class Movement {
    private double sog;
    private double cog;
    private String heading;

    public Movement() {
    }

    public Movement(Double sog, Double cog, String heading) {

        ShipValidation.validateSog(sog);
        ShipValidation.validateCog(cog);
        ShipValidation.validateHeading(heading);
        this.sog = sog;
        cog = convertCog(cog);
        this.cog = cog;
        this.heading = heading;

    }

    public Double convertCog(Double cog) {
        if (cog < 0.0) return 180.0 - cog;
        return cog;
    }

    public Double getSog() {
        return sog;
    }

    public void setSog(double sog) {
        this.sog = sog;
    }

    public Double getCog() {
        return cog;
    }

    public void setCog(double cog) {
        this.cog = cog;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "sog=" + sog +
                ", cog=" + cog +
                ", heading=" + heading +
                '}';
    }
}
