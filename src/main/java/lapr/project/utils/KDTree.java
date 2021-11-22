package lapr.project.utils;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class KDTree<T> {

    private int size = 0;

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

        public Double getX() {
            return coords.getX();
        }

        public Double getY() {
            return coords.getY();
        }

    }

    //----------- end of nested Node class -----------

    private final Comparator<Node<T>> cmpX = new Comparator<Node<T>>() {
        @Override
        public int compare(Node<T> p1, Node<T> p2) {
            return Double.compare(p1.getX(), p2.getX());
        }
    };

    private final Comparator<Node<T>> cmpY = new Comparator<Node<T>>() {
        @Override
        public int compare(Node<T> p1, Node<T> p2) {
            return Double.compare(p1.getY(), p2.getY());
        }
    };

    private Node<T> root;

    public void insert(T port, Double x, Double y) {
        root = insert(port, root, new Point2D.Double(x, y), true);
    }


    // helper: add point p to subtree rooted at node
    private Node<T> insert(T info, final Node<T> node, final Point2D p,
                           final boolean vertical) {
        // if new node, create it
        if (node == null) {
            size++;
            return new Node<T>(info, p.getX(), p.getY(), null, null, vertical);
        }

        // if already in, return it
        if (node.getX() == p.getY() && node.getY() == p.getY()) return node;

        // else, insert it where corresponds (left - right recursive call)
        if (node.vertical && p.getX() < node.coords.getX() || !node.vertical && p.getY() < node.coords.getY())
            node.left = insert(info, node.left, p, !node.vertical);
        else
            node.right = insert(info, node.right, p, !node.vertical);

        return node;
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


//    public T findNearestNeighbour(Node<T> node, double x, double y,
//                                  Node<T> closestNode, boolean divX) {
//        if (node == null)
//            return null;
//        double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
//        double closestDist = Point2D.distanceSq(closestNode.coords.x,
//                closestNode.coords.y, x, y);
//        if (closestDist > d) {
//            closestNode = node;
//            double delta = divX ? x - node.coords.x : y - node.coords.y;
//            double delta2 = delta * delta;
//            Node<T> node1 = delta < 0 ? node.left : node.right;
//            Node<T> node2 = delta < 0 ? node.right : node.left;
//            findNearestNeighbour(node1, x, y, closestNode, !divX);
//            if (delta2 < closestDist) {
//                findNearestNeighbour(node2, x, y, closestNode, !divX);
//                return closestNode.info;
//            }
//        }
//        return closestNode.info;
//    }

    // number of points in the tree
    public int size() {
        return size;
    }
}
