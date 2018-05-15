package com.github.okamumu.jmtrandom;

/**
 * An abstract class to generate 32bit random sequence.
 *
 */
abstract public class MTRand32 implements MTRandInterface {

	/**
	 * Generate a random 32bit integer value
	 * @return An integer
	 */
	abstract public int genRand32();

	/**
	 * Generate a random 31bit integer value which is used for positive integer of signed integer.
	 * @return An integer
	 */
	public int genRand31() {
		return this.genRand32() >>> 1;
	}

	/*
	 * Generate a random 64bit integer value.
	 * This is used to generate a double value.
	 * @return A long integer
	 */
	private long genRand64() {
		return Integer.toUnsignedLong(this.genRand32());
	}

	@Override
	public double genRandRealo0o1() {
	    return (this.genRand64() + 0.5) * (1.0/4294967296.0);
	}

	@Override
	public double genRandRealc0o1() {
		return this.genRand64() * (1.0/4294967296.0);
	}

	@Override
	public double genRandRealc0c1() {
		return this.genRand64() * (1.0/4294967295.0);
	}

}
