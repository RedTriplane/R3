
package org.box2d.r3.jbox2d.f;

import org.box2d.r3.api.Box2DWorldSpecs;

import com.jfixby.scarabei.api.floatn.Float2;

public class JWorldSpecs implements Box2DWorldSpecs {

	private Float2 gravity;
	private boolean doSleep;

	@Override
	public void setGravity (final Float2 gravity) {
		this.gravity = gravity;
	}

	@Override
	public void setDoSleep (final boolean doSleep) {
		this.doSleep = doSleep;
	}

	@Override
	public Float2 getGravity () {
		return this.gravity;
	}

	@Override
	public boolean getDoSleep () {
		return this.doSleep;
	}

}
