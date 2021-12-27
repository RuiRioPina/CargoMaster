package lapr.project.controller;

import lapr.project.model.GraphLocation;
import lapr.project.utils.ColorMap;
import lapr.project.utils.graph.Graph;

import java.util.Map;

public class ColorMapController {

    public ColorMapController() {
    }

    public Map<GraphLocation,Integer> colorMap (Graph <GraphLocation,Double> G)  {
        return ColorMap.colorMap(G);
    }

}
