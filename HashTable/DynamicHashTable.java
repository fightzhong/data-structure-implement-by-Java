package HashTable;

import java.util.Map.Entry;
import java.util.TreeMap;
/*
 *	对于第一个版本的哈希表, 由于数组的大小是固定的, 所以会导致在数据量达到一定程度的时候, 哈希冲突
 *	会越来越多, 这样数据量越多, 性能就会越差, Java8中实现的HashMap, 初始的时候是采用链表, 当数据量达到
 *	一定程度的时候, 就会进行优化成红黑树, 从而加快在同一个索引下的操作, 这里我们采用动态控制数组大小的方式
 *	去减少哈希冲突
 *	
 *	动态控制的条件: 当里面的元素个数达到平均每一个索引的元素个数为10个(可以自定义)的时候, 此时去进行扩容操作, 再重新制作哈希表
 *						当里面的元素个数达到平均每一个索引的元素个数小于等于2个时候, 进行缩容操作,  再重新制作哈希表
 *	扩容: 因为是整型int数组, 所以扩容后的size不能大于整型的最大值Integer.MAX_VALUE
 *	缩容: 因为是整型int数组, 所以缩容后的size不能为0, 最好的做法是不能小于初始大小
 */
public class DynamicHashTable<K, V> {
	// 优化一: 通过静态常量来规范值
	private static final int initCapacity = 7; // 初始化容量, 最好是素数
	private static final int upperBounderay = 10; // 上边界
	private static final int lowerBounderay = 2;  // 下边界
	
	private TreeMap<K, V>[] hashtable;
	private int size;	
	private int capacity;
	
	@SuppressWarnings("unchecked")
	public DynamicHashTable () {
		this.capacity = initCapacity;
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
		if (size >= upperBounderay * capacity && capacity * 2 <= Integer.MAX_VALUE) {
			resize(2 * capacity);
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
		
		if (size <= lowerBounderay * capacity && capacity / 2 >= initCapacity) { // 缩容操作
			resize(capacity / 2);
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



