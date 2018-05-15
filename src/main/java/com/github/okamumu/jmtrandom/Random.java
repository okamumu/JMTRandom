package com.github.okamumu.jmtrandom;

/**
 * A class to provide a random value following a variety of distributions
 *
 */
public class Random {
	
	private final MTRandInterface mtrand;
	
	private int normal_sw;
	private double normal_save;

	/**
	 * Constructor. The default random generator uses MT64.
	 * @param seed An long integer
	 */
	public Random(long seed) {
		this.mtrand = new MT64(seed);
	}

	/**
	 * Constructor with any random generator.
	 * @param mtrand An object of MTRandInterface which is a random generator.
	 */
	public Random(MTRandInterface mtrand) {
		this.mtrand = mtrand;
	}

	/**
	 * Generate a uniform random number on (0,1)
	 * @return A random number.
	 */
	public double nextUnif() {
		return mtrand.genRandRealo0o1();
	}

	/**
	 * Generate a uniform random number on (min, max)
	 * @param min A double value indicating the minimum of range.
	 * @param max A double value indicating the maximum of range.
	 * @return A random number.
	 */
	public double nextUnif(double min, double max) {
		return (max - min) *  mtrand.genRandRealo0o1() + min;
	}

	/**
	 * Generate an exponential random number.
	 * @param rate A double value indicating the rate parameter.
	 * @return A random number.
	 */
	public double nextExp(double rate) {
		return -Math.log(this.nextUnif()) / rate;
	}

	/**
	 * Generate a standard normal (mean 0, var 1) random number.
	 * @return A random number.
	 */
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

	/**
	 * Generate a normal random variable with mean and standard variation (sd).
	 * @param mean A double value for the mean parameter.
	 * @param sd A double value for the standard variation parameter.
	 * @return A random number.
	 */
	public double nextNormal(double mean, double sd) {
		return mean + sd * this.nextNormal();
	}

	/**
	 * Generate a truncated normal random variable from the original normal distribution with mean and standard variation (sd).
	 * @param mean A double value for the mean parameter of the original normal distribution.
	 * @param sd A double value for the standard variation parameter of the original normal distribution.
	 * @return A random number.
	 */
	public double nextTruncNormal(double mean, double sd) {
		double tmp;
		do {
			tmp = this.nextNormal(mean, sd);
		} while (tmp > 0.0);
		return tmp;
	}

	/**
	 * Generate a log-normal random variable from the original normal distribution with meanlog and standard variation (sdlog).
	 * @param meanlog A double value for the mean parameter of the original normal distribution.
	 * @param sdlog A double value for the standard variation parameter of the original normal distribution.
	 * @return A random number.
	 */
	public double nextLogNormal(double meanlog, double sdlog) {
		return Math.exp(nextNormal(meanlog, sdlog));
	}

	/**
	 * Generate a standard logistic random number.
	 * @return A random number
	 */
	public double nextLogistic() {
    	double x = this.nextUnif();
    	return Math.log(x/(1.0-x));
    }

	/**
	 * Generate a logistic random number with location (loc) and scale parameters.
	 * @param loc A double value for the location
	 * @param scale A double value for the scale
	 * @return A random number
	 */
    public double nextLogistic(double loc, double scale) {
		return loc + scale * this.nextLogistic();
    }

    /**
     * Generate a truncated logistic random number from the original logistic distribution.
     * @param loc A double value of the location parameter of the original logistic distribution.
     * @param scale A double value of the scale parameter of the original logistic distribution.
     * @return A random number
     */
    public double nextTruncLogistic(double loc, double scale) {
		double tmp;
		do {
			tmp = this.nextLogistic(loc, scale);
		} while (tmp > 0.0);
		return tmp;
    }

    /**
     * Generate a log-logistic random number from the original logistic distribution.
     * @param loclog A double value of the location parameter of the original logistic distribution.
     * @param scalelog A double value of the scale parameter of the original logistic distribution.
     * @return A random number
     */
	public double nextLogLogistic(double loclog, double scalelog) {
		return Math.exp(nextLogistic(loclog, scalelog));
	}
	
	/* Extreme distribution */

	// TODO
	
	
	/**
	 * Generate a Weibull random number with shape and scale parameters.
	 * @param shape A double value of the shape parameter.
	 * @param scale A double value of the scale parameter.
	 * @return A random number
	 */
	public double nextWeibull(double shape, double scale) {
		return scale * Math.pow(-Math.log(this.nextUnif()), 1.0/shape);
	}

}
