package lapr.project.model;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SummaryOfShipMovement {

private final Ship ship;
private final Map<String,String> summaryMap;

    /**
     * creates the map and gathers the ship's data
     * @param ship ship to summarize
     */
    public SummaryOfShipMovement(Ship ship){
this.ship=ship;
this.summaryMap=new HashMap<>();
summaryMap.put("MMSI",ship.getShipId().getMmsi());
summaryMap.put("Vessel Name",ship.getShipId().getShipName());
summaryMap.put("Start Base Date Time",ship.getStartBaseDateTime());
summaryMap.put("End Base Date Time",ship.getEndBaseDateTime());
summaryMap.put("Total Movement Time",calculateTravelTime());
summaryMap.put("Total Number Of Movements",String.format("%d",ship.getRoute().getSize()));
summaryMap.put("Max SOG",String.format("%.2f",ship.getRoute().getMaxSog()));
summaryMap.put("Mean SOG",String.format("%.2f",ship.getRoute().getMeanSog()));
summaryMap.put("Max COG",String.format("%.2f",ship.getRoute().getMaxCog()));
summaryMap.put("Mean COG",String.format("%.2f",ship.getRoute().getMeanCog()));
summaryMap.put("Travelled Distance",String.format("%.4f km",ship.getRoute().getTravelledDistance()));
summaryMap.put("Delta Distance",String.format("%.4f km",ship.getRoute().getDeltaDistance()));
    }

    /**
     * proper to string of the data.(formatted line by line)
     * @return string with the data
     */
    public String toString(){
        return String.format("MMSI=%s %nVesselName=%s %nStartBaseDateTime= %s %nEndBaseDateTime= %s %nTotal Movement Time= %s %nTotal Number Of Movements= %d %nMax SOG= %.2f %nMean SOG= %.2f %nMax COG= %.2f %nMean COG= %.2f %nTravelledDistance= %.4f km %nDelta Distance= %.4f km",ship.getShipId().getMmsi(),ship.getShipId().getShipName(),ship.getStartBaseDateTime(),ship.getEndBaseDateTime(),calculateTravelTime(),ship.getRoute().getSize(),ship.getRoute().getMaxSog(),ship.getRoute().getMeanSog(),ship.getRoute().getMaxCog(),ship.getRoute().getMeanCog(),ship.getRoute().getTravelledDistance(),ship.getRoute().getDeltaDistance());
    }

    /**
     * calculates the time between the first and last date of the ship
     * @return string with the duration in it (DDHHmm)
     */
    private String calculateTravelTime(){
        return Duration.between(ship.getStartBaseDateTimeLDT(), ship.getEndBaseDateTimeLDT()).toDays()+"D"+Duration.between(ship.getStartBaseDateTimeLDT(), ship.getEndBaseDateTimeLDT()).toHours()%24+"H"+Duration.between(ship.getStartBaseDateTimeLDT(), ship.getEndBaseDateTimeLDT()).toMinutes()%60+"M";
    }

    /**
     * returns the map with all the data.
     * @return created map
     */
    public Map<String,String> getSummaryMap(){
        return this.summaryMap;
    }

    /**
     * returns a specific data depending on the parameter received
     * @param keyValue - received value
     * @return the string with the data depending on the received value.
     */
    public String getMapValue(String keyValue){
        return this.summaryMap.get(keyValue);
    }
}
