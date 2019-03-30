package UnionSearch;

/*
 	第五个版本的并查集: 路径压缩
 	在find的操作的时候, 对每一个查找的元素, 都使其指向其祖先元素, 以此来达到压缩路径的效果, 
 	相对于基于rank优化的来说, 其仅仅改变了find的部分实现
 */
public class UnionFind5 implements UnionFind {
	private int[] parent;
	private int[] rank;
	
	public UnionFind5 (int capacity) {
		parent = new int[capacity];
		rank = new int[capacity];
		for (int i = 0; i < capacity; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
	}

	@Override
	public void unionElement (int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		
		if (aRoot == bRoot) 
			return;
		
		if (rank[aRoot] < rank[bRoot]) {
			parent[bRoot] = aRoot;
		} else if (rank[bRoot] < rank[aRoot]) {
			parent[aRoot] = bRoot;
		} else { // rank[bRoot] = rank[aRoot]
			parent[aRoot] = bRoot;
			parent[bRoot] += 1;
		}
	}
	
	@Override
	public boolean isConnected (int a, int b) {
		return find(a) == find(b);
	}
	
	private int find (int index) {
		while (parent[index] != index) {
			parent[index] = parent[parent[index]];
			index = parent[index];
		}
		
		return index;
	}
	
	@Override 
	public int getSize () {
		return parent.length;
	}
}
