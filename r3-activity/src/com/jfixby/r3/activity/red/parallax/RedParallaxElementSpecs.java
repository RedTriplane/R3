
package com.jfixby.r3.activity.red.parallax;

import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.parallax.ParallaxElementSpecs;

public class RedParallaxElementSpecs implements ParallaxElementSpecs {

	private float multiplier_x = 1f;
	private float multiplier_y = 0f;
	private float multiplier_z = 1f;
	private Component component;

	@Override
	public void setComponent (final Component component) {
		this.component = component;
	}

	@Override
	public Component getComponent () {
		return this.component;
	}

	@Override
	public void setMultiplierX (final float multiplier_x) {
		this.multiplier_x = multiplier_x;
	}

	@Override
	public void setMultiplierY (final float multiplier_y) {
		this.multiplier_y = multiplier_y;
	}

	@Override
	public void setMultiplierZ (final float multiplier_z) {
		this.multiplier_z = multiplier_z;
	}

	@Override
	public float getMultiplierX () {
		return this.multiplier_x;
	}

	@Override
	public float getMultiplierY () {
		return this.multiplier_y;
	}

	@Override
	public float getMultiplierZ () {
		return this.multiplier_z;
	}

}
