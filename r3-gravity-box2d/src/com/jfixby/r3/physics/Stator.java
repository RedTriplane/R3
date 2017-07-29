
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.PhysicsCoreStator;
import com.jfixby.r3.api.physics.PhysicsCoreStatorSpecs;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.time.TimeStream;

public class Stator implements PhysicsCoreStator {

	private final long CRITICAL_DELAY;
	final private RedPhysicsCoreRotor rotor;
	final private TimeStream time_source;
	final long time_step;

	private long current_time;
	private long last_updated_state_time;
	private boolean is_not_the_first_call;

	private long loop_started_time;
	private long system_time;
	private final boolean drop_exception_when_overheated;
	private long step_start_time;

	public Stator (final PhysicsCoreStatorSpecs stator_properties) {
		this.rotor = (RedPhysicsCoreRotor)stator_properties.getRotor();
		this.time_source = stator_properties.getStatorClock();
		this.time_step = stator_properties.getTimeDeltaPerStepInOurUniverse();
		this.read_current_time();
		this.is_not_the_first_call = false;
		this.drop_exception_when_overheated = stator_properties.getDropExceptionWhenOverheated();
		this.CRITICAL_DELAY = stator_properties.getCriticalOverheatDelay();
	}

	private void read_current_time () {
		this.current_time = this.time_source.currentTimeMillis();
	}

	private long systime () {
		return Sys.SystemTime().currentTimeMillis();
	}

	private void choke () {
		this.breathe();
		L.d("Rotor choked...");
	}

	private boolean cant_hold_breath_anymore () {
		return (this.system_time - this.breath_holded_from) >= BREATH_LIMIT;
	}

	private void breathe () {
		this.breath_holded = false;
	}

	private void hold_breath () {
		if (this.breath_holded) {
			return;
		} else {
			this.breath_holded = true;
			this.breath_holded_from = this.system_time;
		}
	}

	boolean breath_holded = false;
	private long breath_holded_from;
	private static final long BREATH_LIMIT = 1000;

	private boolean check_overheat_and_act () {
		if (this.system_time - this.loop_started_time >= this.CRITICAL_DELAY) {

			final long steps = (this.current_time - this.last_updated_state_time) / this.time_step;

			final long lag = (this.system_time - this.loop_started_time);
			L.e("PhysicsCoreStator overheated! Current update lag is " + lag + "ms");
			L.e("             steps left", steps);
			L.e("           current_time", this.current_time);
			L.e("              time_step", this.time_step);
			L.e("              step_time", (this.systime() - this.step_start_time));
			L.e("last_updated_state_time", this.last_updated_state_time);
			L.e("      current_game_time", this.rotor.getGameTime().currentTimeMillis());
			if (this.drop_exception_when_overheated) {
				Err.reportError("PhysicsCoreStator overheated! Current update lag is " + lag + "ms");
			}

			this.hold_breath();
			if (this.cant_hold_breath_anymore()) {
				this.choke();
				return true;
			}
			return false;
		} else {
			this.breathe();
			return false;
		}

	}

	public void onUpdate () {
		this.rotor.onSystemUpdate();
		if (this.is_not_the_first_call) {
			this.read_current_time();

			this.loop_started_time = this.systime();

			while (this.last_updated_state_time < this.current_time) {
				this.step_start_time = this.systime();
				this.rotor.onGameUpdate();

				this.last_updated_state_time = this.last_updated_state_time + this.time_step;
				this.system_time = this.systime();
				final boolean rest = this.check_overheat_and_act();
				if (rest) {
					return;
				}

			}

		} else {
			this.read_current_time();
			this.last_updated_state_time = this.current_time;
			this.rotor.onGameUpdate();
			this.is_not_the_first_call = true;
		}

	}
}
