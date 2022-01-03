package lapr.project.utils.graph.map;

import lapr.project.model.Continent;
import lapr.project.model.Country;
import lapr.project.model.Location;
import lapr.project.utils.graph.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapVertexTest {

    @Test
    void testAddAdjVert() {
        MapVertex<Object, Object> mapVertex = new MapVertex<>("Vert");
        mapVertex.addAdjVert("V Adj", new Edge<>("V Orig", "V Dest", "Weight"));
        assertEquals(1, mapVertex.getAllAdjVerts().size());
    }

    @Test
    void testConstructor4() {
        MapVertex<Object, Object> actualMapVertex = new MapVertex<>("Vert");
        actualMapVertex.setCloseness(10.0);
        assertEquals(10.0, actualMapVertex.getCloseness());
    }

    @Test
    void testConstructor5() {
        assertEquals(0.0, (new MapVertex<>("Vert")).getCloseness());
    }

    @Test
    void testRemAdjVert() {
        MapVertex<Object, Object> mapVertex = new MapVertex<>("Vert");
        mapVertex.remAdjVert("V Adj");
        Collection<Object> expectedAllOutEdges = mapVertex.getAllAdjVerts();
        assertEquals(expectedAllOutEdges, mapVertex.getAllOutEdges());
    }

    @Test
    void testGetEdge() {
        assertNull((new MapVertex<>("Vert")).getEdge("V Adj"));
    }

    @Test
    void testNumAdjVerts() {
        assertEquals(0, (new MapVertex<>("Vert")).numAdjVerts());
    }

    @Test
    void testGetAllAdjVerts() {
        assertTrue((new MapVertex<>("Vert")).getAllAdjVerts().isEmpty());
    }

    @Test
    void testGetAllOutEdges() {
        assertTrue((new MapVertex<>("Vert")).getAllOutEdges().isEmpty());
    }

    @Test
    void testToString() {
        assertEquals("Vert: \n", (new MapVertex<>("Vert")).toString());
    }

    @Test
    void testToString2() {
        MapVertex<Object, Object> mapVertex = new MapVertex<>("Vert");
        mapVertex.addAdjVert("V Adj", new Edge<>("V Orig", "V Dest", "Weight"));
        assertEquals("Vert: \nV Orig -> V Dest\nWeight: Weight", mapVertex.toString());
    }

    @Test
    void testConstructor() {
        MapVertex<Object, Object> actualMapVertex = new MapVertex<>("Vert");
        Collection<Object> allAdjVerts = actualMapVertex.getAllAdjVerts();
        assertTrue(allAdjVerts.isEmpty());
        assertEquals("Vert: \n", actualMapVertex.toString());
        assertEquals("Vert", actualMapVertex.getElement());
        Collection<Edge<Object, Object>> allOutEdges = actualMapVertex.getAllOutEdges();
        assertEquals(allAdjVerts, allOutEdges);
        assertTrue(allOutEdges.isEmpty());
    }

    @Test
    void testConstructor2() {
        MapVertex<Object, Object> actualMapVertex = new MapVertex<>("Vert");
        assertEquals("Vert", actualMapVertex.getElement());
        Collection<Object> expectedAllOutEdges = actualMapVertex.getAllAdjVerts();
        assertEquals(expectedAllOutEdges, actualMapVertex.getAllOutEdges());
    }

    @Test
    void testConstructor3() {
        assertThrows(RuntimeException.class, () -> new MapVertex<>(null));
    }


}
