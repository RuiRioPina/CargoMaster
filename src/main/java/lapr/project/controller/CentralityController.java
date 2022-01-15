package lapr.project.controller;

import lapr.project.model.GraphLocation;
import lapr.project.model.Port;
import lapr.project.utils.Centrality;
import lapr.project.utils.graph.Graph;

import java.util.List;


public class CentralityController {

    public CentralityController() {
    }

    public List<Port> portsWithMoreCentrality (Graph<GraphLocation,Double> graph, int n)  {
        return Centrality.portsWithMoreCentrality(graph,n);
    }
}