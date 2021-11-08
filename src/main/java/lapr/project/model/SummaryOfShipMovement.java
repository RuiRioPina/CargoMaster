package lapr.project.model;

import java.time.Duration;

public class SummaryOfShipMovement {

private Ship ship;
    public SummaryOfShipMovement(Ship ship){
this.ship=ship;
    }
    public String toString(){
        return String.format("MMSI=%s \nVesselName=%s \nStartBaseDateTime= %s \nEndBaseDateTime= %s \nTotal Movement Time= %s \nTotal Number Of Movements= %d \nMax SOG= %.2f \nMean SOG= %.2f \nMax COG= %.2f \nMean COG= %.2f \nTravelledDistance= %.4f km \nDelta Distance= %.4f km",ship.getShipId().getMmsi(),ship.getShipId().getShipName(),ship.getStartBaseDateTime(),ship.getEndBaseDateTime(),calculateTravelTime(),ship.getRoute().getSize(),ship.getRoute().getMaxSog(),ship.getRoute().getMeanSog(),ship.getRoute().getMaxCog(),ship.getRoute().getMeanSog(),ship.getRoute().getTravelledDistance(),ship.getRoute().getDeltaDistance());
    }
    private String calculateTravelTime(){
        return Duration.between(ship.getStartBaseDateTimeLDT(), ship.getEndBaseDateTimeLDT()).toDays()+"D"+Duration.between(ship.getStartBaseDateTimeLDT(), ship.getEndBaseDateTimeLDT()).toHours()%24+"H"+Duration.between(ship.getStartBaseDateTimeLDT(), ship.getEndBaseDateTimeLDT()).toMinutes()%60+"M";
    }
}
