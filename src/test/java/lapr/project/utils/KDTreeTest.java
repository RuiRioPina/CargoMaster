package lapr.project.utils;

import lapr.project.model.Location;
import lapr.project.model.Port;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KDTreeTest {
    Port port = new Port("Europe", "Italy", 11174, "Genoa", new Location("44.4", "8.933333333"));

    Port port1 = new Port("America", "United States", 32151, "Los Angeles", new Location("33.71666667", "-118.2666667"));
    Port port2 = new Port("Europe", "Norway", 27728, "Narvik", new Location("68.41666667", "17.41666667"));

    Port port3;
    KDTree<Port> kdtree;

    @Test
    void testAddToList() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 3.0);
        List<KDTree.Node<Object>> nodeList = kdTree.nodes;
        assertEquals(1, nodeList.size());
        KDTree.Node<Object> getResult = nodeList.get(0);
        assertTrue(getResult.vertical);
        assertEquals("Port", getResult.getInfo());
        assertEquals(3.0, getResult.getY().doubleValue());
        assertNull(getResult.left);
        assertEquals(0, getResult.size);
        assertNull(getResult.right);
    }

    @Test
    void testFindNearestNeigbour() {
        assertNull((new KDTree<>()).findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour2() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour12() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", -2.0, 3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(-2.0, 3.0));
    }
    @Test
    void testFindNearestNeigbour13() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, -3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, -3.0));
    }
    @Test
    void testFindNearestNeigbour14() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", -2.0, -3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour15() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, -3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour16() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", -2.0, 3.0);
        kdTree.addToList("Port", 2.0, -3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour17() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", -2.0, 3.0);
        kdTree.addToList("Port", -2.0, -3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour18() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", -2.0, -3.0);
        kdTree.addToList("Port", -2.0, -3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour3() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour4() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 10.0, 3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour5() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 0.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

    @Test
    void testFindNearestNeigbour6() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 10.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals("Port", kdTree.findNearestNeigbour(2.0, 3.0));
    }

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
    void getSize() {
        KDTree<Port> kdTree = new KDTree();
        int actual = kdTree.size();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void testInsert() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by insert()
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        (new KDTree<>()).insert();
    }

    @Test
    void testInsert2() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals(1, kdTree.size());
        assertEquals(1, kdTree.nodes.size());
    }

    @Test
    void testInsert3() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals(2, kdTree.size());
        assertEquals(2, kdTree.nodes.size());
    }

    @Test
    void testInsert4() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals(3, kdTree.size());
        assertEquals(3, kdTree.nodes.size());
    }

    @Test
    void testInsert5() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 10.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals(2, kdTree.size());
        assertEquals(2, kdTree.nodes.size());
    }

    @Test
    void testInsert6() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals(4, kdTree.size());
        assertEquals(4, kdTree.nodes.size());
    }

    @Test
    void testInsert7() {
        KDTree<Object> kdTree = new KDTree<>();
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.addToList("Port", 2.0, 3.0);
        kdTree.insert();
        assertEquals(6, kdTree.size());
        assertEquals(6, kdTree.nodes.size());
    }

    @Test
    void testConstructor() {
        KDTree<Object> actualKdTree = new KDTree<>();
        assertEquals(0, actualKdTree.size());
        assertTrue(actualKdTree.nodes.isEmpty());
    }

    @Test
    void testNodeConstructor() {
        KDTree.Node<Object> actualNode = new KDTree.Node<>(2.0, 3.0);
        KDTree.Node<Object> node = new KDTree.Node<>(2.0, 3.0);

        actualNode.setLeft(node);
        KDTree.Node<Object> node1 = new KDTree.Node<>(2.0, 3.0);

        actualNode.setRight(node1);
        Point2D.Double expectedCoordinates = actualNode.coords;
        assertSame(expectedCoordinates, actualNode.getCoordinates());
        Point2D.Double expectedCoordinates1 = node1.coords;
        KDTree.Node<Object> node2 = actualNode.right;
        assertSame(expectedCoordinates1, node2.getCoordinates());
        Point2D.Double expectedCoordinates2 = node.coords;
        KDTree.Node<Object> node3 = actualNode.left;
        assertSame(expectedCoordinates2, node3.getCoordinates());
        assertNull(actualNode.getInfo());
        assertNull(node2.getInfo());
        assertNull(node3.getInfo());
        assertEquals(3.0, node3.getY().doubleValue());
        assertEquals(2.0, node3.getX().doubleValue());
        assertEquals(3.0, node2.getY().doubleValue());
        assertEquals(2.0, node2.getX().doubleValue());
    }

    @Test
    void testNodeConstructor2() {
        assertEquals(3.0, (new KDTree.Node<>(2.0, 3.0)).getY().doubleValue());
    }

    @Test
    void testAddToList1() {
        // Setup
        final Port port = null;

        // Run the test
        kdtree.addToList(port, 0.0, 0.0);

        // Verify the results
    }

    @Test
    void testInsert1() {
        // Setup
        // Run the test
        kdtree.insert();

        // Verify the results
    }

    @Test
    void insertTestSize0() {
        List<KDTree.Node<Port>> list = new ArrayList<>();
        KDTree<Port> portKDTree = new KDTree<>();

        KDTree.Node<Port> actual = portKDTree.insert(list,0);
        assertNull(actual);
    }

    @Test
    void insertTestSize1() {
        KDTree.Node<Port> node = new KDTree.Node<>(2,5);
        List<KDTree.Node<Port>> list = new ArrayList<>();
        list.add(node);
        KDTree<Port> portKDTree = new KDTree<>();
        String expected = node.toString();

        KDTree.Node<Port> actual = portKDTree.insert(list,0);
        assertEquals(expected,actual.toString());
    }

    @Test
    void insertTestSize2() {
        KDTree.Node<Port> node = new KDTree.Node<>(2,5);
        KDTree.Node<Port> node1 = new KDTree.Node<>(-5,-5);
        List<KDTree.Node<Port>> list = new ArrayList<>();
        list.add(node);
        list.add(node1);

        KDTree<Port> portKDTree = new KDTree<>();
        String expected = "Node{coords=Point2D.Double[2.0, 5.0], info=null, left=Node{coords=Point2D.Double[-5.0, -5.0], info=null, left=null, right=null, size=0, vertical=false}, right=null, size=0, vertical=false}";

        KDTree.Node<Port> actual = portKDTree.insert(list,0);
        assertEquals(expected,actual.toString());
    }

    @Test
    void insertTestSize2Different() {
        KDTree.Node<Port> node1 = new KDTree.Node<>(2,5);
        KDTree.Node<Port> node = new KDTree.Node<>(-5,-5);
        List<KDTree.Node<Port>> list = new ArrayList<>();
        list.add(node);
        list.add(node1);

        KDTree<Port> portKDTree = new KDTree<>();
        String expected = "Node{coords=Point2D.Double[2.0, 5.0], info=null, left=Node{coords=Point2D.Double[-5.0, -5.0], info=null, left=null, right=null, size=0, vertical=false}, right=null, size=0, vertical=false}";

        KDTree.Node<Port> actual = portKDTree.insert(list,0);
        assertEquals(expected,actual.toString());
    }

    @Test
    void insertTestSize4Different() {
        KDTree.Node<Port> node = new KDTree.Node<>(2,5);
        KDTree.Node<Port> node1 = new KDTree.Node<>(-5,-5);
        KDTree.Node<Port> node2 = new KDTree.Node<>(-10,3);
        KDTree.Node<Port> node3 = new KDTree.Node<>(40,-10);

        List<KDTree.Node<Port>> list = new ArrayList<>();
        list.add(node);
        list.add(node1);
        list.add(node2);
        list.add(node3);

        KDTree<Port> portKDTree = new KDTree<>();
        String expected = "Node{coords=Point2D.Double[2.0, 5.0], info=null, left=Node{coords=Point2D.Double[-10.0, 3.0], info=null, left=Node{coords=Point2D.Double[-5.0, -5.0], info=null, left=null, right=null, size=0, vertical=false}, right=null, size=0, vertical=false}, right=Node{coords=Point2D.Double[40.0, -10.0], info=null, left=null, right=null, size=0, vertical=false}, size=0, vertical=false}";

        KDTree.Node<Port> actual = portKDTree.insert(list,0);
        assertEquals(expected,actual.toString());
    }

    @Test
    void insertTestSize10Different() {
        KDTree.Node<Port> node = new KDTree.Node<>(2,5);
        KDTree.Node<Port> node1 = new KDTree.Node<>(-5,-5);
        KDTree.Node<Port> node2 = new KDTree.Node<>(-10,3);
        KDTree.Node<Port> node3 = new KDTree.Node<>(40,-10);
        KDTree.Node<Port> node4 = new KDTree.Node<>(-5,-5);
        KDTree.Node<Port> node5 = new KDTree.Node<>(5,5);
        KDTree.Node<Port> node6 = new KDTree.Node<>(10,-3);
        KDTree.Node<Port> node7 = new KDTree.Node<>(-40,10);
        KDTree.Node<Port> node8 = new KDTree.Node<>(-10,-3);
        KDTree.Node<Port> node9 = new KDTree.Node<>(40,10);

        List<KDTree.Node<Port>> list = new ArrayList<>();
        list.add(node);
        list.add(node1);
        list.add(node2);
        list.add(node3);
        list.add(node4);
        list.add(node5);
        list.add(node6);
        list.add(node7);
        list.add(node8);
        list.add(node9);

        KDTree<Port> portKDTree = new KDTree<>();
        String expected = "Node{coords=Point2D.Double[2.0, 5.0], info=null, left=Node{coords=Point2D.Double[-10.0, -3.0], info=null, left=Node{coords=Point2D.Double[-5.0, -5.0], info=null, left=null, right=Node{coords=Point2D.Double[-5.0, -5.0], info=null, left=null, right=null, size=0, vertical=false}, size=0, vertical=false}, right=Node{coords=Point2D.Double[-10.0, 3.0], info=null, left=Node{coords=Point2D.Double[-40.0, 10.0], info=null, left=null, right=null, size=0, vertical=false}, right=null, size=0, vertical=false}, size=0, vertical=false}, right=Node{coords=Point2D.Double[5.0, 5.0], info=null, left=Node{coords=Point2D.Double[40.0, -10.0], info=null, left=Node{coords=Point2D.Double[10.0, -3.0], info=null, left=null, right=null, size=0, vertical=false}, right=null, size=0, vertical=false}, right=Node{coords=Point2D.Double[40.0, 10.0], info=null, left=null, right=null, size=0, vertical=false}, size=0, vertical=false}, size=0, vertical=false}";

        KDTree.Node<Port> actual = portKDTree.insert(list,0);
        assertEquals(expected,actual.toString());
    }

    @Test
    void insertTestSize2SizeNodes() {
        KDTree.Node<Port> node1 = new KDTree.Node<>(2,5);
        KDTree.Node<Port> node = new KDTree.Node<>(-5,-5);
        List<KDTree.Node<Port>> list = new ArrayList<>();
        list.add(node);
        list.add(node1);

        KDTree<Port> portKDTree = new KDTree<>();
        String expected = "Node{coords=Point2D.Double[2.0, 5.0], info=null, left=Node{coords=Point2D.Double[-5.0, -5.0], info=null, left=null, right=null, size=0, vertical=false}, right=null, size=0, vertical=false}";

        KDTree.Node<Port> actual = portKDTree.insert(list,2);
        assertEquals(expected,actual.toString());
    }


}