package Heap;

import Array.DynamicArray;

public class MinHeap<T extends Comparable<T>> {
	DynamicArray<T> data;
	
	public MinHeap () {
		data = new DynamicArray<T>();
	}
	
	public MinHeap (int capacity) {
		data = new DynamicArray<T>(capacity);
	}
	
	// 父节点的索引是否会达到0的情况, 如果数组为空, 则此时会报错
	public void add (T t) {
		data.addLast(t);
		int curIndex = data.getSize() - 1;
		while (curIndex > 0) {
			// 获取父节点的索引
			int parentIndex = parentIndex(curIndex);
			if (data.get(parentIndex).compareTo(data.get(curIndex)) <= 0) {
				break;
			}
			swap(parentIndex, curIndex);
			curIndex = parentIndex;
		}
	}
	
	// 元素只有一个的情况?
	public T extractMin () {
		if (isEmpty())
			throw new IllegalArgumentException("heap is empty!");
		
		// 用最后一个元素取代堆顶的元素
		T minValue = data.get(0); // 71327
		T lastValue = data.removeLast(); // 1604414293
		if (data.getSize() == 0) {
			return minValue;
		}
		data.set(0, lastValue);
		
		// 对堆顶的元素进行下浮操作
		int curIndex = 0;
		while (true) {
			int leftChildIndex = leftChildIndex(curIndex);
			int rightChildIndex = rightChildIndex(curIndex);
			T curV = data.get(curIndex);
			
			if (leftChildIndex < 0 || leftChildIndex >= data.getSize()) { // 左孩子不存在, 则直接跳出循环
				break;
			}
			
			// 左孩子一定存在
			if (rightChildIndex > 0 && rightChildIndex < data.getSize()) { // 存在右孩子, 同时也证明存在左孩子
				T leftV = data.get(leftChildIndex);
				T rightV = data.get(rightChildIndex);
				if (leftV.compareTo(rightV) <= 0 && curV.compareTo(leftV) > 0) { // 左边 < 右边
					swap(leftChildIndex, curIndex);
					curIndex = leftChildIndex;
				} else if (leftV.compareTo(rightV) > 0 && curV.compareTo(rightV) > 0) { // 右边 > 左边
					swap(rightChildIndex, curIndex);
					curIndex = rightChildIndex;
				} else {
					break;
				}
			} else { // 只有左孩子
				T leftV = data.get(leftChildIndex);
				if (curV.compareTo(leftV) < 0) {
					break;
				}
				swap(curIndex, leftChildIndex);
				curIndex = leftChildIndex;
			}
		}
		
		return minValue;
	}
	
	private void swap (int i1, int i2) {
		T temp = data.get(i1);
		data.set(i1, data.get(i2));
		data.set(i2, temp);
	}
	
	private int parentIndex (int childIndex) {
		if (childIndex == 0) {
			throw new IllegalArgumentException("index of zero doesn't have parent!");
		}
		return (childIndex - 1) / 2;
	}
	
	private int leftChildIndex (int curIndex) {
		if (curIndex >= data.getSize() || curIndex < 0) {
			throw new IllegalArgumentException("Illegal index");
		}
		return curIndex * 2 + 1;
	}
	
	private int rightChildIndex (int curIndex) {
		if (curIndex >= data.getSize() || curIndex < 0) {
			throw new IllegalArgumentException("Illegal index");
		}
		return curIndex * 2 + 2;
	}
	
	public int getSize () {
		return data.getSize();
	}
	
	public boolean isEmpty () {
		return data.isEmpty();
	}
	
	@Override
	public String toString () {
		return data.toString();
	}
}
