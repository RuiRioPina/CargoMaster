package lapr.project.utils.graph.matrix;

import lapr.project.utils.graph.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MatrixGraphTest {

    
    @Test
    public void testAddVertex_Success(){
        Graph<String, String> g = new MatrixGraph<>(false);
        g.addVertex("A");
        assertEquals(1, g.numVertices());
    }

    @Test
    public void testAddVertex_Failure(){
        MatrixGraph<String, String> g = new MatrixGraph<>(false);
        assertTrue(g.addVertex("a"));
    }

    @Test
    public void testRemoveVertex_Success(){
        Graph<String, String> g = new MatrixGraph<>(true);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addEdge("A", "B", "AB");
        g.addEdge("B", "C", "BC");
        g.addEdge("C", "D", "CD");
        g.addEdge("D", "A", "DA");

        assertEquals(4, g.numVertices());
        assertEquals(4, g.numEdges());

        assertTrue(g.removeVertex("A"));
        assertEquals(3, g.numVertices());
        assertEquals(2, g.numEdges());

        assertTrue(g.removeVertex("B"));
        assertEquals(2, g.numVertices());
        assertEquals(1, g.numEdges());

        assertTrue(g.removeVertex("C"));
        assertEquals(1, g.numVertices());
        assertEquals(0, g.numEdges());

        assertTrue(g.removeVertex("D"));
        assertEquals(0, g.numVertices());
        assertEquals(0, g.numEdges());

    }

    @Test
    public void testRemoveVertex_Failure(){
        MatrixGraph<String, String> g = new MatrixGraph<>(false, 2);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B", "1");
        g.addEdge("B", "C", "2");
        g.addEdge("C", "A", "3");

        assertFalse(g.removeVertex("D"));
    }

    @Test
    public void testRemoveEdge_Success(){
        MatrixGraph<Integer, String> g = new MatrixGraph<>(true, 3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(1, 2, "a");
        g.addEdge(2, 3, "b");
        g.addEdge(3, 1, "c");

        assertTrue(g.removeEdge(1, 2));
        assertTrue(g.removeEdge(2, 3));
        assertTrue(g.removeEdge(3, 1));
    }

    @Test
    public void testRemoveEdge_Failure(){
        MatrixGraph<Integer, String> g = new MatrixGraph<>(false, 3);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(1, 2, "a");
        g.addEdge(2, 3, "b");
        g.addEdge(3, 1, "c");

        assertTrue(g.removeEdge(1, 3));
    }

    @Test
    public void testOutDegree_Success(){
        Graph<String, String> g = new MatrixGraph<>(false, 3);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B", "a-b");
        g.addEdge("B", "C", "b-c");
        assertEquals(1, g.outDegree("A"));
        assertNotEquals(1, g.outDegree("B"));
        assertNotEquals(0, g.outDegree("C"));
    }

    @Test
    public void testOutDegree_Failure(){
        Graph<String, String> g = new MatrixGraph<>(false);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B", "1");
        g.addEdge("B", "C", "2");
        g.addEdge("C", "A", "3");

        assertEquals(2, g.outDegree("A"));
        assertNotEquals(1, g.outDegree("B"));
        assertNotEquals(1, g.outDegree("C"));
    }


    @Test
    public void testAdjVertices_Success(){
        Graph<String, String> g = new MatrixGraph<>(false, 3);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B", "a-b");
        g.addEdge("B", "C", "b-c");
        g.addEdge("C", "A", "c-a");

        Collection<String> adjVertices = g.adjVertices("A");

        assertEquals(2, adjVertices.size());
        assertTrue(adjVertices.contains("B"));
        assertTrue(adjVertices.contains("C"));
    }

    @Test
    public void testAdjVertices_Failure(){
        MatrixGraph<String, String> g = new MatrixGraph<>(false, 3);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B", "a");
        g.addEdge("B", "C", "b");
        g.addEdge("C", "A", "c");

        Collection<String> adjVertices = g.adjVertices("D");

    }

    @Test
    public void testEdges_Success(){
        Graph<String, String> g = new MatrixGraph<>(true, 2);
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B", "E");
        assertEquals(1, g.outDegree("A"));
        assertEquals(1, g.inDegree("B"));
        assertNotNull(g.edge("A", "B"));
    }

    @Test
    public void testEdge_Success(){
        Graph<String, String> g = new MatrixGraph<>(true);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B", "1");
        g.addEdge("B", "C", "2");
        g.addEdge("C", "A", "3");
        assertNotNull(g.edges());
        assertNotNull(g.outgoingEdges(g.vertices().get(0)));
        assertNotEquals("1", g.edge("A", "B"));
        assertNotEquals("2", g.edge("B", "C"));
        assertNotEquals("3", g.edge("C", "A"));
    }


    @Test
    public void testEdge_Failure(){
        Graph<String, String> g = new MatrixGraph<>(true);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B", "a");
        g.addEdge("B", "C", "b");
        g.addEdge("C", "A", "c");

        assertNotNull(g.edge("A", "B"));
        assertNotNull(g.edge("B", "C"));
        assertNotNull(g.edge("C", "A"));

        assertNull(g.edge("A", "C"));
        assertNull(g.edge("B", "A"));
        assertNull(g.edge("C", "B"));

    }
}