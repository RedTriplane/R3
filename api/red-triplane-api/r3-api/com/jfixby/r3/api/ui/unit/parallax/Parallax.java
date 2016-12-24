
package com.jfixby.r3.api.ui.unit.parallax;

import com.jfixby.r3.api.ui.unit.layer.DrawableComponent;
import com.jfixby.scarabei.api.floatn.Float2;

public interface Parallax extends DrawableComponent {

	void setParallaxOffset (Float2 offset);

	void setPositionX (double x);

	void setPositionY (double y);

	double getWidth ();

}
