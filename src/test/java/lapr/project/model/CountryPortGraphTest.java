package lapr.project.model;

import lapr.project.utils.graph.matrix.MatrixGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryPortGraphTest {
    /*@Test
    public void createGraphWithPortsAndCountries(){
        CountryPortGraph countryPortGraph= new CountryPortGraph();
        MatrixGraph<GraphLocation,Double> graph= countryPortGraph.createGraphWithPortsAndCountries(2);
        assertEquals(90,graph.numVertices());
        System.out.println(countryPortGraph.shortestPath(graph, graph.vertex(5),graph.vertex(20)));
      //  System.out.println(countryPortGraph.shortestPathMaritime(graph, graph.vertex(5),graph.vertex(20)));
    }
*/

    @Test
    public void shortestPath(){
        CountryPortGraph countryPortGraph= new CountryPortGraph();
        MatrixGraph<GraphLocation,Double> graph = new MatrixGraph<>(false);
        Continent europe = new Continent("Europe");
        Location country0Capital = new Location("80.0","20.0");
        Country country0 = new Country(europe,country0Capital,"country0","CR0","C0",20,"c0Cap");
        Location country1Capital = new Location("80.0","20.0");
        Country country1 = new Country(europe,country1Capital,"country1","CR1","C1",20,"c1Cap");
        Location country2Capital = new Location("80.0","25.0");
        Country country2 = new Country(europe,country2Capital,"country2","CR2","C2",20,"c2Cap");
        Location country3Capital = new Location("80.0","30.0");
        Country country3 = new Country(europe,country1Capital,"country3","CR3","C3",20,"c3Cap");
        Location country4Capital = new Location("80.0","35.0");
        Country country4 = new Country(europe,country2Capital,"country4","CR4","C4",20,"c4Cap");
        Location country5Capital = new Location("80.0","40.0");
        Country country5 = new Country(europe,country1Capital,"country5","CR5","C5",20,"c5Cap");
        graph.addVertex(country0);
        graph.addVertex(country1);
        graph.addVertex(country2);
        graph.addVertex(country3);
        graph.addVertex(country4);
        graph.addVertex(country5);
        graph.addEdge(country1,country2,4.0);
        graph.addEdge(country1,country5,1.0);
        graph.addEdge(country5,country2,1.0);
        graph.addEdge(country2,country3,1.0);
        graph.addEdge(country3,country5,5.0);
        graph.addEdge(country5,country4,3.0);
        graph.addEdge(country3,country4,2.0);
        List<String> keylist = new ArrayList<>();
        keylist.add("country1");
        keylist.add("country5");
        keylist.add("country2");
        keylist.add("country3");
        //System.out.println(countryPortGraph.shortestPath(graph,country1,country3));
        assertEquals(keylist,countryPortGraph.shortestPath(graph,country1,country3));
        assertEquals(keylist,countryPortGraph.shortestPathLand(graph,country1,country3));

    }



}
