package HashTable;

import java.util.Map.Entry;
import java.util.TreeMap;
/*
 * 对于第二个版本的哈希表来说, 由于其扩容为两倍, 而这个扩容之后就会变成了一个偶数, 从而也会增大哈希冲突的概率
 *  根据哈希取模素数表, 不同范围的值采用不同的取模素数, 其实就是不同capacity
 */

public class DynamicHashTable2<K, V> {
	private static final int[] primeNumberOfMod = {
			53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 98317, 196613, 393241, 786433,
			1572869, 3415739, 6291469, 12582917, 25165843, 50331653, 100663319, 201326611, 402653189,
			805306457, 1610612741
	};
	
	private static int capacityIndex = 0; 
	private static final int upperBounderay = 10; 
	private static final int lowerBounderay = 2;  
	
	private TreeMap<K, V>[] hashtable;
	private int size;	
	private int capacity;
	
	@SuppressWarnings("unchecked")
	public DynamicHashTable2 () {
		this.capacity = primeNumberOfMod[capacityIndex];
		this.size = 0;
		this.hashtable = new TreeMap[capacity];
		
		for (int i = 0; i < hashtable.length; i++)
			hashtable[i] = new TreeMap<K, V>();
	}
	
	private int hash (K key) {
		return (key.hashCode() & 0x7fffffff) % capacity;
	}
	
	// 添加元素
	public void add (K key, V value) {
		TreeMap<K, V> map = hashtable[hash(key)];
		
		if (!map.containsKey(key)) {
			map.put(key, value);
			size++;
		} else {
			map.put(key, value);
		}
		
		// 优化地方二: 添加完成后, 查看是否需要进行扩容
		if (size >= upperBounderay * capacity && capacityIndex < (primeNumberOfMod.length - 1)) {
			capacityIndex++;
			resize(primeNumberOfMod[capacityIndex]);
		}
	}
	
	// 对哈希表进行扩容或者缩容
	@SuppressWarnings("unchecked")
	private void resize (int newCapacity) {
		// 新建一个hashtable
		TreeMap<K, V>[] newHashtable = new TreeMap[newCapacity];
		
		// 初始化新的哈希表
		for (int i = 0; i < newHashtable.length; i++) {
			newHashtable[i] = new TreeMap<>();
		}
		
		// 重新设置新的容量值, 因为在hash()函数中需要用到这个值, 重设才能不会导致哈希值一样
		capacity = newCapacity;
		
		// 将旧的哈希表中的数据放入新的哈希表
		for (int i = 0; i < hashtable.length; i++) {
			for (Entry<K, V> set: hashtable[i].entrySet()) {
				// 重新计算哈希值
				int index = hash(set.getKey());
					
				// 将该键值对放入新的哈希表
				newHashtable[index].put(set.getKey(), set.getValue());
			}
		}
		hashtable = newHashtable;
	}
	
	// 删除元素
	public V remove (K key) {
		TreeMap<K, V> map = hashtable[hash(key)];
		
		if (!map.containsKey(key))
			throw new IllegalArgumentException(key + "is not existed!");
		size--;
		
		if (size <= lowerBounderay * capacity && capacityIndex > 0) { // 缩容操作
			capacityIndex--;
			resize(primeNumberOfMod[capacityIndex]);
		}
		
		return map.remove(key);
	}
	
	// 修改元素, 返回被修改的值
	public V set (K key, V newValue) {
		TreeMap<K, V> map = hashtable[hash(key)];
		
		if (!map.containsKey(key))
			throw new IllegalArgumentException(key + "is not existed!");
		
		return map.replace(key, newValue);
	}
	
	// 查找元素
	public V get (K key) {
		TreeMap<K, V> map = hashtable[hash(key)];
		
		return map.get(key);
	}
	
	public boolean contains (K key) {
		TreeMap<K, V> map = hashtable[hash(key)];
		
		return map.containsKey(key);
	}
	
	public int getSize () {
		return size;
	}
}



