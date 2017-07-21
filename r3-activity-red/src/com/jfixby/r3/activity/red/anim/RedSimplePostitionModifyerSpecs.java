
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.animation.AnimationProgressFunction;
import com.jfixby.r3.activity.api.animation.PositionAnchor;
import com.jfixby.r3.activity.api.animation.SimplePostitionModifyerSpecs;
import com.jfixby.r3.activity.api.raster.CanvasComponent;
import com.jfixby.scarabei.api.time.TimeStream;

public class RedSimplePostitionModifyerSpecs implements SimplePostitionModifyerSpecs {

	private boolean looped;
	private CanvasComponent frame;
	private TimeStream systemTime;
	private PositionAnchor begin_anchor;
	private PositionAnchor end_anchor;
	private AnimationProgressFunction animationProgressFunction;

	@Override
	public void setIsLooped (boolean looped) {
		this.looped = looped;
	}

	@Override
	public void setCanvasComponent (CanvasComponent frame) {
		this.frame = frame;
	}

	@Override
	public void setTimeStream (TimeStream clock) {
		this.systemTime = clock;
	}

	@Override
	public void setBeginAnchor (PositionAnchor begin_anchor) {
		this.begin_anchor = begin_anchor;
	}

	@Override
	public void setEndAnchor (PositionAnchor end_anchor) {
		this.end_anchor = end_anchor;
	}

	@Override
	public void setProgressFunction (AnimationProgressFunction animationProgressFunction) {
		this.animationProgressFunction = animationProgressFunction;
	}

	@Override
	public boolean isLooped () {
		return this.looped;
	}

	@Override
	public CanvasComponent getCanvasComponent () {
		return this.frame;
	}

	@Override
	public TimeStream getTimeStream () {
		return this.systemTime;
	}

	@Override
	public PositionAnchor getBeginAnchor () {
		return this.begin_anchor;
	}

	@Override
	public PositionAnchor getEndAnchor () {
		return this.end_anchor;
	}

	@Override
	public AnimationProgressFunction getProgressFunction () {
		return this.animationProgressFunction;
	}

}
