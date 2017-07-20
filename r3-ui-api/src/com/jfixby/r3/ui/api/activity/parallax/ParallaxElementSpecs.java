
package com.jfixby.r3.ui.api.activity.parallax;

import com.jfixby.r3.ui.api.activity.layer.Component;

public interface ParallaxElementSpecs {

	void setComponent (Component child);

	Component getComponent ();

	void setMultiplierX (float multiplier_x);

	void setMultiplierY (float multiplier_y);

	void setMultiplierZ (float multiplier_z);

	float getMultiplierX ();

	float getMultiplierY ();

	float getMultiplierZ ();
}
