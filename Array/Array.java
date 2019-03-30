package Array;
/*
 * 二次封装数组
 */

public class Array<T> {
	private T[] data;
	private int size;
	
	@SuppressWarnings("unchecked")
	public Array (int capacity) {
		data = (T[])new Object[capacity];
		size = 0;
	}
	
	public Array () {
		this(10);
	}
	
	public int getSize () {
		return size;
	}
		
	public int getCapacity () {
		return data.length;
	}
	
	public boolean isEmpty () {
		return size == 0;
	}
	
	// 添加元素的方法
	public void add (int index, T ele) {
		if (data.length == size) { // 如果size指向了length位置, 说明数组已经放满了
			throw new IllegalArgumentException("add failed, Array is full..");
		}
		
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("add failed, index < 0 and index > size is illegal");
		}
		
		for (int i = size - 1; i >= index; i--) {
			data[i + 1] = data[i];	
		}
		
		data[index] = ele;
		size ++;
	}
	
	public void addFirst (T ele) {
		add(0, ele);
	}
	
	public void addLast (T ele) {
		add(size, ele);
	}
	
	// 删除元素的方法
	public T remove (int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("remove failed, index < 0 and index >= size is not available");
		}
		
		T responseValue = data[index];
		
		for (int i = index; i < size - 1; i++) {
			data[i] = data[i + 1];
		}
		
		size --;
		return responseValue;
	}
	
	public T removeFirst () {
		return remove(0);
	}
	
	public T removeLast () {
		return remove(size - 1);
	}
	
	public void removeElement (T ele) {
		int index = find(ele);
		if (index != -1) {
			remove(index);
		}
	}
	
	// 获取指定索引位置的元素
	public T get (int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index < 0 and index >= " + size + "is not available..");
		}
		
		return data[index];
	}

	// 修改指定索引位置的元素
	public void set (int index, T ele) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index < 0 and index >= " + size + "is not available..");
		}
		
		data[index] = ele;
		
	}

	// 查看是否包含该元素
	public boolean contains (T ele) {
		for (T e: data) {
			if (ele == e) {
				return true;
			}
		}
		return false;
	}
	
	// 查找指定元素的索引
	public int find (T ele) {
		for (int i = 0; i < size; i++) {
			if (data[i] == ele) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder();
		str.append("size: " + size + ", capacity: " + data.length + "\n" + "[ ");
		
		for (int i = 0; i < size; i++) {
			str.append(data[i] + ", ");
		}
		
		int lastIndex = str.lastIndexOf(",");
		str.deleteCharAt(lastIndex);
		str.append("]");
		
		return str.toString();
	}
 }
