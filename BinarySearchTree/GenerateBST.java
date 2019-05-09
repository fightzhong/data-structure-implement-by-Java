package BinarySearchTree;

import java.util.Random;

public class GenerateBST {
    public static BinarySearchTree<Integer> generateBST() {
        Random ran = new Random();
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
//        for (int i = 0; i < 10; i++) {
//            bst.add(ran.nextInt(100));
//        }
            bst.add(4);
            bst.add(2);
            bst.add(6);
            bst.add(0);
            bst.add(3);
            bst.add(5);
            bst.add(7);
            bst.add(1);
        
        return bst;
    }
}
