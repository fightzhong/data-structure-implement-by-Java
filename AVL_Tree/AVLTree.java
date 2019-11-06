package AVL_Tree;

import java.util.ArrayList;

/*
 * AVL树(平衡二叉树)是基于本包下的BinarySearchTreeMap类进行改进的
 * 平衡二叉树: 指的是对于树中的每一个节点, 其左右子树的最大高度差不能超过1
 * 实现思路:
 * 		- 首先我们需要为每一个节点维护一个高度值, 对于这个高度值来其说, 其初始值均为1, 一旦左边有了一个元素, 那么其最大高度就变成2
 * 			所以应该在添加元素的时候来维护这个高度值, 当前元素的高度等于左右子树中最大的那个 加1
 * 
 * 		- 其次我们需要一个辅助函数来获取平衡因子, 它的值等于左右两棵子树的高度差, 而我们判断一棵树是否是平衡二叉树, 就是根据这个平衡因子来看的,
 * 			如果左右子树的高度差大于了1就说明不是平衡二叉树了		 
 * 			辅助函数的实现, 其实就是左右子树高度差, 这里不取绝对值的原因是: 判断添加的元素是在左边还是在右边添加的需要用到该值的正负来判断
 */ 


public class AVLTree<K extends Comparable<K>, V> implements Map<K, V>{
	private class Node {
		K key;
		V value;
		int height;
		Node left;
		Node right;
		
		public Node (K key, V value) {
			this.key = key;
			this.value = value;
			this.height = 1;
			this.left = null;
			this.right = null;
		}
		
		@Override 
		public String toString () {
			return key.toString() + ": " + value;
		}
	}
	
	private int size;
	private Node root;
	
//	@Override
	public void add(K key, V value) {
		if (key == null || value == null)
			throw new IllegalArgumentException("key and value doesn't allow to be null!");
		root = add(root, key, value);
	}
	
	private Node add (Node curNode, K key, V value) {
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

		// 上面的操作都是为了在该节点的子树下添加节点, 所以添加完了后应该在这里维护该节点的高度值
		curNode.height = 1 + Math.max(getHeight(curNode.left), getHeight(curNode.right));
		// 当高度值改变后, 就要开始判断以curNode为根节点的树是否满足平衡二叉树的性质, 不满足则要进行调整
		int balanceFactor = getBalanceFactor(curNode);
			/*
				 前话: 任何一棵树在开始的时候都是平衡二叉树, 当我们在添加元素的时候就会破坏这种平衡性,
				              而我们不停的维护平衡性, 就说明这棵树会不停的成为平衡二叉树, 同时我们对这个平衡性的
				              因为每次只添加一个元素, 我们的二叉树就会只可能出现左右子树高度差为2的情况, 此时也会被维护成平衡二叉树
			*/
			/*
			 			20									20									 15					
		           /   \      添加一个11		  /   \ 		维护平衡性		   /   \
			 		 15	25    -------->		 15	25   --------->		 10	20 
			 		/  \								/  \								/     / \
			 	  10  18							  10   18						  9    18 25
			 		  								 /
			 		  								9
			 	关于getBalanceFactor(curNode.left) > 0的情况, 在添加元素的时候是不会出现 等于0的结果的
			 	而在删除的时候可能会出现等于0的结果	  								
			 */
			// 情况一(LL): 左子树的高度  > 右子树的高度, 同时对于左孩子作为根节点, 其左边的高度大于右边的高度, 说明添加的元素在最左边, 即上图的情况
			if (balanceFactor > 1 && getBalanceFactor(curNode.left) > 0) 
				return rightRotate(curNode);
			
			// 情况二(RR): 右子树的高度  > 左子树的高度, 同时对于右孩子作为根节点, 其右边的高度大于左边的高度, 说明添加的元素在最右边, 即上图的情况
			if (balanceFactor < -1 && getBalanceFactor(curNode.right) < 0) 
				return leftRotate(curNode);
			
			// 情况三(LR): 左子树的高度  > 右子树的高度, 同时对于左孩子作为根节点, 其右边的高度大于左边的高度, 说明添加的元素在右边, 即上图的情况
			if (balanceFactor > 1 && getBalanceFactor(curNode.left) < 0) {
				//	LR -> LL -> 平衡二叉树
				curNode.left = leftRotate(curNode.left);
				return rightRotate(curNode);
			}
			
			// 情况二(RL): 右子树的高度  > 左子树的高度, 同时对于右孩子作为根节点, 其左边的高度大于右边的高度, 说明添加的元素在左边, 即上图的情况
			if (balanceFactor < -1 && getBalanceFactor(curNode.right) > 0) {
				// RL -> RR -> 平衡二叉树
				curNode.right = rightRotate(curNode.right);
				return leftRotate(curNode);
			}
			
		return curNode;
	}
	
	// 对当前节点进行右旋转
	private Node rightRotate (Node curNode) {
		Node leftChild = curNode.left; // curNode的左孩子
		Node rightOfLeftChild = leftChild.right; // 保留leftChild的右边
		leftChild.right = curNode; // 将leftChild指向curNode, 然后再将leftChild之前保留下来的节点指向curNode的左边
		curNode.left = rightOfLeftChild;
		
		// 维护高度
		curNode.height = Math.max(getHeight(curNode.left), getHeight(curNode.right)) + 1;
		leftChild.height = Math.max(getHeight(leftChild.left), getHeight(leftChild.right)) + 1;
		return leftChild;
	}
	
	// 对当前节点进行左旋转
	private Node leftRotate (Node curNode) {
		Node rightChild = curNode.right;
		Node leftOfRightChild = rightChild.left;
		rightChild.left = curNode;
		curNode.right = leftOfRightChild;
		
		// 维护高度
		curNode.height = Math.max(getHeight(curNode.left), getHeight(curNode.right)) + 1;
		rightChild.height = Math.max(getHeight(rightChild.left), getHeight(rightChild.right)) + 1;
		return rightChild;
	}
	
	// 获取一个节点的高度, 等于其左右子树最大高度加一
	private int getHeight (Node node) {
		if (node == null) 
			return 0;
		
		return node.height;
	}
	
	// 获取以node左右子树的平衡因子: 左子树 - 右子树
	private int getBalanceFactor (Node node) {
		if (node == null)
			return 0;
		return getHeight(node.left) - getHeight(node.right);
	}

	// 判断整棵二分搜索树是否是平衡二叉树
	public boolean isAVLTree () {
		return isAVLTree(root);
	}
	
	// 判断整棵二分搜索树是否是平衡二叉树, 递归算法
	private boolean isAVLTree (Node curNode) {
		if (curNode == null) {
			return true;
		}
		
		// 判断以curNode为根的树是否是平衡二叉树, 如果不是, 则返回false, 如果是则判断其左右子树是否是平衡二叉树
		int balanceFactor = getBalanceFactor(curNode); 
		if (Math.abs(balanceFactor) > 1) 
			return false;
		return isAVLTree(curNode.left) && isAVLTree(curNode.right);
	}
	
	// 判断整棵树是否是二分搜索树: 利用二分搜索树的中序遍历是从小到大的有序值
	public boolean isBST () {
		ArrayList<K> arr = new ArrayList<>();
		inOrder(root, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i - 1).compareTo(arr.get(i)) > 0) {
				return false;
			}
		}
		return true;
	}
	
	// 基于一定情况的中序遍历
	private void inOrder (Node node, ArrayList<K> arr) {
		if (node == null) {
			return;
		}
		
		inOrder(node.left, arr);
		arr.add(node.key);
		inOrder(node.right, arr);
	}
	
	@Override
	public V remove(K key) {
		Node removeNode = find(key);
		if (removeNode == null) {
			return null;
		}
		root = remove(root, key);
		return removeNode.value;
	}
	
	// 删除以curNode为根节点的树种键为key的
	private Node remove(Node curNode, K key) {
		if (key.compareTo(curNode.key) > 0) {
			curNode.right = remove(curNode.right, key);
		} else if (key.compareTo(curNode.key) < 0) {
			curNode.left = remove(curNode.left, key);
		} else { // key.compareTo(curNode.key) == 0
			if (curNode.right == null) {
				Node tempNode = curNode.left;
				curNode.left = null;
				curNode = tempNode;
			} else {
				Node minNode = getMin(curNode.right);
				minNode.right = remove(curNode.right, minNode.key);
				minNode.left = curNode.left;
				curNode.left = curNode.right = null;
				curNode = minNode;
			}
		}
		
		if (curNode != null) {
			curNode.height = 1 + Math.max(getHeight(curNode.left), getHeight(curNode.right));
			// 当高度值改变后, 就要开始判断以curNode为根节点的树是否满足平衡二叉树的性质, 不满足则要进行调整
			int balanceFactor = getBalanceFactor(curNode);
				// 这里之所以要等于0是因为删除了一个元素后可能会出现RR,RL同时存在的情况
				/**
				 *    46
				 *      \
				 *      54
				 *      / \
				 *     50 82
				 *        / \
				 *       56 85
				 * 如该例子, 删除了50后就出现了RR,RL同时存在的情况, 那么getBalanceFactor(curNode.right)
				 * 就等于0了, 此时选择其中RR/RL方案进行调整即可
				 */
				if (balanceFactor > 1 && getBalanceFactor(curNode.left) >= 0) 
					return rightRotate(curNode);
				
				if (balanceFactor < -1 && getBalanceFactor(curNode.right) <= 0) 
					return leftRotate(curNode);
				
				if (balanceFactor > 1 && getBalanceFactor(curNode.left) < 0) {
					curNode.left = leftRotate(curNode.left);
					return rightRotate(curNode);
				}
				
				if (balanceFactor < -1 && getBalanceFactor(curNode.right) > 0) {
					curNode.right = rightRotate(curNode.right);
					return leftRotate(curNode);
				}
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
//	private Node removeMin (Node curNode) {
//		if (curNode.left == null) { // 找到了要删除的元素
//			return curNode.right;
//		} else {
//			curNode.left = removeMin(curNode.left);
//		}
//		return curNode;
//	}

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
