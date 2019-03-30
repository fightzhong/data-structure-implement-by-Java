package Trie;

import java.util.HashMap;
import java.util.Set;

// leetcode 677 不用字典树实现
public class MapSum {
	HashMap<String, Integer> data;
	
    /** Initialize your data structure here. */
    public MapSum() {
    	data = new HashMap<String, Integer>();
    }
    
    public void insert(String key, int val) {
    	data.put(key, val);
    }
    
    public int sum(String prefix) {
    	int result = 0;
    	Set<String> keySet = data.keySet();
    	for (String s: keySet) {
    		if (s.startsWith(prefix)) {
    			result += data.get(s);
    		}
    	}
    	return result;
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */