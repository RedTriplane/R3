
package com.jfixby.r3.api.ui.unit.animation;

public interface AnimationFactory {

	LayersAnimationSpecs newLayersAnimationSpecs ();

	LayersAnimation newLayerAnimation (LayersAnimationSpecs specs);

	// --------------------------------

	PositionAnchor newAnchor (long time_stamp);

	PositionsSequenceSpecs newPositionsSequenceSpecs ();

	PositionsSequence newPositionsSequence (PositionsSequenceSpecs specs);

	// --------------------------------

	EventsSequenceSpecs newEventsSequenceSpecs ();

	EventsSequence newEventsSequence (EventsSequenceSpecs specs);

	EventsGroupSpecs newEventsGroupSpecs ();

}
