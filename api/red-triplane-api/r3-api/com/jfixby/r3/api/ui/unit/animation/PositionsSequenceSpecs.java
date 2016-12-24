
package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.scarabei.api.collections.List;

public interface PositionsSequenceSpecs extends AnimationSpecs {

	void addAnchor (PositionAnchor animation_anchor);

	//
	List<PositionAnchor> listAnchors ();

	void setComponent (CanvasPositionable frame);

	CanvasPositionable getComponent ();

	boolean componentRequiresAttachment ();

	void setComponentRequiresAttachment (boolean flag);

	void setOnCompleteListener (OnAnimationDoneListener animation_done_listener);

	OnAnimationDoneListener getOnAnimationDoneListener ();
}
