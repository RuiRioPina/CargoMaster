package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortTest {

    @Test
    void getContinent() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        assertEquals(port.getContinent(), "Europa");
    }

    @Test
    void setContinent() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        port.setContinent("Europe");
        assertEquals(port.getContinent(), "Europe");
    }

    @Test
    void getCountry() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        assertEquals(port.getCountry(), "Portugal");
    }

    @Test
    void setCountry() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        port.setCountry("Tuga");
        assertEquals(port.getCountry(), "Tuga");
    }

    @Test
    void getCode() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        assertEquals(port.getCode(), 1);
    }

    @Test
    void setCode() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        port.setCode(2);
        assertEquals(port.getCode(), 2);
    }

    @Test
    void getNamePort() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        assertEquals(port.getNamePort(),"Leixões");
    }
    @Test
    void getName() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        assertEquals(port.getName(),"Leixões");
    }

    @Test
    void setNamePort() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        port.setNamePort("Matosinhos");
        assertEquals(port.getNamePort(),"Matosinhos");
    }

    @Test
    void getLocation() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        assertEquals(port.getLocation().toString(), new Location("1","1").toString());
    }

    @Test
    void getLatitude() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        assertEquals(port.getLatitude(), "1");
    }

    @Test
    void getLongitude() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        assertEquals(port.getLongitude(), "1");
    }

    @Test
    void testEquals() {

    }


    @Test
    void setLocation() {
        Port port = new Port("Europa","Portugal",1,"Leixões",new Location("1","1"));
        port.setLocation(new Location("2","2"));
        assertEquals(port.getLocation().toString(), new Location("2","2").toString());
    }

    @Test
    void testToString2() {
        Port p = new Port ("Leixões");
        String port = "Port = Leixões";
        assertEquals(port,p.toString());
    }
}