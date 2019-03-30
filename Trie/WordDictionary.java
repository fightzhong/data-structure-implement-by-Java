package Trie;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
// leetcode: 211
class WordDictionary {
    
    private class Node {
        boolean isWord;
        TreeMap<Character, Node> next;
        
        public Node () {
            this.isWord = false;
            next = new TreeMap<Character, Node>();
        }
    }
    
    private Node root;
    
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new Node();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
    	Node curNode = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (curNode.next.get(c) == null) { // 如果该节点的map中不存在该映射
				curNode.next.put(c, new Node());
			}
			curNode = curNode.next.get(c);
		}
		
		if (!curNode.isWord) {
			curNode.isWord = true;
		}
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return search(root, word);
    }
    
    public boolean search (Node curNode, String word) {
    	for (int i = 0; i < word.length(); i++) {
    		char c = word.charAt(i);
    		if (c == '.') {
    			String remainStr =  word.substring(i + 1);	
    			Set<Character> set = curNode.next.keySet();
    			
    			ArrayList<Boolean> result = new ArrayList<Boolean>();
    			for (Character character: set) {
    				// 之前的错误是: remainStr = character + remainStr 
    				boolean b = search(curNode.next.get(character), remainStr);
    				result.add(b);
    			}
    			for (Boolean boo: result) {
    				if (boo == true) {
    					return true;
    				}
    			}
    			return false;
    			
    		} else {
    			if (curNode.next.get(c) == null) {
    				return false;
    			}
    			curNode = curNode.next.get(c);
    		}
    	}
    	return curNode.isWord;
    }
}
