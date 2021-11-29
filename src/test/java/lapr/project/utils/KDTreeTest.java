package lapr.project.utils;

import lapr.project.model.Location;
import lapr.project.model.Port;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KDTreeTest {
    Port port;
    Port port1;
    Port port2;
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

        kdtree.insert(port, p1.getX(), p1.getY());
        kdtree.insert(port1, p.getX(), p.getY());
        kdtree.insert(port2, p2.getX(), p2.getY());
        kdtree.insert(port3, Double.parseDouble(port3.getLocation().getLongitude()), Double.parseDouble(port3.getLocation().getLatitude()));
    }

    @Test
    void insert() {
        KDTree<Port> kdtree = new KDTree<>();
        Point2D.Double p = new Point2D.Double(0.2, 0.3);
        Point2D.Double p1 = new Point2D.Double(32, 10);
        Point2D.Double p2 = new Point2D.Double(-32, -10);

        kdtree.insert(port1, p.getX(), p.getY());
        kdtree.insert(port, p1.getX(), p1.getY());
        kdtree.insert(port3, p2.getX(), p2.getY());
        assertTrue(kdtree.contains(p));
        assertTrue(kdtree.contains(p1));
        assertTrue(kdtree.contains(p2));
    }

    @Test
    void contains() {
    }

    @Test
    void findNearestNeighbour() {
    }

    @Test
    void size() {
        int actual = kdtree.size();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void killRemoveCallToNode() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(1,2);
    }

    @Test
    void killRemoveCallToGetterXNode() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(1,2);
        double actual = node.getX();
        double expected = 1;
        assertEquals(actual,expected);
    }

    @Test
    void killRemoveCallToGetterYNode() {
        KDTree.Node<Port> node = new KDTree.Node<Port>(1,2);
        double actual = node.getY();
        double expected = 2;
        assertEquals(actual,expected);
    }
}