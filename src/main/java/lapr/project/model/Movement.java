package lapr.project.model;

import lapr.project.utils.ShipValidation;

public class Movement {
    private double sog;
    private double cog;
    private double heading;

    public Movement() {
    }

    public Movement(Double sog, Double cog, Double heading) {

        ShipValidation.validateSog(sog);
        ShipValidation.validateCog(cog);
        ShipValidation.validateHeading(heading);
        this.sog = sog;
        if(cog<0) cog = 180-cog;
        this.cog = cog;
        this.heading = heading;

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

    public Double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
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
