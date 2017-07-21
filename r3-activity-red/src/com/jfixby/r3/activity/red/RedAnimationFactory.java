
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.animation.AnimationFactory;
import com.jfixby.r3.activity.api.animation.EventsGroupSpecs;
import com.jfixby.r3.activity.api.animation.EventsSequence;
import com.jfixby.r3.activity.api.animation.EventsSequenceSpecs;
import com.jfixby.r3.activity.api.animation.LayersAnimation;
import com.jfixby.r3.activity.api.animation.LayersAnimationSpecs;
import com.jfixby.r3.activity.api.animation.PositionAnchor;
import com.jfixby.r3.activity.api.animation.PositionsSequence;
import com.jfixby.r3.activity.api.animation.PositionsSequenceSpecs;
import com.jfixby.r3.activity.red.anim.RedAnimationAnchor;
import com.jfixby.r3.activity.red.anim.RedAnimationSequence;
import com.jfixby.r3.activity.red.anim.RedAnimationSequenceSpecs;
import com.jfixby.r3.activity.red.anim.RedEventsGroupSpecs;
import com.jfixby.r3.activity.red.anim.RedEventsSequence;
import com.jfixby.r3.activity.red.anim.RedEventsSequenceSpecs;
import com.jfixby.r3.activity.red.anim.RedLayerAnimation;
import com.jfixby.r3.activity.red.anim.RedLayerAnimationSpecs;

public class RedAnimationFactory implements AnimationFactory {

	private final RedComponentsFactory master;

	public RedAnimationFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public LayersAnimationSpecs newLayersAnimationSpecs () {
		return new RedLayerAnimationSpecs();
	}

	@Override
	public LayersAnimation newLayerAnimation (final LayersAnimationSpecs specs) {
		return new RedLayerAnimation(specs, this.master);
	}

	@Override
	public PositionAnchor newAnchor (final long time_stamp) {
		return new RedAnimationAnchor(time_stamp);
	}

	@Override
	public PositionsSequenceSpecs newPositionsSequenceSpecs () {
		return new RedAnimationSequenceSpecs();
	}

	@Override
	public PositionsSequence newPositionsSequence (final PositionsSequenceSpecs specs) {
		return new RedAnimationSequence(specs, this.master);
	}

	@Override
	public EventsSequenceSpecs newEventsSequenceSpecs () {
		return new RedEventsSequenceSpecs();
	}

	@Override
	public EventsSequence newEventsSequence (final EventsSequenceSpecs specs) {
		return new RedEventsSequence(specs, this.master);
	}

	@Override
	public EventsGroupSpecs newEventsGroupSpecs () {
		return new RedEventsGroupSpecs();
	}

}
