
package com.jfixby.r3.ui.api.activity.raster;

import com.jfixby.r3.ui.api.activity.CanvasPositionable;
import com.jfixby.r3.ui.api.activity.layer.VisibleComponent;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.geometry.CanvasPosition;

public interface CanvasComponent extends VisibleComponent, CanvasPositionable {

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
