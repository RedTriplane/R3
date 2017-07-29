
package org.box2d.r3.jbox2d.d;

import org.box2d.r3.api.CircleShape;
import org.jbox2d.d.common.Vector2;

import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.log.L;

public class JCircleShape extends JShape implements CircleShape {

	private org.jbox2d.d.collision.shapes.CircleShape gdx_shape;
	final private Vector2 gdx_position = new Vector2();;
	private final Float2 position = Geometry.newFloat2();

	public JCircleShape (final org.jbox2d.d.collision.shapes.CircleShape gdx_shape) {
		this.gdx_shape = gdx_shape;
	}

	public JCircleShape () {
		this(new org.jbox2d.d.collision.shapes.CircleShape());
	}

	@Override
	public void setRadius (final double radius) {
		this.gdx_shape.setRadius(radius);
	}

	@Override
	public void setPosition (final Float2 centerTmp) {
		this.gdx_position.set(centerTmp.getX(), centerTmp.getY());
		this.gdx_shape.getVertex(0).set(this.gdx_position);
		// gdx_shape.setPosition(gdx_position);
	}

	@Override
	public ReadOnlyFloat2 getPosition () {
		final Vector2 p = this.gdx_shape.getVertex(0);
		this.position.setXY(p.x, p.y);
		return this.position;
	}

	@Override
	public org.jbox2d.d.collision.shapes.CircleShape getGdxShape () {
		return this.gdx_shape;
	}

	@Override
	public void update (final org.jbox2d.d.collision.shapes.Shape gdx_shape) {
		if (this.gdx_shape != gdx_shape) {
			L.d("suspicious behaviour");
		}
		this.gdx_shape = (org.jbox2d.d.collision.shapes.CircleShape)gdx_shape;
	}

	@Override
	public double getRadius () {
		return this.gdx_shape.getRadius();
	}
}
