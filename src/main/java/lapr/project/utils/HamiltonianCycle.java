package lapr.project.utils;

import lapr.project.model.CountryPortGraph;
import lapr.project.model.GraphLocation;
import lapr.project.model.Port;
import lapr.project.utils.graph.Edge;
import lapr.project.utils.graph.Graph;
import lapr.project.utils.graph.matrix.MatrixGraph;

import java.util.*;

class HamiltonianCycle {

    MatrixGraph<GraphLocation, Double> matrixGraph;
    CountryPortGraph portGraph;
    private boolean foundCircuit = false;

    public HamiltonianCycle() {
        portGraph = new CountryPortGraph();
        matrixGraph = portGraph.createGraphWithPortsAndCountries(2);

    }


    //start (& end) vertex index
    int start;
    //stack used as list to store the path of the cycle
    Stack<GraphLocation> cycle = new Stack<>();
    //number of vertices in the graph
    int N;
    //variable to mark if graph has the cycle
    boolean hasCycle = false;



    //method to inititate the search of the Hamiltonian cycle
    public void findCycle(Graph<GraphLocation, Double> graph, int startVertex) {
        Graph<GraphLocation, Double> g = graph.clone();
        for (GraphLocation vertice: matrixGraph.vertices()) {
            if(vertice instanceof Port) {
                g.removeVertex(vertice);
            }
        }
        g.removeVertex(graph.vertex(38));
        //add starting vertex to the list
        start = startVertex;
        cycle.push(g.vertex(startVertex));

        //start searching the path
        solve(g, g.vertex(start));

    }
    int count = 0;
    int size;
    boolean[] visited;
    double[] dist;
    private void solve(Graph<GraphLocation, Double> graph, GraphLocation vertex) {
        //Base condition: if the vertex is the start vertex
        //and all nodes have been visited (start vertex twice)

        if (graph.key(vertex) == start && cycle.size() == N + 1 && count != 0) {

            hasCycle = true;

            //output the cycle

            //return to explore more hamiltonian cycles
        }

        count++;
        GraphLocation vIntermedium = vertex;

    int nbr;
    if (count == 1) {
        size = graph.numVertices();
        visited = new boolean[size];
        dist = new double[size];
        for (int i = 0; i < size; i++) {
            dist[i] = Double.MAX_VALUE;
            visited[i] = false;
        }
    }

    dist[graph.key(vertex)] = 0;
    while (graph.key(vIntermedium) != 0) {
        if(!foundCircuit) {

            visited[graph.key(vIntermedium)] = true;
        for (GraphLocation vAdj : graph.adjVertices(vIntermedium)) {
            Edge<GraphLocation, Double> edge = graph.edge(vIntermedium, vAdj);
            if (!visited[graph.key(vAdj)] && dist[graph.key(vAdj)] > dist[graph.key(vIntermedium)] + edge.getWeight()) {

                dist[graph.key(vAdj)] = 0;
                dist[graph.key(vAdj)] = edge.getWeight();
                //visit and add vertex to the cycle

            }

        }

        vIntermedium = getVertMinDist(graph, dist, visited, vIntermedium);

        nbr = graph.key(vIntermedium);
        visited[nbr] = true;
        cycle.push(graph.vertex(nbr));

        //Go to the neighbor vertex to find the cycle
        solve(graph, graph.vertex(nbr));
        //Backtrack
        if (graph.adjVertices(vIntermedium).contains(graph.vertex(start))) {

            hasCycle = true;
            cycle.push(graph.vertex(start));
            //output the cycle
            displayCycle();
            foundCircuit = true;
        }

        visited[nbr] = false;
        cycle.pop();
        if(foundCircuit) {
            break;
        }
    }
        if(foundCircuit) {
            break;
        }
    }

    }

    private GraphLocation getVertMinDist(Graph<GraphLocation, Double> graph, double[] dist, boolean[] visited, GraphLocation vIntermedium) {
        int copy = 0;
        Double minDist = Double.MAX_VALUE;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i] && dist[i] != Double.MAX_VALUE && graph.adjVertices(vIntermedium).contains(graph.vertex(i))) {
                if (dist[i] < minDist) {
                    copy = i;
                    minDist = dist[i];
                }

            }
        }
        return graph.vertices().get(copy);
    }

    //Method to display the path of the cycle
    void displayCycle(){
        //converting vertex index to the name
        List<String> names = new ArrayList<>();
        for(GraphLocation idx: cycle){
            names.add(idx.getName());
        }
        System.out.println(names);
    }
}


