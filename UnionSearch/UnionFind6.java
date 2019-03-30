package UnionSearch;

/*
 	第六个版本的并查集: 路径压缩
 	通过递归的方式, 在查询的时候将每一个元素都指向根节点, 这里递归到终点后就会返回根节点, 然后上一个节点会接收这个根节点
 	并指向它, 指向它之后又返回给上上一个节点, 所以上上一个节点也指向这个根节点
 */
public class UnionFind6 implements UnionFind {
	private int[] parent;
	private int[] rank;
	
	public UnionFind6 (int capacity) {
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
	
	// 该方法用于查找index的根节点
	private int find (int index) {
		if (index != parent[index]) { // 如果当前索引不是根节点
			parent[index] = find(parent[index]); // 将index所指向的父节点 指向了其父节点的根节点
		}
		
		return parent[index]; // 这里当我们遍历到根节点的时候, 就返回它本身, 然后上一个节点就会指向它,
									//  同时上一个节点返回的也是这个节点, 那么上上个节点接收到后仍然会指向这个根节点
	}
	
	@Override 
	public int getSize () {
		return parent.length;
	}
}
