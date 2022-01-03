package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

    @Test
    void testToString() {
       String exp = "Container{numberContainer='JORU1234553', typeOfVehicle='null', dimension=null, client=null, type= Type = MAXMINUS5', iso='null', certificate='null', load='Cherries', typeManifest='null', position=[X = 7, Y = 5, Z = 1], nextPort=Port{continent=null, country='null', code=0, namePort='Fisgao Port', location=null, averageCloseness=0.0}, date='2021-12-21 17:30:00', arrivalDate='null', departureDate='null'}";
       TypeContainer tc = new TypeContainer("Leixões","1");
       Client c = new Client("Sérgio","12");
       Container C = new Container("JORU1234553",c,tc,"42PC","certificate","load");
       Container cont = new Container("JORU1234553",new TypeContainer("MAXMINUS5"),"Cherries",
               new Position(7,5,1),new Port("Fisgao Port"),"2021-12-21 17:30:00");
        Container cont1 = new Container("JORB1234553",new TypeContainer("MAXMINUS5"),"Cherries", "LOAD",
                new Position(7,5,1),"Ship",new Port("Fisgao Port"),"2021-12-21 17:30:00","2021-12-25 17:30:00");
        assertNotEquals(exp,C.toString());
        assertEquals(exp,cont.toString());
    }

    @Test
    void getIso() {
        String exp = "Container - nrContainer = JORU1234553, Type = MAXMINUS5', Load = Cherries, Position [X = 7, Y = 5, Z = 1], Port = Fisgao Port, Date = 2021-12-21 17:30:00";
        TypeContainer tc = new TypeContainer("Leixões","1");
        Client c = new Client("Sérgio","12");
        Container C = new Container("JORU1234553",c,tc,"42PC","certificate","load");
        Container cont = new Container("JORU1234553",new TypeContainer("MAXMINUS5"),"Cherries",
                new Position(7,5,1),new Port("Fisgao Port"),"2021-12-21 17:30:00");
        String actual = C.getIso();
        String expected = "42PC";
        assertEquals(actual,expected);
    }
}