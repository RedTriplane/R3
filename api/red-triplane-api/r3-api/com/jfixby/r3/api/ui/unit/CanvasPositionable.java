
package com.jfixby.r3.api.ui.unit;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.CanvasPosition;

public interface CanvasPositionable {

	void setPosition (CanvasPosition position);

	void setPosition (FixedFloat2 position);

	void setPosition (double position_x, double position_y);

	public void offset (final double x, final double y);

	double getPositionX ();

	double getPositionY ();

}
