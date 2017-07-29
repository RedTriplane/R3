
package org.box2d.r3.gdx;

import org.box2d.r3.api.Box2DWorldSpecs;

import com.jfixby.scarabei.api.floatn.Float2;

public class GdxWorldSpecs implements Box2DWorldSpecs {

	private Float2 gravity;
	private boolean doSleep;

	@Override
	public void setGravity (Float2 gravity) {
		this.gravity = gravity;
	}

	@Override
	public void setDoSleep (boolean doSleep) {
		this.doSleep = doSleep;
	}

	@Override
	public Float2 getGravity () {
		return gravity;
	}

	@Override
	public boolean getDoSleep () {
		return doSleep;
	}

}
