package lapr.project.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void convertFeetToMeters() {
        double actual = Utils.convertFeetToMeters(20);
        double expected = 6.1;
        assertEquals(actual, expected);
    }
}