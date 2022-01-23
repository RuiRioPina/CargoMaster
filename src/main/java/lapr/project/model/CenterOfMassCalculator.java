package lapr.project.model;


public class CenterOfMassCalculator {

    private double shipMass;
    private double totalArea;

    private double xCentreMassBridge;
    private double xCentreMassChimney;
    private double xCentreMassTotal;
    private double yCentreMass;

    private double massBridge;
    private double massChimney;


    public double centerOfMassCalculator(String vessel) {
        double result = 0.0;
        double areaChimney;
        double areaBridge;
        //MEDIDAS
        double shipWidth;
        double shipLength;
        if (vessel.equalsIgnoreCase("Natori")) {
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

            areaBridge = 10.8 * 21;
            areaChimney = 13.0 * 8.0;



            result = centreOfMass(areaChimney, areaBridge);
        }
        if (vessel.equalsIgnoreCase("MEISHAN BRIDGE")) {
            shipMass = 152068000;
            shipLength = 366;
            shipWidth = 51;
            totalArea = shipLength * shipWidth;

            /* Centre of Masses */
            xCentreMassBridge = 314;
            xCentreMassChimney = 76.3;
            xCentreMassTotal = 183;

            yCentreMass = shipLength / 2;

            areaBridge = 51 * 8.6;
            areaChimney = 51 * 8.35;


            result = centreOfMass(areaChimney, areaBridge);

        }
        if (vessel.equalsIgnoreCase("Spar Draco") || vessel.equalsIgnoreCase("Lianson Dynamic")) {
            shipMass = 32474000;
            shipLength = 189;
            shipWidth = 32;
            totalArea = shipLength * shipWidth;

            /* Centre of Masses */
            xCentreMassBridge = 17.35;
            xCentreMassChimney = 5.75;
            xCentreMassTotal = 94.5;

            yCentreMass = shipLength / 2;

            double xFront = 173.5;

            areaBridge = 17.7 * 32; // Falta fazer areas para este Spar Draco
            areaChimney = 5.5 * 32;
            double areaFront = 10.0 * 32.0;

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

    public double calculatePercentageOfEachArea(double smallerArea) {
        return smallerArea / totalArea;
    }

    public double calculateMassAccordingToPercentageOfArea(double smallerArea) {
        return shipMass * calculatePercentageOfEachArea(smallerArea);
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public double getyCentreMass() {
        return yCentreMass;
    }
}
