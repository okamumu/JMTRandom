package com.github.okamumu.jmtrandom;

abstract public class MTRand32 implements MTRandInterface {

	abstract public int genRand32();

	public int genRand31() {
		return this.genRand32() >>> 1;
	}

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
