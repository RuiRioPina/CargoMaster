package lapr.project.utils;

import java.util.*;

import lapr.project.model.Continent;
import lapr.project.model.Country;
import lapr.project.model.GraphLocation;
import lapr.project.model.Location;
import lapr.project.utils.graph.Edge;
import lapr.project.utils.graph.Graph;
import lapr.project.utils.graph.matrix.MatrixGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorMapTest {
    @Test
    void testIsSafe() {
        ArrayList<Edge<GraphLocation, Double>> edges = new ArrayList<>();
        assertTrue(ColorMap.isSafe(edges, new HashMap<>()));
    }

    @Test
    public void testColorGraphCountries_Success(){
        Graph<GraphLocation, Double> G = new MatrixGraph<>(false);
        G.addVertex (new Country(new Continent("America"),new Location("1.25","-88.766667"),"Belize","BZ","BLZ",397.6,"Belmopan"));
        G.addVertex(new Country(new Continent("America"),new Location("17.2","-88.766667"),"Brazil","BR","BRA",97.6,"Brasilia"));
        G.addVertex (new Country(new Continent("America"),new Location("7.25","-88.766667"),"Uruguay","UR","URU",397,"Montevideo"));
        G.addVertex(new Country(new Continent("America"),new Location("17.5","-88.766667"),"Argentina","AG","ARG",37.6,"Buenos Aires"));
        G.addVertex (new Country(new Continent("America"),new Location("17.8","-88.766667"),"Mexico","MX","MEX",7.6,"Mexico"));
        G.addVertex (new Country(new Continent("America"),new Location("41.25","-88.766667"),"Venezuela","VN","VEN",12.6,"Caracas"));


        G.addEdge(G.vertices().get(0),G.vertices().get(2), 1.0);
        G.addEdge(G.vertices().get(1),G.vertices().get(3), 1.0);
        G.addEdge(G.vertices().get(2),G.vertices().get(1), 1.0);
        G.addEdge(G.vertices().get(3),G.vertices().get(2), 1.0);
        G.addEdge(G.vertices().get(4),G.vertices().get(3), 1.0);
        G.addEdge(G.vertices().get(3),G.vertices().get(2), 1.0);

        Map<GraphLocation, Integer> result = ColorMap.colorMap(G);
        Map<GraphLocation, Integer> exp = new HashMap<>();
        exp.put(new Country(new Continent("America"),new Location("1.25","-88.766667"),"Belize","BZ","BLZ",397.6,"Belmopan"),1);
        exp.put(new Country(new Continent("America"),new Location("17.2","-88.766667"),"Brazil","BR","BRA",97.6,"Brasilia"),1);
        exp.put(new Country(new Continent("America"),new Location("7.25","-88.766667"),"Uruguay","UR","URU",397,"Montevideo"),0);
        exp.put(new Country(new Continent("America"),new Location("17.5","-88.766667"),"Argentina","AG","ARG",37.6,"Buenos Aires"),2);
        exp.put(new Country(new Continent("America"),new Location("17.8","-88.766667"),"Mexico","MX","MEX",7.6,"Mexico"),0);
        exp.put(new Country(new Continent("America"),new Location("1.25","-88.766667"),"Venezuela","VN","VEN",12.6,"Caracas"),0);

        assertEquals(exp.size(), result.size());
        assertNotEquals(exp,result);
        assertNotNull(exp);

    }

}

