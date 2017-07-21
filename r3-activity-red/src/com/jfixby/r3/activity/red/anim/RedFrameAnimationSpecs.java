
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.animation.FrameAnimationSpecs;
import com.jfixby.r3.activity.api.layer.VisibleComponent;

public class RedFrameAnimationSpecs implements FrameAnimationSpecs {

	private VisibleComponent child;
	private long frameTime;

	@Override
	public void setComponent (final VisibleComponent child) {
		this.child = child;
	}

	@Override
	public VisibleComponent getComponent () {
		return this.child;
	}

	@Override
	public long getFrameTime () {
		return this.frameTime;
	}

	@Override
	public void setFrameTime (final long parseLong) {
		this.frameTime = parseLong;
	}

}
