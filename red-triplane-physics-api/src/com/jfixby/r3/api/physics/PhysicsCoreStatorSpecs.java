package com.jfixby.r3.api.physics;

import com.jfixby.scarabei.api.time.TimeStream;

public interface PhysicsCoreStatorSpecs {

	void setRotor(PhysicsCoreRotor rotor);

	PhysicsCoreRotor getRotor();

	void setStatorClock(TimeStream system);

	TimeStream getStatorClock();

	// ------------------------------

	boolean getDropExceptionWhenOverheated();

	long getCriticalOverheatDelay();

	void setDropExceptionWhenOverheated(boolean drop);

	void setCriticalOverheatDelay(long delay);

	public long getTimeDeltaPerStepInOurUniverse();

	/**
	 * @param how_many_milliseconds_will_pass_per_step_in_our_universe
	 *            This defines how often we update our core.
	 * 
	 *            Example value 1000L/30L will ask the engine to watch time from
	 *            the ITimeSource (specified in the setTimeSource() method) and
	 *            perform core cycles in average 30 times per one second.
	 */
	void setTimeDeltaPerStepInOurUniverse(
			final long how_many_milliseconds_will_pass_per_step_in_our_universe);

}
