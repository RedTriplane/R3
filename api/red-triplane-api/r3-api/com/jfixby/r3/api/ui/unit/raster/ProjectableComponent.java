package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.geometry.RectangleCorner;

public interface ProjectableComponent extends CanvasComponent {
	public void setDebugColor(Color debug_render_color);

	Rectangle shape();

	double getWidth();

	double getHeight();

	void setSize(double width, double height);

	void setWidth(double width);

	void setHeight(double height);

	void setOrigin(ORIGIN_RELATIVE_HORIZONTAL orX, ORIGIN_RELATIVE_VERTICAL orY);

	void setOriginX(ORIGIN_RELATIVE_HORIZONTAL orX);

	void setOriginY(ORIGIN_RELATIVE_VERTICAL orY);

	// public void setOriginAbsolute(double origin_x, double origin_y);

	void setOriginAbsolute(double origin_x, double origin_y);

	void setOriginAbsoluteX(double origin_x);

	void setOriginAbsoluteY(double origin_y);

	void setOriginRelative(double ORIGIN_RELATIVE_HORIZONTAL, double ORIGIN_RELATIVE_VERTICAL);

	void setOriginRelativeX(double ORIGIN_RELATIVE_HORIZONTAL);

	void setOriginRelativeY(double ORIGIN_RELATIVE_VERTICAL);

	public Rectangle setupShape(Rectangle other);

	RectangleCorner getTopLeftCorner();

	RectangleCorner getTopRightCorner();

	RectangleCorner getBottomLeftCorner();

	RectangleCorner getBottomRightCorner();

}
