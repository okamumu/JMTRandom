package com.github.okamumu.jmtrandom;

/**
 * An abstract class to generate 64bit random sequence.
 *
 */
abstract public class MTRand64 implements MTRandInterface {

	/**
	 * The method to generate a random 64bit integer (long)
	 * @return A long integer
	 */
	abstract public long genRand64();

	/**
	 * The method to generate a random 63bit integer which is always a positive value in the case of signed integer.
	 * @return A long integer
	 */
	public long genRand63() {
		return (this.genRand64() >>> 1);
	}

	@Override
	public double genRandRealo0o1() {
	    return ((this.genRand64() >>> 12) + 0.5) * (1.0/4503599627370496.0);
	}

	@Override
	public double genRandRealc0o1() {
	    return (this.genRand64() >>> 11) * (1.0/9007199254740992.0);
	}

	@Override
	public double genRandRealc0c1() {
	    return (this.genRand64() >>> 11) * (1.0/9007199254740991.0);		
	}

}
