
package com.jfixby.r3.api.ui.unit.geometry;

import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;

public interface RectangleComponent extends CanvasComponent, CanvasPositionable {

	void setBorderColor (Color border_color);

	Color getBorderColor ();

	void setFillColor (Color fill_color);

	Color getFillColor ();

	Rectangle getShape ();

	@Override
	void setDebugRenderFlag (boolean b);

	@Override
	boolean getDebugRenderFlag ();

	@Override
	public void setDebugColor (Color debug_render_color);

	@Override
	public Color getDebugColor ();

	void setSize (double width, double height);

	void setOriginRelative (double ORIGIN_RELATIVE_HORIZONTAL, double ORIGIN_RELATIVE_VERTICAL);

	void setOriginRelative (ORIGIN_RELATIVE_HORIZONTAL ORIGIN_RELATIVE_HORIZONTAL,
		ORIGIN_RELATIVE_VERTICAL ORIGIN_RELATIVE_VERTICAL);

}
