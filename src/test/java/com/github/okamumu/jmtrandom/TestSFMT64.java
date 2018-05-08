// package com.github.okamumu.jmtrandom;
//
// public class TestSFMT64 {
//
// 	public static void test(SFMTParams p) {
// 		System.out.printf("%s\n64 bit generated randoms with SFMT64\n", p.SFMT_IDSTR);
//
// 		SFMT64 mt = new SFMT64(1234, p);
// 		System.out.println("1000 outputs of genrand_int64() from a seed 1234");
// 		for (int i=0; i<1000; i++) {
// 			System.out.printf("%20d ", mt.genRand64());
// 			if (i % 5 == 4) {
// 				System.out.println();
// 			}
// 		}
// 		System.out.println();
//
// 		int[] key = new int [] {0x1234, 0x5678, 0x9abc, 0xdef0};
// 		mt = new SFMT64(key, p);
// 		System.out.println("1000 outputs of genrand_int64() from an array {0x1234, 0x5678, 0x9abc, 0xdef0}");
// 		for (int i=0; i<1000; i++) {
// 			System.out.printf("%20d ", mt.genRand64());
// 			if (i % 5 == 4) {
// 				System.out.println();
// 			}
// 		}
// 	}
//
// }
