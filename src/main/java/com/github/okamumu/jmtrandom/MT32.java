/*
 * Mersenne Twister 32bit
 * 
 * The original program, mt19937ar, is provided under 3-clause BSD license.
 * The copyright holders of the original program are Makoto Matsumoto
 * and Takuji Nishimura.
 * 
 * See Lisence.txt of mt19937ar program.
 * http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html
 * 
 */

package com.github.okamumu.jmtrandom;

public final class MT32 extends MTRand32 {

	private final int NN = 624;
	private final int MM = 397;
	
	private final int MATRIX_A = 0x9908b0df;
	private final int UM = 0x80000000;
	private final int LM = 0x7fffffff;

	private final int[] mag01 = new int[] {0, MATRIX_A};

	private final int mt[];
	private int mti;
	
	private MT32() {
		mt = new int [NN];
		mti = NN + 1;
	}
	
	public MT32(int seed) {
		this();
		this.initGenRand(seed);
	}

	public MT32(int[] initKey) {
		this();
		this.initByArray(initKey);
	}

	private void initGenRand(int seed) {
		mt[0] = seed;
		for (mti=1; mti<NN; mti++) {
			mt[mti] = (1812433253 * (mt[mti-1] ^ (mt[mti-1] >>> 30)) + mti);
		}
	}
	
	private void initByArray(int[] initKey) {
		int keyLength = initKey.length;
		this.initGenRand(19650218);
		int i = 1;
		int j = 0;
		int k = (NN > keyLength) ? NN : keyLength;
		for (; k>0; k--) {
			mt[i] = (mt[i] ^ ((mt[i-1] ^(mt[i-1] >>> 30)) * 1664525))
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
			mt[i] = (mt[i] ^ ((mt[i-1] ^ (mt[i-1] >>> 30)) * 1566083941))
					- i;
			i++;
			if (i >= NN) {
				mt[0] = mt[NN-1];
				i = 1;
			}
		}
		mt[0] = 0x80000000;
	}

	@Override
	public int genRand32() {
		int i;
		int x;
		
		if (mti >= NN) {
			if (mti == NN+1) {
				this.initGenRand(5489);
			}
			for (i=0; i<NN-MM; i++) {
				x = (mt[i] & UM) | (mt[i+1] & LM);
				mt[i] = mt[i+MM] ^ (x >>> 1) ^ mag01[(x & 1)];
			}
			for (; i<NN-1; i++) {
				x = (mt[i] & UM) | (mt[i+1] & LM);
				mt[i] = mt[i + (MM-NN)] ^ (x >>> 1) ^ mag01[x & 1];
			}
			x = (mt[NN-1] & UM) | (mt[0] & LM);
			mt[NN-1] = mt[MM-1] ^ (x >>> 1) ^ mag01[x & 1];
			mti = 0;
		}
		
		x = mt[mti++];
		
	    x ^= (x >>> 11);
	    x ^= (x << 7) & 0x9d2c5680;
	    x ^= (x << 15) & 0xefc60000;
	    x ^= (x >>> 18);
	    
	    return x;
	}
}
