package lapr.project.model;

import lapr.project.utils.ShipValidation;

public class Movement {
    private double sog;
    private double cog;
    private String heading;
    /**
     * The empty public constructor
     */
    public Movement() {

    }

    /**
     * Public constructor with all its attributes being initialized and verified.
     */
    public Movement(Double sog, Double cog, String heading) {

        ShipValidation.validateCog(cog);
        ShipValidation.validateHeading(heading);
        this.sog = sog;
        cog = convertCogAndHeading(cog);
        heading = String.valueOf(convertCogAndHeading(Double.parseDouble(heading)));
        this.cog = cog;
        this.heading = heading;

    }

    /**
     * This method will take the negative cog or heading and convert it to its positive equivalent
     * @param value the heading or cog
     * @return the heading or cog with its positive equivalent
     */

    public Double convertCogAndHeading(Double value) {
        if (value < 0.0) return 360.0 + value;
        return value;
    }

    /**
     * Getter method for the sog
     * @return sog
     */

    public Double getSog() {
        return sog;
    }

    /**
     * Setter method for the sog
     * @param sog the sog of the ship
     */

    public void setSog(double sog) {
        this.sog = sog;
    }

    /**
     * Getter method for the cog
     * @return cog
     */

    public Double getCog() {
        return cog;
    }
    /**
     * Setter method for the cog
     * @param cog the cog of the ship
     */
    public void setCog(double cog) {
        this.cog = cog;
    }

    /**
     * Getter method for the heading
     * @return heading
     */

    public String getHeading() {
        return heading;
    }

    /**
     * Setter method for the heading
     * @param heading the heading of the ship
     */

    public void setHeading(String heading) {
        this.heading = heading;
    }
    /**
     * Overriding the toString method for the Movement
     * @return the String containing the informations of a Movement
     */
    @Override
    public String toString() {
        return "Movement{" +
                "sog=" + sog +
                ", cog=" + cog +
                ", heading=" + heading +
                '}';
    }
}
