package StackAndQueue;

import Array.DynamicArray;

public class ArrayQueue<T> implements Queue<T>{
	private DynamicArray<T> data;
	
	public ArrayQueue (int capacity) {
		data = new DynamicArray<T>(capacity);
	}
	
	public ArrayQueue () {
		data = new DynamicArray<T>();
	}
	
	@Override
	public int getSize () {
		return data.getSize();
	}
	
	@Override
	public boolean isEmpty () {
		return data.getSize() == 0;
	}
	
	@Override 
	public void enqueue (T t) {
		data.addLast(t);
	}
	
	@Override
	public T dequeue () {
		return data.removeFirst();
	}
	
	@Override
	public T getFront () {
		return data.get(0);
	}
	
	@Override 
	public String toString () {
		StringBuilder str = new StringBuilder();
		
		str.append("ArrayQueue: front[");
		for (int i = 0; i < data.getSize(); i++) {
			str.append(data.get(i));
			if (i != data.getSize() - 1) {
				str.append(", ");
			}
		}
		
		str.append("]tail");
		return str.toString();
	}
}
