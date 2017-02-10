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

	public double nextUnif(double low, double high) {
		return (high - low) *  mtrand.genRandRealo0o1() + low;
	}

	public double nextExp(double beta) {
		return -Math.log(this.nextUnif()) / beta;
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

	public double nextNormal(double mu, double sig) {
		return mu + sig * this.nextNormal();
	}

	public double nextTruncNormal(double mu, double sig) {
		double tmp;
		do {
			tmp = this.nextNormal(mu, sig);
		} while (tmp > 0.0);
		return tmp;
	}

	public double nextLogNormal(double mu, double sig) {
		return Math.exp(nextNormal(mu, sig));
	}

	/* Logistic distribution */

	public double nextLogistic() {
    	double x = this.nextUnif();
    	return Math.log(x/(1.0-x));
    }

    public double nextLogistic(double mu, double phi) {
		return mu + phi * this.nextLogistic();
    }

    public double nextTruncLogistic(double mu, double phi) {
		double tmp;
		do {
			tmp = this.nextLogistic(mu, phi);
		} while (tmp > 0.0);
		return tmp;
    }

	public double nextLogLogistic(double mu, double phi) {
		return Math.exp(nextLogistic(mu, phi));
	}
	
	/* Extreme distribution */

	// TODO
	
	/* Weibull distribution */

	public double nextWeibull(double alpha, double beta) {
		return Math.pow(-Math.log(this.nextUnif()) / beta, 1.0/alpha);
	}

}
