package lapr.project.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShipValidation {
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private ShipValidation() {

    }

    public static void validateMMSI(String mmsi) {
        if (mmsi.length() != 9) {
            throw new IllegalArgumentException();
        }
        if (mmsi.contains("-")) {
            throw new IllegalArgumentException();
        }


    }

    public static void validateShipName(String shipName) {
        if (shipName.matches("[0-9]+")) {
            throw new InputMismatchException();
        }
    }

    public static void validateIMO(String imoCode) {
        if (imoCode.length() != 10) {
            throw new IllegalArgumentException();
        }
        if (!imoCode.matches("[IMO]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]")) {
            throw new IllegalArgumentException();
        }

    }

    public static void validateBaseDateTime(String baseDateTime) {
        if (!baseDateTime.matches("[0-9]+[0-9]+[/]+[0-9]+[0-9]+[/]+[0-9]+[0-9]+[0-9]+[0-9]+[ ]+[0-9]+[0-9]+[:]+[0-9]+[0-9]")) {
            throw new IllegalArgumentException();
        }
        if (!isDateValid(baseDateTime)) {
            throw new IllegalArgumentException();
        }

        if (!isValidTime(baseDateTime)) {
            throw new IllegalArgumentException();
        }


    }

    public static void validateLatitude(String latitude) {
        if (!latitude.equals("91")) {

            if ((Double.parseDouble(latitude) < -90 || Double.parseDouble(latitude) > 90)) {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void validateLongitude(String longitude) {
        if (!longitude.equals("181")) {
            if ((Double.parseDouble(longitude) < -180 || Double.parseDouble(longitude) > 180)) {
                throw new IllegalArgumentException();
            }
        }
    }



    public static void validateCog(double cog) {
        if (cog > 359) {
            throw new IllegalArgumentException();
        }

    }

    public static void validateHeading(String heading) {
        if (!heading.equals("511")) {
            if ((Double.parseDouble(heading) < 0 || Double.parseDouble(heading) > 360)) {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void validateVesselType(int vesselType) {
        if (vesselType < 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void validateShipSizes(double measure) {
        if (measure <= 0.0) {
            throw new IllegalArgumentException();
        }
    }

    public static void validateTransceiverClass(String transceiverClass) {
        if (!(transceiverClass.equals("A") || transceiverClass.equals("B"))) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Adapted from https://stackoverflow.com/questions/226910/how-to-sanity-check-a-date-in-java
     *
     * @param date
     * @return
     */
    private static boolean isDateValid(String date) {
        try {
            String[] dateformat = date.split(" ");
            String day = dateformat[0];


            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(day);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Adapted from https://www.geeksforgeeks.org/how-to-validate-time-in-24-hour-format-using-regular-expression/
     *
     * @param time
     * @return
     */
    private static boolean isValidTime(String time) {
        String[] dateformat = time.split(" ");
        String hours = dateformat[1];

        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(hours);

        return m.matches();
    }

}
