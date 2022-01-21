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

        assertEquals(0.0026184429083615512, draughtMeishan);  // meters
        assertEquals(81492.36418338002, pressionMeishan); // Newtons
        double draughtNatori = oof.draughtCalculator("Natori", cargoTonage);
        double pressionNatori = oof.pressionCalculator("Natori", cargoTonage);

        assertEquals(0.01711339472250284, draughtNatori); //meters
        assertEquals(26037.31467266995, pressionNatori); //Newtons
        double draughtSpar = oof.draughtCalculator("SPAR DRACO", cargoTonage);
        double pressionSpar = oof.pressionCalculator("Spar Draco", cargoTonage);

        assertEquals(0.008081325285626662, draughtSpar); //meters
        assertEquals(53767.25903259346, pressionSpar); //Newtons

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

        assertEquals(0.36396356426219345, draughtMeishan);  // meters
        assertEquals(84853.73042441298, pressionMeishan); // Newtons
        double draughtNatori = oof.draughtCalculator("NATORI", 270);
        double pressionNatori = oof.pressionCalculator("Natori", 270);

        assertEquals(0.09241233150151551, draughtNatori); //meters
        assertEquals(26748.02689342574, pressionNatori); //Newtons
        double draughtSpar = oof.draughtCalculator("LIANSON DYNAMIC", 53565);
        double pressionSpar = oof.pressionCalculator("Lianson Dynamic", 53565);
       
        assertEquals(8.65752377849152, draughtSpar); //meters
        assertEquals(120230.6312408345, pressionSpar); //Newtons
    }



}