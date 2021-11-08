package lapr.project.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Route {
    List<ShipDynamic> route = new ArrayList<>();

    public void add(ShipDynamic shipDynamic) {
        route.add(shipDynamic);
    }

    public List<ShipDynamic> getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return route +
                ":";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route1 = (Route) o;
        return Objects.equals(route, route1.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route);
    }
    public LocalDateTime getStartDateTimeLDT(){
        return route.get(0).getBaseDateTimeLDT();
    }
    public String getStartDateTime(){
        return route.get(0).getBaseDateTime();
    }
    public LocalDateTime getEndDateTimeLDT(){
        return route.get(route.size()-1).getBaseDateTimeLDT();
    }
    public String getEndDateTime(){
        return route.get(route.size()-1).getBaseDateTime();
    }
    public int getSize(){
        return this.route.size();
    }
    public double getMaxSog(){
        double temp=0;
        for (ShipDynamic shipd: route) {
            if (shipd.getSog()>temp){
                temp=shipd.getSog();
            }
        }
        return temp;
    }
    public double getMaxCog(){
        double temp=0;
        for (ShipDynamic shipd: route) {
            if (shipd.getCog()>temp){
                temp=shipd.getCog();
            }
        }
        return temp;
    }
    public double getMeanSog(){
        double sum =0;
        for (ShipDynamic shipd: route) {
            sum+=shipd.getSog();

        }
        return sum/route.size();
    }
    public double getMeanCog(){
        double sum =0;
        for (ShipDynamic shipd: route) {
            sum+=shipd.getCog();

        }
        return sum/route.size();
    }
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }
    public double getTravelledDistance(){
        double distanceSum=0;
        for (int i=0;i<route.size()-1;i++){
            double lat1=route.get(i).getLatitude();
            double long1=route.get(i).getLongitude();
            double lat2=route.get(i+1).getLatitude();
            double long2=route.get(i+1).getLongitude();
            distanceSum+=distance(lat1,long1,lat2,long2);
        }
        return distanceSum;
    }
    public double getDeltaDistance(){
        return distance(route.get(0).getLatitude(),route.get(0).getLongitude(),route.get(route.size()-1).getLatitude(),route.get(route.size()-1).getLongitude());
    }

}
