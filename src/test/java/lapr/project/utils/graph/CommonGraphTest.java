package lapr.project.utils.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lapr.project.utils.graph.map.MapGraph;
import lapr.project.utils.graph.matrix.MatrixGraph;
import org.junit.jupiter.api.Test;

class CommonGraphTest {
    @Test
    void testIsDirected() {
        assertTrue((new MapGraph<>(true)).isDirected());
        assertFalse((new MapGraph<>(false)).isDirected());
    }

    @Test
    void testNumVertices() {
        assertEquals(0, (new MapGraph<>(true)).numVertices());
    }

    @Test
    void testValidVertex() {
        assertFalse((new MapGraph<>(true)).validVertex("Vert"));
    }

    @Test
    void testValidVertex2() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertTrue(mapGraph.validVertex("Vert"));
    }

    @Test
    void testKey() {
        assertEquals(-1, (new MapGraph<>(true)).key("Vert"));
    }

    @Test
    void testVertex() {
        assertNull((new MapGraph<>(true)).vertex(1));
        assertNull((new MapGraph<>(true)).vertex(-1));
    }

    @Test
    void testVertex2() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertEquals("V Dest", mapGraph.vertex(1));
    }

    @Test
    void testNumEdges() {
        assertEquals(0, (new MapGraph<>(true)).numEdges());
    }

    @Test
    void testCopy() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by copy(Graph, Graph)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        MapGraph<Object, Object> from = new MapGraph<>(true);
        mapGraph.copy(from, new MapGraph<>(true));
    }

    @Test
    void testCopy2() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);

        MapGraph<Object, Object> mapGraph1 = new MapGraph<>(true);
        mapGraph1.addEdge("V Orig", "V Dest", "Weight");
        MapGraph<Object, Object> mapGraph2 = new MapGraph<>(true);
        mapGraph.copy(mapGraph1, mapGraph2);
        assertEquals(2, mapGraph2.vertices.size());
        assertEquals(2, mapGraph2.numVerts);
        assertEquals(1, mapGraph2.numEdges());
        assertEquals("Graph: 2 vertices, 1 edges\nV Orig: \nV Orig -> V Dest\nWeight: Weight\nV Dest: \n\n",
                mapGraph2.toString());
    }

    @Test
    void testCopy3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);

        MapGraph<Object, Object> mapGraph1 = new MapGraph<>(true);
        mapGraph1.addEdge("V Orig", "V Dest", "Weight");
        MatrixGraph<Object, Object> matrixGraph = new MatrixGraph<>(true);
        mapGraph.copy(mapGraph1, matrixGraph);
        assertEquals(2, matrixGraph.vertices.size());
        assertEquals(2, matrixGraph.numVerts);
        assertEquals(1, matrixGraph.numEdges());
        assertEquals(
                "Vertices:\n" + "V Orig\n" + "V Dest\n" + "\n" + "Matrix:\n" + "   |  0  |  1 \n" + " 0 |     |  X  \n"
                        + " 1 |     |     \n" + "\n" + "Edges:\n" + "From 0 to 1-> V Orig -> V Dest\n" + "Weight: Weight\n" + "\n",
                matrixGraph.toString());
    }

    @Test
    void testEquals() {
        assertFalse((new MapGraph<>(true)).equals(null));
        assertFalse((new MapGraph<>(true)).equals("Different type to CommonGraph"));
    }

    @Test
    void testEquals2() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        assertTrue(mapGraph.equals(mapGraph));
        int expectedHashCodeResult = mapGraph.hashCode();
        assertEquals(expectedHashCodeResult, mapGraph.hashCode());
    }

    @Test
    void testEquals3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        MapGraph<Object, Object> mapGraph1 = new MapGraph<>(true);
        assertTrue(mapGraph.equals(mapGraph1));
        int expectedHashCodeResult = mapGraph.hashCode();
        assertEquals(expectedHashCodeResult, mapGraph1.hashCode());
    }

    @Test
    void testEquals4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(false);
        assertFalse(mapGraph.equals(new MapGraph<>(true)));
    }

    @Test
    void testEquals5() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertFalse(mapGraph.equals(new MapGraph<>(true)));
    }
}

