
package com.jfixby.r3.api.activity.geometry;

import com.jfixby.r3.api.activity.CanvasPositionable;
import com.jfixby.r3.api.activity.raster.CanvasComponent;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface RectangularComponent extends CanvasComponent, CanvasPositionable, Rectangle {

	Rectangle shape ();

	public Rectangle setupShape (Rectangle other);

}
