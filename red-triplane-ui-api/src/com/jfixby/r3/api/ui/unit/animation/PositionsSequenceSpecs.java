
package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.geometry.projections.RotateAndOffsetProjection;

public interface PositionsSequenceSpecs extends AnimationSpecs {

	void addAnchor (PositionAnchor animation_anchor);

	//
	List<PositionAnchor> listAnchors ();

	void setFramesContainer (Layer frame);

	Layer getFramesContainer ();

	void setOnCompleteListener (AnimationLifecycleListener animation_done_listener);

	AnimationLifecycleListener getOnAnimationDoneListener ();

	void setUseSpline (boolean use_spline);

	boolean useSpline ();

	void setProjection (RotateAndOffsetProjection projection);

	RotateAndOffsetProjection getProjection ();

}
