import java.util.ArrayList;

import org.junit.Test;

import AVL_Tree.AVLTree;
import AVL_Tree.BinarySearchTreeMap;
import HashTable.DynamicHashTable;
import HashTable.DynamicHashTable2;
import HashTable.HashTable;
import RedBlackTree.RedBlackTree;
public class Tests {

	@Test
	public void test () {
		System.out.println("Pride and Prejudice");

      ArrayList<String> words = new ArrayList<>();
      if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
          System.out.println("Total words: " + words.size());

          // Collections.sort(words);

          // Test BST
          long startTime = System.nanoTime();

          BinarySearchTreeMap<String, Integer> bst = new BinarySearchTreeMap<>();
          for (String word : words) {
              if (bst.contains(word))
                  bst.set(word, bst.get(word) + 1);
              else
                  bst.add(word, 1);
          }

          for(String word: words)
              bst.contains(word);

          long endTime = System.nanoTime();

          double time = (endTime - startTime) / 1000000000.0;
          System.out.println("BST: " + time + " s");


          // Test AVL
          startTime = System.nanoTime();

          AVLTree<String, Integer> avl = new AVLTree<>();
          for (String word : words) {
              if (avl.contains(word))
                  avl.set(word, avl.get(word) + 1);
              else
                  avl.add(word, 1);
          }

          for(String word: words)
              avl.contains(word);

          endTime = System.nanoTime();

          time = (endTime - startTime) / 1000000000.0;
          System.out.println("AVL: " + time + " s");


          // Test RBTree
          startTime = System.nanoTime();

          RedBlackTree<String, Integer> rbt = new RedBlackTree<>();
          for (String word : words) {
              if (rbt.contains(word))
                  rbt.set(word, rbt.get(word) + 1);
              else
                  rbt.add(word, 1);
          }

          for(String word: words)
              rbt.contains(word);

          endTime = System.nanoTime();

          time = (endTime - startTime) / 1000000000.0;
          System.out.println("RBTree: " + time + " s");


          // Test HashTable
          startTime = System.nanoTime();

          // HashTable<String, Integer> ht = new HashTable<>();
          HashTable<String, Integer> ht = new HashTable<>(231071);
          for (String word : words) {
              if (ht.contains(word))
                  ht.set(word, ht.get(word) + 1);
              else
                  ht.add(word, 1);
          }

          for(String word: words)
              ht.contains(word);

          endTime = System.nanoTime();

          time = (endTime - startTime) / 1000000000.0;
          System.out.println("HashTable: " + time + " s");
         
          
          // Test DynamicHashTable
          startTime = System.nanoTime();
          
          DynamicHashTable<String, Integer> DynamicHashTable = new DynamicHashTable<>();
          for (String word : words) {
         	 if (DynamicHashTable.contains(word))
         		 DynamicHashTable.set(word, DynamicHashTable.get(word) + 1);
         	 else
         		 DynamicHashTable.add(word, 1);
          }
          
          for(String word: words)
         	 DynamicHashTable.contains(word);
          
          endTime = System.nanoTime();
          
          time = (endTime - startTime) / 1000000000.0;
          System.out.println("DynamicHashTable: " + time + " s");
          
          // Test DynamicHashTable2
          startTime = System.nanoTime();
          
          DynamicHashTable2<String, Integer> DynamicHashTable2 = new DynamicHashTable2<>();
          for (String word : words) {
         	 if (DynamicHashTable2.contains(word))
         		 DynamicHashTable2.set(word, DynamicHashTable2.get(word) + 1);
         	 else
         		 DynamicHashTable2.add(word, 1);
          }
          
          for(String word: words)
         	 DynamicHashTable2.contains(word);
          
          endTime = System.nanoTime();
          
          time = (endTime - startTime) / 1000000000.0;
          System.out.println("DynamicHashTable2: " + time + " s");
          
          
          
          
      }
	}
}
