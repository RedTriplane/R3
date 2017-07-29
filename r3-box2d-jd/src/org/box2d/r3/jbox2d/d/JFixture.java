
package org.box2d.r3.jbox2d.d;

import org.box2d.r3.api.Box2DBody;
import org.box2d.r3.api.Fixture;
import org.box2d.r3.api.Shape;
import org.box2d.r3.api.ShapeType;

import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class JFixture implements Fixture {

	@Override
	public String toString () {
		return "GdxFixture [gdx_fixture=" + this.gdx_fixture.getFilterData().categoryBits + "]";
	}

	private final org.jbox2d.d.dynamics.Fixture gdx_fixture;

	public JFixture (final org.jbox2d.d.dynamics.Fixture f) {
		this.gdx_fixture = f;
	}

	@Override
	public Box2DBody getBody () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public ShapeType getType () {
		final org.jbox2d.d.collision.shapes.ShapeType T = this.gdx_fixture.getType();
		if (T == org.jbox2d.d.collision.shapes.ShapeType.POLYGON) {
			return ShapeType.Polygon;
		}
		if (T == org.jbox2d.d.collision.shapes.ShapeType.CIRCLE) {
			return ShapeType.Circle;
		}
		if (T == org.jbox2d.d.collision.shapes.ShapeType.EDGE) {
			return ShapeType.Edge;
		}
		if (T == org.jbox2d.d.collision.shapes.ShapeType.CHAIN) {
			return ShapeType.Chain;
		}

		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public Shape getShape () {
		final org.jbox2d.d.collision.shapes.Shape gdx_shape = this.gdx_fixture.getShape();
		if (this.shape == null) {
			this.newShape(gdx_shape);
		}

		this.shape.update(gdx_shape);

		return this.shape;
	}

	private void newShape (final org.jbox2d.d.collision.shapes.Shape gdx_shape) {
		if (gdx_shape.getType() == org.jbox2d.d.collision.shapes.ShapeType.CIRCLE) {
			this.shape = new JCircleShape((org.jbox2d.d.collision.shapes.CircleShape)gdx_shape);
			return;
		}
		if (gdx_shape.getType() == org.jbox2d.d.collision.shapes.ShapeType.POLYGON) {
			this.shape = new JPolygonShape((org.jbox2d.d.collision.shapes.PolygonShape)gdx_shape);
			return;
		}

		L.d("", gdx_shape.getType());
		Err.reportError("");

	}

	JShape shape;

}
