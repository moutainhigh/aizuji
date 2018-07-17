///**
// * 
// */
//package org.gz.common.utils;
//
//import java.util.HashSet;
//
//import org.junit.Test;
//
///**
// * @Title:
// * @author hxj
// * @date 2017年12月17日 下午2:22:10
// */
//public class UUIDUtilsTest {
//
//	@Test
//	public void testGenShortUUID() {
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 10; i++) {
//			genShortUUID();
//		}
//		long end = System.currentTimeMillis();
//		System.err.println("take " + (end - start) + " ms");
//	}
//
//	private void genShortUUID() {
//		int repeatCount = 1000000;
//		HashSet<String> set = new HashSet<String>();
//		String uuid = null;
//		for (int i = 0; i < repeatCount; i++) {
//			uuid = UUIDUtils.genShortUUID();
//			if (i == 0) {
//				System.out.println(uuid);
//			}
//			set.add(uuid);
//		}
//		if (set.size() != repeatCount) {
//			System.err.println("重复");
//		}
//	}
//
//	@Test
//	public void testGenDateUUID() {
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 100; i++) {
//			genDateUUID();
//		}
//		long end = System.currentTimeMillis();
//		System.err.println("take " + (end - start) + " ms");
//	}
//
//	private void genDateUUID() {
//		int repeatCount = 1000000;
//		HashSet<String> set = new HashSet<String>();
//		String uuid = null;
//		for (int i = 0; i < repeatCount; i++) {
//			uuid = UUIDUtils.genDateUUID(null);
//			if (i == repeatCount-1) {
//				System.out.println(uuid);
//			}
//			set.add(uuid);
//		}
//		if (set.size() != repeatCount) {
//			System.err.println("重复");
//		}
//	}
//}
