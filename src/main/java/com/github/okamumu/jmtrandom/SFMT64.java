/*
 * SFMT64 (SIMD-oriented Fast Mersenne Twister 64bit version)
 * 
 * The original program, SFMT, is provided under 3-clause BSD license.
 * The copyright holders of the original SFMT programs are Matsuo Saito, 
 * Makoto Matsumoto, Hiroshima University and The University of Tokyo.
 * 
 * See Lisence.txt of SFMT programs.
 * http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/SFMT/
 * 
 */

package com.github.okamumu.jmtrandom;

public class SFMT64 extends MTRand64 {

	private static SFMTParams defaultParams = SFMTParams.Params19937;

	private final SFMTParams params;

	private int idx;
	private long[] x;

	private final int fSR2;
	private final int fSL2;
	private final int rSR2;
	private final int rSL2;

	private final int pos64;
	
	private final long msk1;
	private final long msk2;

	private final long msk3;

	private static final long int2toLong(int u, int l) {
//		return ((long) u << 32) | (0x00000000ffffffffL & l);
		return Integer.toUnsignedLong(u) << 32 | Integer.toUnsignedLong(l);
	}

	public SFMT64(SFMTParams params) {
		this.params = params;
		fSR2 = 8 * params.SFMT_SR2;
		fSL2 = 8 * params.SFMT_SL2;
		rSR2 = 64 - fSR2;
		rSL2 = 64 - fSL2;
		pos64 = 2 * params.SFMT_POS1;
		
		int tmp;
		tmp = 0xffffffff;
		tmp >>>= params.SFMT_SR1;
		msk1 = int2toLong(params.SFMT_MSK2 & tmp, params.SFMT_MSK1 & tmp);
		msk2 = int2toLong(params.SFMT_MSK4 & tmp, params.SFMT_MSK3 & tmp);

		tmp = 0xffffffff;
		tmp <<= params.SFMT_SL1;
		msk3 = int2toLong(tmp, tmp);

		x = new long [params.SFMT_N64];
	}

	public SFMT64(int seed, SFMTParams params) {
		this(params);
		this.initGenRand(seed);
	}
	
	public SFMT64(int[] initKey, SFMTParams params) {
		this(params);
		this.initByArray(initKey);
	}

	public SFMT64(int seed) {
		this(defaultParams);
		this.initGenRand(seed);
	}
	
	public SFMT64(int[] initKey) {
		this(defaultParams);
		this.initByArray(initKey);
	}
	
	public String getID() {
		return params.SFMT_IDSTR;
	}

	private void initGenRand(int seed) {
		int tmpu, tmpl;
		tmpu = seed;
		tmpl = 1812433253 * (tmpu ^ (tmpu >>> 30)) + 1;
		x[0] = int2toLong(tmpl, tmpu);

		for (int i=2, k=1; i<params.SFMT_N32; i+=2, k++) {
			tmpu = 1812433253 * (tmpl ^ (tmpl >>> 30)) + i;
			tmpl = 1812433253 * (tmpu ^ (tmpu >>> 30)) + i + 1;
			x[k] = int2toLong(tmpl, tmpu);
		}
		idx = params.SFMT_N64;
		this.periodCertification();
	}

	private void periodCertification() {
		long[] parity = new long [] {int2toLong(params.SFMT_PARITY2, params.SFMT_PARITY1), int2toLong(params.SFMT_PARITY4, params.SFMT_PARITY3)};

		int inner = 0;
		for (int i=0; i<2; i++) {
			inner ^= (int) x[i];
			inner ^= (int) (x[i] >>> 32);
		}
		for (int i=16; i>0; i>>>=1) {
			inner ^= inner >>> i;
		}
		inner &= 1;

		if (inner == 1) {
			return;
		}

		for (int i=0; i<2; i++) {
			long work = 0x0000000000000001L;
			for (int j=0; j<64; j++) {
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
		int[] state = new int [x.length * 2];
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
			state[i] = 0x8b8b8b8b;
		}
		if (keyLength + 1 > params.SFMT_N32) {
			count = keyLength + 1;
		} else {
			count = params.SFMT_N32;
		}

		r = func1(state[0] ^ state[mid] ^ state[params.SFMT_N32 - 1]);
		state[mid] += r;
		r += keyLength;
		state[mid + lag] += r;
		state[0] = r;

		count--;
		for (i = 1, j = 0; (j < count) && (j < keyLength); j++) {
			r = func1(state[i] ^ state[(i + mid) % params.SFMT_N32]
					^ state[(i + params.SFMT_N32 - 1) % params.SFMT_N32]);
			state[(i + mid) % params.SFMT_N32] += r;
			r += initKey[j] + i;
			state[(i + mid + lag) % params.SFMT_N32] += r;
			state[i] = r;
			i = (i + 1) % params.SFMT_N32;
		}
		for (; j < count; j++) {
			r = func1(state[i] ^ state[(i + mid) % params.SFMT_N32]
					^ state[(i + params.SFMT_N32 - 1) % params.SFMT_N32]);
			state[(i + mid) % params.SFMT_N32] += r;
			r += i;
			state[(i + mid + lag) % params.SFMT_N32] += r;
			state[i] = r;
			i = (i + 1) % params.SFMT_N32;
		}
		for (j = 0; j < params.SFMT_N32; j++) {
			r = func2(state[i] + state[(i + mid) % params.SFMT_N32]
					+ state[(i + params.SFMT_N32 - 1) % params.SFMT_N32]);
			state[(i + mid) % params.SFMT_N32] ^= r;
			r -= i;
			state[(i + mid + lag) % params.SFMT_N32] ^= r;
			state[i] = r;
			i = (i + 1) % params.SFMT_N32;
		}
		
		for (int v=0, k=0; v<state.length; v+=2, k++) {
			x[k] = int2toLong(state[v+1], state[v]);
		}
		idx = params.SFMT_N64;
		periodCertification();
	}

	private void genRandAll() {
		int r1 = params.SFMT_N64 - 4;
		int r2 = params.SFMT_N64 - 2;
		for (int i=0, pos=pos64; i < params.SFMT_N64; i+=2, pos+=2) {
			if (pos >= params.SFMT_N64) {
				pos = 0;
			}
//			if (i==0)
//			System.out.printf("%016x %016x %016x %016x\n", state[i+1], state[i], state[pos+1], state[pos]);
////			state[i+1] = ((state[pos+1] >>> params.SFMT_SR1) & msk2);
////			state[i] = ((state[pos] >>> params.SFMT_SR1) & msk1);
			x[i+1] ^= ((x[i+1] << fSL2) | (x[i] >>> rSL2))
					^ ((x[pos+1] >>> params.SFMT_SR1) & msk2)
					^ (x[r1+1] >>> fSR2)
					^ ((x[r2+1] << params.SFMT_SL1) & msk3);
			x[i] ^= (x[i] << fSL2)
					^ ((x[pos] >>> params.SFMT_SR1) & msk1)
					^ ((x[r1] >>> fSR2) | (x[r1+1] << rSR2))
					^ ((x[r2] << params.SFMT_SL1) & msk3);
			r1 = r2;
			r2 = i;
//			if (i==0)
//			System.out.printf("%016x %016x\n", state[i+1], state[i]);
		}
	}

	public long genRand64() {
		if (idx >= params.SFMT_N64) {
			genRandAll();
			idx = 0;
		}
		return x[idx++];
	}
}
