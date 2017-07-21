
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.animation.AnimationLifecycleListener;
import com.jfixby.r3.activity.api.animation.PositionAnchor;
import com.jfixby.r3.activity.api.animation.PositionsSequenceSpecs;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.geometry.projections.RotateAndOffsetProjection;
import com.jfixby.scarabei.api.time.TimeStream;

public class RedAnimationSequenceSpecs implements PositionsSequenceSpecs {

	List<PositionAnchor> anchors = Collections.newList();
	private boolean is_looped;
	private Layer frame;
	private TimeStream clock;
	private AnimationLifecycleListener animation_done_listener;
	private boolean use_bezier;
	private RotateAndOffsetProjection projection;

	@Override
	public boolean isLooped () {
		return this.is_looped;
	}

	@Override
	public void setIsLooped (final boolean is_looped) {
		this.is_looped = is_looped;
	}

	@Override
	public void addAnchor (final PositionAnchor animation_anchor) {
		this.anchors.add(animation_anchor);
	}

	@Override
	public List<PositionAnchor> listAnchors () {
		return this.anchors;
	}

	@Override
	public TimeStream getTimeStream () {
		return this.clock;
	}

	@Override
	public void setTimeStream (final TimeStream time) {
		this.clock = time;
	}

	@Override
	public void setOnCompleteListener (final AnimationLifecycleListener animation_done_listener) {
		this.animation_done_listener = animation_done_listener;
	}

	@Override
	public AnimationLifecycleListener getOnAnimationDoneListener () {
		return this.animation_done_listener;
	}

	@Override
	public void setUseSpline (final boolean use_bezier) {
		this.use_bezier = use_bezier;
	}

	@Override
	public boolean useSpline () {
		return this.use_bezier;
	}

	@Override
	public void setFramesContainer (final Layer frame) {
		this.frame = frame;
	}

	@Override
	public Layer getFramesContainer () {
		return this.frame;
	}

	@Override
	public void setProjection (final RotateAndOffsetProjection projection) {
		this.projection = projection;
	}

	@Override
	public RotateAndOffsetProjection getProjection () {
		return this.projection;
	}

}
