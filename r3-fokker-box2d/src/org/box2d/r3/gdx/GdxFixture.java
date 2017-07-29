
package org.box2d.r3.gdx;

import org.box2d.r3.api.Box2DBody;
import org.box2d.r3.api.Fixture;
import org.box2d.r3.api.Shape;
import org.box2d.r3.api.ShapeType;

import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class GdxFixture implements Fixture {

	@Override
	public String toString () {
		return "GdxFixture [gdx_fixture=" + this.gdx_fixture.getFilterData().categoryBits + "]";
	}

	private final com.badlogic.gdx.physics.box2d.Fixture gdx_fixture;

	public GdxFixture (final com.badlogic.gdx.physics.box2d.Fixture f) {
		this.gdx_fixture = f;
	}

	@Override
	public Box2DBody getBody () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public ShapeType getType () {
		final com.badlogic.gdx.physics.box2d.Shape.Type T = this.gdx_fixture.getType();
		if (T == com.badlogic.gdx.physics.box2d.Shape.Type.Polygon) {
			return ShapeType.Polygon;
		}
		if (T == com.badlogic.gdx.physics.box2d.Shape.Type.Circle) {
			return ShapeType.Circle;
		}
		if (T == com.badlogic.gdx.physics.box2d.Shape.Type.Edge) {
			return ShapeType.Edge;
		}
		if (T == com.badlogic.gdx.physics.box2d.Shape.Type.Chain) {
			return ShapeType.Chain;
		}

		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public Shape getShape () {
		final com.badlogic.gdx.physics.box2d.Shape gdx_shape = this.gdx_fixture.getShape();
		if (this.shape == null) {
			this.newShape(gdx_shape);
		}

		this.shape.update(gdx_shape);

		return this.shape;
	}

	private void newShape (final com.badlogic.gdx.physics.box2d.Shape gdx_shape) {
		if (gdx_shape.getType() == com.badlogic.gdx.physics.box2d.Shape.Type.Circle) {
			this.shape = new GdxCircleShape((com.badlogic.gdx.physics.box2d.CircleShape)gdx_shape);
			return;
		}
		if (gdx_shape.getType() == com.badlogic.gdx.physics.box2d.Shape.Type.Polygon) {
			this.shape = new GdxPolygonShape((com.badlogic.gdx.physics.box2d.PolygonShape)gdx_shape);
			return;
		}

		L.d("", gdx_shape.getType());
		Err.throwNotImplementedYet();

	}

	GdxShape shape;

}
