package lapr.project.utils;

public class Utils {
    static final double METERS_PER_FOOT = 0.305;
    public static double convertFeetToMeters(double feet) {
        return feet * METERS_PER_FOOT;
    }
}
