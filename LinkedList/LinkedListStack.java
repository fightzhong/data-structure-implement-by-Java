package LinkedList;

import StackAndQueue.Stack;
public class LinkedListStack<E> implements Stack<E> {
	LinkedList<E> data = new LinkedList<E>();
	
	public LinkedListStack () {}
	
	@Override
	public boolean isEmpty() {
		return getSize() == 0;
	}
	
	@Override
	public void push (E e) {
		data.addLast(e);
	}
	
	@Override
	public E pop () {
		return data.removeLast();
	}
	
	@Override
	public E peek () {
		return data.getLast();
	}
	
	@Override
	public int getSize () {
		return data.getSize();
	}
	
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder();
		str.append("Stack: [");
		for (int i = 0; i < getSize(); i++) {
			str.append(data.get(i));
			if (i != (getSize() - 1)) {
				str.append(", ");
			}
		}
		
		str.append("]peek size: " + getSize());
		return str.toString();
	}
}
