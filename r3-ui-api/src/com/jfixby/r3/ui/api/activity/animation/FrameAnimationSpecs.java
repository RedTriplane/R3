
package com.jfixby.r3.ui.api.activity.animation;

import com.jfixby.r3.ui.api.activity.layer.VisibleComponent;

public interface FrameAnimationSpecs {

	void setComponent (VisibleComponent child);

	VisibleComponent getComponent ();

	long getFrameTime ();

	void setFrameTime (long parseLong);

}
