package AVL_Tree;

// 基于二分搜索树实现的Map映射
public class BinarySearchTreeMap<K extends Comparable<K>, V> implements Map<K, V>{
	private class Node {
		K key;
		V value;
		Node left;
		Node right;
		
		public Node (K key, V value) {
			this.key = key;
			this.value = value;
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
	
	@Override
	public void add(K key, V value) {
		if (key == null || value == null)
			throw new IllegalArgumentException("key and value doesn't allow to be null!");
		root = add(root, key, value);
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
		return curNode;
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
