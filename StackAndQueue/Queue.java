package StackAndQueue;

public interface Queue<T> {
	// 入队操作
	public abstract void enqueue (T t);
	// 出队操作
	public abstract T dequeue ();
	// 获取队首的元素
	public abstract T getFront ();
	// 获取队列的大小
	public abstract int getSize ();
	// 判断队列是否为空
	public abstract boolean isEmpty();
}
