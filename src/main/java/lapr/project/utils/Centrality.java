package lapr.project.utils;

import lapr.project.model.GraphLocation;
import lapr.project.model.Port;
import lapr.project.utils.graph.Algorithms;
import lapr.project.utils.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class Centrality {

        public static List <Port> portsWithMoreCentrality(Graph<GraphLocation, Double> graph, int n) {
        Map<GraphLocation, Double> network = new HashMap<>();
        ArrayList<LinkedList<GraphLocation>> paths = new ArrayList<>();
        ArrayList<Double> dists = new ArrayList<>();

        for (GraphLocation port : graph.vertices()) {
            double sum = 0;
            Algorithms.shortestPaths(graph, port, paths, dists);
            for (int i = 0; i < dists.size(); i++) {
                if (dists.get(i) != Double.MAX_VALUE) {
                    sum = sum + dists.get(i);
                }
            }
                network.put(port, sum / graph.numVertices());
        }


        //sort network by descending value based on https://www.baeldung.com/java-hashmap-sort
        LinkedHashMap<GraphLocation,Double> sortedNetwork =  network.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (entry1, entry2) -> entry2, LinkedHashMap::new));


        LinkedHashMap<Port, Double> nPorts = new LinkedHashMap<>();
        int cnt = 0;

        for (Object key : sortedNetwork.keySet()) {
            if (key.getClass().equals(Port.class)) {
                if (cnt == n) {
                    break;
                }
                nPorts.put((Port) key, sortedNetwork.get(key));
                cnt++;
            }
        }

        return new ArrayList<>(nPorts.keySet());
    }

}
