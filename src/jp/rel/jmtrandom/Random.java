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

	public double nextUnif(double min, double max) {
		return (max - min) *  mtrand.genRandRealo0o1() + min;
	}

	public double nextExp(double rate) {
		return -Math.log(this.nextUnif()) / rate;
	}

	/* Normal distribution */

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

	public double nextNormal(double mean, double sd) {
		return mean + sd * this.nextNormal();
	}

	public double nextTruncNormal(double mean, double sd) {
		double tmp;
		do {
			tmp = this.nextNormal(mean, sd);
		} while (tmp > 0.0);
		return tmp;
	}

	public double nextLogNormal(double mean, double sd) {
		return Math.exp(nextNormal(mean, sd));
	}

	/* Logistic distribution */

	public double nextLogistic() {
    	double x = this.nextUnif();
    	return Math.log(x/(1.0-x));
    }

    public double nextLogistic(double loc, double scale) {
		return loc + scale * this.nextLogistic();
    }

    public double nextTruncLogistic(double loc, double scale) {
		double tmp;
		do {
			tmp = this.nextLogistic(loc, scale);
		} while (tmp > 0.0);
		return tmp;
    }

	public double nextLogLogistic(double loc, double scale) {
		return Math.exp(nextLogistic(loc, scale));
	}
	
	/* Extreme distribution */

	// TODO
	
	/* Weibull distribution */
	
	public double nextWeibull(double shape, double scale) {
		return scale * Math.pow(-Math.log(this.nextUnif()), 1.0/shape);
	}

}
