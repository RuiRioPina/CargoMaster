package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void testToString() {
        String oof = "Country{Continent{continent=America}', capitalLocation=Location{longitude=-88.766667, latitude=17.25}, name='Belize', alpha2code='BZ', alpha3code='BLZ', population=397.6, capital='Belmopan'\n" +
                "}";
        Country aa = new Country(new Continent("America"),new Location("17.25","-88.766667"),"Belize","BZ","BLZ",397.6,"Belmopan");
        assertEquals(oof,aa.toString());

    }
}