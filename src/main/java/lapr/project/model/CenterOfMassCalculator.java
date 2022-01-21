package lapr.project.model;

import java.util.Scanner;

public class CenterOfMassCalculator {

    private double shipMass;
    private double totalArea;
    private double shipLength;
    private double shipWidth;

    private double xCentreMassBridge;
    private double xCentreMassChimney;
    private double xCentreMassTotal;
    private double yCentreMass;
    //MEDIDAS
    private double xBridge;
    private double xChimney;

    private double massBridge;
    private double massChimney;

    private double areaBridge;
    private double areaChimney;
    private double areaFront;


    public double centerOfMassCalculator(String vessel) {
        double result = 0.0;
        if (vessel.equalsIgnoreCase("Natori") || vessel.equalsIgnoreCase("NATORI")) {
            shipMass = 7390000;
            shipLength = 136;
            shipWidth = 21;
            totalArea = shipLength * shipWidth;

            /* Centre of Masses */
            xCentreMassBridge = 5.4;
            xCentreMassChimney = 123.4;
            xCentreMassTotal = 67.4;

            yCentreMass = shipLength / 2;

            /*Measurements*/

            xBridge = 5.4;
            xChimney = 123.4;

            areaBridge = 10.8 * 21;
            areaChimney = 13.0 * 8.0;



            result = centreOfMass(areaChimney, areaBridge);
        }
        if (vessel.equalsIgnoreCase("Meishan Bridge") || vessel.equalsIgnoreCase("MEISHAN BRIDGE")) {
            shipMass = 152068000;
            shipLength = 366;
            shipWidth = 51;
            totalArea = shipLength * shipWidth;

            /* Centre of Masses */
            xCentreMassBridge = 314;
            xCentreMassChimney = 76.3;
            xCentreMassTotal = 183;

            yCentreMass = shipLength / 2;

            xBridge = 8.6;
            xChimney = 8.32;

            areaBridge = 51 * 8.6;
            areaChimney = 51 * 8.35;


            result = centreOfMass(areaChimney, areaBridge);

        }
        if (vessel.equals("Spar Draco") || vessel.equals("Lianson Dynamic")
                || vessel.equalsIgnoreCase("SPAR DRACO") || vessel.equalsIgnoreCase("LIANSON DYNAMIC")) {
            shipMass = 32474000;
            shipLength = 189;
            shipWidth = 32;
            totalArea = shipLength * shipWidth;

            /* Centre of Masses */
            xCentreMassBridge = 17.35;
            xCentreMassChimney = 5.75;
            xCentreMassTotal = 94.5;

            yCentreMass = shipLength / 2;

            xBridge = 17.5;
            xChimney = 5.5;
            double xFront = 173.5;

            areaBridge = 17.7 * 32; // Falta fazer areas para este Spar Draco
            areaChimney = 5.5 * 32;
            areaFront = 10 * 32;

            massBridge = calculateMassAccordingToPercentageOfArea(areaBridge);
            massChimney = calculateMassAccordingToPercentageOfArea(areaChimney);
            double massFront = calculateMassAccordingToPercentageOfArea(areaFront);


            result =  (massBridge * xCentreMassBridge + massChimney * xCentreMassChimney + (xCentreMassTotal * (shipMass - massChimney - massBridge -massFront) + massFront * xFront)) / shipMass;

        }
        return result;
    }


    public double centreOfMass(double areaChimney, double areaBridge) {

        massBridge = calculateMassAccordingToPercentageOfArea(areaBridge);
        massChimney = calculateMassAccordingToPercentageOfArea(areaChimney);

        return (massBridge * xCentreMassBridge + massChimney * xCentreMassChimney + (xCentreMassTotal * (shipMass - massChimney - massBridge))) / shipMass;
    }

    private double calculatePercentageOfEachArea(double smallerArea) {
        return smallerArea / totalArea;
    }

    private double calculateMassAccordingToPercentageOfArea(double smallerArea) {
        return shipMass * calculatePercentageOfEachArea(smallerArea);
    }

    public static void main(String[] args) {
        CenterOfMassCalculator centerOfMassCalculator = new CenterOfMassCalculator();

        System.out.printf("The centre of mass will be %.1fm for x and %.1f for y.",centerOfMassCalculator.centerOfMassCalculator("Natori"),centerOfMassCalculator.shipWidth/2);
    }






}
