package RedBlackTree;

import AVL_Tree.Map;

/*
 	2-3树: 是一棵绝对平衡的树, 从任意一个节点到叶子节点经过的节点个数相同
 	特点: 2-3树的节点分为两种, 一种是2节点, 一种是3节点, 2节点跟二叉树的节点是一样的, 都是一个节点对应两个孩子节点
 			而3节点则是一个节点里面存在着两个节点, 同时其指向了三个孩子节点, 2-3树在添加元素的时候, 不能向空节点添加元素
 			只能在叶子节点进行合并, 如果叶子节点是个2节点, 那么就直接合并成三节点, 如果叶子节点是个3节点, 那么先将该节点
 			合并到3节点上, 形成一个4节点, 然后4节点进行拆分成一个二叉树, 最后将二叉树的根节点往上进行合并
 			
 	红黑树: 红黑树跟2-3树是等价的, 红黑树对于一个3节点, 采用的不是一个节点位置存放两个节点的思路, 而是将小的节点用红色
 			  去表示, 大的节点用黑色去表示, 然后以二分搜索树的形式表现出来, 对于红黑树来说, 不是一棵平衡二叉树, 而是一个
 			  "黑平衡"二叉树, 因为从任意一个同深度的节点到叶子节点, 经过的黑色节点的个数是相同的, 而在2-3树中, 则是经过的节点数
 			  相同, 其实这两者是等价的
 			  
 	红黑树的设计:
 			<1> 为了能够表示红黑两种颜色的节点, 我们需要在节点中维护一个color变量
 			<2> 一般规定红黑树中表示3节点的两个节点, 左边的(即小的节点)的颜色为红色, 右边的为黑色,	
 				并且小的节点作为大节点的子节点进行表示
 			<3> 我们添加一个节点的时候, 这个节点默认是红色的	
 	
 	红黑树的特性:
 			<1> 每一个节点要么是红色要么是黑色
 			<2> 根节点一定是黑色
 						用2-3树来表示的时候, 如果根节点是2节点, 那么它一定是黑色, 如果根节点是3节点, 那么红色的节点在红黑树中
 						是指左边的那个节点, 大的节点作为根节点, 所以小的节点是子节点, 为红色, 大的节点为根节点, 为黑色
 			<3> 如果一个节点为红色, 那么它的孩子节点一定是黑色的, 因为红色的节点必须作为黑色节点的孩子
 			<4> 从相同深度的任意一个节点出发, 到达叶子节点经过的黑色节点的个数是一样的
 			<5> 每一个叶子节点(这里叶子节点指的是空节点)是黑色的
 			
 	向红黑树中添加节点:
 			<1> 我们需要保证添加完节点后根节点是黑色的, 即 root.color = BLACK;
 			<2> 添加节点有两种情况:
 						1、向2节点中添加节点(2节点一定是黑色的)
 								- 向左边添加, 直接添加即可, 新增的节点必然是红色, 所以转换为2-3树的思想, 应该直接合并即可, 添加后是满足了红黑树性质的
 								- 向右边添加, 首先父亲节点是黑色的, 那么右孩子为新增的节点, 则该节点是红色的, 不满足红黑树的定义, 需要进行左旋转
 											18      add(20)  		18					左旋转  		 20
 										 /      	---------->   /   \			----------->	/
 										T1							 T1   20(红)						  18
 																										  /
 																										  T1
 						
 						2、向3节点中添加节点
 								- 向右边添加, 新增元素是红色, 不满足红黑树性质, 放在2-3树中来说, 3节点变为了临时4节点, 此时应该对其进行
 									分化成三个2节点, 即变成一棵二叉树, 然后再将这个二叉树的根向上去融合, 用红黑树的方式则是两个孩子节点变为
 									2节点, 则颜色变为黑色, 然后由于根节点需要向上进行融合, 而融合的节点应该是红色的, 所以我们需要将这个根节点设置为红色
 									这个过程也叫  ---> 颜色的反转
														黑								黑									红
													  / 		 向右添加			  /  \		   颜色反转		  /  \
													 红			---------> 		 红	   红		---------->     黑    黑
													/ \ 							/ \								/  \
												  T1 T2						  T1 T2							  T1  T2
 								- 向左边添加, 新增的元素在左边, 在2-3树中其应该是左右两个节点依托在中间节点中, 然后拆分为三个2节点, 最后将根2节点向上融合
 									在红黑树中, 则应该进行右旋转形成一棵二叉树, 然后将两个依托在父亲节点的元素设置为红色, 因为红色是依托在父亲节点的, 然后
 									父亲节点应该为原来元素的颜色, 就形成了上面的情况(红黑红), 此时进行颜色的反转, 红变黑, 黑变红继续向上融合
 								
														黑								黑							 	红1									黑1(红1)					红
													  /  \	  向左添加			  /  \		   右旋转		  /  \		 维持2-3树的性质	  /   \  	颜色反转	  /  \
									情况一:		 红1	T2	 ---------> 	红1	  T2		---------->  红2    黑        --------------> 红2  红		--------> 黑    黑
													  \ 							/ \							 	   / \							  / \						  / \
												     T1						   红2 T1							     T1 T2							 T1 T2					 T1 T2
		 									
														黑								黑								黑						 	红2							   黑2(红2)					      红
													  /  \	  向左添加			  /  \		   左旋转		  /  \		    右旋转      /     \	维持2-3树的性质	  /   \  	颜色反转	  	  /  \
									情况二:		 红1	T2	 ---------> 	红1	  T2		---------->  红2  T2     -------->红1    黑    -------------------->  红1     红		--------> 黑   黑
													/   							/ \							 / \	   	          /	\  / \					  / \  / \				    / \  / \
												  T1  						  T1 红2 							红1 T4   	            T1 T3 T4 T2					 T1 T3 T4 T2				T1 T3 T4 T2
		 																			  / \							/ \
		 																			 T3 T4					  T1 T3
 
 */
public class RedBlackTree<K extends Comparable<K>, V> implements Map<K, V>{
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
		
		// 我们所有的判断都基于一个黑色节点进行判断
		if (curNode.color == BLACK) {
			
			if (curNode.right != null && curNode.right.color == RED) { // 右边颜色为红色, 此时需要判断是2节点右边还是3节点右边

				if (curNode.left != null && curNode.left.color == RED) { // 3节点右
					return flipColor(curNode);
				} else {
					return leftRotate(curNode);
				}
				
			}
			
			if (curNode.left != null && curNode.left.color == RED) {
				if (curNode.left.left != null && curNode.left.left.color == RED) { 	// 3节点左左
					return rightRotate(curNode);
				}
				
				if (curNode.left.right != null && curNode.left.right.color == RED) {	// 3节点左右
					return lastCondition(curNode);
				}
			}
			
		}
		
		return curNode;
	}
	
	/*
	 左旋转: 2节点向右添加元素
		   node                x
		  /   \     左旋转    /  \
		 T1   x   ---------> node  T3
		     / \             /   \
		    T2 T3           T1   T2
	*/  
	private Node leftRotate (Node node) {
		Node x = node.right;
		
		// 左旋转
	   /*
	    	不能是node.right = null; 因为我们添加后的元素是需要向上回溯的
	    	 所以这里可能会出现右边的节点为红色并且其还有子节点的情况
	    */
		node.right = x.left;
		x.left = node;
		
		// 维护颜色
		x.color = BLACK; 
		node.color = RED;
		
		return x;
	}
	
	// 颜色反转: 3节点向右添加元素
	private Node flipColor (Node node) {
		node.left.color = node.right.color = BLACK;
		node.color = RED;
		
		return node;
	}
	
	/*
	 右旋转: 3节点向左左边添加元素
			   node               x
			  /   \     右旋转   /  \
			 x    T2   -------> y   node
			/ \                     /  \
			y  T1                   T1  T2
	*/ 
	private Node rightRotate (Node node) {
		Node x = node.left;
		
		// 右旋转
		node.left = x.right;
		x.right = node;
		
		// 维护颜色
		x.left.color = x.right.color = BLACK;
		x.color = RED;
		
		return x;
	}
	
	/*
	 	最后一种情况: 3节点向左边的右边添加元素, y为新增元素, 此时x, y都是红色的
				 node               	 node								y
				/   \     对X左旋转      /      \		右旋转情况		  / \
				x    T2   ---------> y    T2 ----------->  	 x   node
			  / \                  / \ 							/ \  /  \
			 T1  y                x  T4						  T1 T3 T4 T2
	  			 / \					/ \
	  			T3 T4				  T1 T3
	  
	 */
	private Node lastCondition (Node node) {
		Node x = node.left;
		Node y = x.right;
		
		x.right = y.left;
		y.left = x;
		node.left = y;
		
		return rightRotate(node);
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

