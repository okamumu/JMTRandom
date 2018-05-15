package com.github.okamumu.jmtrandom;

/**
 * An interface to provide a random sequence.
 *
 */
public interface MTRandInterface {

	/**
	 * Generates a random number on (0,1)-real-interval
	 * @return A double value on (0,1)
	 */
	public double genRandRealo0o1();

	/**
	 * Generates a random number on [0,1)-real-interval
	 * @return A double value on [0,1)
	 */
	public double genRandRealc0o1();

	/**
	 * Generates a random number on [0,1]-real-interval
	 * @return A double value on [0,1]
	 */
	public double genRandRealc0c1();

}
