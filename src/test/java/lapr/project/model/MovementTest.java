package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {
    @Test
    public void killRemoveCallTo1() {

        Throwable exception = assertThrows(NullPointerException.class,
                ()->{new Movement(null,30.0,30.0);} );
    }
    @Test
    public void killRemoveCallTo2() {

        Throwable exception = assertThrows(NullPointerException.class,
                ()->{new Movement(30.0,null,30.0);} );
    }
    @Test
    public void killRemoveCallTo3() {

        Throwable exception = assertThrows(NullPointerException.class,
                ()->{new Movement(30.0,30.0,null);} );
    }
    @Test
    public void testCog() {
        new Movement(30.0,0.0,30.0);
        new Movement(30.0,-0.1,30.0);
        new Movement(30.0,0.1,30.0);
    }

    @Test
    void convertCog() {
        new Movement(30.0,0.0,30.0).convertCog(0.0);
        new Movement(30.0,0.0,30.0).convertCog(-1.0);
        new Movement(30.0,0.0,30.0).convertCog(1.0);
    }
}