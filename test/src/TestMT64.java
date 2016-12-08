import jp.rel.jmtrandom.MT64;

public class TestMT64 {
	
	public static void test() {
		long[] key = new long [] {0x12345L, 0x23456L, 0x34567L, 0x45678L};
		MT64 mt = new MT64(key);
		
		System.out.println("1000 outputs of genrand_int64()");
		for (int i=0; i<1000; i++) {
			System.out.printf("%20d ", mt.genRand64());
			if (i % 5 == 4) {
				System.out.println();
			}
		}
		
		System.out.println();
		System.out.println("1000 outputs of genrand_real2()");
		for (int i=0; i<1000; i++) {
			System.out.printf("%10.8f ", mt.genRandRealc0o1());
			if (i % 5 == 4) {
				System.out.println();
			}
		}		
	}

}
