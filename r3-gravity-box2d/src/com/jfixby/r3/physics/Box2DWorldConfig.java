
package com.jfixby.r3.physics;

import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class Box2DWorldConfig {

	@Override
	public String toString () {
		return "Box2DWorldConfig [gravity=" + gravity + ", stepping=" + stepping + ", velocityIterations=" + velocityIterations
			+ ", positionIterations=" + positionIterations + "]";
	}

	public static final double STEPS_PER_BOX2D_SECOND = FokkerConfig.STEPS_IN_BOX2D_SECOND;

	private final boolean autoclearForces = true;

	final Float2 gravity = Geometry.newFloat2(0f, 0f);

	public Float2 getGravityVector () {
		return gravity;
	}

	double stepping = 1d / STEPS_PER_BOX2D_SECOND;
	int velocityIterations = 16;
	int positionIterations = 16;

	private boolean doSleep;

	public boolean allowSleepingBodies () {
		return true;
	}

	public int getPositionIterations () {
		return positionIterations;
	}

	public int getVelocityIterations () {
		return velocityIterations;
	}

	public double getBox2DTimeStepping () {
		return stepping;
	}

	public void setGravity (final double gravityX, final double gravityY) {
		gravity.setX(gravityX);
		gravity.setY(gravityY);
	}

	public boolean getAutoclearForces () {
		return this.autoclearForces;
	}

}
