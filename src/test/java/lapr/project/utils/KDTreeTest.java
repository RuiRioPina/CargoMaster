package lapr.project.utils;

import lapr.project.model.Location;
import lapr.project.model.Port;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class KDTreeTest {
    Port port = new Port("Europe", "Italy", 11174, "Genoa", new Location("44.4", "8.933333333"));

    Port port1 = new Port("America", "United States", 32151, "Los Angeles", new Location("33.71666667", "-118.2666667"));
    Port port2 = new Port("Europe", "Norway", 27728, "Narvik", new Location("68.41666667", "17.41666667"));

    Port port3;
    KDTree<Port> kdtree;

    @BeforeEach
    public void setUp() {
        port = new Port("Europe", "Italy", 11174, "Genoa", new Location("44.4", "8.933333333"));
        port1 = new Port("America", "United States", 32151, "Los Angeles", new Location("33.71666667", "-118.2666667"));
        port2 = new Port("Europe", "Norway", 27728, "Narvik", new Location("68.41666667", "17.41666667"));
        port3 = new Port("America", "Chile", 28082, "Valparaiso", new Location("-33.01666667", "-71.63333333"));
        kdtree = new KDTree<>();
        Point2D.Double p = new Point2D.Double(0.2, 0.3);
        Point2D.Double p1 = new Point2D.Double(32, 10);
        Point2D.Double p2 = new Point2D.Double(-32, -10);
        kdtree.addToList(port, Double.parseDouble(port.getLocation().getLongitude()), Double.parseDouble(port.getLocation().getLatitude()));
        kdtree.addToList(port1, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()));
        kdtree.addToList(port2, Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude()));
        kdtree.addToList(port3, Double.parseDouble(port3.getLocation().getLongitude()), Double.parseDouble(port3.getLocation().getLatitude()));
        kdtree.insert();
    }


    @Test
    void contains() {
    }

    @Test
    void findNearestNeighbour() {
    }


    @Test
    void killRemoveCallToNode() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(1, 2);
    }

    @Test
    void killRemoveCallToGetterXNode() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(1, 2);
        double actual = node.getX();
        double expected = 1;
        assertEquals(actual, expected);
    }

    @Test
    void killRemoveCallToGetterYNode() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(1, 2);
        double actual = node.getY();
        double expected = 2;
        assertEquals(actual, expected);
    }

    @Test
    void killRemoveCallToMutant() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, 1.0, 2.0, new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);

    }

    @Test
    void killRemoveCallToMutant2() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, -3.0, -4.0, new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        KDTree.Node<Port> node2 = new KDTree.Node<Port>(node);

    }

    @Test
    void killCompareAxisMutants() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        node.compareAxis(node, 0);
        node.compareAxis(node, 1);
    }

    KDTree.Node<Port> node1 = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
    KDTree.Node<Port> node2 = new KDTree.Node<Port>(port, 1.0, 1.0, new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
    KDTree.Node<Port> node3 = new KDTree.Node<Port>(port, 75.0, 75.0, new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);

    @Test
    void killCompareAxisMutants1() {

        node1.compareAxis(node2, 0);
        node1.compareAxis(node3, 1);
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    node1.compareAxis(node2, -1);
                });
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                () -> {
                    node1.compareAxis(node2, 2);
                });
    }

    @Test
    void killGetCoords() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        Point2D.Double actual = node.getCoordinates();
        Point2D.Double expected = new Point2D.Double(node.getX(), node.getY());
        assertEquals(expected, actual);
    }

    @Test
    void killComparatorX() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpX.compare(node1, node2);
        int expected = -1;
        assertEquals(expected, actual);
    }
    @Test
    void killComparator2X() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpX.compare(node2, node1);
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void killComparator3X() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpX.compare(node, node1);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void killComparatorY() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpY.compare(node1, node2);
        int expected = 1;
        assertEquals(expected, actual);
    }
    @Test
    void killComparator2Y() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpY.compare(node2, node1);
        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test
    void killComparator3Y() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpY.compare(node, node1);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void killMutantNoList() {
        KDTree<Port> kdTree = new KDTree();
        kdTree.insert();
    }

    @Test
    void killMutantWrongDepth() {
        ImportPorts.importPorts("csvFiles/sports.csv");
    }
    @Test
    void getSize() {
        KDTree<Port> kdTree = new KDTree();
        int actual = kdTree.size();
        int expected = 0;
        assertEquals(expected, actual);
    }
}