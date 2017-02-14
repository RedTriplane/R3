
package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.geometry.RotateAndOffsetProjection;

public interface PositionsSequenceSpecs extends AnimationSpecs {

	void addAnchor (PositionAnchor animation_anchor);

	//
	List<PositionAnchor> listAnchors ();

	void setFramesContainer (Layer frame);

	Layer getFramesContainer ();

	boolean componentRequiresAttachment ();

	void setComponentRequiresAttachment (boolean flag);

	void setOnCompleteListener (OnAnimationDoneListener animation_done_listener);

	OnAnimationDoneListener getOnAnimationDoneListener ();

	void setUseSpline (boolean use_spline);

	boolean useSpline ();

	void setProjection (RotateAndOffsetProjection projection);

	RotateAndOffsetProjection getProjection ();

}
