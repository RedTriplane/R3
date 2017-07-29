
package org.box2d.r3.gdx;

import com.badlogic.gdx.math.Vector2;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class GdxPoint2D implements Float2 {
	final private Vector2 gdx_vector;
	private final Float2 redPoint;

	public GdxPoint2D (final Vector2 position) {
		this.gdx_vector = position;
		this.redPoint = Geometry.newFloat2();
	}

	public Vector2 getGdxPoint () {
		return this.gdx_vector;
	}

	public GdxPoint2D update () {
		this.gdx_vector.x = (float)this.getX();
		this.gdx_vector.y = (float)this.getY();
		return this;
	}

	@Override
	public GdxPoint2D setXY (final double x, final double y) {
		this.redPoint.setXY(x, y);
		return this.update();
	}

	@Override
	public GdxPoint2D setX (final double x) {
		this.redPoint.setX(x);
		return this.update();
	}

	@Override
	public GdxPoint2D setY (final double y) {
		this.redPoint.setY(y);
		return this.update();
	}

	@Override
	public GdxPoint2D set (final ReadOnlyFloat2 other) {
		this.redPoint.set(other);
		return this.update();
	}

	@Override
	public GdxPoint2D setXY () {
		this.redPoint.setXY();
		return this.update();
	}

	@Override
	public GdxPoint2D add (final ReadOnlyFloat2 offset) {
		this.redPoint.add(offset);
		return this.update();
	}

	@Override
	public GdxPoint2D addX (final double delta) {
		this.redPoint.addX(delta);
		return this.update();
	}

	@Override
	public GdxPoint2D addY (final double delta) {
		this.redPoint.addY(delta);
		return this.update();
	}

	@Override
	public GdxPoint2D add (final double deltaX, final double deltaY) {
		this.redPoint.add(deltaX, deltaY);
		return this.update();
	}

	@Override
	public double getX () {
		return this.redPoint.getX();
	}

	@Override
	public double getY () {
		return this.redPoint.getY();
	}

	@Override
	public boolean isInEpsilonDistance (final ReadOnlyFloat2 other) {
		return this.redPoint.isInEpsilonDistance(other);
	}

	@Override
	public boolean isInEpsilonDistanceOfZero () {
		return this.redPoint.isInEpsilonDistanceOfZero();
	}

	@Override
	public double norm () {
		return this.redPoint.norm();
	}

	@Override
	public double distanceTo (final ReadOnlyFloat2 other) {
		return this.redPoint.distanceTo(other);
	}

	@Override
	public Float2 scaleXY (final double factor) {
		this.redPoint.scaleXY(factor);
		return this.update();
	}

	@Override
	public Float2 scaleXY (final double x, final double y) {
		this.redPoint.scaleXY(x, y);
		return this.update();
	}

	@Override
	public Float2 scaleXY (final Float2 other) {
		this.redPoint.scaleXY(other);
		return this.update();
	}

	@Override
	public double product (final Float2 other) {
		return this.redPoint.product(other);

	}

	@Override
	public Float2 setLinearSum (final ReadOnlyFloat2 a, final double alpha, final ReadOnlyFloat2 b, final double betta) {
		this.redPoint.setLinearSum(a, alpha, b, betta);
		return this.update();
	}

	@Override
	public void subtract (final ReadOnlyFloat2 position) {
		this.redPoint.subtract(position);
		this.update();
	}

}
