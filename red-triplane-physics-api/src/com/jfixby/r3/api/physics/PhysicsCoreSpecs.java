package com.jfixby.r3.api.physics;

public interface PhysicsCoreSpecs {

	/*
	 * Physics core has clock indicating internal game-time. This setting tells
	 * the clock how to change its indications after each update call.
	 * 
	 * Example value 1000L/25L tells the clock that each core cycle moves time
	 * 40 game-milliseconds ahead.
	 */

	void setTimeDeltaPerStepInTheCore(
			final long how_many_milliseconds_will_pass_per_step_inside_the_core);

	long getTimeDeltaPerStepInTheCore();

	double getGravityX();

	double getGravityY();

	void setGravity(double gravity_x, double gravity_y);

}
