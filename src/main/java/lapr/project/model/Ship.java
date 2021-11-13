package lapr.project.model;

import java.time.LocalDateTime;
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
    public Double getTravelledDistance() {
        return route.getTravelledDistance();
    }

    public int getVesselType() {
        return characteristics.getVesselType();
    }

    public Double getMeanSOG () {
        return route.getMeanSog();
    }
    public double getRouteTravelledDistance() {
        return route.getTravelledDistance();
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
    public String getStartBaseDateTime(){
        return this.route.getStartDateTime();
    }
    public String getEndBaseDateTime(){
        return this.route.getEndDateTime();
    }
    public LocalDateTime getStartBaseDateTimeLDT(){
        return this.route.getStartDateTimeLDT();
    }
    public LocalDateTime getEndBaseDateTimeLDT(){
        return this.route.getEndDateTimeLDT();
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
    public boolean isClose(Ship ship){
        if (Route.distance(this.route.getDepartureLat(),this.route.getDepartureLong(),ship.getRoute().getDepartureLat(),ship.getRoute().getDepartureLong())>5){
            return false;
        }
        if (Route.distance(this.route.getArrivalLat(),this.route.getArrivalLong(),ship.getRoute().getArrivalLat(),ship.getRoute().getArrivalLong())>5){
            return false;
        }
        if (Route.distance(this.route.getArrivalLat(),this.route.getArrivalLong(),ship.getRoute().getArrivalLat(),ship.getRoute().getArrivalLong())==0){
            return false;
        }
        return true;
    }

}
