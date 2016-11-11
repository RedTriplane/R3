
package com.jfixby.r3.api.ui.unit.parallax;

import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.r3.api.ui.unit.layer.DrawableComponent;

public interface Parallax extends DrawableComponent {

	void setParallaxOffset (Float2 offset);

	void setPositionX (double x);

	void setPositionY (double y);

	double getWidth ();

}
