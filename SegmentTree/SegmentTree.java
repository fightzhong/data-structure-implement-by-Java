package SegmentTree;

/*
	为了便于以后忘记了元素个数为4n是怎么求的, 所以本篇对线段树的元素个数进行详细的说明
	对于二叉树的元素个数和层数之间的关系: 第h层的个数为2^(h-1), 是一个等比数列
				层数   			个数  
				0			 1
				1			 2
				2			 4
				3			 8
				4			 16...
	等比数列求和公式:
				Sn = 2^0 + 2^1 + 2^2 + 2^3 + ... + 2^n
				2Sn = 2^1 + 2^2 + 2^3 + ... + 2^n + 2^(n + 1)
				2Sn - Sn = Sn = 2^(n + 1) - 1;
	所以对于满二叉树来说, 一共n层, 元素一共为: 2^(n + 1) - 1个;
	所以对于满二叉树来说, 前n - 1层元素总个数一共为: 2^n - 1;
	又因为第n层的个数为 2^n个, 所以可以认为后一层的元素个数为前面所有层数的元素个数和
	
	对于元素一共有n个, 想要形成线段树, 如果n为2^k形式的数, 那么对于n个元素一定能占满最后一排, 因为满二叉树的规律也是2^n形式
	如果在最差的情况, n为2^k + 1形式, 那么这多余的一个元素就要再一次被平分线段区间, 即到了下一层, 这样我们就需要重新开辟一层的空间
	
	空间: 第n层有n个元素, 按照该层元素为上面所有元素之和, 那么就一共有2n个元素, 如果在最坏的情况, 多了一个元素, 那么就又要开辟一层, 该层空间为2n
		所以对于一个线段树来说, 其要开辟的空间大小为4n个
 */
// 线段树一般是不可变的, 所以我们在声明的时候可以直接确定数组的大小
// 对于一组元素, 要实现区间访问, 那么就需要将其构造成线段树的形式, 这里我们需要对该数组拷贝一个副本来防止引用出现问题
public class SegmentTree<T> { 
	private T[] data;
	private T[] tree; // 将data构造成线段树的形式, 并以tree变量保存, 后面访问线段树也是操作该线段树
	private Merger<T> merger;
	
	@SuppressWarnings("unchecked")
	public SegmentTree (T[] arr, Merger<T> merger) {
		data = (T[])new Object[arr.length];
		
		for (int i = 0; i < arr.length; i++)
			data[i] = arr[i];
		tree = (T[])new Object[data.length * 4]; // 开辟4n个空间
		this.merger = merger; // 合并的规则
		// 构造线段树
		buildSegmentTree(0, 0, data.length - 1); // 构造线段树中索引为0的值, 它的值等于data中[0, data.length - 1]区间值的合并
	}
	
	private void buildSegmentTree (int curIndex, int leftBorder, int rightBorder) {
		if (leftBorder == rightBorder) { // 当左右边界的值相等时,就说明只有一个元素了, 此时就应该将当前索引的值等于data中左边界或者右边界的值
			tree[curIndex] = data[leftBorder];
			return;
		}
		
		int midBorder = leftBorder + (rightBorder - leftBorder) / 2; // 获取分割的中点
		// 所以当前元素的值就是其(左边界 - 中点)与(中点 + 1 - 右边界)值的合并
		int leftChildIndex = leftChildIndex(curIndex);
		int rightChildIndex = rightChildIndex(curIndex);
		// 构造左右孩子的值
		buildSegmentTree(leftChildIndex, leftBorder, midBorder);
		buildSegmentTree(rightChildIndex, midBorder + 1, rightBorder);
		
		//构造完成后, 就可以通过左右孩子的值来获取当前值
		tree[curIndex] = merger.merger(tree[leftChildIndex], tree[rightChildIndex]);
	}
	
	// 用户调用的, 需要查询哪个区间的值
	public T search (int leftBorder, int rightBoder) {
		if (leftBorder < 0 || rightBoder >= data.length || leftBorder > rightBoder) {
			throw new IndexOutOfBoundsException();
		}
		T result = search(0, 0, data.length - 1, leftBorder, rightBoder);
		return result;
	}
	
	// 递归算法, 对于线段树来说, 在0号元素, 其表示的区间范围是[0, (data.length -1)], 在该区间范围内查找[left,right]区间的值
	// 如果要查找的区间在左右两边都有, 则最后需要将值进行合并
	// 递归的终点是, 当查找的区间等于该索引表示的区间
	private T search (int curIndex, int curLeft, int curRight, int leftBorder, int rightBorder) {
		if (curLeft == leftBorder && curRight == rightBorder) {
			return tree[curIndex];
		}
		int midIndex = curLeft + (curRight - curLeft) / 2;
		int leftChildIndex = leftChildIndex(curIndex);
		int rightChildIndex = rightChildIndex(curIndex);
		
		if (midIndex < leftBorder) { // 查询的区间在当前总区间的右边
			return search(rightChildIndex, midIndex + 1, curRight, leftBorder, rightBorder);
		} else if (midIndex >= rightBorder) { // 查询的区间在当前总区间的左边
			return search(leftChildIndex, curLeft, midIndex, leftBorder, rightBorder);
		} else { // 查询的区间包括了中点
			T leftResult = search(leftChildIndex, curLeft, midIndex, leftBorder, midIndex);
			T rightResult = search(rightChildIndex, midIndex + 1, curRight, midIndex + 1, rightBorder);
			T result = merger.merger(leftResult, rightResult);
			return result;
		}
	}

	// 更新指定索引下的值
	public void set (int index, T t) {
		data[index] = t;
		set(0, 0, data.length - 1, index, t);
	}
	
	private void set (int curIndex, int leftBorder, int rightBorder, int setIndex, T t) {
		if (leftBorder == rightBorder) {
			tree[curIndex] = t;
			return;
		}
		
		int midIndex = leftBorder + (rightBorder - leftBorder) / 2;
		int leftChildIndex = leftChildIndex(curIndex);
		int rightChildIndex = rightChildIndex(curIndex);
		
		if (setIndex <= midIndex) { // 去左边查找
			set(leftChildIndex, leftBorder, midIndex, setIndex, t);
		} else if (setIndex > midIndex) { // 去右边查找
			set(rightChildIndex, midIndex + 1, rightBorder, setIndex, t);
		}
		
		tree[curIndex] = merger.merger(tree[leftChildIndex], tree[rightChildIndex]);
	}
	
	
	private int leftChildIndex (int index) {
		return index * 2 + 1;
	}
	
	private int rightChildIndex (int index) {
		return index * 2 + 2;
	}

}

@FunctionalInterface
interface Merger<T> {
	T merger(T a, T b);
}


