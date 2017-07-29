
package org.box2d.r3.gdx;

import org.box2d.r3.api.CircleShape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.log.L;

public class GdxCircleShape extends GdxShape implements CircleShape {

	private com.badlogic.gdx.physics.box2d.CircleShape gdx_shape;
	final private Vector2 gdx_position = new Vector2();;
	private final Float2 position = Geometry.newFloat2();

	public GdxCircleShape (final com.badlogic.gdx.physics.box2d.CircleShape gdx_shape) {
		this.gdx_shape = gdx_shape;
	}

	public GdxCircleShape () {
		this(new com.badlogic.gdx.physics.box2d.CircleShape());
	}

	@Override
	public void setRadius (final double radius) {
		this.gdx_shape.setRadius((float)radius);
	}

	@Override
	public void setPosition (final Float2 centerTmp) {
		this.gdx_position.set((float)centerTmp.getX(), (float)centerTmp.getY());
		this.gdx_shape.setPosition(this.gdx_position);
	}

	@Override
	public ReadOnlyFloat2 getPosition () {
		final Vector2 p = this.gdx_shape.getPosition();
		this.position.setXY(p.x, p.y);
		return this.position;
	}

	@Override
	public com.badlogic.gdx.physics.box2d.CircleShape getGdxShape () {
		return this.gdx_shape;
	}

	@Override
	public void update (final Shape gdx_shape) {
		if (this.gdx_shape != gdx_shape) {
			L.d("suspicious behaviour");
		}
		this.gdx_shape = (com.badlogic.gdx.physics.box2d.CircleShape)gdx_shape;
	}

	@Override
	public double getRadius () {
		return this.gdx_shape.getRadius();
	}
}
