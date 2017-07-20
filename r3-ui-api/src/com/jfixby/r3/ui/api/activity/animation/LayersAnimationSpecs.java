
package com.jfixby.r3.ui.api.activity.animation;

import com.jfixby.scarabei.api.collections.List;

public interface LayersAnimationSpecs extends AnimationSpecs {

	FrameAnimationSpecs newFrameSpecs ();

	void addFrame (FrameAnimationSpecs child);

	List<FrameAnimationSpecs> getFrames ();

	// void setIsSimple(boolean is_simple_animation);

	// boolean isSimple();

// long getFrameTime ();

// void setFrameTime (long single_frame_time);

	// void setIsPositionModifyer(boolean is_positions_modifyer_animation);

	// boolean isPositionModifyer();

	// Anchor addAnchor(long parseLong, double position_x, double position_y);

	// List<Anchor> getAnchors();

}
