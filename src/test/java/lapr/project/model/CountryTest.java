package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryTest {

    @Test
    void testConstructor() {
        Continent continent = new Continent("Continent");
        Country actualCountry = new Country(continent, new Location(), "Name", "Alpha2code", "Alpha3code", 10.0, "Capital");

        assertEquals(
                "Country{continent=Continent{continent=Continent}, capitalLocation=Location{longitude=null, latitude=null},"
                        + " name='Name', alpha2code='Alpha2code', alpha3code='Alpha3code', population=10.0, capital='Capital',"
                        + " averageCloseness=0.0}",
                actualCountry.toString());
        assertEquals("Name", actualCountry.getName());
    }

    @Test
    void testToString() {
        String oof = "Country{continent=Continent{continent=America}, capitalLocation=Location{longitude=-88.766667, latitude=17.25}, name='Belize', alpha2code='BZ', alpha3code='BLZ', population=397.6, capital='Belmopan', averageCloseness=0.0" +
                "}";
        Country aa = new Country(new Continent("America"), new Location("17.25", "-88.766667"), "Belize", "BZ", "BLZ", 397.6, "Belmopan");
        assertEquals(oof, aa.toString());

    }

    @Test
    void testGetLocation() {
        Country aa = new Country(new Continent("America"), new Location("17.25", "-88.766667"), "Belize", "BZ", "BLZ", 397.6, "Belmopan");

        Location location = new Location("17.25", "-88.766667");
        assertEquals(location.toString(), aa.getLocation().toString());
    }

    @Test
    void testGetName() {
        Country aa = new Country(new Continent("America"), new Location("17.25", "-88.766667"), "Belize", "BZ", "BLZ", 397.6, "Belmopan");

        String name = "Belize";
        assertEquals(name, aa.getName());
    }

    @Test
    void testGetCloseness() {
        Continent continent = new Continent("Continent");
        assertEquals(0.0,
                (new Country(continent, new Location(), "Name", "Alpha2code", "Alpha3code", 10.0, "Capital")).getCloseness()
                        .doubleValue());
    }
}