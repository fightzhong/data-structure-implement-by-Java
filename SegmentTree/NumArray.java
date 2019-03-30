package SegmentTree;
/* 
 	leetcode: 303 && 307题
 */ 
public class NumArray {
    private SegmentTree<Integer> tree;
    public NumArray(int[] nums) {
        Integer[] arr = new Integer[nums.length];
        for (int i = 0; i < arr.length; i++)
            arr[i] = nums[i];
        this.tree = new SegmentTree<Integer>(arr, (a, b) -> a + b);
    }
    
    public int sumRange(int i, int j) {
        return tree.search(i, j);
    }
    
    public void update(int i, int val) {
        tree.set(i, val);
    }
}

