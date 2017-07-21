
package com.jfixby.r3.activity.api.parallax;

import com.jfixby.r3.activity.api.layer.Component;

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
