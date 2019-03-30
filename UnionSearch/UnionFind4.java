package UnionSearch;
/*
 	第四版本的并查集:
 		基于rank进行优化,不采用基于个数的优化方式, 维护一个rank数组, 代表每一个节点
 		作为根节点的时候, 其深度的大小.
 		在合并的时候, 将深度小的合并到深度大的
 			- 深度相同的情况下, a 合并到 b, 那么b的深度应该要加1
 */
public class UnionFind4 implements UnionFind {
	private int[] parent;
	private int[] rank;
	
	public UnionFind4 (int capacity) {
		// initialize the array
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
		
		// 应该将个数少的并到个数多的
		if (rank[aRoot] < rank[bRoot]) {
			parent[aRoot] = bRoot;
		} else if (rank[bRoot] < rank[aRoot]){
			parent[bRoot] = aRoot;
		} else { // rank[aRoot] == rank[bRoot]
			parent[bRoot] = aRoot;
			rank[aRoot] += 1;
		}
	}
	
	@Override
	public boolean isConnected (int a, int b) {
		return find(a) == find(b);
	}
	
	// search the parentIndex of index;
	public int find (int index) {
		while (parent[index] != index) {
			index = parent[index];
		}
		return index;
	}
	
	@Override
	public int getSize () {
		return parent.length;
	}
}
















