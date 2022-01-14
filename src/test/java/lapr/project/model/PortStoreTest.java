package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortStoreTest {

    @Test
    void testInsert2() {
        PortStore portStore = new PortStore();
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.insert();
        assertEquals(1, portStore.store.size());
    }

    @Test
    void testInsert3() {
        PortStore portStore = new PortStore();
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.insert();
        assertEquals(2, portStore.store.size());
    }

    @Test
    void testInsert4() {
        PortStore portStore = new PortStore();
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.insert();
        assertEquals(3, portStore.store.size());
    }

    @Test
    void testInsert5() {
        PortStore portStore = new PortStore();
        portStore.addToList(new Port("Name Port"), 10.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.insert();
        assertEquals(2, portStore.store.size());
    }

    @Test
    void testInsert6() {
        PortStore portStore = new PortStore();
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.insert();
        assertEquals(4, portStore.store.size());
    }

    @Test
    void testInsert7() {
        PortStore portStore = new PortStore();
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.addToList(new Port("Name Port"), 2.0, 3.0);
        portStore.insert();
        assertEquals(6, portStore.store.size());
    }

    @Test
    void testGetSize() {
        assertEquals(0, (new PortStore()).getSize());
    }
    @Test
    void testGetSize1() {
        PortStore portStore = new PortStore();
        portStore.addToList(new Port("Europe","Portugal",1,"Leixoes",new Location("1","1")),1,1);
        portStore.insert();
        assertEquals(1, portStore.getSize());
    }


    @Test
    void testCodeDoesNotExist() {
        assertFalse((new PortStore()).codeExists(1));
    }


}

