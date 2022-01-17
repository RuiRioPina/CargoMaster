package lapr.project.model;

import lapr.project.utils.PrintToFile;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DraughtAndPressionCalculatorTest {

    @Test
    void draughtAndPressionCalculator() throws IOException {
        double cargoTonage = 50;
        DraughtAndPressionCalculator oof = new DraughtAndPressionCalculator();
        double draughtMeishan = oof.draughtCalculator("Meishan Bridge", cargoTonage);
        double pressionMeishan = oof.pressionCalculator("Meishan Bridge", cargoTonage);
        assertEquals(7.912106430985572, draughtMeishan);  // meters
        assertEquals(75008.61789966225, pressionMeishan); // Newtons
        double draughtNatori = oof.draughtCalculator("Natori", cargoTonage);
        double pressionNatori = oof.pressionCalculator("Natori", cargoTonage);
        assertEquals(2.5291670066084686, draughtNatori); //meters
        assertEquals(24246.769315983904, pressionNatori); //Newtons
        double draughtSpar = oof.draughtCalculator("SPAR DRACO", cargoTonage);
        double pressionSpar = oof.pressionCalculator("Spar Draco", cargoTonage);
        assertEquals(5.221015051112139, draughtSpar); //meters
        assertEquals(48425.544989454975, pressionSpar); //Newtons

        StringBuilder output = new StringBuilder();
        output.append("DRAUGHT AND PRESSION CALCUTATIONS\n\n")
                .append("VESSEL NATORI\n").append("Draught - ").append(draughtNatori).append(" m\n").append("Pression - ").append(pressionNatori).append(" N\n\n")
                .append("VESSEL MEISHAN BRIDGE\n").append("Draught - ").append(draughtMeishan).append(" m\n").append("Pression - ").append(pressionMeishan).append(" N\n\n")
                .append("VESSEL SPAR DRACO\n").append("Draught - ").append(draughtSpar).append(" m\n").append("Pression - ").append(pressionSpar).append(" N\n");

        PrintToFile.printB(output,"DraughtAndPressionCalculator.txt");
    }


    @Test
    void testDraughtCalculator() {
        DraughtAndPressionCalculator oof = new DraughtAndPressionCalculator();
        double draughtMeishan = oof.draughtCalculator("MEISHAN BRIDGE", 6950);
        double pressionMeishan = oof.pressionCalculator("Meishan Bridge", 6950);
       // System.out.println(draughtMeishan);
       // System.out.println(pressionMeishan);
        assertEquals(8.270995808796222, draughtMeishan);  // meters
        assertEquals(78128.91849790238, pressionMeishan); // Newtons
        double draughtNatori = oof.draughtCalculator("NATORI", 270);
        double pressionNatori = oof.pressionCalculator("Natori", 270);
       // System.out.println(draughtNatori);
       // System.out.println(pressionNatori);
        assertEquals(2.6039542030404395, draughtNatori); //meters
        assertEquals(24912.739122294504, pressionNatori); //Newtons
        double draughtSpar = oof.draughtCalculator("LIANSON DYNAMIC", 53565);
        double pressionSpar = oof.pressionCalculator("Lianson Dynamic", 53565);
       // System.out.println(draughtSpar);
       //  System.out.println(pressionSpar);
        assertEquals(13.811674885704011, draughtSpar); //meters
        assertEquals(110082.0913868141, pressionSpar); //Newtons
    }



}