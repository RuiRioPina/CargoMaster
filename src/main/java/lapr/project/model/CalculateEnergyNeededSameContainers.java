package lapr.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculateEnergyNeededSameContainers {
    private double a = 2.56;
    private double b = 2.44;
    private double c = 6.09;
    private double area= 2 * a + 2 * b + 2 * c;
    private double thickness_external = 0.002;
    private double thickness_middle = 0.095;
    private double thickness_internal = 0.003;
    private double k_external;
    private double k_middle;
    private double k_internal;



    public double energyCalculator(double hours_voyage, List<Double> temperatures,List<Double> hoursChanging, int numberOfContainersMinus5, int numberOfContainersSeven) {
        k_external = 15;
        k_middle = 0.030;
        k_internal = 0.033;

        double r1MinusFive;
        double r2MinusFive;
        double r3MinusFive;

        double r1Seven;
        double r2Seven;
        double r3Seven;

        r1MinusFive = thickness_external / (k_external * area);
        r2MinusFive = thickness_external / (k_middle * area);
        r3MinusFive = thickness_external / (k_internal * area);


        k_external = 273;
        k_middle = 0.045;
        k_internal = 0.055;

        r1Seven = thickness_external / (k_external * area);
        r2Seven = thickness_middle / (k_middle * area);
        r3Seven = thickness_internal / (k_internal * area);
        double rtotalSeven = r1Seven + r2Seven + r3Seven;
        double rtotalMinusFive = r1MinusFive + r2MinusFive + r3MinusFive;
        double result = 0;
        double Q;
        double Q1;
        for (int i = 0; i < temperatures.size(); i++) {
            Q = (temperatures.get(i) - 7) / rtotalSeven;
            Q1 = (temperatures.get(i) - (-5)) / rtotalMinusFive;
            result += (Q * 3600 * hours_voyage) + (Q1 * 3600 * hours_voyage);
        }

        return result;
    }

    public static void main(String[] args) {
        CalculateEnergyNeededSameContainers energyNeededSameContainers = new CalculateEnergyNeededSameContainers();
        Scanner scanner = new Scanner(System.in);
        List<Double> temperatures = new ArrayList<>();

        System.out.println("Insert the number of hours in the voyage:");
        double hours_voyage = scanner.nextDouble();
        System.out.println("Insert the actual temperature:");

        double actualTemperature = scanner.nextDouble();

        System.out.println("How many times does the voyage change in temperature?");
        int changesInTemperature = scanner.nextInt();
        List<Double> hoursInChanging = new ArrayList<>();
        temperatures.add(actualTemperature);

        for (int i = 0; i < changesInTemperature; i++) {
            temperatures.add(scanner.nextDouble());
            hoursInChanging.add(scanner.nextDouble());
        }

        System.out.println("How many -5ºC containers will the ship have in the voyage?");
        int numberOfMinusFiveContainers = scanner.nextInt();

        System.out.println("How many 7ºC containers will the ship have in the voyage?");
        int numberOfSevenContainers = scanner.nextInt();
        System.out.println("The necessary energy for those containers is :" + energyNeededSameContainers.energyCalculator(hours_voyage,temperatures,hoursInChanging,numberOfMinusFiveContainers,numberOfSevenContainers) + " J");

    }

}
