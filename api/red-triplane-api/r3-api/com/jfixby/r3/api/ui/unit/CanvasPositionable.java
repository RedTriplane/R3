
package com.jfixby.r3.api.ui.unit;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.math.Angle;

public interface CanvasPositionable {

	void setPosition (CanvasPosition position);

	void setPosition (ReadOnlyFloat2 position);

	void setPosition (double position_x, double position_y);

	public void offset (final double x, final double y);

	double getPositionX ();

	double getPositionY ();

	public Angle getRotation ();

	public void setRotation (Angle rotation);

	public void setRotation (double rotation);

	public void setPositionX (double x);

	public void setPositionY (double y);

}
