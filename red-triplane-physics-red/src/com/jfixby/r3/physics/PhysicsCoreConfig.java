
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.PhysicsCoreSpecs;
import com.jfixby.scarabei.api.err.Err;

public class PhysicsCoreConfig implements PhysicsCoreSpecs {

	private long how_many_milliseconds_will_pass_per_step_inside_the_core;
	private double gravity_x;
	private double gravity_y;

	@Override
	public void setTimeDeltaPerStepInTheCore (final long how_many_milliseconds_will_pass_per_step_inside_the_core) {
		if (how_many_milliseconds_will_pass_per_step_inside_the_core < 0) {
			Err.reportError("Negative (" + how_many_milliseconds_will_pass_per_step_inside_the_core + " is not allowed.");
		}
		this.how_many_milliseconds_will_pass_per_step_inside_the_core = how_many_milliseconds_will_pass_per_step_inside_the_core;
	}

	@Override
	public long getTimeDeltaPerStepInTheCore () {
		return this.how_many_milliseconds_will_pass_per_step_inside_the_core;
	}

	@Override
	public double getGravityX () {

		return this.gravity_x;
	}

	@Override
	public double getGravityY () {

		return this.gravity_y;
	}

	@Override
	public void setGravity (final double gravity_x, final double gravity_y) {
		this.gravity_x = gravity_x;
		this.gravity_y = gravity_y;

	}
}
