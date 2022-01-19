package lapr.project.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HamiltonianCycleTest {
    HamiltonianCycle hamiltonianCycle;
    @BeforeEach
    void setup() {
        hamiltonianCycle = new HamiltonianCycle();

    }

    @Test
    void findCycle() {
        //vertices
        //adjacency matrix


        //Driver code

        hamiltonianCycle.findCycle(hamiltonianCycle.matrixGraph, 11);

        //if the graph doesn't have any Hamiltonian Cycle
        if (!hamiltonianCycle.hasCycle) {
            System.out.println("No Hamiltonian Cycle");
        }
    }


}