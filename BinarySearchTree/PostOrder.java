package BinarySearchTree;

import java.util.ArrayList;
import java.util.Stack;

public class PostOrder {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = GenerateBST.generateBST();
        BinarySearchTree<Integer>.Node root = bst.getRoot();
        ArrayList<Integer> listRecursive = new ArrayList<Integer>();
        ArrayList<Integer> listNoRecursive = new ArrayList<Integer>();
        
        postOrder(root, listRecursive);
        postOrderNoRecursive1(root, listNoRecursive);
        
        System.out.println("递归的结果: " + listRecursive);
        System.out.println("非递归的结果: " + listNoRecursive);
    }

    // 后序遍历: 递归实现
    private static void postOrder(BinarySearchTree<Integer>.Node node, ArrayList<Integer> list) {
        if (node == null) {
            return;
        }
        
        postOrder(node.left, list);
        postOrder(node.right, list);
        list.add(node.data);
    }
    
    // 后序遍历: 非递归实现2
    private static boolean printState = false; // 指定两种状态用布尔值表示, 这样后面判断的时候就直接用变量进行判断是否相等
    private static boolean unprintState = true;
    private static void postOrderNoRecursive1(BinarySearchTree<Integer>.Node root, ArrayList<Integer> list) {
        /**
                     设置两个状态, 一个为打印状态, 一个为访问状态, 一开始我们先将根节点设为访问状态压入栈中
                     然后开始访问栈顶的元素, 如果处于访问状态, 就要依次把该节点的左孩子, 右孩子, 该节点压入栈
                     其中左孩子和右孩子为访问状态, 当访问完这两个节点的时候应该打印当前节点, 所以当前节点应该设为
                     打印状态
                     访问左孩子, 左孩子处于访问状态, 那么重复上述操作即可     
         */
        if (root == null) {
            return;
        }
        
        class State {
            boolean state;
            BinarySearchTree<Integer>.Node node;

            public State(boolean state, BinarySearchTree<Integer>.Node node) {
                this.state = state;
                this.node = node;
            }
        }
        
        Stack<State> stack = new Stack<State>();
        stack.push(new State(unprintState, root));
        
        while (!stack.isEmpty()) {
            State peek = stack.pop();
            boolean state = peek.state;
            BinarySearchTree<Integer>.Node curNode = peek.node;
            
            if (state == printState) {
                list.add(curNode.data);
            } else {
                stack.push(new State(printState, curNode));
                
                if (curNode.right != null) {
                    stack.push(new State(unprintState, curNode.right));
                }
                
                if (curNode.left != null) {
                    stack.push(new State(unprintState, curNode.left));
                }
            }
        }
    }
}











