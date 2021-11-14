package lapr.project.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Ship class
 */

public class Ship implements Comparable<Ship> {

    private Identification shipId;

    private ShipCharacteristics characteristics;

    private Route route;

    /**
     * Public constructor with all its attributes being initialized and verified.
     *
     * @param shipId          the identification instance of a Ship
     * @param characteristics the characteristics instance of a Ship
     * @param route           the route instance of a Ship
     */
    public Ship(Identification shipId, ShipCharacteristics characteristics, Route route) {
        this.shipId = shipId;
        this.characteristics = characteristics;
        this.route = route;
    }

    /**
     * Getter method for a ship Identification
     * @return identification of a ship
     */

    public Identification getShipId() {
        return shipId;
    }

    /**
     * Getter method for a ship Characteristics
     * @return Characteristics of a ship
     */


    public ShipCharacteristics getCharacteristics() {
        return characteristics;
    }


    /**
     * Getter method for a ship Route
     * @return Route of a ship
     */

    public Route getRoute() {
        return route;
    }

    public Double getTravelledDistance() {
        return route.getTravelledDistance();
    }

    public int getVesselType() {
        return characteristics.getVesselType();
    }

    public Double getMeanSOG() {
        return route.getMeanSog();
    }

    public double getRouteTravelledDistance() {
        return route.getTravelledDistance();
    }

    /**
     * Setter method for the identification of the ship
     * @param shipId identification of the ship
     */
    public void setShipId(Identification shipId) {
        this.shipId = shipId;
    }
    /**
     * Setter method for the characteristics of the ship
     * @param characteristics characteristics of the ship
     */

    public void setCharacteristics(ShipCharacteristics characteristics) {
        this.characteristics = characteristics;
    }

    /**
     * Setter method for the route of the ship
     * @param route route of the ship
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * get method for the start base date time String
     * @return String with the Start date
     */
    public String getStartBaseDateTime() {
        return this.route.getStartDateTime();
    }
    /**
     * get method for the end base date time String
     * @return String with the end date
     */
    public String getEndBaseDateTime() {
        return this.route.getEndDateTime();
    }
    /**
     * get method for the start base date time LocalDateTime
     * @return LocalDateTime with the Start date
     */
    public LocalDateTime getStartBaseDateTimeLDT() {
        return this.route.getStartDateTimeLDT();
    }
    /**
     * get method for the end base date time LocalDateTime
     * @return LocalDateTime with the end date
     */
    public LocalDateTime getEndBaseDateTimeLDT() {
        return this.route.getEndDateTimeLDT();
    }


    /**
     * Overriding the compare to method for the ship.
     * @param ship the ship being compared to this instance
     * @return int containing the result of the comparison 1- if this ship is greater than the other, 0- if they are equal, -1 - if this one is less than the other
     */
    @Override
    public int compareTo(Ship ship) {
        String typeCode = null;
        String searchCode = ship.getShipId().getSearchCode();
        if (searchCode != null) {
            return this.getShipId().returnCodeAccordingToTheFormat(searchCode).compareTo(ship.shipId.returnCodeAccordingToTheFormat(searchCode));
        }
        return this.getShipId().getMmsi().compareTo(ship.getShipId().getMmsi());
    }

    /**
     * Overriding the equals method for the Ship
     * @param o the object being compared to
     * @return a boolean indicating whether the two Identifications are the same
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return Objects.equals(shipId, ship.shipId) && Objects.equals(characteristics, ship.characteristics) && Objects.equals(route, ship.route);
    }
    /**
     * Overriding the hashCode method for the Ship
     * @return a int with the code for the Ship
     */
    @Override
    public int hashCode() {
        return Objects.hash(shipId, characteristics, route);
    }
    /**
     * Overriding the toString method for the Ship
     * @return the String containing the informations of a Ship
     */
    @Override
    public String toString() {
        return "Ship -" +
                shipId +
                characteristics +
                route + '\n';
    }

    /**
     * compares the distance of two ships arrival and departure locations and compares them
     * @param ship ship to be compared
     * @return boolean value saying whether ships are close or not.
     */
    public boolean isClose(Ship ship) {
        if (Route.distance(this.route.getDepartureLat(), this.route.getDepartureLong(), ship.getRoute().getDepartureLat(), ship.getRoute().getDepartureLong()) > 5) {
            return false;
        }
        if (Route.distance(this.route.getArrivalLat(), this.route.getArrivalLong(), ship.getRoute().getArrivalLat(), ship.getRoute().getArrivalLong()) > 5) {
            return false;
        }
        if (Route.distance(this.route.getArrivalLat(), this.route.getArrivalLong(), ship.getRoute().getArrivalLat(), ship.getRoute().getArrivalLong()) == 0) {
            return false;
        }
        return true;
    }

}
