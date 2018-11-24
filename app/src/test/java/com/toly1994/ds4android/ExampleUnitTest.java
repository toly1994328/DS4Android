package com.toly1994.ds4android;

import com.toly1994.ds4android.analyze.L;
import com.toly1994.ds4android.ds.impl.chart.LinkedChart;
import com.toly1994.ds4android.ds.tree.AVLTree;
import com.toly1994.ds4android.ds.tree.BinarySearchTree;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        LinkedChart<String> lc = new LinkedChart<>();

        lc.add("toly");

        System.out.println(lc.get(0));

        String toly2 = lc.set(0, "toly2");

        System.out.println(lc.get(0));
        System.out.println(toly2);
    }

    @Test
    public void bst() {
        //        BST<Integer> bst = new BST<>();
//
//        int[] nums = {10, 8, 6, 16, 9, 12, 22, 15, 72, 19};
//        for (int num : nums)
//            bst.add(num);

  /*
            10
        /       \
       8        16
     /  \      /  \
    6    9   12   22
             \    / \
             15 19  72
                        */

//        removeTest(bst);

        BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
//        BinarySearchTree<Integer> bst2 = new BinarySearchTree<>(true);

        int[] nums = {10, 8, 6, 16, 9, 12, 22, 15, 72, 19};
        for (int num : nums) {
            bst1.add(num);
        }
        L.d(bst1 + L.l());
    }


    @Test
    public void avl() {
        //        BST<Integer> bst = new BST<>();
//
//        int[] nums = {10, 8, 6, 16, 9, 12, 22, 15, 72, 19};
//        for (int num : nums)
//            bst.add(num);

  /*
            10
        /       \
       8        16
     /  \      /  \
    6    9   12   22
             \    / \
             15 19  72
                        */

//        removeTest(bst);

        AVLTree<Integer> avlTree = new AVLTree<>();

//        BinarySearchTree<Integer> bst2 = new BinarySearchTree<>(true);

        int[] nums = {10, 8, 6, 16, 9, 12, 22, 15, 72, 19};
        for (int num : nums) {
            avlTree.add(num);
        }

        ArrayList<Integer> integers = new ArrayList<>();
        avlTree.orderIn(integers);
        System.out.println(integers);
    }
}