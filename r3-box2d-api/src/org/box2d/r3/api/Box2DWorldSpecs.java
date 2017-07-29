package org.box2d.r3.api;

import com.jfixby.scarabei.api.floatn.Float2;

public interface Box2DWorldSpecs {

	public static final double STEPS_PER_BOX2D_SECOND = 60;

	void setGravity(Float2 gravity);

	void setDoSleep(boolean doSleep);

	public Float2 getGravity();

	boolean getDoSleep();

}
