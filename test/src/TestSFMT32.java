import jp.rel.jmtrandom.SFMT32;
import jp.rel.jmtrandom.SFMTParams;

public class TestSFMT32 {

	public static void test(SFMTParams p) {
		System.out.printf("%s\n32 bit generated randoms\n", p.SFMT_IDSTR);

		SFMT32 mt = new SFMT32(1234, p);
		System.out.println("1000 outputs of genrand_int32() from a seed 1234");
		for (int i=0; i<1000; i++) {
			System.out.printf("%11d ", mt.genRand32());
			if (i % 5 == 4) {
				System.out.println();
			}
		}
		System.out.println();

		int[] key = new int [] {0x1234, 0x5678, 0x9abc, 0xdef0};
		mt = new SFMT32(key, p);
		System.out.println("1000 outputs of genrand_int32() from an array {0x1234, 0x5678, 0x9abc, 0xdef0}");
		for (int i=0; i<1000; i++) {
			System.out.printf("%11d ", mt.genRand32());
			if (i % 5 == 4) {
				System.out.println();
			}
		}
	}
	
	// for testing 64bit SFMT
	public static void test64(SFMTParams p) {
		System.out.printf("%s\n64 bit generated randoms with SFMT32\n", p.SFMT_IDSTR);

		SFMT32 mt = new SFMT32(1234, p);
		System.out.println("1000 outputs of genrand_int64() from a seed 1234");
		for (int i=0; i<1000; i++) {
			System.out.printf("%20d ", mt.genRand64());
			if (i % 5 == 4) {
				System.out.println();
			}
		}
		System.out.println();

		int[] key = new int [] {0x1234, 0x5678, 0x9abc, 0xdef0};
		mt = new SFMT32(key, p);
		System.out.println("1000 outputs of genrand_int64() from an array {0x1234, 0x5678, 0x9abc, 0xdef0}");
		for (int i=0; i<1000; i++) {
			System.out.printf("%20d ", mt.genRand64());
			if (i % 5 == 4) {
				System.out.println();
			}
		}
	}

}
