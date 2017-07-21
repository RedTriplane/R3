
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.geometry.EllipseComponent;
import com.jfixby.r3.activity.api.geometry.GeometryComponentsFactory;
import com.jfixby.r3.activity.api.geometry.PolyComponent;
import com.jfixby.r3.activity.api.geometry.RectangleComponent;
import com.jfixby.r3.activity.red.geo.RedRectangleComponent;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;

public class RedGeometryFactory implements GeometryComponentsFactory {

	private final RedComponentsFactory master;

	public RedGeometryFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public RectangleComponent newRectangle () {
		final Rectangle rectangle = Geometry.newRectangle();
		return new RedRectangleComponent(this.master, rectangle);
	}

	@Override
	public EllipseComponent newEllipse () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public RectangleComponent newRectangle (final Rectangle shape) {
		return new RedRectangleComponent(this.master, shape);
	}

	@Override
	public PolyComponent newPoly () {
		Err.throwNotImplementedYet();
		return null;
	}
}
