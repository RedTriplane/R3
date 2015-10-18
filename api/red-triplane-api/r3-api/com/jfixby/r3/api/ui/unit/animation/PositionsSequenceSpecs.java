package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.cmns.api.collections.List;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;

public interface PositionsSequenceSpecs extends AnimationSpecs {

	void addAnchor(PositionAnchor animation_anchor);

	//
	List<PositionAnchor> listAnchors();

	void setComponent(CanvasComponent frame);

	CanvasComponent getComponent();

}
