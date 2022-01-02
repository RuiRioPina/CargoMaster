package lapr.project.controller;

import lapr.project.model.CountryPortGraph;
import lapr.project.model.GraphLocation;
import lapr.project.utils.PrintToFile;
import lapr.project.utils.graph.Graph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ColorMapControllerTest {

    @Test
    void colorMap() throws IOException {
        CountryPortGraph cPG = new CountryPortGraph();
        ColorMapController cMP = new ColorMapController();

        Graph<GraphLocation,Double> graph = cPG.createGraphWithPortsAndCountries(2);
        Map <GraphLocation,Integer> mapColored = cMP.colorMap(graph);

        assertEquals(68, mapColored.size());
        assertEquals(0, mapColored.get(graph.vertices().get(34)));

        StringBuilder output = new StringBuilder("");
        for (GraphLocation country : mapColored.keySet()){
            output.append(country.getName()).append(" ").append(mapColored.get(country)).append("\n");
        }
        PrintToFile.printB(output,"colorMap.txt");
    }

}