package Trie;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// leetcode 677 用字典树实现
@SuppressWarnings("unused")
public class MapSumInTrie {
	public static void main(String[] args) {
		MapSumInTrie m = new MapSumInTrie();
		m.insert("apple", 3);
		m.insert("app", 2);
		System.out.println(m.sum("app"));
	}
	
	
	private class Node {
		Map<Character, Node> next;
		boolean isWord;
		int value;
		
		public Node () {
			next = new HashMap<>();
			isWord = false;
			value = 0;
		}
	} 
	
	private Node root;
	
	/** Initialize your data structure here. */
	public MapSumInTrie() {
		root = new Node();
	}
	
	public void insert(String key, int val) {
		Node curNode = root;
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			if (curNode.next.get(c) == null) {
				curNode.next.put(c, new Node());
			}
			curNode = curNode.next.get(c);
		}
		if (!curNode.isWord) {
			curNode.isWord = true;
		}
		curNode.value = val;
	}
	
	public int sum(String prefix) {
		Node curNode = root;
		for (int i = 0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			if (curNode.next.get(c) == null) {
				return 0;
			}
			curNode = curNode.next.get(c);
		}
		
		int[] resultArr = new int[1];
		
		// 到了这一步才开始加值, 因为默认的值都是0, 所以不用判断是否是一个单词
		getSumSearch(curNode, resultArr);
		return resultArr[0];
	}
	
	public void getSumSearch (Node curNode, int[] resultArr) {
		resultArr[0] += curNode.value;
		Set<Character> keySet = curNode.next.keySet();
		for (Character c: keySet) {
			getSumSearch(curNode.next.get(c), resultArr);
		}	
	}
}

