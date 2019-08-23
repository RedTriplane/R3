
package com.jfixby.r3.activity.api.geometry;

import com.jfixby.r3.activity.api.raster.CanvasComponent;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface RectangularComponent extends CanvasComponent, Rectangle {

	Rectangle shape ();

	public Rectangle setupShape (Rectangle other);

}
