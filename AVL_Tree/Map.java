package AVL_Tree;

public interface Map<K, V> {
	public abstract void add (K key, V value);
	public abstract V remove (K key);
	public abstract void set (K key, V value);
	public abstract V get (K key);
	public abstract boolean contains (K key);
	public abstract int getSize ();
	public abstract boolean isEmpty ();
}


