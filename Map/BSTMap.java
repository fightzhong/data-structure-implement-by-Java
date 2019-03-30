package Map;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {
	private class Node {
		public K key;
		public V value;
		public Node left;
		public Node right;
		
		public Node (K key, V value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
		}
	}
	
	private Node root;
	private int size;

	public BSTMap() {
		root = null;
		size = 0;
	}
	
	@Override
	public void add(K key, V value) {
		root = add(root, key, value);
	}
	
	private Node add (Node curNode, K key, V value) {
		if (curNode == null) {
			size++;
			return new Node(key, value);
		}
		if (key.compareTo(curNode.key) > 0)
			curNode.right = add(curNode.right, key, value);
		else if (key.compareTo(curNode.key) < 0)
			curNode.left = add(curNode.left, key, value);
		
		return curNode;
	}

	@Override
	public V remove(K key) {
		if (contains(key)) {
			Node delNode = remove(root, key);
			size--;
			return delNode.value;
		}
		return null;
	}
	
	private Node remove (Node curNode, K key) {
		if (curNode == null)
			throw new IllegalArgumentException("key of " + key + " is not existed!");
		
		if (key.compareTo(curNode.key) > 0) 
			return remove(curNode.right, key);
		else if (key.compareTo(curNode.key) < 0)
			return remove(curNode.left, key);
		else { // key.compareTo(curNode.key) == 0 找到了要删除的元素
			
			if (curNode.right == null) { // 右边没有元素
				return curNode.left;
			} else { // 右边有元素
				Node minNode = getMinNode(curNode.right);
				minNode.left = curNode.left;
				minNode.right = curNode.right;
				delMinNode(curNode.right); // 切断最小节点右边的联系
				return minNode;
			}
			
		}
	}
	
	// 删除以curNode为根的树中最小的节点, 并将删除后的树返回;
	private Node delMinNode (Node curNode) {
		if (curNode == null)
			throw new NullPointerException();
		if (curNode.left == null) {
			Node rightNode = curNode.right;
			curNode.right = null;
			return rightNode;
		} else {
			curNode.left = delMinNode(curNode.left);
			return curNode;
		}
	}
	
	private Node getNode (Node curNode, K key) {
		if (curNode == null)
			return null;
		if (key.compareTo(curNode.key) > 0)
			return getNode(curNode.right, key);
		else if (key.compareTo(curNode.key) < 0)
			return getNode(curNode.left, key);
		return curNode;
	}
	
	@Override
	public void set(K key, V value) {
		Node node = getNode(root, key);
		if (node == null)
			throw new IllegalArgumentException("key of " + key + " is not existed!");
		node.value = value;
	}

	@Override
	public V get(K key) {
		Node node = getNode(root, key);
		return node == null ? null : node.value;
	}

	@Override
	public boolean contains(K key) {
		Node node = getNode(root, key);
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

	private Node getMinNode (Node curNode) {
		if (isEmpty())
			throw new IllegalArgumentException("Map is empty!");
		while (curNode.left != null)
			curNode = curNode.left;
		return curNode;
	}
}
