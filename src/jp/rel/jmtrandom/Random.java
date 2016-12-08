package jp.rel.jmtrandom;

public class Random {
	
	private final MTRandInterface mtrand;
	
	private int normal_sw;
	private double normal_save;

	public Random(long seed) {
		this.mtrand = new MT64(seed);
	}

	public Random(MTRandInterface mtrand) {
		this.mtrand = mtrand;
	}

	public double nextUnif() {
		return mtrand.genRandRealo0o1();
	}

	public double nextNormal() {
		if (normal_sw == 0) {
			double t = Math.sqrt(-2 * Math.log(this.nextUnif()));
			double u = Math.PI * this.nextUnif();
			normal_save = t * Math.sin(u);
			normal_sw = 1;
			return t * Math.cos(u);
		} else {
			normal_sw = 0;
			return normal_save;
		}
	}

	public double nextNormal(double mu, double sig) {
		return mu + sig * this.nextNormal();
	}

	public double nextExp(double r) {
		return -Math.log(this.nextUnif()) / r;
	}

}
