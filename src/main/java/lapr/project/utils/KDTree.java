package lapr.project.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KDTree<T> {

    private int size = 0;
    private int k = 2;
    int depth = 0;
    protected static final int X_AXIS = 0;
    protected static final int Y_AXIS = 1;
    List<Node<T>> nodes = new ArrayList<>();

    public void addToList(T port, double x, double y) {
        nodes.add(new Node<>(port, x, y, null, null, true));
    }

    public static class Node<T> {
        protected Point2D.Double coords = new Point2D.Double();
        protected T info;
        protected Node<T> left;
        protected Node<T> right;
        protected int size;
        protected boolean vertical;


        public Node(double x, double y) {
            coords.setLocation(x, y);
        }

        public Node(T info, Double x, Double y, Node<T> leftChild, Node<T> rightChild, final boolean v) {
            this.info = info;
            coords.setLocation(x, y);
            size = 0;
            left = leftChild;
            right = rightChild;
            vertical = v;
        }

        public Node(Node<T> node) {
            this.info = node.info;
            coords.setLocation(node.getX(), node.getY());
            size = 0;
            left = node.left;
            right = node.right;
            vertical = node.vertical;
        }


        public Double getX() {
            return coords.getX();
        }

        public Double getY() {
            return coords.getY();
        }

        public void setLeft(Node<T> left) {
            this.left = left;

        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

        public Point2D.Double getCoordinates() {
            return coords;
        }
    }

    //----------- end of nested Node class -----------

    private final Comparator<Point2D.Double> cmpX = new Comparator<Point2D.Double>() {
        @Override
        public int compare(Point2D.Double p1, Point2D.Double p2) {
            return Double.compare(p1.getX(), p2.getX());
        }
    };

    private final Comparator<Point2D.Double> cmpY = new Comparator<Point2D.Double>() {
        @Override
        public int compare(Point2D.Double p1, Point2D.Double p2) {
            return Double.compare(p1.getY(), p2.getY());
        }
    };

    private Node<T> root;

    public void insert() {
        root = insert(nodes, 0);
    }

    // does the tree contain the point p?
    public boolean contains(final Point2D p) {
        return contains(root, p.getX(), p.getY());
    }

    // helper: does the subtree rooted at node contain (x, y)?
    private boolean contains(Node<T> node, double x, double y) {
        if (node == null) return false;
        if (node.coords.getX() == x && node.coords.getY() == y) return true;

        if (node.vertical && x < node.coords.getX() || !node.vertical && y < node.coords.getY())
            return contains(node.left, x, y);
        else
            return contains(node.right, x, y);
    }

    // number of points in the tree
    public int size() {
        return size;
    }


    private Node<T> insert(List<Node<T>> list, int depth) {
        int sizeOfLists = list.size();
        Node<T> node = null;

        List<Point2D.Double> pointsOfTheNodes = new ArrayList<>();
        for (Node<T> node1 : list) {
            pointsOfTheNodes.add(node1.coords);
        }
        if (k < 1) {
            System.err.println("ERROR: KDTree.recursiveBuildFaster(): Number of Dimensions has not been defined.");
            return null;
        }
        if (depth < 0) {
            System.err.println("ERROR: KDTree.recursiveBuildFaster(): Cannot build tree from a negative depth!");
            return null;
        }
        if (sizeOfLists == 0) {
            System.err.println("ERROR: KDTree.recursiveBuildfaster(): Cannot build tree from an empty list.");
            return node;
        }

        int axis = depth % k;    //the 'axis' is the dimension that the nodes should be compared by.
        //it is determined by the current depth
        if (axis == X_AXIS) {
            Collections.sort(pointsOfTheNodes, cmpX);
        } else if (axis == Y_AXIS) {
            Collections.sort(pointsOfTheNodes, cmpY);
        }
        //split list by median
        Node<T> median;
        int mid = sizeOfLists / 2; //floor of sizeOfLists/2
        median = list.get(mid); //get median object from desired axis-sorted list in list

        //create node, split list around median and recur
        //if there is only one node left in the list, it's our median,
        //so we're done with building the tree and we can return the
        //last node which we just created from the median.
        node = new Node<T>(median);
        if (depth == 0) {
            root = node; //set node to root if it's the first one

        }

        //create new sets of branches, split around the median object.
        List<Node<T>> less = new ArrayList<>(); //all object less than or equal to (on current axis)
        List<Node<T>> more = new ArrayList<>(); //all objects greater than (on current axis)
        more = nodes.subList(mid, sizeOfLists-1);
        less = nodes.subList(0,mid);
        size++;
        if (sizeOfLists > 2) {
            print(pointsOfTheNodes);
            node.setLeft(this.insert(list.subList(0, mid), depth+1)); //Recur on sublist of everything before midpoint
            node.setRight(this.insert(list.subList(mid+1, sizeOfLists), depth+1)); //recur on sublist of everything after midpoint
        } else if (sizeOfLists == 2) { //mid must be 1
            print(pointsOfTheNodes);
            if (compareAxis(list.get(0),list.get(1), axis) >= 0)

                node.setRight(this.insert(list.subList(0, 1), depth+1)); //the node before mid
            else
                node.setLeft(this.insert(list.subList(0, 1), depth+1)); //node before mid
        }
        return node;
    }

    public int compareAxis(Node<T> kd1,Node<T> kd2, int axis) {
        if (axis > 1 || axis < 0) {
            throw new IllegalArgumentException(String.format("Axis %d out of range: must be 1 or 0", axis));
        }
        Point2D.Double mine = kd1.getCoordinates();
        Point2D.Double other = kd2.getCoordinates();
        if(axis==0) {
            return Double.compare(mine.getX(), other.getX());
        }else {
            return Double.compare(mine.getY(), other.getY());

        }
    }
        private void print(List<Point2D.Double> pointsOfTheNodes) {
            for (Point2D.Double p: pointsOfTheNodes) {
                System.out.println(pointsOfTheNodes);
            }
        }

}
