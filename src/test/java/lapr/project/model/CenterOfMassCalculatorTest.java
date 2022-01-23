package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CenterOfMassCalculatorTest {

    @Test
    void centerOfMassCalculator() {
        CenterOfMassCalculator centerOfMassCalculator = new CenterOfMassCalculator();
        double expected = 64.5156862745098;
        double actual = centerOfMassCalculator.centerOfMassCalculator("Natori");
        assertEquals(expected, actual);
        double expected1 = 183.64386612021858;
        double actual1 = centerOfMassCalculator.centerOfMassCalculator("Meishan Bridge");
        assertEquals(expected1, actual1);
        double expected2 = 88.87206349206349;
        double actual2 = centerOfMassCalculator.centerOfMassCalculator("Spar Draco");
        assertEquals(expected2, actual2);

        System.out.printf("The centre of mass will be %.1fm for x and %.1f for y.",centerOfMassCalculator.centerOfMassCalculator("Natori"),centerOfMassCalculator.getyCentreMass()/2);

    }


    @Test
    void calculateMassAccordingToPercentageOfArea() {
        CenterOfMassCalculator centerOfMassCalculator = new CenterOfMassCalculator();
        double expected = 0.2;
        centerOfMassCalculator.setTotalArea(500);

        double actual = centerOfMassCalculator.calculatePercentageOfEachArea(100);

        assertEquals(expected, actual);


    }

    @Test
    void setTotalArea() {
        CenterOfMassCalculator centerOfMassCalculator = new CenterOfMassCalculator();
        centerOfMassCalculator.setTotalArea(500);
        assertEquals(500,centerOfMassCalculator.getTotalArea());
    }

    @Test
    void getYCentreOfMass() {
        CenterOfMassCalculator centerOfMassCalculator = new CenterOfMassCalculator();

        assertEquals(0.0,centerOfMassCalculator.getyCentreMass());
    }
}