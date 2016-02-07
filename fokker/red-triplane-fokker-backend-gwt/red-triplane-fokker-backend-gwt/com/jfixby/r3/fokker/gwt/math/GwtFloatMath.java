package com.jfixby.r3.fokker.gwt.math;

import com.jfixby.red.math.RedFloatMath;

public class GwtFloatMath extends RedFloatMath {

	@Override
	public double native_sin(double f) {
		return Math.sin(f);
	}

	@Override
	public double native_asin(double f) {
		return Math.asin(f);
	}

	@Override
	public double native_cos(double f) {
		return Math.cos(f);
	}

	@Override
	public double native_pow(double f, double d) {
		return Math.pow(f, d);
	}

	@Override
	public double native_sqrt(double f) {
		return Math.sqrt(f);
	}

	@Override
	public double log(double base, double exp_value) {
		return Math.log(exp_value) / Math.log(base);
	}

}
