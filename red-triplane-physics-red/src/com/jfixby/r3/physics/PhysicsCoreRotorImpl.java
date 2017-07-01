
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.PhysicsCoreRotor;
import com.jfixby.r3.api.physics.PhysicsCoreSpecs;
import com.jfixby.scarabei.api.time.TimeStream;

public class PhysicsCoreRotorImpl extends RedPhysicsCoreRotor implements PhysicsCoreRotor, TimeStream, IRotorStatus {

	final private RedPhysicsCore physicsCore;
	long current_cycle = 0;
	long current_game_time = 0L;
	final long game_time_step;

	private IRotorCycleListener cycle_listener = IRotorCycleListener.NULL;;

	public PhysicsCoreRotorImpl (final PhysicsCoreSpecs game_universe_config, RedPhysicsCore physicsCore) {

		this.game_time_step = (game_universe_config.getTimeDeltaPerStepInTheCore());
		this.physicsCore = physicsCore;
		current_game_time = 0L;
	}

	public void onGameUpdate () {
		this.cycle_listener.onPreCycle(this);
		this.current_game_time = this.current_game_time + this.game_time_step;
		this.physicsCore.doStep(this); // time step
		this.cycle_listener.onPostCycle(this);
		this.current_cycle++;
	}

	@Override
	public TimeStream getGameTime () {
		return this;
	}

	@Override
	public long currentTimeMillis () {
		return this.current_game_time;
	}

	public void onSystemUpdate () {

	}

	@Override
	public String toString () {
		return "GameTime [" + this.currentTimeMillis() + "]";
	}

	public void setCycleListener (IRotorCycleListener cycle_listener) {
		this.cycle_listener = cycle_listener;
		if (this.cycle_listener == null) {
			this.cycle_listener = IRotorCycleListener.NULL;
		}
	}

	@Override
	public long getCurrentCycle () {
		return this.current_cycle;
	}

}
