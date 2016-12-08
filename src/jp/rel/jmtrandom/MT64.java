/*
 * Mersenne Twister 64bit
 * 
 * The original program, mt19937-64, is provided under 3-clause BSD license.
 * The copyright holders of the original program are Makoto Matsumoto
 * and Takuji Nishimura.
 * 
 * See Lisence.txt of mt19937-64 program.
 * http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html
 * 
 */

package jp.rel.jmtrandom;

public final class MT64 extends MTRand64 {

	private final int NN = 312;
	private final int MM = 156;
	
	private final long MATRIX_A = 0xB5026F5AA96619E9L;
	private final long UM = 0xFFFFFFFF80000000L;
	private final long LM = 0x000000007FFFFFFFL;

	private final long[] mag01 = new long[] {0L, MATRIX_A};

	private final long mt[];
	private int mti;
	
	private MT64() {
		mt = new long [NN];
		mti = NN + 1;
	}
	
	public MT64(long seed) {
		this();
		this.initGenRand64(seed);
	}

	public MT64(long[] initKey) {
		this();
		this.initByArray64(initKey);
	}

	private void initGenRand64(long seed) {
		mt[0] = seed;
		for (mti=1; mti<NN; mti++) {
			mt[mti] = (6364136223846793005L * (mt[mti-1] ^ (mt[mti-1] >>> 62)) + mti);
		}
	}
	
	private void initByArray64(long[] initKey) {
		int keyLength = initKey.length;
		this.initGenRand64(19650218L);
		int i = 1;
		int j = 0;
		int k = (NN > keyLength) ? NN : keyLength;
		for (; k>0; k--) {
			mt[i] = (mt[i] ^ ((mt[i-1] ^(mt[i-1] >>> 62)) * 3935559000370003845L))
					+ initKey[j] + j;
			i++;
			j++;
			if (i >= NN) {
				mt[0] = mt[NN-1];
				i = 1;
			}
			if (j >= keyLength) {
				j = 0;
			}
		}
		for (k=NN-1; k>0; k--) {
			mt[i] = (mt[i] ^ ((mt[i-1] ^ (mt[i-1] >>> 62)) * 2862933555777941757L))
					- i;
			i++;
			if (i >= NN) {
				mt[0] = mt[NN-1];
				i = 1;
			}
		}
		mt[0] = 1L << 63;
	}

	@Override
	public long genRand64() {
		int i;
		long x;
		
		if (mti >= NN) {
			if (mti == NN+1) {
				this.initGenRand64(5489L);
			}
			for (i=0; i<NN-MM; i++) {
				x = (mt[i] & UM) | (mt[i+1] & LM);
				mt[i] = mt[i+MM] ^ (x >>> 1) ^ mag01[(int) (x & 1L)];
			}
			for (; i<NN-1; i++) {
				x = (mt[i] & UM) | (mt[i+1] & LM);
				mt[i] = mt[i + (MM-NN)] ^ (x >>> 1) ^ mag01[(int) (x & 1L)];
			}
			x = (mt[NN-1] & UM) | (mt[0] & LM);
			mt[NN-1] = mt[MM-1] ^ (x >>> 1) ^ mag01[(int) (x & 1L)];
			mti = 0;
		}
		
		x = mt[mti++];
		
	    x ^= (x >>> 29) & 0x5555555555555555L;
	    x ^= (x << 17) & 0x71D67FFFEDA60000L;
	    x ^= (x << 37) & 0xFFF7EEE000000000L;
	    x ^= (x >>> 43);
	    
	    return x;
	}
}
