package lapr.project.model;

import lapr.project.utils.ShipValidation;
import lapr.project.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DimensionTest {


    @Test
    void testLength40() {

        Dimension dimension = new Dimension("42LD");
        double actual = dimension.decodeLengthFromIso("42LD");
        double expected = Utils.convertFeetToMeters(40);
        assertEquals(expected, actual);

    }

    @Test
    void testLength20() {
        Dimension dimension = new Dimension("42LD");
        double actual = dimension.decodeLengthFromIso("22LD");
        double expected = Utils.convertFeetToMeters(20);
        assertEquals(expected, actual);

    }

    @Test
    void testLengthL() {
        Dimension dimension = new Dimension("L5PC");
        double actual = dimension.decodeLengthFromIso("L5PC");
        double expected = Utils.convertFeetToMeters(45);
        assertEquals(expected, actual);

    }

    @Test
    void testWidth0() {
        Dimension dimension = new Dimension("40PC");
        double actual = dimension.decodeHeightAndWidthFromIso("40PC");
        double expected = Utils.convertFeetToMeters(8);
        assertEquals(expected, actual);

    }

    @Test
    void testWidth2() {
        Dimension dimension = new Dimension("42PC");
        double actual = dimension.decodeHeightAndWidthFromIso("42PC");
        double expected = Utils.convertFeetToMeters(8.6);
        assertEquals(expected, actual);

    }

    @Test
    void testWidth5() {
        Dimension dimension = new Dimension("45PC");
        double actual = dimension.decodeHeightAndWidthFromIso("45PC");
        double expected = Utils.convertFeetToMeters(9.6);
        assertEquals(expected, actual);

    }

    @Test
    void testLengthDefault() {
        Dimension dimension = new Dimension("25PC");
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{
                    dimension.decodeLengthFromIso("S5PC");} );
    }

    @Test
    void testWidthDefault() {
        Dimension dimension = new Dimension("42PC");
        Throwable exception = assertThrows(IllegalArgumentException.class,
                ()->{
                    dimension.decodeHeightAndWidthFromIso("4SPC");} );
    }
}
