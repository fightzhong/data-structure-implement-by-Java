package LinkedList;
// 通过对链表添加一个尾指针, 可以使得对链表首尾的增删改查的时间复杂度为O(1);

public class LinkedListWithTailPointer<T> {
	private class Node {
		private T t;
		private Node next;
		
		public Node (T t, Node node) {
			this.t = t;
			this.next = node;
		}
		
		public T getT () {
			return t;
		}
		
		@Override
		public String toString () {
			return t.toString();
		}
	}

	private Node dummyHead;
	private Node tail;
	private int size;	
	
	public LinkedListWithTailPointer () {
		this.tail = null;
		this.dummyHead = new Node(null, tail);
		size = 0;
	}

	// 添加元素
	public void add (int index, T t) {
		
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index < 0 and index > size are illegal!");
		}
		
		Node preNode = dummyHead;
		for (int i = 0; i < index - 1; i++) {
			preNode = preNode.next;
		}
		
		preNode.next = new Node(t, preNode.next);
		if (index == size) {
			tail = preNode.next;
		}
		size++;
	}
	
	public void addFirst (T t) {
		add(0, t);
	}

	public void addLast (T t) {
		tail.next = new Node(t, tail.next);
	}
	
	// 删除元素
	public T remove (int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index < 0 and index >= size are illegal!!");
		}
		
		if (isEmpty()) {
			throw new IllegalArgumentException("LinkedList is empty!");
		}
		
		Node preNode = dummyHead;
		for (int i = 0; i < index - 1; i++) {
			preNode = preNode.next;
		}
		Node curNode = preNode.next;
		preNode.next = curNode.next;
		curNode.next = null;
		
		return curNode.getT();
	}
	
	public int getSize () {
		return size;
	}
	
	public boolean isEmpty () {
		return size == 0;
	}
}
