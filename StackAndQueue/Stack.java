package StackAndQueue;

public interface Stack<T> {
	// 入栈
	public abstract void push(T t);
	// 出栈
	public abstract T pop();
	// 获取栈顶的元素
	public abstract T peek();
	// 获取栈中元素个数
	public abstract int getSize();
	// 判断栈中是否为空
	public abstract boolean isEmpty();
}
