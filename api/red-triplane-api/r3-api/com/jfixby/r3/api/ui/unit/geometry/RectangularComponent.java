
package com.jfixby.r3.api.ui.unit.geometry;

import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.cmns.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.geometry.RectangleCorner;
import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;

public interface RectangularComponent extends CanvasComponent, CanvasPositionable {

	Rectangle shape ();

	public void setOriginAbsolute (FixedFloat2 point);

	double getWidth ();

	double getHeight ();

	void setSize (double width, double height);

	void setWidth (double width);

	void setHeight (double height);

	void setOriginRelative (ORIGIN_RELATIVE_HORIZONTAL ORIGIN_RELATIVE_HORIZONTAL,
		ORIGIN_RELATIVE_VERTICAL ORIGIN_RELATIVE_VERTICAL);

	void setOriginRelativeX (ORIGIN_RELATIVE_HORIZONTAL ORIGIN_RELATIVE_HORIZONTAL);

	void setOriginRelativeY (ORIGIN_RELATIVE_VERTICAL ORIGIN_RELATIVE_VERTICAL);

	// public void setOriginAbsolute(double origin_x, double origin_y);

	void setOriginRelative (double ORIGIN_RELATIVE_HORIZONTAL, double ORIGIN_RELATIVE_VERTICAL);

	void setOriginRelativeX (double ORIGIN_RELATIVE_HORIZONTAL);

	void setOriginRelativeY (double ORIGIN_RELATIVE_VERTICAL);

	public Rectangle setupShape (Rectangle other);

	RectangleCorner getTopLeftCorner ();

	RectangleCorner getTopRightCorner ();

	RectangleCorner getBottomLeftCorner ();

	RectangleCorner getBottomRightCorner ();

	CanvasPosition getPosition ();

}
