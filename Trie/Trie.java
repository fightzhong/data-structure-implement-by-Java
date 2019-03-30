package Trie;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

// 假设Trie里面存的是英语单词
public class Trie {
	private class Node {
		TreeMap<Character, Node> data;
		boolean isWord;
		
		public Node () {
			data = new TreeMap<Character, Node>();
			isWord = false;
		}
	}
	
	private Node root;
	private int size;
	
	public Trie () {
		root = new Node();
		size = 0;
	}
	
	public void add (String word) {
		Node curNode = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (curNode.data.get(c) == null) { // 如果该节点的map中不存在该映射
				curNode.data.put(c, new Node());
			}
			curNode = curNode.data.get(c);
		}
		
		if (!curNode.isWord) {
			curNode.isWord = true;
			size++;
		}
	}
	
	public boolean contains (String word) {
		Node curNode = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (curNode.data.get(c) == null) {
				return false;
			}
			
			curNode = curNode.data.get(c);
		}
		if (curNode.isWord) 
			return true;
		return false;
	}
	
	public int getSize () {
		return size;
	}
	
	public boolean isEmpty () {
		return size == 0;
	}
	
	// .代表任意字符
	public boolean match(String word) {
        return match(root, word);
    }
    
    public boolean match (Node curNode, String word) {
    	for (int i = 0; i < word.length(); i++) {
    		char c = word.charAt(i);
    		if (c == '.') {
    			String remainStr =  word.substring(i + 1);	
    			Set<Character> set = curNode.data.keySet();
    			
    			ArrayList<Boolean> result = new ArrayList<Boolean>();
    			for (Character character: set) {
    				String searchStr = character + remainStr;
    				boolean b = match(curNode.data.get(character), searchStr);
    				result.add(b);
    			}
    			for (Boolean boo: result) {
    				if (boo == true) {
    					return true;
    				}
    			}
    			return false;
    			
    		} else {
    			if (curNode.data.get(c) == null) {
    				return false;
    			}
    			curNode = curNode.data.get(c);
    		}
    	}
    	return curNode.isWord;
    }
}
