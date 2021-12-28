package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void testToString() {
        String oof = "Country{continent=Continent{continent=America}, capitalLocation=Location{longitude=-88.766667, latitude=17.25}, name='Belize', alpha2code='BZ', alpha3code='BLZ', population=397.6, capital='Belmopan', averageCloseness=0.0" +
                "}";
        Country aa = new Country(new Continent("America"),new Location("17.25","-88.766667"),"Belize","BZ","BLZ",397.6,"Belmopan");
        assertEquals(oof,aa.toString());

    }
    @Test
    void  testGetLocation(){
        Country aa = new Country(new Continent("America"),new Location("17.25","-88.766667"),"Belize","BZ","BLZ",397.6,"Belmopan");

        Location location= new Location("17.25","-88.766667");
        assertEquals(location.toString(),aa.getLocation().toString());
    }
    @Test
    void  testGetName(){
        Country aa = new Country(new Continent("America"),new Location("17.25","-88.766667"),"Belize","BZ","BLZ",397.6,"Belmopan");

       String name = "Belize";
        assertEquals(name,aa.getName());
    }
}