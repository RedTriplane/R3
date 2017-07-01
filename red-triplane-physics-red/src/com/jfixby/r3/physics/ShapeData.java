
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.BODY_SHAPE_TYPE;
import com.jfixby.r3.api.physics.BoxShape;
import com.jfixby.r3.api.physics.CircleShape;
import com.jfixby.r3.api.physics.PolyBodySpecs;
import com.jfixby.r3.api.physics.PolyShape;
import com.jfixby.scarabei.api.err.Err;

public class ShapeData implements BoxShape, CircleShape, PolyShape {

	public static final float MINIMAL_SIZE = FokkerConfig.MINIMAL_PHYSICAL_SIZE_ALLOWED;
	private static final double MIN_RADIUS = MINIMAL_SIZE / 2;

	private final BODY_SHAPE_TYPE shape_type; // final

	public ShapeData (final BODY_SHAPE_TYPE shape_type) {
		this.shape_type = shape_type;
	}

	public ShapeData (final ShapeData to_clone) {
		this.shape_type = to_clone.shape_type;
	}

	public BODY_SHAPE_TYPE getShapeType () {
		return this.shape_type;
	}

	// --Poly-------------------------
	private final RedPolyBodySpecs physics_shape = new RedPolyBodySpecs();
	private final double scale = 1d;

	// --Cirlce-------------------------
	private double radius = MIN_RADIUS;

	@Override
	public void setRadius (final double radius) {
		this.radius = radius;
		if (this.radius < MIN_RADIUS) {
			this.radius = MIN_RADIUS;
		}

	}

	@Override
	public double getRadius () {
		return this.radius;
	}

	// --Box-------------------------
	private double width = MINIMAL_SIZE;
	private double height = MINIMAL_SIZE;

	@Override
	public double getWidth () {
		return this.width;
	}

	@Override
	public double getHeight () {
		return this.height;
	}

	@Override
	public void setHeight (final double height) {
		this.height = height;
		if (this.height < MINIMAL_SIZE) {
			this.height = MINIMAL_SIZE;
		}
	}

	@Override
	public void setWidth (final double width) {
		this.width = width;
		if (this.width < MINIMAL_SIZE) {
			this.width = MINIMAL_SIZE;
		}
	}

	@Override
	public void setSize (final double width, final double height) {
		this.setWidth(width);
		this.setHeight(height);
	}

	// --Poly-------------------------

	// -------------------------------

	public boolean isEqualTo (final ShapeData other) {
		return this.equals(other);
	}

	public void setValues (final ShapeData other) {
		if (this.shape_type == BODY_SHAPE_TYPE.BOX) {
			this.height = other.height;
			this.width = other.width;
			return;
		}

		if (this.shape_type == BODY_SHAPE_TYPE.CIRCLE) {
			this.radius = other.radius;
			return;
		}

		if (this.shape_type == BODY_SHAPE_TYPE.POLY) {
			this.physics_shape.setup(other.physics_shape);
			return;
		}

		Err.throwNotImplementedYet();

	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final ShapeData other = (ShapeData)obj;

		if (this.shape_type == BODY_SHAPE_TYPE.BOX) {
			if (Double.doubleToLongBits(this.height) != Double.doubleToLongBits(other.height)) {
				return false;
			}
			if (Double.doubleToLongBits(this.width) != Double.doubleToLongBits(other.width)) {
				return false;
			}
			return true;
		}

		if (this.shape_type == BODY_SHAPE_TYPE.CIRCLE) {
			if (Double.doubleToLongBits(this.radius) != Double.doubleToLongBits(other.radius)) {
				return false;
			}
			return true;
		}

		if (this.shape_type == BODY_SHAPE_TYPE.POLY) {
			if (this.physics_shape == null) {
				if (other.physics_shape != null) {
					return false;
				}
			} else {
				return this.physics_shape.equals(other.physics_shape);
			}
			return true;
		}

		Err.reportError("Unknown shape: " + this.shape_type);
		return false;

	}

	public void setupGeometry (final PolyBodySpecs specs) {
		if (specs.listCircles().size() == 0 && specs.listPolyBodyChains().size() == 0) {
			Err.reportError("Empty specs");
		}
		this.physics_shape.setup(specs);
	}

	public RedPolyBodySpecs getPhysicsShape () {
		return this.physics_shape;
	}

	public double getScale () {
		return this.scale;
	}

}
