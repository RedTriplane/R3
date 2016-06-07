
package com.jfixby.r3.api.ui.unit.geometry;

import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;

public interface RectangularComponent extends CanvasComponent, CanvasPositionable, Rectangle {

	Rectangle shape ();

	public Rectangle setupShape (Rectangle other);

}
