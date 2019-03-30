package HashTable;

import java.util.TreeMap;

/*
 	哈希表: 不管是集合还是映射, 都将Key的哈希值作为其索引位置, 然后将值存入链表或者树中, 通过取模来保证数据的不分散性质
 	哈希表在底层是一个数组, 由于数组是一种基于索引的访问, 所以对其操作大部份的时间复杂度可以保持在O(1)
 	为了能够将我们的数据放入在哈希表中, 我们需要对数据进行一定的处理来使得每一个数据能够存放在一个索引的
 	位置, 从而实现空间换时间的操作, 但是可能会存在一些问题
 	哈希表存储身份证号:  101481198512156666
 				假如用哈希表去存储身份证号的话, 那么对于18位的身份证号来说, 要开辟的数组空间是很大的, 并且对于17位以下的数据是不存在的
 				那么就会导致空间的过大浪费, 优化的方式就是采用取模的方式来获取一个身份证号的哈希值, 假如对身份证的后6位进行1000000(一百万)的取模, 
 				那么就需要开辟1000000个数据的位置, 但是对于倒数第五位和第六位来说, 其表示的日期, 那么就会导致32万以后的位置是不存在的, 也造成了浪费
 	
 	所以设计一个哈希函数来数组的空间利用得到有效的利用, 并且分布均匀也是很困难的, 下面列举一些哈希函数的设计通常情况
 	
 	整型: 对于不同范围的整型来说, 可以取模不同的数, 而且需要是素数(合数造成哈希冲突的可能性更大)
 	浮点型: 在计算机的底层, 浮点型是以整型的方式进行存储的, 那么我们就可以将其转换为整型的值来表示, 然后再用整型的方式去计算其哈希值
 	字符串:   我们要将字符串转换成一个整型
 			<1> 一个整型比如166可以写成:    1*10^2 + 6*10^1 + 6*10^0
 			<2> 一个字符串也可以写成这样的形式: 
 			 			- 假如是26位的英文字母:
 			 					hash(abcd) = (a * 26^3 + b * 26^2 + c * 26^1 + d * 26^0) % M;  --> 一个整数进行取模
 			 			 由于这个26仅仅代表了26个英文字母, 那么加上大写的可以达到52个, 也可能有小数点这些字符, 所以可以看成是B为底数	
 	 						  hash(abcd) = (a * B^3 + b * B^2 + c * B^1 + d * B^0) % M;
 	 					- 如果幂过大, 那么即使用空间去换时间, 在计算幂的过程中值很大时也可能会导致时间消耗过大, 所以需要进行优化	  
	 						  hash(abcd) = ( (( (a * B + b) * B + c ) * B + d) * B ) % M, 利用提取的方式使得每一次只乘以一个B
 						  即使这样计算仍然可能会使得值过大, 那么我们可以把取模的操作也并在里面来, 使得每次先取模得到较小的值后再进行乘法操作
 						 	  hash(abcd) = (( ( (a % M) * B + b) % M * B + c ) % M *B + d) % M * B
 						 	  
 	哈希冲突:
 	 		我们往哈希表里面存入数据的时候, 是通过计算该数据的哈希值来获取插入数据的索引的, 那么就可能会遇到哈希值相同的情况,
 	 		所以对于每一个索引的位置都采用链表的形式进行数据的存储, 但是对于哈希冲突达到一定程度的时候, 链表也会造成性能问题,
 	 		所以在Java8以后对每一个索引里面采用另一种数据存储的方式: 在一定范围内的数据个数下采用链表, 当超过该范围后采用红黑树
 	 		这种数据结构来提高性能
 	 		
 	hash函数: 通过键的哈希值来计算其应该插入位置的索引
 	 		计算方式: 先将键进行绝对值运算, 求得一个正int类型的值, 然后对capacity进行取模运算, 取模后的值即为应该插入的位置
 	 		   		(key.hashCode() & 0x7fffffff) % capacity
 	 			  或者  (Math.abs(key.hashCode())) % capacity
 */

public class HashTable<K, V> {
	// 采用底层为红黑树实现的Java自带的TreeMap来作为哈希表的底层数组存储结构
	private TreeMap<K, V>[] hashtable;
	private int size;	// 哈希表中存储数据的个数
	private int capacity; // 哈希表的容量, 即数组的大小
	
	@SuppressWarnings("unchecked")
	public HashTable (int capacity) {
		this.hashtable = new TreeMap[capacity];
		this.size = 0;
		this.capacity = capacity;
		
		// 初始化哈希表
		for (int i = 0; i < hashtable.length; i++)
			hashtable[i] = new TreeMap<K, V>();
	}
	
	public HashTable () {
		this(97);
	}
	
	// hash: 通过键的哈希值来计算其应该插入位置的索引
	private int hash (K key) {
		return (key.hashCode() & 0x7fffffff) % capacity;
	}
	
	// 添加元素
	public void add (K key, V value) {
		// 插入位置的索引所在的TreeMap
		TreeMap<K, V> map = hashtable[hash(key)];
		
		// 如果不存在则需要将元素进行插入, 并且维护size, 存在则只需要更新值就好了
		if (!map.containsKey(key)) {
			map.put(key, value);
			size++;
		} else {
			map.put(key, value);
		}
	}
	
	// 删除元素
	public V remove (K key) {
		// 删除位置的索引所在的TreeMap
		TreeMap<K, V> map = hashtable[hash(key)];
		
		if (!map.containsKey(key))
			throw new IllegalArgumentException(key + "is not existed!");
		size--;
		return map.remove(key);
	}
	
	// 修改元素, 返回被修改的值
	public V set (K key, V newValue) {
		// 修改位置的索引所在的TreeMap
		TreeMap<K, V> map = hashtable[hash(key)];
		
		if (!map.containsKey(key))
			throw new IllegalArgumentException(key + "is not existed!");
		
		return map.replace(key, newValue);
	}
	
	// 查找元素
	public V get (K key) {
		// 修改位置的索引所在的TreeMap
		TreeMap<K, V> map = hashtable[hash(key)];
		
		return map.get(key);
	}
	
	// 查看是否含有该元素
	public boolean contains (K key) {
		// 修改位置的索引所在的TreeMap
		TreeMap<K, V> map = hashtable[hash(key)];
		
		return map.containsKey(key);
	}
	
	public int getSize () {
		return size;
	}
}



