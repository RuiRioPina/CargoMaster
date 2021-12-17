package lapr.project.utils.graph.map;

import lapr.project.model.Continent;
import lapr.project.model.Country;
import lapr.project.model.Location;
import lapr.project.utils.graph.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapGraphTest {

    private MapGraph<Country, Integer> mapGraphUnderTest;

    @BeforeEach
    void setUp() {
        mapGraphUnderTest = new MapGraph<>(false);
    }

    @Test
    void testConstructor() {
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(true);
        assertTrue(actualMapGraph.isDirected());
        assertEquals("\nGraph not defined!!", actualMapGraph.toString());
        assertEquals(0, actualMapGraph.numEdges());
    }

    @Test
    void testConstructor2() {
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(new MapGraph<>(true));
        assertTrue(actualMapGraph.isDirected());
        assertEquals("\nGraph not defined!!", actualMapGraph.toString());
        assertEquals(0, actualMapGraph.numEdges());
    }

    @Test
    void testConstructor3() {
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(new MapGraph<>(false));
        assertFalse(actualMapGraph.isDirected());
        assertEquals("\nGraph not defined!!", actualMapGraph.toString());
        assertEquals(0, actualMapGraph.numEdges());
    }

    @Test
    void testConstructor4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(mapGraph);
        assertTrue(actualMapGraph.isDirected());
        assertEquals("Graph: 2 vertices, 1 edges\nV Orig: \nV Orig -> V Dest\nWeight: Weight\nV Dest: \n\n",
                actualMapGraph.toString());
        assertEquals(1, actualMapGraph.numEdges());
    }

    @Test
    void testConstructor5() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge(new Edge<>("V Orig", "V Dest", "Weight"), "V Dest", "Weight");
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(mapGraph);
        assertTrue(actualMapGraph.isDirected());
        assertEquals("Graph: 2 vertices, 1 edges\n" + "V Orig -> V Dest\n" + "Weight: Weight: \n" + "V Orig -> V Dest\n"
                + "Weight: Weight -> V Dest\n" + "Weight: Weight\n" + "V Dest: \n" + "\n", actualMapGraph.toString());
        assertEquals(1, actualMapGraph.numEdges());
    }

    @Test
    void testConstructor6() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", new Edge<>("V Orig", "V Dest", "Weight"), "Weight");
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(mapGraph);
        assertTrue(actualMapGraph.isDirected());
        assertEquals("Graph: 2 vertices, 1 edges\n" + "V Orig: \n" + "V Orig -> V Orig -> V Dest\n" + "Weight: Weight\n"
                + "Weight: Weight\n" + "V Orig -> V Dest\n" + "Weight: Weight: \n" + "\n", actualMapGraph.toString());
        assertEquals(1, actualMapGraph.numEdges());
    }

    @Test
    void testConstructor7() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(false);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(mapGraph);
        assertFalse(actualMapGraph.isDirected());
        assertEquals("Graph: 2 vertices, 2 edges\n" + "V Orig: \n" + "V Orig -> V Dest\n" + "Weight: Weight\n"
                + "V Dest: \n" + "V Dest -> V Orig\n" + "Weight: Weight\n", actualMapGraph.toString());
        assertEquals(2, actualMapGraph.numEdges());
    }

    @Test
    void testConstructor8() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge(new Edge<>(new Edge<>("V Orig", "V Dest", "Weight"), "V Dest", "Weight"), "V Dest", "Weight");
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(mapGraph);
        assertTrue(actualMapGraph.isDirected());
        assertEquals("Graph: 2 vertices, 1 edges\n" + "V Orig -> V Dest\n" + "Weight: Weight -> V Dest\n"
                + "Weight: Weight: \n" + "V Orig -> V Dest\n" + "Weight: Weight -> V Dest\n" + "Weight: Weight -> V Dest\n"
                + "Weight: Weight\n" + "V Dest: \n" + "\n", actualMapGraph.toString());
        assertEquals(1, actualMapGraph.numEdges());
    }

    @Test
    void testConstructor9() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        Edge<Object, Object> vOrig = new Edge<>("V Orig", "V Dest", "Weight");

        mapGraph.addEdge(new Edge<>(vOrig, new Edge<>("V Orig", "V Dest", "Weight"), "Weight"), "V Dest", "Weight");
        MapGraph<Object, Object> actualMapGraph = new MapGraph<>(mapGraph);
        assertTrue(actualMapGraph.isDirected());
        assertEquals(
                "Graph: 2 vertices, 1 edges\n" + "V Orig -> V Dest\n" + "Weight: Weight -> V Orig -> V Dest\n"
                        + "Weight: Weight\n" + "Weight: Weight: \n" + "V Orig -> V Dest\n" + "Weight: Weight -> V Orig -> V Dest\n"
                        + "Weight: Weight\n" + "Weight: Weight -> V Dest\n" + "Weight: Weight\n" + "V Dest: \n" + "\n",
                actualMapGraph.toString());
        assertEquals(1, actualMapGraph.numEdges());
    }

    @Test
    void testValidVertex() {
        // Setup
        final Country vert = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");

        // Run the test
        final boolean result = mapGraphUnderTest.validVertex(vert);

        // Verify the results
        assertFalse(result);
    }

    @Test
    void testValidVertex2() {
        assertFalse((new MapGraph<>(true)).validVertex("Vert"));
    }

    @Test
    void testValidVertex3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertTrue(mapGraph.validVertex("Vert"));
    }

    @Test
    void testAdjVertices2() {
        assertThrows(UnsupportedOperationException.class, () -> (new MapGraph<>(true)).adjVertices("Vert"));
    }

    @Test
    void testEdges() {
        // Setup
        // Run the test
        final Collection<Edge<Country, Integer>> result = mapGraphUnderTest.edges();

        // Verify the results
    }

    @Test
    void testEdges2() {
        assertTrue((new MapGraph<>(true)).edges().isEmpty());
    }

    @Test
    void testEdges3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertEquals(1, mapGraph.edges().size());
    }

    @Test
    void testEdge() {
        assertNull((new MapGraph<>(true)).edge(1, 1));
        assertNull((new MapGraph<>(true)).edge("V Orig", "V Dest"));
    }

    @Test
    void testEdge1() {
        // Setup
        final Country vOrig = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");
        final Country vDest = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "z", 0, "a");

        // Run the test
        final Edge<Country, Integer> result = mapGraphUnderTest.edge(vOrig, vDest);

        // Verify the results
    }

    @Test
    void testEdge2() {
        // Setup
        // Run the test
        final Edge<Country, Integer> result = mapGraphUnderTest.edge(0, 0);

        // Verify the results
    }

    @Test
    void testEdge3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertNull(mapGraph.edge(1, 1));
    }

    @Test
    void testEdge4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", new Edge<>("V Orig", "V Dest", "Weight"), "Weight");
        assertNull(mapGraph.edge(1, 1));
    }

    @Test
    void testEdge5() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertNull(mapGraph.edge(1, 2));
    }

    @Test
    void testEdge6() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", new Edge<>(new Edge<>("V Orig", "V Dest", "Weight"), "V Dest", "Weight"), "Weight");
        assertNull(mapGraph.edge(1, 1));
    }

    @Test
    void testEdge7() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        Edge<Object, Object> vOrig = new Edge<>("V Orig", "V Dest", "Weight");

        mapGraph.addEdge("V Orig", new Edge<>(vOrig, new Edge<>("V Orig", "V Dest", "Weight"), "Weight"), "Weight");
        assertNull(mapGraph.edge(1, 1));
    }

    @Test
    void testEdge8() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by edge(Object, Object)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        mapGraph.edge("V Orig", "V Dest");
    }

    @Test
    void testEdge9() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", 2, "Weight");
        assertNull(mapGraph.edge("V Orig", "V Dest"));
    }

    @Test
    void testEdge10() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertNull(mapGraph.edge(new Edge<>("V Orig", "V Dest", "Weight"), "V Dest"));
    }

    @Test
    void testOutDegree() {
        // Setup
        final Country vert = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");

        // Run the test
        final int result = mapGraphUnderTest.outDegree(vert);

        // Verify the results
        assertEquals(-1, result);
    }

    @Test
    void testOutDegree2() {
        assertEquals(-1, (new MapGraph<>(true)).outDegree("Vert"));
    }

    @Test
    void testOutDegree3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertEquals(0, mapGraph.outDegree("Vert"));
    }

    @Test
    void testInDegree() {
        // Setup
        final Country vert = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");

        // Run the test
        final int result = mapGraphUnderTest.inDegree(vert);

        // Verify the results
        assertEquals(-1, result);
    }

    @Test
    void testInDegree2() {
        assertEquals(-1, (new MapGraph<>(true)).inDegree("Vert"));
    }

    @Test
    void testInDegree3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertEquals(0, mapGraph.inDegree("Vert"));
    }

    @Test
    void testInDegree4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertEquals(-1, mapGraph.inDegree(new Edge<>("V Orig", "V Dest", "Weight")));
    }

    @Test
    void testOutgoingEdges() {
        // Setup
        final Country vert = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");

        // Run the test
        final Collection<Edge<Country, Integer>> result = mapGraphUnderTest.outgoingEdges(vert);

        // Verify the results
    }

    @Test
    void testOutgoingEdges2() {
        assertNull((new MapGraph<>(true)).outgoingEdges("Vert"));
    }

    @Test
    void testOutgoingEdges3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertTrue(mapGraph.outgoingEdges("Vert").isEmpty());
    }

    @Test
    void testIncomingEdges() {
        // Setup
        final Country vert = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");

        // Run the test
        final Collection<Edge<Country, Integer>> result = mapGraphUnderTest.incomingEdges(vert);

        // Verify the results
    }

    @Test
    void testIncomingEdges2() {
        assertNull((new MapGraph<>(true)).incomingEdges("Vert"));
    }

    @Test
    void testIncomingEdges3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertTrue(mapGraph.incomingEdges("Vert").isEmpty());
    }

    @Test
    void testIncomingEdges4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertNull(mapGraph.incomingEdges(new Edge<>("V Orig", "V Dest", "Weight")));
    }

    @Test
    void testAddVertex() {
        // Setup
        final Country vert = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");
        ;

        // Run the test
        final boolean result = mapGraphUnderTest.addVertex(vert);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testAddVertex2() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        assertTrue(mapGraph.addVertex("Vert"));
        assertEquals("Graph: 1 vertices, 0 edges\nVert: \n\n", mapGraph.toString());
    }

    @Test
    void testAddVertex3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertFalse(mapGraph.addVertex("Vert"));
    }

    @Test
    void testAddVertex4() {
        assertThrows(RuntimeException.class, () -> (new MapGraph<>(true)).addVertex(null));
    }

    @Test
    void testAddEdge() {
        // Setup
        final Country vOrig = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");
        ;
        final Country vDest = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");
        ;
        final Integer weight = 2;

        // Run the test
        final boolean result = mapGraphUnderTest.addEdge(vOrig, vDest, weight);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testAddEdge2() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        assertTrue(mapGraph.addEdge("V Orig", "V Dest", "Weight"));
        assertEquals("Graph: 2 vertices, 1 edges\nV Orig: \nV Orig -> V Dest\nWeight: Weight\nV Dest: \n\n",
                mapGraph.toString());
        assertEquals(1, mapGraph.numEdges());
    }

    @Test
    void testAddEdge3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(false);
        assertTrue(mapGraph.addEdge("V Orig", "V Dest", "Weight"));
        assertEquals("Graph: 2 vertices, 2 edges\n" + "V Orig: \n" + "V Orig -> V Dest\n" + "Weight: Weight\n"
                + "V Dest: \n" + "V Dest -> V Orig\n" + "Weight: Weight\n", mapGraph.toString());
        assertEquals(2, mapGraph.numEdges());
    }

    @Test
    void testAddEdge4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertFalse(mapGraph.addEdge("V Orig", "V Dest", "Weight"));
    }

    @Test
    void testAddEdge5() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge(2, "V Dest", "Weight");
        assertTrue(mapGraph.addEdge("V Orig", "V Dest", "Weight"));
        assertEquals("Graph: 3 vertices, 2 edges\n" + "2: \n" + "2 -> V Dest\n" + "Weight: Weight\n" + "V Dest: \n" + "\n"
                + "V Orig: \n" + "V Orig -> V Dest\n" + "Weight: Weight\n", mapGraph.toString());
        assertEquals(2, mapGraph.numEdges());
    }

    @Test
    void testAddEdge6() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", 2, "Weight");
        assertTrue(mapGraph.addEdge("V Orig", "V Dest", "Weight"));
        assertEquals("Graph: 3 vertices, 2 edges\n" + "V Orig: \n" + "V Orig -> 2\n" + "Weight: WeightV Orig -> V Dest\n"
                + "Weight: Weight\n" + "2: \n" + "\n" + "V Dest: \n" + "\n", mapGraph.toString());
        assertEquals(2, mapGraph.numEdges());
    }

    @Test
    void testAddEdge7() {
        assertThrows(RuntimeException.class, () -> (new MapGraph<>(true)).addEdge(null, "V Dest", "Weight"));
    }

    @Test
    void testAddEdge8() {
        assertThrows(RuntimeException.class, () -> (new MapGraph<>(true)).addEdge("V Orig", null, "Weight"));
    }

    @Test
    void testAddEdge9() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(false);
        assertTrue(mapGraph.addEdge(new Edge<>("V Orig", "V Dest", "Weight"), "V Dest", "Weight"));
        assertEquals("Graph: 2 vertices, 2 edges\n" + "V Orig -> V Dest\n" + "Weight: Weight: \n" + "V Orig -> V Dest\n"
                + "Weight: Weight -> V Dest\n" + "Weight: Weight\n" + "V Dest: \n" + "V Dest -> V Orig -> V Dest\n"
                + "Weight: Weight\n" + "Weight: Weight\n", mapGraph.toString());
        assertEquals(2, mapGraph.numEdges());
    }

    @Test
    void testAddEdge10() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(false);
        assertTrue(mapGraph.addEdge("V Orig", new Edge<>("V Orig", "V Dest", "Weight"), "Weight"));
        assertEquals("Graph: 2 vertices, 2 edges\n" + "V Orig: \n" + "V Orig -> V Orig -> V Dest\n" + "Weight: Weight\n"
                + "Weight: Weight\n" + "V Orig -> V Dest\n" + "Weight: Weight: \n" + "V Orig -> V Dest\n"
                + "Weight: Weight -> V Orig\n" + "Weight: Weight\n", mapGraph.toString());
        assertEquals(2, mapGraph.numEdges());
    }

    @Test
    void testRemoveVertex() {
        // Setup
        final Country vert = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");
        ;

        // Run the test
        final boolean result = mapGraphUnderTest.removeVertex(vert);

        // Verify the results
        assertFalse(result);
    }

    @Test
    void testRemoveVertex2() {
        assertFalse((new MapGraph<>(true)).removeVertex("Vert"));
    }

    @Test
    void testRemoveVertex3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertTrue(mapGraph.removeVertex("Vert"));
        assertEquals("\nGraph not defined!!", mapGraph.toString());
        assertEquals(0, mapGraph.numEdges());
    }

    @Test
    void testRemoveVertex4() {
        assertThrows(RuntimeException.class, () -> (new MapGraph<>(true)).removeVertex(null));
    }

    @Test
    void testRemoveVertex5() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addVertex("Vert");
        assertFalse(mapGraph.removeVertex(new Edge<>("V Orig", "V Dest", "Weight")));
    }

    @Test
    void testRemoveEdge() {
        // Setup
        final Country vOrig = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "a", "a", 0, "a");
        final Country vDest = new Country(new Continent("Europe"), new Location("1", "2"),
                "a", "z", "a", 0, "a");
        ;

        // Run the test
        final boolean result = mapGraphUnderTest.removeEdge(vOrig, vDest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    void testRemoveEdge2() {
        assertFalse((new MapGraph<>(true)).removeEdge("V Orig", "V Dest"));
    }

    @Test
    void testRemoveEdge3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertTrue(mapGraph.removeEdge("V Orig", "V Dest"));
        assertEquals("Graph: 2 vertices, 0 edges\nV Orig: \n\nV Dest: \n\n", mapGraph.toString());
        assertEquals(0, mapGraph.numEdges());
    }

    @Test
    void testRemoveEdge4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", 2, "Weight");
        assertFalse(mapGraph.removeEdge("V Orig", "V Dest"));
    }

    @Test
    void testRemoveEdge5() {
        assertThrows(RuntimeException.class, () -> (new MapGraph<>(true)).removeEdge(null, "V Dest"));
    }

    @Test
    void testRemoveEdge6() {
        assertThrows(RuntimeException.class, () -> (new MapGraph<>(true)).removeEdge("V Orig", null));
    }

    @Test
    void testRemoveEdge7() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(false);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertTrue(mapGraph.removeEdge("V Orig", "V Dest"));
        assertEquals("Graph: 2 vertices, 0 edges\nV Orig: \n\nV Dest: \n\n", mapGraph.toString());
        assertEquals(0, mapGraph.numEdges());
    }

    @Test
    void testRemoveEdge8() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertFalse(mapGraph.removeEdge(new Edge<>("V Orig", "V Dest", "Weight"), "V Dest"));
    }

    @Test
    void testRemoveEdge9() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge(2, "V Dest", "Weight");
        mapGraph.addEdge("V Orig", 2, "Weight");
        assertFalse(mapGraph.removeEdge("V Orig", "V Dest"));
    }

    @Test
    void testClone() {
        // Setup
        // Run the test
        final MapGraph<Country, Integer> result = mapGraphUnderTest.clone();

        // Verify the results
    }

    @Test
    void testClone2() {
        MapGraph<Object, Object> actualCloneResult = (new MapGraph<>(true)).clone();
        assertTrue(actualCloneResult.isDirected());
        assertEquals("\nGraph not defined!!", actualCloneResult.toString());
        assertEquals(0, actualCloneResult.numEdges());
    }

    @Test
    void testClone3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        MapGraph<Object, Object> actualCloneResult = mapGraph.clone();
        assertTrue(actualCloneResult.isDirected());
        assertEquals("Graph: 2 vertices, 1 edges\nV Orig: \nV Orig -> V Dest\nWeight: Weight\nV Dest: \n\n",
                actualCloneResult.toString());
        assertEquals(1, actualCloneResult.numEdges());
    }

    @Test
    void testClone4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge(new Edge<>("V Orig", "V Dest", "Weight"), "V Dest", "Weight");
        MapGraph<Object, Object> actualCloneResult = mapGraph.clone();
        assertTrue(actualCloneResult.isDirected());
        assertEquals("Graph: 2 vertices, 1 edges\n" + "V Orig -> V Dest\n" + "Weight: Weight: \n" + "V Orig -> V Dest\n"
                + "Weight: Weight -> V Dest\n" + "Weight: Weight\n" + "V Dest: \n" + "\n", actualCloneResult.toString());
        assertEquals(1, actualCloneResult.numEdges());
    }

    @Test
    void testClone5() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", new Edge<>("V Orig", "V Dest", "Weight"), "Weight");
        MapGraph<Object, Object> actualCloneResult = mapGraph.clone();
        assertTrue(actualCloneResult.isDirected());
        assertEquals("Graph: 2 vertices, 1 edges\n" + "V Orig: \n" + "V Orig -> V Orig -> V Dest\n" + "Weight: Weight\n"
                + "Weight: Weight\n" + "V Orig -> V Dest\n" + "Weight: Weight: \n" + "\n", actualCloneResult.toString());
        assertEquals(1, actualCloneResult.numEdges());
    }

    @Test
    void testClone6() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(false);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        MapGraph<Object, Object> actualCloneResult = mapGraph.clone();
        assertFalse(actualCloneResult.isDirected());
        assertEquals("Graph: 2 vertices, 2 edges\n" + "V Orig: \n" + "V Orig -> V Dest\n" + "Weight: Weight\n"
                + "V Dest: \n" + "V Dest -> V Orig\n" + "Weight: Weight\n", actualCloneResult.toString());
        assertEquals(2, actualCloneResult.numEdges());
    }

    @Test
    void testClone7() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge(new Edge<>(new Edge<>("V Orig", "V Dest", "Weight"), "V Dest", "Weight"), "V Dest", "Weight");
        MapGraph<Object, Object> actualCloneResult = mapGraph.clone();
        assertTrue(actualCloneResult.isDirected());
        assertEquals("Graph: 2 vertices, 1 edges\n" + "V Orig -> V Dest\n" + "Weight: Weight -> V Dest\n"
                + "Weight: Weight: \n" + "V Orig -> V Dest\n" + "Weight: Weight -> V Dest\n" + "Weight: Weight -> V Dest\n"
                + "Weight: Weight\n" + "V Dest: \n" + "\n", actualCloneResult.toString());
        assertEquals(1, actualCloneResult.numEdges());
    }

    @Test
    void testClone8() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        Edge<Object, Object> vOrig = new Edge<>("V Orig", "V Dest", "Weight");

        mapGraph.addEdge(new Edge<>(vOrig, new Edge<>("V Orig", "V Dest", "Weight"), "Weight"), "V Dest", "Weight");
        MapGraph<Object, Object> actualCloneResult = mapGraph.clone();
        assertTrue(actualCloneResult.isDirected());
        assertEquals(
                "Graph: 2 vertices, 1 edges\n" + "V Orig -> V Dest\n" + "Weight: Weight -> V Orig -> V Dest\n"
                        + "Weight: Weight\n" + "Weight: Weight: \n" + "V Orig -> V Dest\n" + "Weight: Weight -> V Orig -> V Dest\n"
                        + "Weight: Weight\n" + "Weight: Weight -> V Dest\n" + "Weight: Weight\n" + "V Dest: \n" + "\n",
                actualCloneResult.toString());
        assertEquals(1, actualCloneResult.numEdges());
    }

    @Test
    void testToString() {
        // Setup
        // Run the test
        final String result = mapGraphUnderTest.toString();

        // Verify the results
        assertNotEquals("result", result);
    }

    @Test
    void testToString2() {
        assertEquals("\nGraph not defined!!", (new MapGraph<>(true)).toString());
    }

    @Test
    void testToString3() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge("V Orig", "V Dest", "Weight");
        assertEquals("Graph: 2 vertices, 1 edges\nV Orig: \nV Orig -> V Dest\nWeight: Weight\nV Dest: \n\n",
                mapGraph.toString());
    }

    @Test
    void testToString4() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        mapGraph.addEdge(new MapVertex<>("Vert"), "V Dest", "Weight");
        assertEquals("Graph: 2 vertices, 1 edges\nVert: \n: \nVert: \n -> V Dest\nWeight: Weight\nV Dest: \n\n",
                mapGraph.toString());
    }

    @Test
    void testToString5() {
        MapGraph<Object, Object> mapGraph = new MapGraph<>(true);
        MapVertex<Object, Object> vOrig = new MapVertex<>("Vert");
        mapGraph.addEdge(vOrig, new MapVertex<>("Vert"), "Weight");
        assertEquals("Graph: 2 vertices, 1 edges\nVert: \n: \nVert: \n -> Vert: \n\nWeight: Weight\nVert: \n: \n\n",
                mapGraph.toString());
    }
}
