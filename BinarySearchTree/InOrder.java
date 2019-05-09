package BinarySearchTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InOrder {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = GenerateBST.generateBST();
        BinarySearchTree<Integer>.Node root = bst.getRoot();
        ArrayList<Integer> listRecursive = new ArrayList<Integer>();
        ArrayList<Integer> listNoRecursive = new ArrayList<Integer>();
        
        inOrder(root, listRecursive);
        inOrderNoRecursive1(root, listNoRecursive);
//        inOrderNoRecursive2(root, listNoRecursive);
        
        System.out.println("递归的结果: " + listRecursive);
        System.out.println("非递归的结果: " + listRecursive);
//        System.out.println("非递归的结果: " + listNoRecursive);
    }
    
    // 中序遍历: 递归实现
    public static void inOrder (BinarySearchTree<Integer>.Node node, List<Integer> list) {
        if (node == null) {
            return;
        }
        
        inOrder(node.left, list);
        list.add(node.data);
        inOrder(node.right, list);
        
    }
    
    // 中序遍历: 非递归实现1
    public static void inOrderNoRecursive1 (BinarySearchTree<Integer>.Node node, List<Integer> list) {
        Stack<BinarySearchTree<Integer>.Node> stack = new Stack<BinarySearchTree<Integer>.Node>();
        BinarySearchTree<Integer>.Node curNode = node;
        
        while (curNode != null && !stack.isEmpty()) {
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            
            if (!stack.isEmpty()) {
                BinarySearchTree<Integer>.Node peek = stack.pop();
                list.add(peek.data);
                curNode = curNode.right;
            }
        }
        
    }
    
    
    // 中序遍历: 非递归实现2
    public static boolean printState = true;
    public static boolean unprintState = false;
    public static void inOrderNoRecursive2 (BinarySearchTree<Integer>.Node node, List<Integer> list) {
        /*
                     给予节点两种状态: 一种为打印状态, 一种为非打印状态,
                      如果当前栈中的节点为非打印状态, 那么往栈中依次推入当前节点的右节点,当前节点,左节点,代表了访问顺序
                      如果当前栈顶的节点为打印状态, 那么就直接使用该节点的数据就好了 
         */
        
        // State类用于保存当前节点和其当前的状态
        class State {
            boolean state;
            BinarySearchTree<Integer>.Node node;
            
            public State (boolean state, BinarySearchTree<Integer>.Node node) {
                this.state = state;
                this.node = node;
            }
        }
        
        Stack<State> stack = new Stack<State>();
        stack.push(new State(unprintState, node));
        
        while (!stack.isEmpty()) {
            // 将栈顶的元素推出, 判断其状态
            State peek = stack.pop();
            BinarySearchTree<Integer>.Node curNode = peek.node;
            boolean state = peek.state;
            
            // 如果状态是非打印状态, 那么推入其右节点, 当前节点, 左节点
            if (state == unprintState) {
               if (curNode.right != null) {
                   stack.push(new State(unprintState, curNode.right));
               }
               stack.push(new State(printState, curNode));
               if (curNode.left != null) {
                   stack.push(new State(unprintState, curNode.left)); 
               }
            } else {
                
                // 如果是打印状态, 那么直接使用该数据
                list.add(curNode.data);
            }
        }
        
    }
}




