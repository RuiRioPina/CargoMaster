package lapr.project.model;

public class EnergyVoyage {

    private double k_external;
    private double k_middle;
    private double k_internal;
    private final double a = 2.56;
    private final double b = 2.44;
    private final double c = 6.09;
    private double totalAreaExposed = 2 * (2 * b * 4 * a) + 3 * (2 *b * 5 * c); // 40 contentores

    public double energyCalculator(double hours_voyage1, double temperature1,
                                   double hours_voyage2, double temperature2, double type) {

        if (type != -5 && type != 7) {
            return 0;
        }
        if (type == -5) {
           k_external = 15;
           k_middle = 0.030;
           k_internal = 0.033;
        }
        if (type == 7) {
            k_external = 273;
            k_middle = 0.045;
            k_internal = 0.055;
        }


        double thicknessExternal = 0.002;
        double thicknessMiddle = 0.095;
        double thicknessInternal = 0.003;
        double r1 = thicknessExternal / (k_external * totalAreaExposed);
        double r2 = thicknessMiddle / (k_middle * totalAreaExposed);
        double r3 = thicknessInternal / (k_internal * totalAreaExposed);
        double rtotal = r1 + r2 + r3;

        double Q1 = (temperature1 - type) / rtotal;
        double E1 = Q1 * 3600 * hours_voyage1;

        double Q2 = (temperature2 - type) / rtotal;
        double E2 = Q2 * 3600 * hours_voyage2;

        return E1 + E2;
    }

    public void setTotalArea(double totalAreaExposed) {
        this.totalAreaExposed = totalAreaExposed;
    }
    /*
    US 412
     */
    public double us412Calc(double areaExposed,double type){
        if (type != -5 && type != 7) {
            return 0;
        }
        if (type == -5) {
            k_external = 15;
            k_middle = 0.030;
            k_internal = 0.033;
        }
        if (type == 7) {
            k_external = 273;
            k_middle = 0.045;
            k_internal = 0.055;
        }
        double thicknessExternal = 0.002;
        double thicknessMiddle = 0.095;
        double thicknessInternal = 0.003;
        double r1 = thicknessExternal / (k_external * areaExposed);
        double r2 = thicknessMiddle / (k_middle * areaExposed);
        double r3 = thicknessInternal / (k_internal *areaExposed);
        double rtotal = r1 + r2 + r3;

        double q1 = (20- type) / rtotal;
        return q1 * 9000;
    }
    public double us415Calc (double areaExposedMinusFive,double areaExposedSeven,double temperature1, double temperature2){
       double kExternalMinusFive = 15;
       double kMiddleMinusFive = 0.030;
       double kInternalMinusFive = 0.033;

       double kExternalSeven = 273;
       double kMiddleSeven = 0.045;
       double kInternalSeven = 0.055;

       double thicknessExternal = 0.002;
       double thicknessMiddle = 0.095;
       double thicknessInternal = 0.003;

       double r1MinusFive = thicknessExternal / (kExternalMinusFive * areaExposedMinusFive);
       double r2MinusFive = thicknessMiddle / (kMiddleMinusFive * areaExposedMinusFive);
       double r3MinusFive = thicknessInternal / (kInternalMinusFive *areaExposedMinusFive);

       double r1Seven = thicknessExternal / (kExternalSeven * areaExposedSeven);
       double r2Seven = thicknessMiddle / (kMiddleSeven * areaExposedSeven);
       double r3Seven = thicknessInternal / (kInternalSeven *areaExposedSeven);

       double rTotalMinusFive=r1MinusFive+r2MinusFive+r3MinusFive;
       double rTotalSeven=r1Seven+r2Seven+r3Seven;

       double q1MinusFive=(temperature1 +5)/rTotalMinusFive;
       double q1Seven=(temperature1-7)/rTotalSeven;

       double q2MinusFive=(temperature2 +5)/rTotalMinusFive;
       double q2Seven=(temperature2-7)/rTotalSeven;

       if (q1MinusFive+q1Seven>q2MinusFive+q2Seven){
           return q1MinusFive+q1Seven;
       }else return q2MinusFive+q2Seven;
    }
    public int generatorsNecessary(double areaExposedMinusFive,double areaExposedSeven,double temperature1, double temperature2){
        int returnValue=1;
        double energyValue=us415Calc(areaExposedMinusFive,areaExposedSeven,temperature1,temperature2);
        double generatorEnergy=75000;
        while (energyValue>generatorEnergy){
            returnValue++;
            generatorEnergy+=75000;
        }
        return returnValue;
    }
}
