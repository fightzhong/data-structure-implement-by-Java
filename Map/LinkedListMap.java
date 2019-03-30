package Map;
// 以链表为底层实现的Map映射, 可添加重复元素
public class LinkedListMap<K, V> implements Map<K, V> {
	private class Node {
		public K key;
		public V value;
		public Node next;
		
		public Node (K key, V value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
//		@Override
//		public String toString () {
//			return value.toString();
//		}
	}

	private Node head;
	private int size;
	
	public LinkedListMap () {
		this.head = null;
		this.size = 0;
	}
	
	@Override
	public void add (K key, V value) {
		head = new Node(key, value, head);
		size++;
	}

	@Override
	public V remove (K key) {
		Node delNode = remove(head, key);
		return delNode == null ? null : delNode.value;
	}
	
	// 删除以curNode为头节点的链表中键为key的
	private Node remove (Node curNode, K key) {
		if (curNode == null) // 递归到底也没有找到该键, 则返回null
			return null;
		
		if (key.equals(curNode.key)) {
			Node node = curNode.next;
			curNode.next = null;
			return node;
		}
		
		return remove(curNode.next, key);
	}
	
	private Node getNode (Node curNode, K key) {
		if (curNode == null)
			return null;
		
		if (key.equals(curNode.key))
			return curNode;
		
		return getNode(curNode.next, key);
	}
	
	@Override
	public void set (K key, V newVaue) {
		Node node = getNode(head, key);
		if (node == null)
			throw new IllegalArgumentException("key of " + key + " is not existed");
		
		node.value = newVaue;
	}
	
	@Override
	public V get (K key) {
		Node node = getNode(head, key);
		return node == null ? null : node.value;
	}
	
	
	@Override
	public boolean contains (K key) {
		Node node = getNode(head, key);
		return node == null ? false : true;
	}
	
	@Override 
	public int getSize () {
		return size;
	}
	
	@Override
	public boolean isEmpty () {
		return size == 0;
	}
	
	@Override 
	public String toString () {
		return "";
	}
}


