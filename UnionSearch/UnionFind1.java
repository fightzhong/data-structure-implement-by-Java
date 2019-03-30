package UnionSearch;

import java.util.Arrays;

/*
  	第一版本的并查集:
  		采用数组作为底层数据存储, 每一个索引所在的位置视为一个Node节点, 索引所在的位置的值为
  		该Node节点所在的集合, 比如5号索引的值为1, 说明5号索引的节点指向的父亲节点是1号索引所在的节点,
  		即同一号索引为同一个集合
  		
  	复杂度分析: Quick Find
  			isConnected: O(1)
  			unionElement: O(n) --> 因为这里需要遍历每一个元素
 */
public class UnionFind1 implements UnionFind {
	private int[] id;

	public UnionFind1(int size) {
		id = new int[size];
		for (int i = 0; i < size; i++) {
			id[i] = i;
		}
	}

	@Override
	public void unionElement(int a, int b) {
		int aID = find(a);
		int bID = find(b);
		for (int i = 0; i < id.length; i++) {
			if (id[i] == aID) {
				id[i] = bID;
			}
		}
	}

	// 判断两个节点是否有连接
	@Override
	public boolean isConnected(int a, int b) {
		return find(a) == find(b);
	}

	// 查找id为index的所属的集合的根节点
	private int find(int index) {
		return id[index];
	}

	@Override
	public int getSize() {
		return id.length;
	}
	
	@Override
	public String toString () {
		return "UnionFind1: " + Arrays.toString(this.id);
	}
}
