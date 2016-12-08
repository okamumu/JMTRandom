import jp.rel.jmtrandom.SFMTParams;

public class TestMT {

	private static SFMTParams[] params = new SFMTParams[] {
			SFMTParams.Params607,
			SFMTParams.Params1279,
			SFMTParams.Params2281,
			SFMTParams.Params4253,
			SFMTParams.Params11213,
			SFMTParams.Params19937,
			SFMTParams.Params44497,
			SFMTParams.Params86243,
			SFMTParams.Params132049,
			SFMTParams.Params216091
	};

	public static void main(String[] args) {
	    long start, end;

	    // test MT32
		System.out.println("----------------------- Test MT32 -----------------------");
	    start = System.currentTimeMillis();
	    TestMT32.test();
		end = System.currentTimeMillis();
		System.out.println();
		System.out.println("MT32 Execution time: " + (end - start)  + "ms");
		System.out.println();

	    // test MT64
		System.out.println("----------------------- Test MT64 -----------------------");
	    start = System.currentTimeMillis();
	    TestMT64.test();
		end = System.currentTimeMillis();
		System.out.println();
		System.out.println("MT64 Execution time: " + (end - start)  + "ms");
		System.out.println();

	    // test SFMT32
		System.out.println("----------------------- Test SFMT32 -----------------------");
		System.out.println();
		
		for (SFMTParams p : params) {
		    start = System.currentTimeMillis();
			TestSFMT32.test(p);
			end = System.currentTimeMillis();
			System.out.println();
			System.out.println("SFMT32 Execution time: " + (end - start)  + "ms " + p.SFMT_IDSTR);
			System.out.println();
		}

	    // test SFMT64
		System.out.println("----------------------- Test SFMT64 -----------------------");
		System.out.println();
		
		for (SFMTParams p : params) {
			System.out.println("----------------------- Generate genrand64 with SFMT64 -----------------------");
			TestSFMT32.test64(p);
			System.out.println();

			start = System.currentTimeMillis();
			TestSFMT64.test(p);
			end = System.currentTimeMillis();
			System.out.println();
			System.out.println("SFMT63 Execution time: " + (end - start)  + "ms " + p.SFMT_IDSTR);
			System.out.println();
		}
	}
}
