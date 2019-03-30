package Heap;
/*
 	Heap(堆): 堆结构, 是一种完全二叉树, 完全二叉树的元素是从二叉树的左边开始放置的, 以其为基础实现的最大堆/最小堆要求每一个元素不能
 	 		 小于其任何一个子元素, 所以就形成了在树的顶端的元素是最大的/最小的
  	下面的实现相对有一点繁琐, 最好size用数组的size来维护, 再多加一个size导致过于繁琐
 */

import Array.DynamicArray;

public class MaxHeap<T extends Comparable<T>> {
	
	private DynamicArray<T> data;
	private int size;

	public MaxHeap (int capacity) {
		data = new DynamicArray<T>(capacity);
		size = 0;
	}
	
	public MaxHeap () {
		data = new DynamicArray<T>();
		size = 0;
	}
	
	// Heapify操作, 将用户传入的数组转换成最大堆的形式
	public MaxHeap (T[] arr) {
		data = new DynamicArray<T>(arr.length);
		for (T t: arr) {
			data.addLast(t);
			size++;
		}
		
		// 我们发现, 最后一个节点对应的父节点是肯定有子节点的, 而其他节点不一定有
		// 我们需要从最后一个子节点的父节点开始执行下浮操作, 使其满足最大堆的效果
		// 并且此时的时间复杂度为nlog(n), 而将数组的元素一个个放入data, 再执行上浮操作时间复杂度为longn^2;
		for (int i = parentIndex((size - 1)); i >= 0; i--) {
			siftDown(i);
		}
	}
	
	public void add (T t) {
		data.addLast(t); // 添加元素
		siftUp(size++); // 上浮元素
	}
	
	// 删除元素一般是删除最大堆中最大的元素, 原理: 将最大的删除, 然后将数组最后一个元素放在最大值的位置, 执行下浮操作, 使得数据保持最大堆的性质
	public T extractMax () {
		if (isEmpty())
			throw new IllegalArgumentException("heap is empty");
		T removeEle = data.get(0);
		if (size > 1) {
			data.set(0, data.get(size - 1)); // 用最后的元素来顶替最大值的位置
			data.removeLast();
			size--;
			siftDown(0); // 执行下浮操作
		} else {
			data.removeLast();
			size--;
		}
		
		return removeEle;
	}

	// 提取最大值, 并添加一个值(可以在最大值的位置放入添加的值, 再执行siftDown操作, 这样就只需要一次logn的操作了
	public T replace (T t) {
		T returnVal = data.get(0);
		data.set(0, t);
		siftDown(0);
		return returnVal;
	}
	
	private void siftDown (int index) {
		while (true) {
			// 获取左右孩子中的最大值的索引
			int maxVal;
			int leftIndex = leftChildIndex(index);
			int rightIndex = rightChildIndex(index);
			if (leftIndex >= size) {
				break;
			}
			// 由上一个判断可以得到一定有左孩子
			if (rightChildIndex(index) >= size) {
				maxVal = leftIndex;
			} else { // 左右孩子都存在的情况
				if (data.get(leftIndex).compareTo(data.get(rightIndex)) > 0) { // 左边 > 右边
					maxVal = leftIndex;
				} else { // 左边  < 右边
					maxVal = rightIndex;
				}
			}
			// 判断当前索引的值和最大值的索引的大小
			if (data.get(index).compareTo(data.get(maxVal)) >= 0) { // 是二叉堆的形式则退出循环
				break;
			}
			swap(index, maxVal); // 不是二叉堆的形式则交换值
			index = maxVal;
		}
	}
	
	private void siftUp (int index) {
		while (index > 0) { // 当index > 0时视情况执行上浮操作
			int parentIndex = parentIndex(index);
			if (data.get(parentIndex).compareTo(data.get(index)) >= 0) { // 如果父节点的值大于当前节点的值, 即符合最大堆的形式, 则让循环终止
				break;
			}
			// 不符合最大堆的形式, 则调换两个索引的值
			swap(parentIndex, index);
			index = parentIndex;
		}
	}
	
	private void swap (int index, int index2) {
		T t = data.get(index);
		data.set(index, data.get(index2));
		data.set(index2, t);
	}
	
	private int parentIndex (int index) {
		if (index == 0)
			throw new IllegalArgumentException("heap is empty!");
		return (index - 1) / 2;
	}
	
	private int leftChildIndex (int index) {
		return index * 2 + 1;
	}
	
	private int rightChildIndex (int index) {
		return index * 2 + 2;
	}
	
	public int size () {
		return size;
	}
	
	public boolean isEmpty () {
		return size == 0;
	}
}
