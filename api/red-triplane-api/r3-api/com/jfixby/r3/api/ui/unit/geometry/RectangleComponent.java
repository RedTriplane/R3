
package com.jfixby.r3.api.ui.unit.geometry;

import com.jfixby.scarabei.api.color.Color;

public interface RectangleComponent extends RectangularComponent {

	void setBorderColor (Color border_color);

	Color getBorderColor ();

	void setFillColor (Color fill_color);

	Color getFillColor ();

}
