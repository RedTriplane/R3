package com.jfixby.r3.api.ui.unit.animation;


public interface AnimationFactory {

	LayersAnimationSpecs newLayersAnimationSpecs();

	LayersAnimation newLayerAnimation(LayersAnimationSpecs specs);

	// --------------------------------

	PositionAnchor newAnchor(int time_stamp);

	PositionsSequenceSpecs newPositionsSequence();

	PositionsSequence newPositionsSequence(PositionsSequenceSpecs specs);

	// --------------------------------

	EventsSequenceSpecs newEventsSequenceSpecs();

	EventsSequence newEventsSequence(EventsSequenceSpecs specs);

	EventsGroupSpecs newEventsGroupSpecs();

}
