package lapr.project.model;

public class DraughtAndPressionCalculator {

    private double shipMass;
    private final double seaWaterDensity = 1023;
    private double shipLength;
    private double shipWidth;
    private int shipCapacity;


    public double draughtCalculator(String vessel, double cargoTonage) {
        double result = 0.0;
        if (vessel.equals("Natori") || vessel.equals("NATORI")) {
            shipMass = 7390;
            shipLength = 136;
            shipWidth = 21;
            shipCapacity = 540;
            if (cargoTonage <= shipCapacity * 0.5){
                result = draught(shipMass, seaWaterDensity, shipLength, shipWidth, cargoTonage);
            }
        }
        if (vessel.equals("Meishan Bridge") || vessel.equals("MEISHAN BRIDGE")) {
            shipMass = 152068;
            shipLength = 366;
            shipWidth = 51;
            shipCapacity = 13900;
            if (cargoTonage <= shipCapacity * 0.5){
                result = draught(shipMass, seaWaterDensity, shipLength, shipWidth, cargoTonage);
            }
        }
        if  (vessel.equals("Spar Draco") || vessel.equals("Lianson Dynamic")
                || vessel.equals("SPAR DRACO") || vessel.equals("LIANSON DYNAMIC")) {
            shipMass = 32474;
            shipLength = 189;
            shipWidth = 32;
            shipCapacity = 53565;
            if (cargoTonage <= shipCapacity){
                result = draught(shipMass, seaWaterDensity, shipLength, shipWidth, cargoTonage);
            }
        }
        return result;
    }


    public double draught (double shipMass, double seaWaterDensity, double shipLength,
                                     double shipWidth, double  cargoTonage) {

        double shipEmptyMass = shipMass * Math.pow(10,3);
        double shipEmptyDraught = shipEmptyMass / (shipLength * shipWidth * seaWaterDensity);


        double shipTotalMass = (shipMass + cargoTonage) * Math.pow(10,3);
        double shipTotalDraught = shipTotalMass/(shipLength * shipWidth * seaWaterDensity);

        return shipTotalDraught - shipEmptyDraught;

    }

    public double pressionCalculator(String vessel, double cargoTonage) {
        double pression = 0.0;
        double draught;
        if (vessel.equals("Natori") || vessel.equals("NATORI")) {
            shipMass = 7390;
            shipLength = 136;
            shipWidth = 21;
            shipCapacity = 540;
            if (cargoTonage <= shipCapacity * 0.5){
                draught = draught(shipMass, seaWaterDensity, shipLength, shipWidth,cargoTonage);
                pression  = pression(draught,shipMass,cargoTonage,shipLength,shipWidth);
            }
        }
        if (vessel.equals("Meishan Bridge") || vessel.equals("MEISHAN BRIDGE")) {
            shipMass = 152068;
            shipLength = 366;
            shipWidth = 51;
            shipCapacity = 13900;
            if (cargoTonage <= shipCapacity * 0.5){
                draught = draught(shipMass, seaWaterDensity, shipLength, shipWidth, cargoTonage);
                pression  = pression(draught,shipMass,cargoTonage,shipLength,shipWidth);
            }
        }
        if (vessel.equals("Spar Draco") || vessel.equals("Lianson Dynamic")
            || vessel.equals("SPAR DRACO") || vessel.equals("LIANSON DYNAMIC")) {
            shipMass = 32474;
            shipLength = 189;
            shipWidth = 32;
            shipCapacity = 53565;
            if (cargoTonage <= shipCapacity){
                draught = draught(shipMass, seaWaterDensity, shipLength, shipWidth, cargoTonage);
                pression  = pression(draught,shipMass,cargoTonage,shipLength,shipWidth);
            }
        }
        return pression;
    }

    public double pression (double draught, double shipMass, double cargoTonage,
                            double shipLength, double shipWidth) {

        double shipTotalMass = (shipMass + cargoTonage) * Math.pow(10,3);
        double acceleration = 10;

        double area = (shipLength * shipWidth) + ((draught * shipWidth) * 4);

        return (shipTotalMass * acceleration)/(area);

    }

}
