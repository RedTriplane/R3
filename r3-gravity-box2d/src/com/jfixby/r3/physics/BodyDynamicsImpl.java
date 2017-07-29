
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.Body;
import com.jfixby.r3.api.physics.BodyDynamicsListener;
import com.jfixby.r3.api.physics.BodyMass;
import com.jfixby.r3.api.physics.BodyPosition;
import com.jfixby.r3.api.physics.ForcesApplicator;
import com.jfixby.r3.physics.body.BodyImpl;

public class BodyDynamicsImpl implements ForcesApplicator {

	final private BodyImpl master;

	public BodyDynamicsImpl (final BodyImpl body) {
		this.master = body;
	}

	public void applyForcesIfNeeded () {
		if (this.dynamics_listener != null) {
			this.dynamics_listener.onApplyForcesCallBack(this);
		}
	}

	private BodyDynamicsListener dynamics_listener;

	public void setDynamicsListener (final BodyDynamicsListener dynamics_listener) {
		this.dynamics_listener = dynamics_listener;
	}

	@Override
	public BodyPosition getBodyPosition () {
		return this.master.warpingData();
	}

	@Override
	public BodyMass getBodyMassValues () {
		return this.master.mass();
	}

	@Override
	public void applyForce (final double force_x, final double force_y) {

		this.master.body_avatar.applyForce(force_x, force_y);
	}

	@Override
	public boolean isBody (final Body body) {
		return this.master == body;
	}

	public BodyDynamicsListener getDynamicsListener () {
		return this.dynamics_listener;
	}
}
