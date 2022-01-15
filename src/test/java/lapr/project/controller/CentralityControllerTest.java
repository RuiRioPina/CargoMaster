package lapr.project.controller;

import lapr.project.model.CountryPortGraph;
import lapr.project.model.GraphLocation;
import lapr.project.model.Port;
import lapr.project.utils.PrintToFile;
import lapr.project.utils.graph.Graph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CentralityControllerTest {

    @Test
    void portsWithMoreCentrality() throws IOException {
        CountryPortGraph cPG = new CountryPortGraph();
        CentralityController cC = new CentralityController();
        Graph<GraphLocation,Double> graph = cPG.createGraphWithPortsAndCountries(2);

        int n = 2;
        List<Port> ports = cC.portsWithMoreCentrality(graph,n);

        String one = ports.get(0).getName();
        assertEquals(one,"Vancouver");
        String two = ports.get(1).getName();
        assertEquals(two,"Los Angeles");

        List<Port> centralityPorts = new ArrayList<>();
        centralityPorts.add((Port)graph.vertices().get(75));
        centralityPorts.add((Port)graph.vertices().get(69));
        assertEquals(centralityPorts,ports);
        System.out.println(ports);

        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < ports.size(); i++){
            output.append("Port - ").append(ports.get(i).getNamePort()).append(" ").append(ports.get(i).getCountry()).
                    append(" Code - ").append(ports.get(i).getCode()).append("\n");
        }
        PrintToFile.printB(output,"portsWithMoreCentrality.txt");

    }

}