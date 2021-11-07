package lapr.project.model;

import java.util.Objects;

public class Ship implements Comparable<Ship> {

    private Identification shipId;

    private ShipCharacteristics characteristics;

    private Route route;


    public Ship(Identification shipId, ShipCharacteristics characteristics, Route route) {
        this.shipId = shipId;
        this.characteristics = characteristics;
        this.route = route;
    }

    public Identification getShipId() {
        return shipId;
    }

    public ShipCharacteristics getCharacteristics() {
        return characteristics;
    }

    public Route getRoute() {
        return route;
    }

    public void setShipId(Identification shipId) {
        this.shipId = shipId;
    }


    public void setCharacteristics(ShipCharacteristics characteristics) {
        this.characteristics = characteristics;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public int compareTo(Ship ship) {
        String typeCode = null;
        String searchCode = ship.getShipId().getSearchCode();
        if (searchCode != null) {
            return this.getShipId().returnCodeAccordingToTheFormat(searchCode).compareTo(ship.shipId.returnCodeAccordingToTheFormat(searchCode));
        }
        return this.getShipId().getMmsi().compareTo(ship.getShipId().getMmsi());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return Objects.equals(shipId, ship.shipId) && Objects.equals(characteristics, ship.characteristics) && Objects.equals(route, ship.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipId, characteristics, route);
    }

    @Override
    public String toString() {
        return "Ship -" +
                shipId +
                characteristics +
                route + '\n';
    }


}
