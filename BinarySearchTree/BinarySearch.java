package BinarySearchTree;

import java.util.Arrays;

// 二分查找法, 前提是这个数组是有序的
public class BinarySearch {
		
	public static void main(String[] args) {
		Integer[] arr = new Integer[10];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		System.out.println(Arrays.toString(arr));
		for (int i = 0; i < arr.length; i++) {
			System.out.println(BinarySearch.binarySearch(arr, 10));
		}
		
	}
	
	public static <T extends Comparable<T>> int binarySearch (T[] arr, T t) {
		
		return binarySearch(arr, t, 0, arr.length - 1);
	}
	
	// 在数组arr的闭区间[l, r]查找元素t
	public static <T extends Comparable<T>> int binarySearch (T[] arr, T t, int l, int r) {
		
		if (l == r) {
			return arr[l].compareTo(t) == 0 ? l : -1;
		}
		
		int min = (r - l) / 2 + l;
		
		if (arr[min].compareTo(t) > 0) {
			return binarySearch(arr, t, l, min);
		} else if (arr[min].compareTo(t) < 0) {
			return binarySearch(arr, t, min + 1, r);
		} else {
			return min;
		}
		
	}
}
