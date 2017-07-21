
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.animation.FrameAnimationSpecs;
import com.jfixby.r3.activity.api.animation.LayersAnimationSpecs;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.time.TimeStream;

public class RedLayerAnimationSpecs implements LayersAnimationSpecs {

	List<FrameAnimationSpecs> frames = Collections.newList();
	// List<Anchor> anchors = JUtils.newList();
	private TimeStream clock;
	private boolean is_looped;
	// private boolean is_simple_animation;

	// private boolean is_positions_modifyer_animation;

	@Override
	public void addFrame (final FrameAnimationSpecs child) {
		this.frames.add(child);
	}

	@Override
	public List<FrameAnimationSpecs> getFrames () {
		return this.frames;
	}

	@Override
	public void setTimeStream (final TimeStream clock) {
		this.clock = clock;
	}

	@Override
	public TimeStream getTimeStream () {
		return this.clock;
	}

	@Override
	public void setIsLooped (final boolean is_looped) {
		this.is_looped = is_looped;
	}

	@Override
	public boolean isLooped () {
		return this.is_looped;
	}

	@Override
	public FrameAnimationSpecs newFrameSpecs () {
		return new RedFrameAnimationSpecs();
	}

}
