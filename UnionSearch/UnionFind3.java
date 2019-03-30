package UnionSearch;

import java.util.Arrays;
/*
 	第三版本的并查集:	(减少树的高度, 尽量避免树退化成了链表的形式)
 			基于size进行优化 , 我们需要额外的维护一个保存每一个节点作为根节点的时候其元素的个数,
 			在合并的时候,需要将个数少的合并到个数多的树上, 例如: 四个节点和一个节点合并, 为了不让
 			树的深度变得更高, 需要将一个节点的合并到4个节点上
 */
public class UnionFind3 implements UnionFind {
	
	private int[] parent;
	private int[] size;
	
	public UnionFind3 (int capacity) {
		parent = new int[capacity];
		size = new int[capacity];
		// 初始化并查集的指向, 以及每一个节点的根节点所在的树的size为0
		for (int i = 0; i < capacity; i++) {
			parent[i] = i;
			this.size[i] = 1;
		}
	}
	
	@Override
	public void unionElement (int a, int b) {
		if (find(a) == find(b)) { // 不需要并, 因为两个的指向相同
			return;
		}
		
		int aRoot = find(a); // a的根节点
		int bRoot = find(b); // b的根节点
		
		// 优化地方: 将size小的并到size大的根节点上, 不能反过来, 否则会导致树越来越高
		if (size[aRoot] < size[bRoot]) {
			parent[aRoot] = bRoot;
			size[bRoot] += size[aRoot]; 
		} else { // size[aRoot] >= size[bRoot]
			parent[bRoot] = aRoot;
			size[aRoot] += size[bRoot]; 
		}
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
		return "UnionFind3: " + Arrays.toString(this.parent) + "\n" + "========\n" + Arrays.toString(this.size);
	}
	
}
