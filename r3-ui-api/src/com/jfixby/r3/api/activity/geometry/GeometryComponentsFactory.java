package com.jfixby.r3.api.activity.geometry;

import com.jfixby.scarabei.api.geometry.Rectangle;

public interface GeometryComponentsFactory {

	RectangleComponent newRectangle();

	EllipseComponent newEllipse();

	RectangleComponent newRectangle(Rectangle shape);

	PolyComponent newPoly();

}
