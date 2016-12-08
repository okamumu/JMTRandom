package jp.rel.jmtrandom;

abstract public class MTRand64 implements MTRandInterface {

	abstract public long genRand64();

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
