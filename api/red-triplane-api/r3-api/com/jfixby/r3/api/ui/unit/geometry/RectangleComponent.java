package com.jfixby.r3.api.ui.unit.geometry;

import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;

public interface RectangleComponent extends CanvasComponent, CanvasPositionable {

	void setBorderColor(Color border_color);

	Color getBorderColor();

	void setFillColor(Color fill_color);

	Color getFillColor();

	Rectangle getShape();

	void setDebugRenderFlag(boolean b);

	boolean getDebugRenderFlag();

	public void setDebugColor(Color debug_render_color);

	public Color getDebugColor();

	void setSize(double width, double height);

	void setOriginRelative(double or_relative_x, double or_relative_y);

}
