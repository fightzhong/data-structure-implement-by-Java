package Heap;

import java.util.Arrays;
import java.util.Random;

/**
 * 	在第一个版本的索引堆中, 如果我们要修改data中索引为i的值, 那么先要修改data中索引为i的值, 然后在indexHeap中
 *		 找到值为i的索引, 对该值进行维护最小索引堆的操作, 先对其进行下沉操作, 确保该位置为堆顶所在的索引堆是一棵最小索引堆, 然后再进行上浮操作, 去维持
 * 	上面的索引堆
 * 
 * 	弊端: 我们每修改一个元素, 都要先找到该元素对应的索引在indexHeap中的位置, 需要进行遍历, 然后再对其维持索引堆操作, 前者为n级别的操作, 后者为logn级别的
 * 			操作, 所以修改一个元素的时间复杂度为O(n) + O(logn)级别
 * 	
 * 	优化: (实际写的时候画图容易理解, 可以构造一个5个数据的data, 然后将其变成索引堆)
 * 		我们可以再添加一个数组rev, 该数组的作用是用来保存data中每一个索引在indexHeap的位置, 这样我们在修改的时候就可以直接找到这个这个索引, 再对其进行
 * 		上浮下沉, 不用再遍历一遍indexHeap了
 * 
 * @author zhongshenglong
 */

public class IndexMinHeapOptimized<T extends Comparable<T>> {
	private int[] heap;			// 堆数据
	private T[] data;				// 用户可见的数据, 即用户实际操作的数据
	private int size;				// 堆中的元素个数
	private int[] rev;			// 用于保存data中每一个索引在heap中的位置
	
	@SuppressWarnings("unchecked")
	public IndexMinHeapOptimized (int capacity) {
		heap = new int[capacity];
		data = (T[])new Comparable[capacity];
		rev = new int[capacity];
		size = 0;
		
		// 先初始化rev中所有值为-1, 代表了data中的所有索引都不在heap中
		for (int i = 0; i < rev.length; i++) {
			rev[i] = -1;
		}
	}
	
	/**在索引为index的位置上添加元素item**/
	public void insert (int index, T item) {
		// 先确保index位置上没有元素
		assert data[index] == null;
		
		data[index] = item;
		heap[size] = index;
		// 初始的时候data中index这个索引被存放在heap中size位置
		rev[index] = size;
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
	
	/**提取索引堆中的最小值在data中的索引**/
	public int extractMinIndex () {
		int index = heap[0];
		heap[0] = heap[size - 1];
		size--;
		
		siftDown(0);
		return index;
	}
	
	public void change (int index, T newVal) {
		assert data[index] != null;
		
		// 更改data数组中的值为新值
		data[index] = newVal;
		
		// 找到该值在堆中的位置
		int i = rev[index];
		siftDown(i);
		siftUp(i);
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
			
			// 注意: 对于rev的交换必须在heap变化之前进行交换, 否则获取到的heap[index]就不是原来的index了
			rev[heap[index]] = minChild;
			rev[heap[minChild]] = index;
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
			// 注意: 对于rev的交换必须在heap变化之前进行交换, 否则获取到的heap[index]就不是原来的index了
			rev[heap[index]] = parentIndex;
			rev[heap[parentIndex]] = index;
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
		IndexMinHeapOptimized<Integer> heap = new IndexMinHeapOptimized<>(10);
		Random ran = new Random();
		
		for (int i = 9; i >= 0; i --) {
			heap.insert(i, ran.nextInt(100));
		}
		
		System.out.println(Arrays.toString(heap.data));
		System.out.println(Arrays.toString(heap.heap));
		System.out.println(Arrays.toString(heap.rev));
		
		heap.change(5, ran.nextInt(100));
		
		System.out.println(Arrays.toString(heap.data));
		System.out.println(Arrays.toString(heap.heap));
		System.out.println(Arrays.toString(heap.rev));
		
		for (int i = 0; i < 10; i++) {
			System.out.println(heap.extractMin());
		}
		
//		heap.insert(0, 15);
//		heap.insert(1, 5);
////		heap.insert(2, 30);
//		System.out.println(Arrays.toString(heap.data));
//		System.out.println(Arrays.toString(heap.heap));
//		System.out.println(Arrays.toString(heap.rev));
		
	}
}
