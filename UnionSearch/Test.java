package UnionSearch;

import java.util.Random;

public class Test {
	public static void main(String[] args) {
		int operatorNumber = 1000000; // 操作次数
		int dataNumber = 10000; // 数据个数
		
//		test(new UnionFind1(dataNumber), operatorNumber, dataNumber);
		System.out.println("==================");
		test(new UnionFind2(dataNumber), operatorNumber, dataNumber);
		System.out.println("==================");
		test(new UnionFind3(dataNumber), operatorNumber, dataNumber);
	}
	
	public static void test (UnionFind unionFind, int operatorNumber, int dataNumber) {
		Random random = new Random();
		long start = System.currentTimeMillis();
		
		// 并
		for (int i = 0; i < operatorNumber; i++) {
			unionFind.unionElement(random.nextInt(dataNumber), random.nextInt(dataNumber));
		}
		
		// 查
		for (int i = 0; i < operatorNumber; i++) {
			unionFind.isConnected(random.nextInt(dataNumber), random.nextInt(dataNumber));
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("unionFind: " + (end - start)/1000.0 + "s");
	}
}
