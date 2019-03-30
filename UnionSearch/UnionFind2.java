package UnionSearch;

import java.util.Arrays;

/*
 	第二版本的并查集:
 			采用数组作为底层存储, 利用数组构造成一棵由子节点指向父节点的树, 每一个节点初始化后都是指向自己的, 即
 			该节点是该集合中的根节点
 			实现的原理是: 通过优化find方法, 不停的查找一个节点的根节点, 对于isConnected方法, 两个节点的根节点相同的情况下
 						 就说明这两个节点有连接, 对于unionElement方法, 将其中一个根节点指向另一个根节点就可以了
 	复杂度分析:
 			isConnected: O(h)
 			unionElement: O(h)
 					两个都是O(h)的复杂度, 比O(n)小, 缺点是极端情况下, 会形成链表的情况
 */
public class UnionFind2 implements UnionFind {
	
	private int[] parent;
	
	public UnionFind2 (int size) {
		parent = new int[size];
		// 初始化并查集的指向
		for (int i = 0; i < size; i++)
			parent[i] = i;
	}
	
	@Override
	public void unionElement (int a, int b) {
		
		int aRoot = find(a); // a的根节点
		int bRoot = find(b); // b的根节点
		
		if (aRoot == bRoot) {
			return;
		}
		
		// 将a的根节点指向b的根节点或者将b的根节点指向a的根节点
		parent[aRoot] = bRoot;
	}
	
	@Override
	public boolean isConnected (int a, int b) {
		return find(a) == find(b);
	}
	
	// 查找一个节点的根节点
	private int find (int index) {
		if (index < 0 || index > getSize()) 
			throw new IllegalArgumentException("index is out of bound!");
		
		while (parent[index] != index) { // 只有该节点指向自己的时候才说明其是根节点, 所以这里需要一直查找根节点
			index = parent[index];
		}
		
		return index;
	}
	
	@Override
	public int getSize () {
		return parent.length;
	}
	
	@Override
	public String toString () {
		return "UnionFind2: " + Arrays.toString(this.parent);
	}
}
