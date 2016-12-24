
package com.jfixby.r3.api.ui.unit.geometry;

import com.jfixby.r3.api.ui.unit.CanvasPositionable;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface RectangularComponent extends CanvasComponent, CanvasPositionable, Rectangle {

	Rectangle shape ();

	public Rectangle setupShape (Rectangle other);

}
