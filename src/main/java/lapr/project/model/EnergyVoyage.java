package lapr.project.model;

public class EnergyVoyage {

    private double a = 2.56;
    private double b = 2.44;
    private double c = 6.09;
    private double totalAreaExposed = 2 * (2 * b * 4 * a) + 3 * (2 *b * 5 * c); // 40 contentores
    private double thickness_external = 0.002;
    private double thickness_middle = 0.095;
    private double thickness_internal = 0.003;
    private double k_external;
    private double k_middle;
    private double k_internal;
    private double r1;
    private double r2;
    private double r3;


    public double energyCalculator(double hours_voyage, double actualTemperature, double type) {
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
        r1 = thickness_external / (k_external * totalAreaExposed);
        r2 = thickness_middle / (k_middle * totalAreaExposed);
        r3 = thickness_internal / (k_internal * totalAreaExposed);
        double rtotal = r1 + r2 + r3;
        double Q = (actualTemperature - type) / rtotal;
        return Q * 3600 * hours_voyage;
    }

}
