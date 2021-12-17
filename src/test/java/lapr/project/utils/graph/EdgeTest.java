package lapr.project.utils.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EdgeTest {
    @Test
    void testConstructor() {
        Edge<Object, Object> actualEdge = new Edge<>("V Orig", "V Dest", "Weight");
        actualEdge.setWeight("Weight");
        assertEquals("V Orig -> V Dest\nWeight: Weight", actualEdge.toString());
    }

    @Test
    void testConstructor2() {
        Edge<Object, Object> actualEdge = new Edge<>("V Orig", "V Dest", "Weight");

        assertEquals("V Dest", actualEdge.getVDest());
        assertEquals("Weight", actualEdge.getWeight());
        assertEquals("V Orig", actualEdge.getVOrig());
    }

    @Test
    void testConstructor3() {
        assertThrows(RuntimeException.class, () -> new Edge<>(null, "V Dest", "Weight"));

    }

    @Test
    void testConstructor4() {
        assertThrows(RuntimeException.class, () -> new Edge<>("V Orig", null, "Weight"));

    }

    @Test
    void testEquals() {
        assertFalse((new Edge<>("V Orig", "V Dest", "Weight")).equals(null));
        assertFalse((new Edge<>("V Orig", "V Dest", "Weight")).equals("Different type to Edge"));
    }

    @Test
    void testEquals2() {
        Edge<Object, Object> edge = new Edge<>("V Orig", "V Dest", "Weight");
        assertTrue(edge.equals(edge));
        int expectedHashCodeResult = edge.hashCode();
        assertEquals(expectedHashCodeResult, edge.hashCode());
    }

    @Test
    void testEquals3() {
        Edge<Object, Object> edge = new Edge<>("V Orig", "V Dest", "Weight");
        Edge<Object, Object> edge1 = new Edge<>("V Orig", "V Dest", "Weight");

        assertTrue(edge.equals(edge1));
        int expectedHashCodeResult = edge.hashCode();
        assertEquals(expectedHashCodeResult, edge1.hashCode());
    }

    @Test
    void testEquals4() {
        Edge<Object, Object> edge = new Edge<>(0, "V Dest", "Weight");
        assertFalse(edge.equals(new Edge<>("V Orig", "V Dest", "Weight")));
    }

    @Test
    void testEquals5() {
        Edge<Object, Object> edge = new Edge<>("V Orig", 0, "Weight");
        assertFalse(edge.equals(new Edge<>("V Orig", "V Dest", "Weight")));
    }
}

