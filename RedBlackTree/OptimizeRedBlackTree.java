package RedBlackTree;

import AVL_Tree.Map;
/*
 * 	上一个版本的红黑树的添加逻辑由于是自己编写的, 逻辑过于复杂, 其实是可以简化的
 * 	简化过程:
 * 			一				二				三					     四							五
 * 
 * 			黑     			黑				黑					 黑2(红2)				           红2
 * 		  /	------>  /	 ----->  /     ------>  /    \    ----->     /       \ 
 * 		  红			   红1			   红2				    红1    红				黑1(红1)    黑(红)
 * 						   \			  /
 * 						     红2			 红1	
 *  	分析:
 *  			- 2节点左边添加: 则是情况一, 做法是直接返回当前节点
 * 			- 2节点右边添加: 则是情况二, 因为右边是红色, 此时不必去考虑当前的颜色, 只要右边是红色, 我们就执行左旋转操作, 
 * 									同时左旋转操作中需要维持颜色的时候子节点变到当前节点位置的时候不能赋予黑色了(上一个版本是变成黑色), 而应该
 * 									变成当前节点的原来的颜色
 * 			- 3节点左边的右边添加: 则是情况二, 需要变成情况三, 再变成情况四, 再变成情况五, 然后返回红2
 * 			- 3节点左边的左边添加: 则是情况三, 需要变成情况四, 再变成情况五, 然后返回红2
 * 			- 3节点右边添加: 则是情况四, 需要变成情况五, 然后返回红2
 * 
 * 	所以我们只需要每一次都判断这三个情况就可以了
 * 
 * 
 * 
 */
public class OptimizeRedBlackTree<K extends Comparable<K>, V> implements Map<K, V> {
	private final boolean RED = true;
	private final boolean BLACK = false;
	
	private class Node {
		K key;
		V value;
		Node left;
		Node right;
		boolean color;
		
		public Node (K key, V value) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
			this.color = RED;
		}
	}
	
	private int size;
	private Node root;
	
	// 向红黑树中添加元素
	@Override
	public void add(K key, V value) {
		if (key == null || value == null)
			throw new IllegalArgumentException("key and value doesn't allow to be null!");
		root = add(root, key, value);
		// 红黑树定义: 我们需要保证添加完节点后根节点是黑色的
		root.color = BLACK;
	}
	
	public Node add (Node curNode, K key, V value) {
		if (curNode == null) {
			size++;
			return new Node(key, value);
		}
		
		if (key.compareTo(curNode.key) > 0) {
			curNode.right = add(curNode.right, key, value);
		} else if (key.compareTo(curNode.key) < 0) {
			curNode.left = add(curNode.left, key, value);
		} else { // key.compareTo(curNode.key) == 0
			curNode.value = value;
		}
		
		// 在此次优化中, 不必去考虑当前节点的颜色了
		/*
		 		一				二				三					     四							五
	  
	  			黑     			黑				黑					 黑2(红2)				           红2
	  		  /	------>  /	 ----->  /     ------>  /    \    ----->     /       \ 
	  		  红			   红1			   红2				    红1    红				黑1(红1)    黑(红)
	  						   \			  /
	  						     红2			 红1	
	  */
		if (isRed(curNode.right) && !isRed(curNode.left)) {
			curNode = leftRotate(curNode);
		}
		
		if (isRed(curNode.left) && curNode.left.left != null && isRed(curNode.left.left)) {
			curNode = rightRotate(curNode);
		}
		
		if (isRed(curNode.left) && isRed(curNode.right)) {
			curNode = flipColor(curNode);
		}
		
		return curNode;
	}
	
	private Node leftRotate (Node node) {
		Node x = node.right;
		
		
		node.right = x.left;
		x.left = node;
		
		x.color = node.color; 
		node.color = RED;
		
		return x;
	}
	
	private Node flipColor (Node node) {
		node.left.color = node.right.color = BLACK;
		node.color = RED;
		
		return node;
	}
	
	private Node rightRotate (Node node) {
		Node x = node.left;
		
		node.left = x.right;
		x.right = node;
		
		x.color = BLACK;			
		node.color = RED;
		
		return x;
	}
	
	private boolean isRed (Node node) {
		if (node == null)
			return BLACK;
		return node.color;
	}
	
	@Override
	public V remove(K key) {
		root = remove(root, key);
		return null;
	}
	
	private Node remove(Node curNode, K key) {
		if (curNode == null) {
			throw new IllegalArgumentException("key doesn't exist!!");
		}
		
		if (key.compareTo(curNode.key) > 0) {
			curNode.right = remove(curNode.right, key);
		} else if (key.compareTo(curNode.key) < 0) {
			curNode.left = remove(curNode.left, key);
		} else { // key.compareTo(curNode.key) == 0
			if (curNode.right == null) {
				size--;
				return curNode.left;
			}
			
			Node rightMinNode = getMin(curNode.right); // 右边最小的元素
			rightMinNode.right = removeMin(curNode.right);
			rightMinNode.left = curNode.left;
			curNode.left = null;
			curNode.right = null;
			size--;
			return rightMinNode;
		}
		return curNode;
	}
	
	private Node getMin (Node curNode) {
		if (curNode == null) {
			throw new IllegalArgumentException("node is null");
		}
		
		while (curNode.left != null) {
			curNode = curNode.left;
		}
		return curNode;
	}
	
	// 返回删除最小节点后新的根节点, 给上一层接收
	private Node removeMin (Node curNode) {
		if (curNode.left == null) { // 找到了要删除的元素
			return curNode.right;
		} else {
			curNode.left = removeMin(curNode.left);
		}
		return curNode;
	}
	
	@Override
	public void set(K key, V value) {
		Node node = find(key);
		if (node == null) 
			throw new IllegalArgumentException("key doesn't exist!");
		node.value = value;
	}
	
	private Node find (K key) {
		Node curNode = root;
		while (curNode != null) {
			if (key.compareTo(curNode.key) > 0) {
				curNode = curNode.right;
			} else if (key.compareTo(curNode.key) < 0) {
				curNode = curNode.left;
			} else { // key.compareTo(curNode.key) == 0
				return curNode;
			}
		}
		return null;
	}
	
	
	@Override
	public V get(K key) {
		return find(key).value;
	}
	
	@Override
	public boolean contains(K key) {
		Node node = find(key);
		return node == null ? false : true;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
}
