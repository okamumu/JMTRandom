import jp.rel.jmtrandom.MT32;

public class TestMT32 {
	
	public static void test() {
		int[] key = new int [] {0x123, 0x234, 0x345, 0x456};
		MT32 mt = new MT32(key);
		
		System.out.println("1000 outputs of genrand_int32()");
		for (int i=0; i<1000; i++) {
			System.out.printf("%11d ", mt.genRand32());
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
