package BinarySearchTree;

import java.util.Stack;

import StackAndQueue.ArrayQueue;
import StackAndQueue.Queue;

public class BinarySearchTree<T extends Comparable<T>> {
	private class Node {
		T data;
		Node left;
		Node right;
		
		public Node (T data) {
			this.data = data;
			left = null;
			right = null;
		}
		
		@Override 
		public String toString () {
			return data.toString();
		}
	}
	
	private Node root;
	private int size;
	
	public BinarySearchTree () {
		root = null;
		size = 0;
	}
	
	// 添加元素
	public void add (T t) {
		root = add(root, t);
	}
	
	// 在当前子树上添加元素, 并将添加后的树返回
	private Node add (Node curNode, T t) {
		if (curNode == null) {
			size++;
			return new Node(t);
		}
		
		if (t.compareTo(curNode.data) > 0) {
			curNode.right = add(curNode.right, t);
		} else if (t.compareTo(curNode.data) < 0) {
			curNode.left = add(curNode.left, t);
		}
		return curNode;
	}
	
	// 前序遍历, 先遍历当前节点, 再遍历左节点, 最后遍历右节点
	public void preOrder () {
		preOrder(root);
	}
	
	private void preOrder (Node curNode) {
		if (curNode == null) {
			return;
		}
		
		System.out.println(curNode.data);
		preOrder(curNode.left);
		preOrder(curNode.right);
	}
	
	// 中序遍历, 先遍历左节点, 再遍历当前节点, 最后遍历右节点, 结果为从小到大排序
	public void inOrder () {
		inOrder(root);
	}
	
	private void inOrder (Node curNode) {
		if (curNode == null) {
			return;
		}
		
		inOrder(curNode.left);
		System.out.println(curNode.data);
		inOrder(curNode.right);
	}
	
	// 后序遍历, 先遍历左节点, 再遍历右节点, 最后遍历当前节点, 回收对象机制, 先将当前节点的所有引用都删除, 再删除当前对象
	public void postOrder () {
		postOrder(root);
	}
	
	private void postOrder (Node curNode) {
		if (curNode == null) {
			return;
		}
		postOrder(curNode.left);
		postOrder(curNode.right);
		System.out.println(curNode.data);
	}
	
	// 前序遍历非递归写法
	public void preOrderNotRecursive () {
		if (isEmpty()) {
			throw new IllegalArgumentException("bst is empty!");
		}
		
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		
		while (!stack.isEmpty()) {
			Node popNode = stack.pop();
			System.out.println(popNode.data);
			if (popNode.right != null) 
				stack.push(popNode.right);
			if (popNode.left != null)
				stack.push(popNode.left);
		}
	}
	
	// 查找最小元素
//	public T getMin () {
//		if (isEmpty()) {
//			throw new IllegalArgumentException("bst is empty!");
//		}
//		
//		Node curNode = root;
//		while (curNode != null && curNode.left != null) {
//			curNode = curNode.left;
//		}
//		
//		return curNode.data;
//	}
	
	// 寻找最小的元素
	public Node getMin () {
		if (isEmpty())
			throw new IllegalArgumentException("bst is empty!");
		return getMin(root);
	}
	
	private Node getMin (Node curNode) {
		if (curNode.left == null) {
			return curNode;
		}
		return getMin(curNode.left);
	}
	
	// 删除最小元素
	public void delMin () {
		root = delMin(root);
	}
	
	// 删除以node为根节点的最小节点, 并返回删除后的树
	private Node delMin (Node node) {
		if (node == null) 
			throw new IllegalArgumentException("node is not exist!");
		
		if (node.left == null) { // 如果其left为空, 说明该node为最小节点, 那么此时将其右边的子树返回即可
			Node returnNode = node.right;
			return returnNode;
		}
		node.left = delMin(node.left);
		return node;
	}
	
	public void remove (T t) {
		root = remove(root, t);
	}
	
	public Node remove(Node curNode, T t) {
		if (curNode == null) { // 如果找到最后发现该元素不在树中, 则返回null给上一层接收就好了
			return null;
		}
		
		if (t.compareTo(curNode.data) == 0) { // 找到了要删除的元素 
			if (curNode.right != null) {
				// 获取其右边最小的元素
				Node minNode = getMin(curNode.right);
				// 删除该最小元素在右边的引用
				delMin(curNode.right);
				
				minNode.left = curNode.left;
				minNode.right = curNode.right;
				size--;
				return minNode;
			}
			size--;
			return curNode.left;
		} else if (t.compareTo(curNode.data) > 0) {
			curNode.right = remove(curNode.right, t);
		} else {
			curNode.left = remove(curNode.left, t);
		}
		
		return curNode;
	}
	
	// 是否包含该元素
	public boolean contains (T t) {
		return contains(root, t);
	}
	
	private boolean contains (Node curNode, T t) {
		if (curNode == null)
			return false;
		
		if (t.compareTo(curNode.data) == 0) {
			return true;
		} else if (t.compareTo(curNode.data) > 0) {
			return contains(curNode.right, t);
		} else { // (t.compareTo(curNode.data) < 0)
			return contains(curNode.left, t);
		}
	}
	
	// 层序遍历
	public void levelOrder () {
		if (isEmpty())
			throw new IllegalArgumentException("bst is empty!");
		Queue<Node> queue = new ArrayQueue<>();
		queue.enqueue(root);
		
		while (!queue.isEmpty()) {
			Node dequeueNode = queue.dequeue();
			System.out.println(dequeueNode.data);
			if (dequeueNode.left != null)
				queue.enqueue(dequeueNode.left);
			
			if (dequeueNode.right != null)
				queue.enqueue(dequeueNode.right);
		}
	}
	
	// 生成二叉树
	@Override
	public String toString () {
		return generateString();
	}
	
	private int getDepth (Node node, T t, int depth) {
		if (t.compareTo(node.data) == 0) {
			return depth;
		} else if (t.compareTo(node.data) > 0) {
			return getDepth(node.right, t, ++depth);
		} else { // t.compareTo(node.data) < 0
			return getDepth(node.left, t, ++depth);
		}
	}
	
	private String generateDash (int depth) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < depth; i++)
			str.append("--");
		return str.toString();
	}
	
	// 利用层序遍历生成二叉树的直观表示	
//	private String generateString () {
//		if (isEmpty())
//			return "empty tree";
//		StringBuilder str = new StringBuilder();
//		Queue<Node> queue = new ArrayQueue<>();
//		queue.enqueue(root);
//		while (!queue.isEmpty()) {
//			Node dequeueNode = queue.dequeue();
//			String strData = generateDash(getDepth(root, dequeueNode.data, 0)) + dequeueNode.data + "\n";
//			if (dequeueNode.left != null)
//				queue.enqueue(dequeueNode.left);
//			str.append(strData);
//			if (dequeueNode.right != null)
//				queue.enqueue(dequeueNode.right);
//		}
//		return str.toString();
//	}
	
	// 利用前序遍历生成二叉树的直观表示
	/*
		10
		--7
		----6
		----8
		--20
		----15
		----30
	 */
	private String generateString () {
		if (isEmpty())
			return "empty tree";
		StringBuilder str = new StringBuilder();
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node popStack = stack.pop();
			String strData = generateDash(getDepth(root, popStack.data, 0)) + popStack.data + "\n";
			str.append(strData);
			if (popStack.right != null)
				stack.push(popStack.right);
			if (popStack.left != null)
				stack.push(popStack.left);
		}
		return str.toString();
	}
	
	
	
	public boolean isEmpty () {
		return size == 0;
	}
	
	public int getSize () {
		return size;
	}
	
}
