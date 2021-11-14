package lapr.project.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class will be a list of ShipDynamics
 */

public class Route {
    /**
     * The list itself
     */
    List<ShipDynamic> route = new ArrayList<>();


    /**
     * Method for adding shipDynamics for the route
     * @param shipDynamic the shipDynamic of a ship
     */
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

    /**
     * returns the start date time LocalDateTime
     * @return LocalDateTime with the start date
     */
    public LocalDateTime getStartDateTimeLDT() {
        return route.get(0).getBaseDateTimeLDT();
    }
    /**
     * returns the start date time String
     * @return String with the start date
     */
    public String getStartDateTime() {
        return route.get(0).getBaseDateTime();
    }
    /**
     * returns the end date time LocalDateTime
     * @return LocalDateTime with the end date
     */
    public LocalDateTime getEndDateTimeLDT() {
        return route.get(route.size() - 1).getBaseDateTimeLDT();
    }
    /**
     * returns the end date time string
     * @return String with the end date
     */
    public String getEndDateTime() {
        return route.get(route.size() - 1).getBaseDateTime();
    }

    /**
     * returns the number of messages received
     * @return int with the number of messages received
     */
    public int getSize() {
        return this.route.size();
    }

    /**
     * returns the max SOG of the route
     * @return double with the Max SOG value.
     */
    public double getMaxSog() {
        double temp = 0;
        for (ShipDynamic shipd : route) {
            if (shipd.getSog() > temp) {
                temp = shipd.getSog();
            }
        }
        return temp;
    }
    /**
     * returns the max COG of the route
     * @return double with the Max COG value.
     */
    public double getMaxCog() {
        double temp = 0;
        for (ShipDynamic shipd : route) {
            if (shipd.getCog() > temp) {
                temp = shipd.getCog();
            }
        }
        return temp;
    }
    /**
     * returns the mean SOG of the route
     * @return double with the mean SOG value.
     */
    public double getMeanSog() {
        double sum = 0;
        for (ShipDynamic shipd : route) {
            sum += shipd.getSog();

        }
        return sum / route.size();
    }
    /**
     * returns the mean COG of the route
     * @return double with the mean COG value.
     */
    public double getMeanCog() {
        double sum = 0;
        for (ShipDynamic shipd : route) {
            sum += shipd.getCog();

        }
        return sum / route.size();
    }

    /**
     * calculates the distance between two points in the earth (in km)
     * @param lat1 latitude of the first point
     * @param lon1 longitude of the first point
     * @param lat2 latitude of the second point
     * @param lon2 longitude of the second point
     * @return Double with the distance between the two points
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

    /**
     * calculates the distance between all the points that the ship travelled
     * @return Double with the travelled distance of the ship
     */
    public double getTravelledDistance() {
        double distanceSum = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            String lat1 = route.get(i).getLatitude();
            String long1 = route.get(i).getLongitude();
            String lat2 = route.get(i + 1).getLatitude();
            String long2 = route.get(i + 1).getLongitude();
            distanceSum += distance(Double.parseDouble(lat1), Double.parseDouble(long1), Double.parseDouble(lat2), Double.parseDouble(long2));
        }
        return distanceSum;
    }

    /**
     * get method for the distance between the first and last point
     * @return Double with the delta distance value
     */
    public double getDeltaDistance() {
        return distance(Double.parseDouble(route.get(0).getLatitude()), Double.parseDouble(route.get(0).getLongitude()), Double.parseDouble(route.get(route.size() - 1).getLatitude()), Double.parseDouble(route.get(route.size() - 1).getLongitude()));
    }

    /**
     * get method for the departure latitude
     * @return Double value for the departure latitude
     */
    public double getDepartureLat() {
        return Double.parseDouble(route.get(0).getLatitude());
    }
    /**
     * get method for the departure longitude
     * @return Double value for the departure longitude
     */
    public double getDepartureLong() {
        return Double.parseDouble(route.get(0).getLongitude());
    }
    /**
     * get method for the arrival latitude
     * @return Double value for the arrival latitude
     */
    public double getArrivalLat() {
        return Double.parseDouble(route.get(route.size() - 1).getLatitude());
    }
    /**
     * get method for the arrival longitude
     * @return Double value for the arrival longitude
     */
    public double getArrivalLong() {
        return Double.parseDouble(route.get(route.size() - 1).getLongitude());
    }

}
