package UnionSearch;

public interface UnionFind {
	int getSize ();
	boolean isConnected (int a, int b);
	void unionElement (int a, int b);
}
