package LinkedList;

/*
 * 	时间复杂度分析:
 * 		- add() 			O(n)
 * 		- addFirst()		O(1)
 * 		- addLast()			O(n)
 * 									--> O(n)
 * 		- remove() 			O(n)
 * 		- removeFirst()		O(1)
 * 		- removeLast()		O(n)
 * 									--> O(n)
 * 		- set() 			O(n)
 * 		- setFirst()		O(1)
 * 		- setLast()			O(n)
 * 									--> O(n)
 * 		- get() 			O(n)
 * 		- getFirst()		O(1)
 * 		- getLast()			O(n)
 * 									--> O(n)
 * 		通过下面对链表的实现, 我们发现对链表的增删改查的时间复杂度为O(n), 这样是极为耗费性能的
 * 		我们可以通过添加一个尾指针的方式使得对链表的首位操作的时间复杂度变为O(1), 并且我们一般也是这样操作的
 * 	
 */
public class LinkedList<T> {
	private class Node {
		T data;
		Node next;
		
		public Node (T t, Node next) {
			data = t;
			this.next = next;
		}
		
		@Override
		public String toString () {
			return data.toString();
		}
	}
	
	private Node dummyHead;
	private int size;
	
	public LinkedList () {
		dummyHead = new Node(null, null);
		size = 0;
	}
	
	public int getSize () {
		return size;
	}
	
	public boolean isEmpty () {
		return size == 0;
	}
	
	public void add (int index, T t) {
		if (index < 0 || index > size)
			throw new IllegalArgumentException("illegal index of " + index);
		
		Node preNode = dummyHead;
		for (int i = 0; i < index; i++) 
			preNode = preNode.next;
//		Node newNode = new Node(t, preNode.next);
//		preNode.next = newNode;
		preNode.next = new Node(t, preNode.next);
		size++;
	}
	
	public void addFirst (T t) {
		add(0, t);
	}
	
	public void addLast (T t) {
		add(size, t);
	}
	
	public T remove (int index) {
		if (isEmpty()) 
			throw new IllegalArgumentException("linkedList is empty!");
		
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("illegal index of " + index);
		
		Node preNode = dummyHead;
		for (int i = 0; i < index; i++) 
			preNode = preNode.next;
		Node curNode = preNode.next;
		
		preNode.next = curNode.next;
		curNode.next = null;
		size--;
		
		return curNode.data;
	}
	
	public T removeFirst () {
		return remove(0);
	}
	
	public T removeLast () {
		return remove(size - 1);
	}
	
	public void set (int index, T t) {
		if (isEmpty()) 
			throw new IllegalArgumentException("linkedList is empty!");
		
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("illegal index of " + index);
		
		Node curNode = dummyHead.next;
		for (int i = 0; i < index; i++) 
			curNode = curNode.next;
		
		curNode.data = t;
	}
	
	public T get (int index) {
		if (isEmpty()) 
			throw new IllegalArgumentException("linkedList is empty!");
		
		if (index < 0 || index >= size)
			throw new IllegalArgumentException("illegal index of " + index);
		
		Node curNode = dummyHead.next;
		for (int i = 0; i < index; i++) 
			curNode = curNode.next;
		
		return curNode.data;
	}
	
	public T getFirst () {
		return get(0);
	}
	
	public T getLast () {
		return get(size - 1);
	}
	
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder();
		str.append("LinkedList: top[");
		
		Node curNode = dummyHead.next;
		while (curNode != null) {
			str.append(curNode + " -> ");
			curNode = curNode.next;
		}
		
		str.append("Null] size: " + size);
		return str.toString();
	}
	
}
