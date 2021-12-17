/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lapr.project.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author DEI-ESINF
 */
public class AVLTest {

    public AVLTest() {
    }

    /**
     * Test of insert method, of class AVL.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");

        //test Simple right rotation 
        AVL<Integer> instance = new AVL();
        int arr[] = {8, 4, 10, 2, 6, 3};
        Integer[] inorder1 = {2, 3, 4, 6, 8, 10};
        for (int i = 0; i < arr.length; i++)            //new elements
            instance.insert(arr[i]);

        List<Integer> lExpected = Arrays.asList(inorder1);
        assertEquals(lExpected, instance.inOrder());
        System.out.println("<instance 1>");
        System.out.println(instance);
        System.out.println("height1=" + instance.height());
        System.out.println("------------------------------------");

        //test Simple left rotation
        AVL<Integer> instance2 = new AVL();
        int arr2[] = {8, 4, 10, 9, 15, 12};
        Integer[] inorder2 = {4, 8, 9, 10, 12, 15};
        for (int i = 0; i < arr2.length; i++)
            instance2.insert(arr2[i]);
        System.out.println("<instance 2>");
        System.out.println(instance2);
        System.out.println("height2=" + instance2.height());
        lExpected = Arrays.asList(inorder2);
        assertEquals(lExpected, instance2.inOrder());
        assertEquals(instance2.height(), 2);
        System.out.println("------------------------------------");

        //test double rotation 
        AVL<Integer> instance3 = new AVL();
        int arr3[] = {8, 4, 10, 2, 6, 5};
        Integer[] inorder3 = {2, 4, 5, 6, 8, 10};
        for (int i = 0; i < arr3.length; i++)
            instance3.insert(arr3[i]);
        System.out.println("<instance 3>");
        System.out.println(instance3);
        System.out.println("height3=" + instance3.height());
        lExpected = Arrays.asList(inorder3);
        assertEquals(lExpected, instance3.inOrder());
        assertEquals(instance3.height(), 2);
        System.out.println("------------------------------------");
    }

    @Test
    void testInsert2() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(1);
        assertFalse(avl.isEmpty());
        BST.Node<Integer> node = avl.root;
        assertNull(node.getRight());
        assertEquals(1, node.getElement().intValue());
        assertNull(node.getLeft());
    }

    @Test
    void testInsert3() {
        AVL<Integer> avl = new AVL<>();
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
    void testInsert4() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(1);
        avl.insert(1);
        assertFalse(avl.isEmpty());
    }

    @Test
    void testInsert5() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(-1);
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

    @Test
    void testInsert6() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(0);
        avl.insert(-1);
        assertEquals(1, avl.height());
        assertFalse(avl.isEmpty());
        BST.Node<Integer> left = avl.root.getLeft();
        assertEquals(-1, left.getElement().intValue());
        assertNull(left.getRight());
        assertNull(left.getLeft());
    }

    @Test
    void testInsert7() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(1);
        avl.insert(0);
        avl.insert(-1);
        assertEquals(1, avl.height());
        assertFalse(avl.isEmpty());
        BST.Node<Integer> node = avl.root;
        assertNull(node.getRight().getLeft());
        BST.Node<Integer> left = node.getLeft();
        assertNull(left.getRight());
        assertNull(left.getLeft());
        assertEquals(-1, left.getElement().intValue());
    }

    @Test
    void testInsert8() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(Integer.MIN_VALUE);
        avl.insert(0);
        avl.insert(-1);
        assertEquals(1, avl.height());
        assertFalse(avl.isEmpty());
        BST.Node<Integer> node = avl.root;
        assertEquals(-1, node.getElement().intValue());
        assertNull(node.getRight().getLeft());
        assertNull(node.getLeft().getRight());
    }

    @Test
    void testInsert9() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(1);
        avl.insert(Integer.MIN_VALUE);
        avl.insert(-1);
        assertEquals(1, avl.height());
        assertFalse(avl.isEmpty());
        BST.Node<Integer> node = avl.root;
        assertEquals(-1, node.getElement().intValue());
        assertNull(node.getRight().getLeft());
        assertNull(node.getLeft().getRight());
    }

    /**
     * Test of remove method, of class AVL.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        List<Integer> lExpected;
        AVL<Integer> instance;

        instance = new AVL();
        int arr[] = {8, 4, 10, 2, 6, 3};
        for (int i = 0; i < arr.length; i++)
            instance.insert(arr[i]);


        //no rotations needed
        instance.remove(3);
        lExpected = Arrays.asList(2, 4, 6, 8, 10);
        assertEquals(lExpected, instance.inOrder());
        assertEquals(instance.height(), 2);

        //test Simple left rotation 
        instance.remove(2);
        lExpected = Arrays.asList(4, 6, 8, 10);
        assertEquals(lExpected, instance.inOrder());
        assertEquals(instance.height(), 2);

        instance.remove(10);
        lExpected = Arrays.asList(4, 6, 8);
        assertEquals(lExpected, instance.inOrder());
        assertEquals(instance.height(), 1);

        instance.remove(6);
        lExpected = Arrays.asList(4, 8);
        assertEquals(lExpected, instance.inOrder());
        assertEquals(1, instance.height());

        instance.remove(4);
        lExpected = Arrays.asList(8);
        assertEquals(lExpected, instance.inOrder());
        assertEquals(0, instance.height());

        instance.remove(8);
        lExpected = Arrays.asList();
        assertEquals(lExpected, instance.inOrder());
        assertEquals(-1, instance.height());

        System.out.println("------------------------------------");
    }

    @Test
    void testRemove2() {
        AVL<Integer> avl = new AVL<>();
        avl.remove(1);
        assertEquals(-1, avl.height());
    }

    @Test
    void testRemove3() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(0);
        avl.remove(1);
        assertFalse(avl.isEmpty());
        assertNull(avl.root.getRight());
    }

    @Test
    void testRemove4() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(1);
        avl.remove(1);
        assertEquals(-1, avl.height());
    }

    @Test
    void testRemove5() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(1);
        avl.insert(0);
        avl.remove(1);
        assertFalse(avl.isEmpty());
    }

    @Test
    void testRemove6() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(-1);
        avl.insert(0);
        avl.remove(1);
        assertEquals(1, avl.height());
        assertFalse(avl.isEmpty());
        assertNull(avl.root.getRight().getRight());
    }

    @Test
    void testRemove7() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(0);
        avl.remove(-1);
        assertFalse(avl.isEmpty());
        assertNull(avl.root.getLeft());
    }

    @Test
    void testRemove8() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(-1);
        avl.insert(1);
        avl.insert(0);
        avl.remove(1);
        assertFalse(avl.isEmpty());
        assertNull(avl.root.getRight());
    }

    @Test
    void testRemove9() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(-1);
        avl.insert(0);
        avl.remove(-1);
        assertFalse(avl.isEmpty());
    }

    @Test
    void testRemove10() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(-1);
        avl.insert(1);
        avl.insert(0);
        avl.remove(0);
        assertFalse(avl.isEmpty());
        BST.Node<Integer> node = avl.root;
        assertNull(node.getRight());
        assertEquals(1, node.getElement().intValue());
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        AVL<Integer> instance = new AVL();
        int arr[] = {1, 8};
        for (int i = 0; i < arr.length; i++) {
            instance.insert(arr[i]);
        }
        AVL<Integer> instance2 = new AVL();
        int arr2[] = {1, 8};
        for (int i = 0; i < arr2.length; i++) {
            instance2.insert(arr2[i]);
        }
        assertTrue(instance.equals(instance2));
        instance2.remove(8);
        assertFalse(instance.equals(instance2));
    }

    @Test
    void testEquals2() {
        assertFalse((new AVL<>()).equals(null));
    }

    @Test
    void testEquals3() {
        assertFalse((new AVL<>()).equals("Different type to AVL"));
    }

    @Test
    void testEquals4() {
        AVL<Integer> avl = new AVL<>();
        assertTrue(avl.equals(avl));
        int expectedHashCodeResult = avl.hashCode();
        assertEquals(expectedHashCodeResult, avl.hashCode());
    }

    @Test
    void testEquals5() {
        AVL<Integer> avl = new AVL<>();
        AVL<Integer> avl1 = new AVL<>();
        assertTrue(avl.equals(avl1));
        int notExpectedHashCodeResult = avl.hashCode();
        assertFalse(Objects.equals(notExpectedHashCodeResult, avl1.hashCode()));
    }

    @Test
    void testEquals6() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(0);
        assertFalse(avl.equals(new AVL<>()));
    }

    @Test
    void testEquals7() {
        AVL<Integer> avl = new AVL<>();

        AVL<Integer> avl1 = new AVL<>();
        avl1.insert(0);
        assertFalse(avl.equals(avl1));
    }

    @Test
    void testEquals8() {
        AVL<Integer> avl = new AVL<>();
        avl.insert(0);

        AVL<Integer> avl1 = new AVL<>();
        avl1.insert(0);
        assertTrue(avl.equals(avl1));
        int notExpectedHashCodeResult = avl.hashCode();
        assertFalse(Objects.equals(notExpectedHashCodeResult, avl1.hashCode()));
    }

    @Test
    void testEquals9() {
        AVL<Integer> avl = new AVL<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        BST.Node<Integer> root1 = new BST.Node<>(1, leftChild3,
                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null))));

        BST.Node<Integer> leftChild7 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild8 = new BST.Node<>(1, leftChild7, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild9 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild10 = new BST.Node<>(1, leftChild8,
                new BST.Node<>(1, leftChild9, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild11 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild12 = new BST.Node<>(1, leftChild11, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild13 = new BST.Node<>(1, null, null);

        assertTrue(avl.equals(root1, new BST.Node<>(1, leftChild10,
                new BST.Node<>(1, leftChild12, new BST.Node<>(1, leftChild13, new BST.Node<>(1, null, null))))));
    }

    @Test
    void testEquals10() {
        AVL<Integer> avl = new AVL<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        BST.Node<Integer> root1 = new BST.Node<>(0, leftChild3,
                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null))));

        BST.Node<Integer> leftChild7 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild8 = new BST.Node<>(1, leftChild7, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild9 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild10 = new BST.Node<>(1, leftChild8,
                new BST.Node<>(1, leftChild9, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild11 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild12 = new BST.Node<>(1, leftChild11, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild13 = new BST.Node<>(1, null, null);

        assertFalse(avl.equals(root1, new BST.Node<>(1, leftChild10,
                new BST.Node<>(1, leftChild12, new BST.Node<>(1, leftChild13, new BST.Node<>(1, null, null))))));
    }

    @Test
    void testEquals11() {
        AVL<Integer> avl = new AVL<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(0, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        BST.Node<Integer> root1 = new BST.Node<>(1, leftChild3,
                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null))));

        BST.Node<Integer> leftChild7 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild8 = new BST.Node<>(1, leftChild7, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild9 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild10 = new BST.Node<>(1, leftChild8,
                new BST.Node<>(1, leftChild9, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild11 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild12 = new BST.Node<>(1, leftChild11, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild13 = new BST.Node<>(1, null, null);

        assertFalse(avl.equals(root1, new BST.Node<>(1, leftChild10,
                new BST.Node<>(1, leftChild12, new BST.Node<>(1, leftChild13, new BST.Node<>(1, null, null))))));
    }

    @Test
    void testEquals12() {
        AVL<Integer> avl = new AVL<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild7 = new BST.Node<>(1, new BST.Node<>(1, leftChild3,
                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null)))), null);

        BST.Node<Integer> leftChild8 = new BST.Node<>(1, leftChild7, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild9 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild10 = new BST.Node<>(1, leftChild8,
                new BST.Node<>(1, leftChild9, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild11 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild12 = new BST.Node<>(1, leftChild11, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild13 = new BST.Node<>(1, null, null);

        BST.Node<Integer> root1 = new BST.Node<>(1, leftChild10,
                new BST.Node<>(1, leftChild12, new BST.Node<>(1, leftChild13, new BST.Node<>(1, null, null))));

        BST.Node<Integer> leftChild14 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild15 = new BST.Node<>(1, leftChild14, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild16 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild17 = new BST.Node<>(1, leftChild15,
                new BST.Node<>(1, leftChild16, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild18 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild19 = new BST.Node<>(1, leftChild18, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild20 = new BST.Node<>(1, null, null);

        assertFalse(avl.equals(root1, new BST.Node<>(1, leftChild17,
                new BST.Node<>(1, leftChild19, new BST.Node<>(1, leftChild20, new BST.Node<>(1, null, null))))));
    }

    @Test
    void testEquals13() {
        AVL<Integer> avl = new AVL<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, leftChild, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, leftChild1,
                new BST.Node<>(1, leftChild2, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, leftChild4, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild7 = new BST.Node<>(1, null, new BST.Node<>(1, leftChild3,
                new BST.Node<>(1, leftChild5, new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null)))));

        BST.Node<Integer> leftChild8 = new BST.Node<>(1, leftChild7, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild9 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild10 = new BST.Node<>(1, leftChild8,
                new BST.Node<>(1, leftChild9, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild11 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild12 = new BST.Node<>(1, leftChild11, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild13 = new BST.Node<>(1, null, null);

        BST.Node<Integer> root1 = new BST.Node<>(1, leftChild10,
                new BST.Node<>(1, leftChild12, new BST.Node<>(1, leftChild13, new BST.Node<>(1, null, null))));

        BST.Node<Integer> leftChild14 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild15 = new BST.Node<>(1, leftChild14, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild16 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild17 = new BST.Node<>(1, leftChild15,
                new BST.Node<>(1, leftChild16, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild18 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild19 = new BST.Node<>(1, leftChild18, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild20 = new BST.Node<>(1, null, null);

        assertFalse(avl.equals(root1, new BST.Node<>(1, leftChild17,
                new BST.Node<>(1, leftChild19, new BST.Node<>(1, leftChild20, new BST.Node<>(1, null, null))))));
    }

    @Test
    void testEquals14() {
        AVL<Integer> avl = new AVL<>();
        BST.Node<Integer> leftChild = new BST.Node<>(1, null, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild1 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild2 = new BST.Node<>(1, leftChild,
                new BST.Node<>(1, leftChild1, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild3 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild4 = new BST.Node<>(1, leftChild3, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild5 = new BST.Node<>(1, null, null);

        BST.Node<Integer> root1 = new BST.Node<>(1, leftChild2,
                new BST.Node<>(1, leftChild4, new BST.Node<>(1, leftChild5, new BST.Node<>(1, null, null))));

        BST.Node<Integer> leftChild6 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild7 = new BST.Node<>(1, leftChild6, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild8 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild9 = new BST.Node<>(1, leftChild7,
                new BST.Node<>(1, leftChild8, new BST.Node<>(1, null, null)));

        BST.Node<Integer> leftChild10 = new BST.Node<>(1, null, null);

        BST.Node<Integer> leftChild11 = new BST.Node<>(1, leftChild10, new BST.Node<>(1, null, null));

        BST.Node<Integer> leftChild12 = new BST.Node<>(1, null, null);

        assertFalse(avl.equals(root1, new BST.Node<>(1, leftChild9,
                new BST.Node<>(1, leftChild11, new BST.Node<>(1, leftChild12, new BST.Node<>(1, null, null))))));
    }
}
