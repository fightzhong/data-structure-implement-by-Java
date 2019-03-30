package StackAndQueue;
import Array.DynamicArray;
/*
 * 数组栈: 利用动态数组封装数组栈
 */
public class ArrayStack<T> implements Stack<T> {
	private DynamicArray<T> data;
	
	public ArrayStack (int capacity) {
		this.data = new DynamicArray<T>(capacity);
	}
	
	public ArrayStack () {
		this.data = new DynamicArray<T>();
	}
	
	@Override
	public int getSize () {
		return data.getSize();
	}
	
	@Override
	public boolean isEmpty () {
		return data.isEmpty();
	}
	
	@Override
	public void push (T t) {
		data.addLast(t);
	}
	
	@Override
	public T pop () {
		return data.removeLast();
	}
	
	@Override
	public T peek () {
		return data.get(data.getSize() - 1);
	}
	
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder();
		str.append("ArrayStack: [");
		
		for (int i = 0; i < data.getSize(); i++) {
			if (i != data.getSize() - 1) {
				str.append(data.get(i) + ", ");
			} else {
				str.append(data.get(i));
			}
		}
		
		str.append("] top");
		return str.toString();
	}
}
