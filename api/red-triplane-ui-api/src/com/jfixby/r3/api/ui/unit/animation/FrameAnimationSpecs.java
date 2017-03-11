
package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface FrameAnimationSpecs {

	void setComponent (VisibleComponent child);

	VisibleComponent getComponent ();

	long getFrameTime ();

	void setFrameTime (long parseLong);

}
