package Heap;

import java.util.Arrays;
import java.util.Random;

public class IndexMinHeap<T extends Comparable<T>> {
	private int[] heap;			// 堆数据
	private T[] data;				// 用户可见的数据, 即用户实际操作的数据
	private int size;				// 堆中的元素个数
	
	@SuppressWarnings("unchecked")
	public IndexMinHeap (int capacity) {
		heap = new int[capacity];
		data = (T[])new Comparable[capacity];
		size = 0;
	}
	
	/**在索引为index的位置上添加元素item**/
	public void insert (int index, T item) {
		// 先确保index位置上没有元素
		assert data[index] == null;
		
		data[index] = item;
		heap[size] = index;
		size++;
		
		siftUp(size - 1);
	}
	
	/**提取索引堆中的最小值**/
	public T extractMin () {
		T min = data[heap[0]];
		heap[0] = heap[size - 1];
		size--;
		
		siftDown(0);
		return min;
	}
	
	public void change (int index, T newVal) {
		assert data[index] != null;
		
		// 更改data数组中的值为新值
		data[index] = newVal;
		
		// 找到该值在堆中的位置
		for (int i = 0; i < size; i ++) {
			if (heap[i] == index) {
				// 对该元素在堆中的位置进行上浮和下沉, 维护堆的性质
				siftDown(i);
				siftUp(i);
				break;
			}
		}
	}
	
	/**对堆中索引为index的元素进行下沉**/
	public void siftDown (int index) {
		while (index < size) {
			int leftChild = index * 2 + 1;
			int rightChild = index * 2 + 2;
			
			int minChild = getMinChild(leftChild, rightChild);
			if (minChild == -1) {
				return;
			}
			
			if (data[heap[index]].compareTo(data[heap[minChild]]) < 0) {
				return;
			}
			swap(index, minChild);
			index = minChild;
		}
	}
	
	/**对堆中索引为index的元素进行上浮**/
	private void siftUp (int index) {
		while (index > 0) {
			int parentIndex = (index - 1) / 2; 
			if (data[heap[index]].compareTo(data[heap[parentIndex]]) > 0) {
				return;
			}
			swap(index, parentIndex);
			index = parentIndex;
		}
	}
	
	private int getMinChild (int i, int j) {
		if (i >= size) {
			return -1;
		} else if (j >= size) {
			return i;
		} else {
			if (data[heap[i]].compareTo(data[heap[j]]) > 0) {
				return j;
			}
			
			return i;
		}
	}
	
	/**对堆中索引为i, j两个位置的元素进行位置交换**/
	private void swap (int i, int j) {
		int temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	/**获取堆中元素的个数**/
	public int getSize () {
		return size;
	}
	
	public static void main(String[] args) {
		IndexMinHeap<Integer> heap = new IndexMinHeap<>(10);
		Random ran = new Random();
		
		for (int i = 9; i >= 0; i --) {
			heap.insert(i, ran.nextInt(100));
		}
		
		System.out.println(Arrays.toString(heap.data));
		System.out.println(Arrays.toString(heap.heap));
		
		heap.change(5, 100);
		
		for (int i = 0; i < 10; i++) {
			System.out.println(heap.extractMin());
		}
		
	}
}
