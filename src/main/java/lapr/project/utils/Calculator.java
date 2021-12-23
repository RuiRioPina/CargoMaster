package lapr.project.utils;

import lapr.project.model.Location;

public class Calculator {
    public static double calculateLocationDifference(Location location1,Location location2){
        return distance(Double.parseDouble(location1.getLatitude()),Double.parseDouble(location1.getLongitude()),Double.parseDouble(location2.getLatitude()),Double.parseDouble(location2.getLongitude()));
        
    }
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
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
}
