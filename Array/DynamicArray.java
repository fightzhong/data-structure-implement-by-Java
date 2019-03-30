package Array;
/*
 * 动态数组: 当数组中元素个数满了后再添加元素会扩容一倍,
 * 		       当数组中元素个数<=数组的长度时, 说明确实用不了那么大的数组, 此时缩容1/2
 */

public class DynamicArray<T> {
	private T[] data;
	private int size;
	
	@SuppressWarnings("unchecked")
	public DynamicArray (int capacity) {
		data = (T[])new Object[capacity];
		size = 0;
	}
	
	public DynamicArray () {
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
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("add failed, index < 0 and index > size is illegal");
		}
		
		if (size == data.length) { // 表明此时数组已经是满的了, 此时扩容, 然后将本身存在的数据复制到新数组
			resizeArr(data.length * 2);
		}
		
		// 添加元素
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
	
	@SuppressWarnings("unchecked")
	private void resizeArr (int capacity) {
		T[] newArr = (T[])new Object[capacity];
		for (int i = 0; i < size; i++) {
			newArr[i] = data[i];
		}
		data = newArr;
		newArr = null;
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
		
		// 我们选择在删除完元素后再对数组进行判断, 元素只占满了数组的1/4左右, 缩容数组到1/2
		// 如果这个判断和缩容在删除开始的时候就执行, 会导致元素个数相对来说少了一个, 即本应该2个元素时删除, 却变成了1个元素的时候才删除(长度为10的情况下)
		if (size <= data.length / 4) {
			resizeArr(data.length / 2);
		}
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

