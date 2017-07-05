
package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.layer.DrawableComponent;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.geometry.CanvasPosition;

public interface CanvasComponent extends DrawableComponent, CanvasPositionable {

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
