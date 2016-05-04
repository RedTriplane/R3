
package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.math.Angle;
import com.jfixby.r3.api.ui.unit.layer.DrawableComponent;

public interface CanvasComponent extends DrawableComponent {

	public Angle getRotation ();

	public void setRotation (Angle rotation);

	public void setRotation (double rotation);

	public void setPosition (double x, double y);

	public void setPosition (FixedFloat2 position_xy);

	public void setPositionX (double x);

	public void setPositionY (double y);

	public double getPositionX ();

	public double getPositionY ();

	// public CanvasPosition getPosition();

	public void setPosition (CanvasPosition position);

	void setOriginAbsolute (double origin_x, double origin_y);

	public void setOriginAbsolute (CanvasPosition position);

	void setOriginAbsoluteX (double origin_x);

	void setOriginAbsoluteY (double origin_y);

	public void setOpacity (double alpha);

	public double getOpacity ();

	void setDebugRenderFlag (boolean b);

	boolean getDebugRenderFlag ();

	public void setDebugColor (Color debug_render_color);

	public Color getDebugColor ();

}
