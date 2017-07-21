
package com.jfixby.r3.activity.api.animation;

import com.jfixby.r3.activity.api.layer.VisibleComponent;

public interface FrameAnimationSpecs {

	void setComponent (VisibleComponent child);

	VisibleComponent getComponent ();

	long getFrameTime ();

	void setFrameTime (long parseLong);

}
