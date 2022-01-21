package lapr.project.model;

import lapr.project.utils.PrintToFile;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EnergyVoyageTest {

    @Test
    void energyCalculator() throws IOException {
        EnergyVoyage ev = new EnergyVoyage();
        double hours_voyage1 = 2;
        double hours_voyage2 = 1;
        double temperature1 = 20;
        double temperature2 = 30;
        double type1 = 7;
        double type2 = -5;
        double energyR7 = ev.energyCalculator(hours_voyage1,temperature1,hours_voyage2,temperature2,type1);
        assertEquals(4.44514233859427E7,energyR7);

        double energyM5 = ev.energyCalculator(hours_voyage1,temperature1,hours_voyage2,temperature2,type2);
        assertEquals(5.126102354136202E7, energyM5);

        double mut = ev.energyCalculator(hours_voyage1,temperature1,hours_voyage2,temperature2,0);
        assertEquals(0,mut);

        StringBuilder output = new StringBuilder();
        output.append("ENERGY CALCULATIONS\n").
                append("Energy required for the voyage of containers at -5 -> " + energyM5 +" J\n").
                append("Energy required for the voyage of containers at 7 -> " + energyR7 +" J\n");

        PrintToFile.printB(output,"EnergyCalculator.txt");
    }

    @Test
    void energy() throws IOException {
         final double a = 2.56;
         final double b = 2.44;
         final double c = 6.09;
         final int numberOfContainers = 40;
        EnergyVoyage ev = new EnergyVoyage();
        ev.setTotalArea((2 * a + 2 * b + 2 * c) * numberOfContainers);
        double hours_voyage1 = 2;
        double hours_voyage2 = 1;
        double temperature1 = 20;
        double temperature2 = 30;
        double type1 = 7;
        double type2 = -5;

        double energyR7 = ev.energyCalculator(hours_voyage1,temperature1,hours_voyage2,temperature2,type1);
        assertEquals(7.226517494354057E7,energyR7);

        double energyM5 = ev.energyCalculator(hours_voyage1,temperature1,hours_voyage2,temperature2,type2);
        assertEquals(8.333561789098865E7, energyM5);

        double mut = ev.energyCalculator(hours_voyage1,temperature1,hours_voyage2,temperature2,0);
        assertEquals(0,mut);

        StringBuilder output = new StringBuilder();
        output.append("ENERGY CALCULATIONS\n").
                append("Energy required for the voyage of containers at -5 -> " + energyM5 +" J\n").
                append("Energy required for the voyage of containers at 7 -> " + energyR7 +" J\n");

        PrintToFile.printB(output,"EnergyCalculatorSameConditions.txt");
    }
}