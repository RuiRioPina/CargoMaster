package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CenterOfMassCalculatorTest {

    @Test
    void centerOfMassCalculator() {
        CenterOfMassCalculator centerOfMassCalculator = new CenterOfMassCalculator();

        centerOfMassCalculator.centerOfMassCalculator("Natori");
    }

    @Test
    void centreOfMass() {
        CenterOfMassCalculator centerOfMassCalculator = new CenterOfMassCalculator();

        centerOfMassCalculator.centreOfMass(200,100);
    }
}