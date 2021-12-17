package lapr.project.utils;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author DEI-ESINF
 */
public class BSTTest {
    Integer[] arr = {20, 15, 10, 13, 8, 17, 40, 50, 30, 7};
    int[] height = {0, 1, 2, 3, 3, 3, 3, 3, 3, 4};
    Integer[] inorderT = {7, 8, 10, 13, 15, 17, 20, 30, 40, 50};
    Integer[] preorderT = {20, 15, 10, 8, 7, 13, 17, 40, 30, 50};
    Integer[] posorderT = {7, 8, 13, 10, 17, 15, 30, 50, 40, 20};

    BST<Integer> instance;

    @Test
    void testRoot() {
        assertNull((new BST<>()).root());
    }

    public BSTTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new BST();
        for (int i : arr)
            instance.insert(i);
    }

    @Test
    void testConstructor() {
        assertEquals("", (new BST<>()).toString());
    }

    /**
     * Test of size method, of class BST.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        assertEquals(arr.length, instance.size());

        BST<String> sInstance = new BST();
        assertEquals(0, sInstance.size());
        sInstance.insert("A");
        assertEquals(1, sInstance.size());
        sInstance.insert("B");
        assertEquals(2, sInstance.size());
        sInstance.insert("A");
        assertEquals(2, sInstance.size());
    }

    @Test
    void testSize2() {
        assertEquals(0, (new BST<>()).size());
    }

    @Test
    void testSize3() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        assertEquals(1, bst.size());
    }

    @Test
    void testSize4() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.insert(0);
        assertEquals(2, bst.size());
    }

    @Test
    void testSize5() {
        BST<Integer> bst = new BST<>();
        bst.insert(-1);
        bst.insert(0);
        assertEquals(2, bst.size());
    }

    /**
     * Test of insert method, of class BST.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        int arr[] = {20, 15, 10, 13, 8, 17, 40, 50, 30, 20, 15, 10};
        BST<Integer> instance = new BST();
        for (int i = 0; i < 9; i++) {            //new elements
            instance.insert(arr[i]);
            assertEquals(instance.size(), i + 1);
        }
        for (int i = 9; i < arr.length; i++) {    //duplicated elements => same size
            instance.insert(arr[i]);
            assertEquals(instance.size(), 9);
        }
    }

    @Test
    void testInsert2() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        assertFalse(bst.isEmpty());
        BST.Node<Integer> node = bst.root;
        assertNull(node.getRight());
        assertEquals(1, node.getElement().intValue());
        assertNull(node.getLeft());
    }

    @Test
    void testInsert3() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        bst.insert(1);
        assertEquals(1, bst.height());
        BST.Node<Integer> right = bst.root.getRight();
        assertNull(right.getLeft());
        assertEquals(1, right.getElement().intValue());
        assertNull(right.getRight());
    }

    @Test
    void testInsert4() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.insert(1);
        assertFalse(bst.isEmpty());
    }

    @Test
    void testInsert5() {
        BST<Integer> bst = new BST<>();
        bst.insert(-1);
        bst.insert(0);
        bst.insert(1);
        assertEquals(2, bst.height());
        BST.Node<Integer> right = bst.root.getRight().getRight();
        assertEquals(1, right.getElement().intValue());
        assertNull(right.getRight());
        assertNull(right.getLeft());
    }

    @Test
    void testInsert6() {
        BST<Integer> bst = new BST<>();
        bst.insert(null);
        assertEquals(-1, bst.height());
    }

    @Test
    void testInsert7() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        bst.insert(-1);
        assertEquals(1, bst.height());
        BST.Node<Integer> left = bst.root.getLeft();
        assertEquals(-1, left.getElement().intValue());
        assertNull(left.getRight());
        assertNull(left.getLeft());
    }

    @Test
    void testInsert8() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.insert(0);
        bst.insert(-1);
        assertEquals(2, bst.height());
        BST.Node<Integer> left = bst.root.getLeft().getLeft();
        assertEquals(-1, left.getElement().intValue());
        assertNull(left.getRight());
        assertNull(left.getLeft());
    }

    @Test
    void testInsert9() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(0);
        avl.insert(0);
        avl.insert(1);
        assertEquals(1, avl.height());
        assertFalse(avl.isEmpty());
        BST.Node<Integer> right = avl.root.getRight();
        assertNull(right.getLeft());
        assertEquals(1, right.getElement().intValue());
        assertNull(right.getRight());
    }

    @Test
    void testInsert10() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(-1);
        avl.insert(0);
        avl.insert(0);
        avl.insert(1);
        assertEquals(1, avl.height());
        assertFalse(avl.isEmpty());
        BST.Node<Integer> node = avl.root;
        BST.Node<Integer> right = node.getRight();
        assertNull(right.getLeft());
        assertNull(node.getLeft().getRight());
        assertEquals(1, right.getElement().intValue());
        assertNull(right.getRight());
    }

    /**
     * Test of remove method, of class BST.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");

        int qtd = arr.length;
        instance.remove(999);

        assertEquals(instance.size(), qtd);
        for (int i = 0; i < arr.length; i++) {
            instance.remove(arr[i]);
            qtd--;
            assertEquals(qtd, instance.size());
        }

        instance.remove(999);
        assertEquals(0, instance.size());
    }

    @Test
    void testRemove2() {
        BST<Integer> bst = new BST<>();
        bst.remove(1);
        assertEquals(-1, bst.height());
    }

    @Test
    void testRemove3() {
        AVL<Integer> avl = new AVL<>();
        avl.remove(1);
        assertEquals(-1, avl.height());
    }

    @Test
    void testRemove4() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        bst.remove(1);
        assertFalse(bst.isEmpty());
        assertNull(bst.root.getRight());
    }

    @Test
    void testRemove5() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.remove(1);
        assertEquals(-1, bst.height());
    }

    @Test
    void testRemove6() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.insert(0);
        bst.remove(1);
        assertFalse(bst.isEmpty());
    }

    @Test
    void testRemove7() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        bst.remove(-1);
        assertFalse(bst.isEmpty());
        assertNull(bst.root.getLeft());
    }

    @Test
    void testRemove8() {
        BST<Integer> bst = new BST<>();
        bst.insert(-1);
        bst.insert(0);
        bst.remove(-1);
        assertFalse(bst.isEmpty());
    }

    @Test
    void testRemove9() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(1);
        avl.insert(0);
        avl.remove(-1);
        assertEquals(1, avl.height());
        assertFalse(avl.isEmpty());
        assertNull(avl.root.getLeft().getLeft());
    }

    /**
     * Test of isEmpty method, of class BST.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isempty");

        assertFalse(instance.isEmpty());
        instance = new BST();
        assertTrue(instance.isEmpty());

        instance.insert(11);
        assertFalse(instance.isEmpty());

        instance.remove(11);
        assertTrue(instance.isEmpty());
    }

    @Test
    void testIsEmpty2() {
        assertTrue((new BST<>()).isEmpty());
    }

    @Test
    void testIsEmpty3() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        assertFalse(bst.isEmpty());
    }

    /**
     * Test of height method, of class BST.
     */
    @Test
    public void testHeight() {
        System.out.println("height");

        instance = new BST();
        assertEquals(instance.height(), -1);
        for (int idx = 0; idx < arr.length; idx++) {
            instance.insert(arr[idx]);
            assertEquals(instance.height(), height[idx]);
        }
        instance = new BST();
        assertEquals(instance.height(), -1);
    }

    @Test
    void testHeight2() {
        assertEquals(-1, (new BST<>()).height());
    }

    @Test
    void testHeight3() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        assertEquals(0, bst.height());
    }

    @Test
    void testHeight4() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.insert(0);
        assertEquals(1, bst.height());
    }

    @Test
    void testHeight5() {
        BST<Integer> bst = new BST<>();
        bst.insert(-1);
        bst.insert(0);
        assertEquals(1, bst.height());
    }

    @Test
    void testHeight6() {
        BST<Integer> bst = new BST<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        assertEquals(4, bst.height(new BST.Node<>(1, leftChild3,
                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null))))));
    }

    /**
     * Test of smallestelement method, of class TREE.
     */
    @Test
    public void testSmallestElement() {
        System.out.println("smallestElement");
        assertEquals(new Integer(7), instance.smallestElement());
        instance.remove(7);
        assertEquals(new Integer(8), instance.smallestElement());
        instance.remove(8);
        assertEquals(new Integer(10), instance.smallestElement());
    }

    @Test
    void testSmallestElement2() {
        assertNull((new BST<>()).smallestElement());
    }

    @Test
    void testSmallestElement3() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        assertEquals(0, bst.smallestElement().intValue());
    }

    @Test
    void testSmallestElement4() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.insert(0);
        assertEquals(0, bst.smallestElement().intValue());
    }

    @Test
    void testSmallestElement5() {
        BST<Integer> bst = new BST<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        assertEquals(
                1, bst
                        .smallestElement(new BST.Node<>(1, leftChild3,
                                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null)))))
                        .intValue());
    }


    @Test
    void testFind2() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        assertNull(bst.find(1));
    }

    @Test
    void testFind3() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        assertSame(bst.root, bst.find(1));
    }

    @Test
    void testFind4() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        assertNull(bst.find(-1));
    }

    @Test
    void testFind5() {
        BST<Integer> bst = new BST<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        BST.Node<Integer> node = new BST.Node<>(1, leftChild3,
                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null))));

        assertSame(node, bst.find(node, 1));
    }

    @Test
    void testFind6() {
        BST<Integer> bst = new BST<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        BST.Node<Integer> node = new BST.Node<>(1, leftChild5,
                new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null)));

        assertSame(node, bst.find(new BST.Node<>(0, leftChild3, node), 1));
    }

    @Test
    void testFind7() {
        BST<Integer> bst = new BST<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        assertNull(bst.find(new BST.Node<>(1, leftChild3,
                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null)))), 0));
    }

    /**
     * Test of processBstByLevel method, of class TREE.
     */
    @Test
    public void testProcessBstByLevel() {
        System.out.println("processbstbylevel");
        Map<Integer, List<Integer>> expResult = new HashMap();
        expResult.put(0, Arrays.asList(new Integer[]{20}));
        expResult.put(1, Arrays.asList(new Integer[]{15, 40}));
        expResult.put(2, Arrays.asList(new Integer[]{10, 17, 30, 50}));
        expResult.put(3, Arrays.asList(new Integer[]{8, 13}));
        expResult.put(4, Arrays.asList(new Integer[]{7}));

        Map<Integer, List<Integer>> result = instance.nodesByLevel();

        for (Map.Entry<Integer, List<Integer>> e : result.entrySet())
            assertEquals(expResult.get(e.getKey()), e.getValue());
    }


    /**
     * Test of inOrder method, of class BST.
     */
    @Test
    public void testInOrder() {
        System.out.println("inOrder");
        List<Integer> lExpected = Arrays.asList(inorderT);
        assertEquals(lExpected, instance.inOrder());
    }

    @Test
    void testInOrder3() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        Iterable<Integer> actualInOrderResult = bst.inOrder();
        actualInOrderResult.iterator();
        assertEquals(1, ((Collection<Integer>) actualInOrderResult).size());
        assertEquals(1, ((List<Integer>) actualInOrderResult).get(0));
    }

    @Test
    void testPreOrder2() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        Iterable<Integer> actualPreOrderResult = bst.preOrder();
        actualPreOrderResult.iterator();
        assertEquals(1, ((Collection<Integer>) actualPreOrderResult).size());
        assertEquals(1, ((List<Integer>) actualPreOrderResult).get(0));
    }

    /**
     * Test of preOrder method, of class BST.
     */
    @Test
    public void testpreOrder() {
        System.out.println("preOrder");
        List<Integer> lExpected = Arrays.asList(preorderT);
        assertEquals(lExpected, instance.preOrder());
    }


    @Test
    void testPosOrder2() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        Iterable<Integer> actualPosOrderResult = bst.posOrder();
        actualPosOrderResult.iterator();
        assertEquals(1, ((Collection<Integer>) actualPosOrderResult).size());
        assertEquals(1, ((List<Integer>) actualPosOrderResult).get(0));
    }

    /**
     * Test of posOrder method, of class BST.
     */
    @Test
    public void testposOrder() {
        System.out.println("posOrder");
        List<Integer> lExpected = Arrays.asList(posorderT);
        assertEquals(lExpected, instance.posOrder());
    }

    @Test
    void testNodesByLevel() {
        assertNull((new BST<>()).nodesByLevel());
    }

    @Test
    void testNodesByLevel2() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        Map<Integer, List<Integer>> actualNodesByLevelResult = bst.nodesByLevel();
        assertEquals(1, actualNodesByLevelResult.size());
        List<Integer> getResult = actualNodesByLevelResult.get(0);
        assertEquals(1, getResult.size());
        assertEquals(0, getResult.get(0));
    }

    @Test
    void testNodesByLevel3() {
        BST<Integer> bst = new BST<>();
        bst.insert(1);
        bst.insert(0);
        Map<Integer, List<Integer>> actualNodesByLevelResult = bst.nodesByLevel();
        assertEquals(2, actualNodesByLevelResult.size());
        List<Integer> getResult = actualNodesByLevelResult.get(0);
        assertEquals(1, getResult.size());
        assertEquals(1, getResult.get(0));
        List<Integer> getResult1 = actualNodesByLevelResult.get(1);
        assertEquals(1, getResult1.size());
        assertEquals(0, getResult1.get(0));
    }

    @Test
    void testNodesByLevel4() {
        BST<Integer> bst = new BST<>();
        bst.insert(-1);
        bst.insert(0);
        Map<Integer, List<Integer>> actualNodesByLevelResult = bst.nodesByLevel();
        assertEquals(2, actualNodesByLevelResult.size());
        List<Integer> getResult = actualNodesByLevelResult.get(0);
        assertEquals(1, getResult.size());
        assertEquals(-1, getResult.get(0));
        List<Integer> getResult1 = actualNodesByLevelResult.get(1);
        assertEquals(1, getResult1.size());
        assertEquals(0, getResult1.get(0));
    }

    @Test
    void testNodesByLevel5() {
        BST<Integer> bst = new BST<>();
        bst.insert(0);
        bst.insert(1);
        bst.insert(-1);
        Map<Integer, List<Integer>> actualNodesByLevelResult = bst.nodesByLevel();
        assertEquals(2, actualNodesByLevelResult.size());
        List<Integer> getResult = actualNodesByLevelResult.get(0);
        assertEquals(1, getResult.size());
        assertEquals(0, getResult.get(0));
        List<Integer> getResult1 = actualNodesByLevelResult.get(1);
        assertEquals(2, getResult1.size());
        assertEquals(-1, getResult1.get(0));
        assertEquals(1, getResult1.get(1));
    }
}
