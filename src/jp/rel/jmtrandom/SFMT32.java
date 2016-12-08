/*
 * SFMT32 (SIMD-oriented Fast Mersenne Twister 32bit version)
 * 
 * The original program, SFMT, is provided under 3-clause BSD license.
 * The copyright holders of the original SFMT programs are Matsuo Saito, 
 * Makoto Matsumoto, Hiroshima University and The University of Tokyo.
 * 
 * See Lisence.txt of SFMT programs.
 * http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/SFMT/
 * 
 */

package jp.rel.jmtrandom;

public final class SFMT32 extends MTRand32 {
	
	private static SFMTParams defaultParams = SFMTParams.Params19937;

	private final SFMTParams params;

	private int idx;
	private final int[] x;

	private final int fSR2;
	private final int fSL2;
	private final int rSR2;
	private final int rSL2;

	private final int pos32;

	public SFMT32(SFMTParams params) {
		this.params = params;
		fSR2 = 8 * params.SFMT_SR2;
		fSL2 = 8 * params.SFMT_SL2;
		rSR2 = 32 - fSR2;
		rSL2 = 32 - fSL2;
		pos32 = 4 * params.SFMT_POS1;
		x = new int [params.SFMT_N32];
	}

	public SFMT32(int seed, SFMTParams params) {
		this(params);
		this.initGenRand(seed);
	}
	
	public SFMT32(int[] initKey, SFMTParams params) {
		this(params);
		this.initByArray(initKey);
	}
	
	public SFMT32(int seed) {
		this(defaultParams);
		this.initGenRand(seed);
	}
	
	public SFMT32(int[] initKey) {
		this(defaultParams);
		this.initByArray(initKey);
	}
	
	public String getID() {
		return params.SFMT_IDSTR;
	}

	private void initGenRand(int seed) {
		x[0] = seed;
		for (int i=1; i<params.SFMT_N32; i++) {
			x[i] = 1812433253 * (x[i-1] ^ (x[i-1] >>> 30)) + i;
		}
		idx = params.SFMT_N32;
		this.periodCertification();
	}

	private void periodCertification() {
		int[] parity = new int [] {params.SFMT_PARITY1, params.SFMT_PARITY2, params.SFMT_PARITY3, params.SFMT_PARITY4};

		int inner = 0;
		for (int i=0; i<4; i++) {
			inner ^= x[i] & parity[i];
		}
		for (int i=16; i>0; i>>>=1) {
			inner ^= inner >>> i;
		}
		inner &= 1;

		if (inner == 1) {
			return;
		}

		for (int i=0; i<4; i++) {
			int work = 1;
			for (int j=0; j<32; j++) {
				if ((work & parity[i]) != 0) {
					x[i] ^= work;
					return;
				}
				work = work << 1;
			}
		}
	}

	private int func1(int x) {
		return (x ^ (x >>> 27)) * 1664525;
	}

	private int func2(int x) {
		return (x ^ (x >>> 27)) * 1566083941;
	}

	private void initByArray(int[] initKey) {
		int keyLength = initKey.length;
		int i, j, r, lag, mid, count;
		int size = params.SFMT_N * 4;

		if (size >= 623) {
			lag = 11;
		} else if (size >= 68) {
			lag = 7;
		} else if (size >= 39) {
			lag = 5;
		} else {
			lag = 3;
		}
		mid = (size - lag) / 2;

		for (i=0; i<params.SFMT_N32; i++) {
			x[i] = 0x8b8b8b8b;
		}
		if (keyLength + 1 > params.SFMT_N32) {
			count = keyLength + 1;
		} else {
			count = params.SFMT_N32;
		}

		r = func1(x[0] ^ x[mid] ^ x[params.SFMT_N32 - 1]);
		x[mid] += r;
		r += keyLength;
		x[mid + lag] += r;
		x[0] = r;

		count--;
		for (i = 1, j = 0; (j < count) && (j < keyLength); j++) {
			r = func1(x[i] ^ x[(i + mid) % params.SFMT_N32]
					^ x[(i + params.SFMT_N32 - 1) % params.SFMT_N32]);
			x[(i + mid) % params.SFMT_N32] += r;
			r += initKey[j] + i;
			x[(i + mid + lag) % params.SFMT_N32] += r;
			x[i] = r;
			i = (i + 1) % params.SFMT_N32;
		}
		for (; j < count; j++) {
			r = func1(x[i] ^ x[(i + mid) % params.SFMT_N32]
					^ x[(i + params.SFMT_N32 - 1) % params.SFMT_N32]);
			x[(i + mid) % params.SFMT_N32] += r;
			r += i;
			x[(i + mid + lag) % params.SFMT_N32] += r;
			x[i] = r;
			i = (i + 1) % params.SFMT_N32;
		}
		for (j = 0; j < params.SFMT_N32; j++) {
			r = func2(x[i] + x[(i + mid) % params.SFMT_N32]
					+ x[(i + params.SFMT_N32 - 1) % params.SFMT_N32]);
			x[(i + mid) % params.SFMT_N32] ^= r;
			r -= i;
			x[(i + mid + lag) % params.SFMT_N32] ^= r;
			x[i] = r;
			i = (i + 1) % params.SFMT_N32;
		}
		idx = params.SFMT_N32;
		periodCertification();
	}

	private void genRandAll() {
		int r1 = params.SFMT_N32 - 8;
		int r2 = params.SFMT_N32 - 4;
		for (int i=0, pos=pos32; i < params.SFMT_N32; i+=4, pos+=4) {
			if (pos >= params.SFMT_N32) {
				pos = 0;
			}
//			if (i==0)
//			System.out.printf("%08x %08x %08x %08x | %08x %08x %08x %08x \n", x[i+3], x[i+2], x[i+1], x[i], x[pos+3], x[pos+2], x[pos+1], x[pos]);
//			x[i+3] = ((x[pos+3] >>> params.SFMT_SR1) & params.SFMT_MSK4);
//			x[i+2] = ((x[pos+2] >>> params.SFMT_SR1) & params.SFMT_MSK3);
//			x[i+1] = ((x[pos+1] >>> params.SFMT_SR1) & params.SFMT_MSK2);
//			x[i] = ((x[pos] >>> params.SFMT_SR1) & params.SFMT_MSK1);
			x[i+3] ^= ((x[i+3] << fSL2) | (x[i+2] >>> rSL2))
					^ ((x[pos+3] >>> params.SFMT_SR1) & params.SFMT_MSK4)
					^ (x[r1+3] >>> fSR2)
					^ (x[r2+3] << params.SFMT_SL1);
			x[i+2] ^= ((x[i+2] << fSL2) | (x[i+1] >>> rSL2))
					^ ((x[pos+2] >>> params.SFMT_SR1) & params.SFMT_MSK3)
					^ ((x[r1+2] >>> fSR2) | (x[r1+3] << rSR2))
					^ (x[r2+2] << params.SFMT_SL1);
			x[i+1] ^= ((x[i+1] << fSL2) | (x[i] >>> rSL2))
					^ ((x[pos+1] >>> params.SFMT_SR1) & params.SFMT_MSK2)
					^ ((x[r1+1] >>> fSR2) | (x[r1+2] << rSR2))
					^ (x[r2+1] << params.SFMT_SL1);
			x[i] ^= (x[i] << fSL2)
					^ ((x[pos] >>> params.SFMT_SR1) & params.SFMT_MSK1)
					^ ((x[r1] >>> fSR2) | (x[r1+1] << rSR2))
					^ (x[r2] << params.SFMT_SL1);
//			if (i==0)
//			System.out.printf("%08x %08x %08x %08x\n", x[i+3], x[i+2], x[i+1], x[i]);
			r1 = r2;
			r2 = i;
		}
	}

	@Override
	public int genRand32() {
		if (idx >= params.SFMT_N32) {
			genRandAll();
			idx = 0;
		}
		return x[idx++];
	}
	
	// for test
	public long genRand64() {
		if (idx >= params.SFMT_N32) {
			genRandAll();
			idx = 0;
		}
		int r1 = x[idx];
		int r2 = x[idx + 1];
		idx += 2;
		return (Integer.toUnsignedLong(r2) << 32) | Integer.toUnsignedLong(r1);
	}

}
