
package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.math.Angle;
import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.layer.DrawableComponent;

public interface CanvasComponent extends DrawableComponent, CanvasPositionable {

	public Angle getRotation ();

	public void setRotation (Angle rotation);

	public void setRotation (double rotation);

	public void setPositionX (double x);

	public void setPositionY (double y);

	@Override
	public double getPositionX ();

	@Override
	public double getPositionY ();

	// public CanvasPosition getPosition();

	void setOriginAbsolute (double origin_x, double origin_y);

	public void setOriginAbsolute (CanvasPosition position);

	void setOriginAbsoluteX (double origin_x);

	void setOriginAbsoluteY (double origin_y);

	public void setOpacity (float alpha);

	public float getOpacity ();

	void setDebugRenderFlag (boolean b);

	boolean getDebugRenderFlag ();

	public void setDebugColor (Color debug_render_color);

	public Color getDebugColor ();

}
