
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.PhysicsCoreRotor;
import com.jfixby.r3.api.physics.PhysicsCoreStatorSpecs;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.time.TimeStream;

public class StatorProperties implements PhysicsCoreStatorSpecs {

	private PhysicsCoreRotor rotor;

	@Override
	public void setRotor (final PhysicsCoreRotor rotor) {
		this.rotor = rotor;
	}

	@Override
	public PhysicsCoreRotor getRotor () {
		return this.rotor;
	}

	@Override
	public long getTimeDeltaPerStepInOurUniverse () {
		return this.how_many_milliseconds_will_pass_per_step_in_our_universe;
	}

	TimeStream time_source = Sys.SystemTime();
	private long critical_overheat_delay = 20000;
	private boolean crash_on_overheat;
	private long how_many_milliseconds_will_pass_per_step_in_our_universe = 1000L / 60L;;

	@Override
	public void setStatorClock (TimeStream time_source) {
		if (time_source == null) {
			time_source = Sys.SystemTime();
		}
		this.time_source = time_source;
	}

	@Override
	public TimeStream getStatorClock () {
		return this.time_source;
	}

	@Override
	public boolean getDropExceptionWhenOverheated () {
		return this.crash_on_overheat;
	}

	@Override
	public long getCriticalOverheatDelay () {
		return this.critical_overheat_delay;
	}

	@Override
	public void setDropExceptionWhenOverheated (final boolean drop) {
		this.crash_on_overheat = drop;

	}

	@Override
	public void setCriticalOverheatDelay (final long delay) {
		if (delay < 0) {
			Err.reportError("CriticalOverheatDelay is too small: " + delay);
		}
		if (delay < 1000) {
			L.e("CriticalOverheatDelay is too small: " + delay);
		}
		this.critical_overheat_delay = delay;

	}

	@Override
	public void setTimeDeltaPerStepInOurUniverse (final long how_many_milliseconds_will_pass_per_step_in_our_universe) {
		if (how_many_milliseconds_will_pass_per_step_in_our_universe < 0) {
			Err.reportError("Negative (" + how_many_milliseconds_will_pass_per_step_in_our_universe + " is not allowed.");
		}
		this.how_many_milliseconds_will_pass_per_step_in_our_universe = how_many_milliseconds_will_pass_per_step_in_our_universe;

	}

}
