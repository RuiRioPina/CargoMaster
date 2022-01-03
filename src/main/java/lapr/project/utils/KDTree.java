package lapr.project.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KDTree<T> {

    private int size = 0;
    protected static final int X_AXIS = 0;
    protected static final int Y_AXIS = 1;
    List<Node<T>> nodes = new ArrayList<>();

    /**
     * US201 - Adds the ports from the CSV to a List for further manipulation on the insert method
     *
     * @param port the port to be added
     * @param x    the x of the port
     * @param y    the y of the port
     */

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

        public int compareAxis(Node<T> node, int axis) {
            if (axis > 1 || axis < 0) {
                throw new IllegalArgumentException(String.format("Axis %d out of range: must be 1 or 0", axis));
            }
            double mine;
            double other;
            if (axis == X_AXIS) {
                mine = coords.getX();
                other = node.getX();
            } else {
                mine = coords.getY();
                other = node.getY();
            }

            return Double.compare(mine, other);
        }

        public T getInfo() {
            return this.info;
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

        @Override
        public String toString() {
            return "Node{" +
                    "coords=" + coords +
                    ", info=" + info +
                    ", left=" + left +
                    ", right=" + right +
                    ", size=" + size +
                    ", vertical=" + vertical +
                    '}';
        }
    }

    //----------- end of nested Node class -----------


    Comparator<Node<T>> cmpY = Comparator.comparingDouble(Node::getY);

    Comparator<Node<T>> cmpX = Comparator.comparingDouble(Node::getX);
    private Node<T> root;


    public T findNearestNeigbour(double x, double y) {
        return findNearestNeighbour(root, x, y, root, true);
    }

    private T findNearestNeighbour(Node<T> node, double x, double y,
                                   Node<T> closestNode, boolean divX) {
        if (node == null) {
            return null;
        }
        double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
        double closestDist = Point2D.distanceSq(closestNode.coords.x,
                closestNode.coords.y, x, y);
        if (closestDist > d) {
            closestNode.info = node.getInfo();
            closestNode.coords.x = node.coords.getX();
            closestNode.coords.y = node.coords.getY();
        }
        double delta = divX ? x - node.coords.x : y - node.coords.y;

        double delta2 = delta * delta;
        Node<T> node1 = delta < 0 ? node.left : node.right;
        Node<T> node2 = delta < 0 ? node.right : node.left;
        findNearestNeighbour(node1, x, y, closestNode, !divX);
        if (delta2 < closestDist) {
            findNearestNeighbour(node2, x, y, closestNode, !divX);

        }

        return closestNode.info;
    }

    // number of points in the tree
    public int size() {
        return size;
    }

    /**
     * US201 - Public method of the insertion of ports
     */

    public void insert() {
        root = insert(nodes, 0);
    }

    /**
     * US201 - Private method of the insertion of ports
     *
     * @param list  list containing all the ports added
     * @param depth the depth where the node of the port will be added
     * @return the last node added
     */

    public Node<T> insert(List<Node<T>> list, int depth) {
        int sizeOfLists = list.size();
        Node<T> node;

        if (sizeOfLists == 0) {
            return null;
        }

        int k = 2;
        int axis = depth % k;


        if (axis == X_AXIS) {
            list.sort(cmpX);
        } else if (axis == Y_AXIS) {
            list.sort(cmpY);
        }
        Node<T> median;
        int mid = sizeOfLists / 2;
        median = list.get(mid);


        node = new Node<>(median);
        if (depth == 0) {
            root = node;
        }


        if (sizeOfLists > 2) {
            node.setLeft(this.insert(list.subList(0, mid), depth + 1));
            node.setRight(this.insert(list.subList(mid + 1, sizeOfLists), depth + 1));
        } else if (sizeOfLists == 2) { //mid must be 1
            if (list.get(0).compareAxis(list.get(1), axis) >= 0) {
                node.setRight(this.insert(list.subList(0, 1), depth + 1));
            } else {
                node.setLeft(this.insert(list.subList(0, 1), depth + 1));
            }
        }
        size++;

        return node;
    }


}
