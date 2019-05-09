package BinarySearchTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PreOrder {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = GenerateBST.generateBST();
        BinarySearchTree<Integer>.Node root = bst.getRoot();
        ArrayList<Integer> listRecursive = new ArrayList<Integer>();
        ArrayList<Integer> listNoRecursive = new ArrayList<Integer>();
        
        preOrder(root, listRecursive);
        preOrderNoRecursive(root, listNoRecursive);
        
        System.out.println("递归的结果: " + listRecursive);
        System.out.println("非递归的结果: " + listNoRecursive);
    }
    
    // 前序遍历: 递归实现
    public static void preOrder (BinarySearchTree<Integer>.Node node , List<Integer> list) {
        if (node == null) {
            return;
        }
        
        list.add(node.data);
        preOrder(node.left, list);
        preOrder(node.right, list);
    }
    
    // 前序遍历: 非递归实现
    public static void preOrderNoRecursive (BinarySearchTree<Integer>.Node node, List<Integer> list) {
        Stack<BinarySearchTree<Integer>.Node> stack = new Stack<>();
        stack.add(node);
        
        while (!stack.isEmpty()) {
            BinarySearchTree<Integer>.Node curNode = stack.pop();
            list.add(curNode.data);
            
            if (curNode.right != null) {
                stack.push(curNode.right);
            }
            
            if (curNode.left != null) {
                stack.push(curNode.left);
            }
        }
    }
}






