package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

    @Test
    void testToString() {
       String exp = "Container - nrContainer = JORU1234553, Type = MAXMINUS5', Load = Cherries, Position [X = 7, Y = 5, Z = 1], Port = Fisgao Port, Date = 2021-12-21 17:30:00";
       TypeContainer tc = new TypeContainer("Leixões","1");
       Dimension d = new Dimension(1,1);
       Client c = new Client("Sérgio","12");
       Container C = new Container("JORU1234553",d,c,tc,"iso","certificate","load");
       Container cont = new Container("JORU1234553",new TypeContainer("MAXMINUS5"),"Cherries",
               new Position(7,5,1),new Port("Fisgao Port"),"2021-12-21 17:30:00");
        assertNotEquals(exp,C.toString());
        assertEquals(exp,cont.toString());
    }
}