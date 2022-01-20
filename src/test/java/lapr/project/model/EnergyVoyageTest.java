package lapr.project.model;

import lapr.project.utils.PrintToFile;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EnergyVoyageTest {

    @Test
    void energyCalculator() throws IOException {
        EnergyVoyage ev = new EnergyVoyage();
        double hours_voyage = 2.5;
        double actualTemperature = 20;
        double type1 = 7;
        double type2 = -5;
        double energyR7 = ev.energyCalculator(hours_voyage,actualTemperature,type1);
        assertEquals(2.948308693965587E7,energyR7);
        System.out.println(energyR7);
        double energyM5 = ev.energyCalculator(hours_voyage,actualTemperature,type2);
        assertEquals(3.76919290745309E7, energyM5);
        System.out.println(energyM5);
        double mut = ev.energyCalculator(2.5,20,0);
        assertEquals(0,mut);

        StringBuilder output = new StringBuilder();
        output.append("ENERGY CALCULATIONS\n").
                append("Energy required for the voyage of containers at -5 -> " + energyM5 +" J\n").
                append("Energy required for the voyage of containers at 7 -> " + energyR7 +" J\n");

        PrintToFile.printB(output,"EnergyCalculator.txt");
    }
}