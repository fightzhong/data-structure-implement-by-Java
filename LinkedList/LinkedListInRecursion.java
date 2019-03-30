package LinkedList;

// 递归实现链表
public class LinkedListInRecursion<T> {
	private class Node {
		T data;
		Node next;
		
		public Node (T t, Node n) {
			data = t;
			next = n;
		}

		@Override
		public String toString () {
			return data.toString();
		}
	}
	
	private Node head;
	private int size;
	
	public LinkedListInRecursion () {
		head = null;
		size = 0;
	}
	
	public int getSize () {
		return size;
	}
	
	public boolean isEmpty () {
		return size == 0;
	}
	
	// 在指定位置添加元素
	public void add (int index, T t) {
		if (index > size || index < 0) {
			throw new IllegalArgumentException("index < 0 and index >= size are illegal!");
		}
		
		head = addRecursion(0, head, index, t);
		size++;
	}
	
	// 在指定索引下添加元素, 并返回添加元素后的该节点
	private Node addRecursion (int curIndex, Node curNode, int index, T t) {
		// 链表中已经存在节点
		// 递归终止条件
		if (curIndex == index) {
			Node node = new Node(t, curNode);
			return node;
		}
		
		curNode.next = addRecursion(curIndex + 1, curNode.next, index, t); 
		return curNode;
	}
	
	public void addFirst (T t) {
		add(0, t);
	}
	
	public void addLast (T t) {
		add(size, t);
	}
	
	// 删除元素
	public void remove (int index) {
		if (index >= size || index < 0) 
			throw new IllegalArgumentException("index < 0 and index >= size are illegal!");
		
		if (isEmpty())
			throw new IllegalArgumentException("LinkedList is empty!");
		
		head = removeRecursion(0, head, index);
		size--;
	}

	// 删除指定索引的元素, 并将该元素的next返回
	private Node removeRecursion (int curIndex, Node curNode, int index) {
		if (curIndex == index) {
			Node node = curNode.next;
			curNode.next = null;
			return node;
		}
		
		curNode.next = removeRecursion(curIndex + 1, curNode.next, index);
		return curNode;
	}

	public void removeFirst () {
		remove(0);
	}
	
	public void removeLast () {
		remove(size - 1);
	}
	
	// 获取指定索引的值
	public Node get (int index) {
		if (index < 0 || index > size) 
			throw new IllegalArgumentException("element is not exist of index " + index);
		return getRecursion(0, head, index);
	}
	
	// 获取指定索引的元素并返回
	private Node getRecursion (int curIndex, Node curNode, int index) {
		if (curIndex == index) 
			return curNode;
		return getRecursion(curIndex + 1, curNode.next, index);
	}
	
	public Node getFirst () {
		return get(0);
	}
	
	public Node getLast () {
		return get(size - 1);
	}

	public void set (int index, T t) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("element is not exist of index " + index);
		}
		setRecursion(0, head, index, t);
	}
	
	private void setRecursion (int curIndex, Node curNode, int index, T t) {
		if (curIndex == index) {
			curNode.data = t;
		} else {
			setRecursion(curIndex + 1, curNode.next, index, t);
		}
	}
	
	
	public boolean contains (T t) {
		return containsRecursion(head, t); 
	}
	
	private boolean containsRecursion (Node curNode, T t) {
		if (curNode == null)
			return false;
		
		if (curNode.data == t)
			return true;
		
		return containsRecursion(curNode.next, t);
	}
	
	
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder();
		str.append("LinkedList: top[");
		
		Node curNode = head;
		while (curNode != null) {
			str.append(curNode + " -> ");
			curNode = curNode.next;
		}
		
		str.append("Null] size: " + size);
		return str.toString();
	}
}
