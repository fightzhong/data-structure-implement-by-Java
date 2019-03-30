package StackAndQueue;
/*
 * 循环队列:我们需要浪费一个空间, 为了防止数组满的情况出现tail == front;
 * 		数组满的情况: ( tail + 1 ) % 数组的长度 == front
 * 		数组空的情况: tail == front;
 * 
 */


public class LoopQueue<T> implements Queue<T>{
	private T[] data;
	private int front;
	private int tail;
	
	@SuppressWarnings("unchecked")
	public LoopQueue (int capacity) {
		data = (T[]) new Object[capacity + 1];
		front = 0;
		tail = 0;
	}
	
	@SuppressWarnings("unchecked")
	public LoopQueue () {
		data = (T[]) new Object[11];
	}
	
	@Override
	public int getSize () {
		if (tail > front) 
			return tail - front;
		else if (tail < front)
			return data.length - front + tail;
		else 
			return 0;
	}
	
	@Override
	public boolean isEmpty () {
		return tail == front;
	}
	
	@Override 
	public void enqueue (T t) {
		// 满足(tail + 1) % data.length == front 时队列就是满的情况, 需要扩容
		if ((tail + 1) % data.length == front) {
			resize((data.length * 2) - 1);
		}
		
		data[tail] = t;
		tail = (tail + 1) % data.length;
	}
	
	@Override
	public T dequeue () {
		if (tail == front) // 队列为空的情况下不能执行出队操作
			throw new IllegalArgumentException("Queue is empty");
		
		// 出队操作
		T deqeueValue = data[front];
		data[front] = null;
		front = (front + 1) % data.length;
		
		// 元素个数等于1/4时缩容, 并且缩容后的容量不能为0, 如果为0了, 就等于数组是空的
		if (getSize() <= ((data.length - 1)  / 4) && ((data.length - 1)  / 2) > 0) { 
			resize((data.length - 1) / 2);
		}
		
		return deqeueValue;
	}
	
	@SuppressWarnings("unchecked")
	private void resize (int capacity) {
		T[] newData = (T[])new Object[capacity];
		int index = 0;
		for (int i = front; i != tail; i = (i + 1) % data.length) {
			newData[index++] = data[i]; // 将元素一个个放入新的数组中
		}
		data = newData;
		newData = null;
		front = 0;
		tail = index;
	}
	
	@Override
	public T getFront () {
		return data[front];
	}
	
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder();
		str.append("LoopQueue: size: " + getSize() + ",   capacity: " + (data.length - 1) + "  front["); 
		
		for (int i = front; i != tail; i = (i + 1) % data.length) {
			str.append(data[i]);
			if ((i + 1) % data.length != tail) {  // 下一个不是tail
				str.append(", ");
			}
		}
		str.append("]tail");
		return str.toString();
	}
}







