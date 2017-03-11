
package com.jfixby.r3.api.ui.unit.parallax;

import com.jfixby.r3.api.ui.unit.layer.Component;

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
