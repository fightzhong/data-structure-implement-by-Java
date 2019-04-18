package UnionSearch;

/*
 	第五个版本的并查集: 路径压缩
 	在find的操作的时候, 对每一个查找的元素, 都使其指向其祖先元素, 以此来达到压缩路径的效果, 
 	相对于基于rank优化的来说, 其仅仅改变了find的部分实现, 并且我们在进行路径压缩的过程中, 不再去维护
 	rank的语义, 仅仅只用于在合并的过程中一个比较操作而已, 由于路径压缩的存在, 有可能导致i的深度比j小
 	但是rank数组中i的深度却比j大的情况
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
			parent[aRoot] = bRoot;
		} else if (rank[bRoot] < rank[aRoot]) {
			parent[bRoot] = aRoot;
		} else { // rank[bRoot] = rank[aRoot]
			parent[aRoot] = bRoot;
			rank[bRoot] += 1;
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
