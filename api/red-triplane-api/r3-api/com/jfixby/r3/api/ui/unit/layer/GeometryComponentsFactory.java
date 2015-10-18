package com.jfixby.r3.api.ui.unit.layer;

import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.geometry.EllipseComponent;
import com.jfixby.r3.api.ui.unit.geometry.PolyComponent;
import com.jfixby.r3.api.ui.unit.geometry.RectangleComponent;

public interface GeometryComponentsFactory {

	RectangleComponent newRectangle();

	EllipseComponent newEllipse();

	RectangleComponent newRectangle(Rectangle shape);

	PolyComponent newPoly();

}
