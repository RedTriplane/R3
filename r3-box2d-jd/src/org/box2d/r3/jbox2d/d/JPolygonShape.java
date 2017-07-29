
package org.box2d.r3.jbox2d.d;

import org.box2d.r3.api.PolygonShape;
import org.jbox2d.d.common.Vector2;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.ClosedPolygonalChain;
import com.jfixby.scarabei.api.geometry.Geometry;

public class JPolygonShape extends JShape implements PolygonShape {

	private org.jbox2d.d.collision.shapes.PolygonShape gdx_shape;
	final ClosedPolygonalChain chain;

	public JPolygonShape () {
		this.gdx_shape = new org.jbox2d.d.collision.shapes.PolygonShape();
		this.chain = Geometry.newClosedPolygonalChain();
	}

	public JPolygonShape (final org.jbox2d.d.collision.shapes.PolygonShape gdx_shape2) {
		this.gdx_shape = gdx_shape2;
		this.chain = Geometry.newClosedPolygonalChain();
	}

	@Override
	public void setAsBox (final double half_width, final double half_height) {
		this.gdx_shape.setAsBox(half_width, half_height);
	}

	Vector2[] tmp = null;

	private Vector2[] wrap (final Collection<Float2> vertices) {
		final int N = vertices.size();

		if (this.tmp == null) {
			this.tmp = new Vector2[N];
		}
		if (this.tmp.length != N) {
			this.tmp = new Vector2[N];
		}
		for (int i = 0; i < N; i++) {
			this.tmp[i] = new Vector2();
			this.tmp[i].x = vertices.getElementAt(i).getX();
			this.tmp[i].y = vertices.getElementAt(i).getY();
			// = ((GdxPoint2D) vertices.getElementAt(i)).getGdxPoint();
		}
		return this.tmp;
	}

	@Override
	public org.jbox2d.d.collision.shapes.PolygonShape getGdxShape () {
		return this.gdx_shape;
	}

	@Override
	public void update (final org.jbox2d.d.collision.shapes.Shape gdx_shape) {
		this.gdx_shape = (org.jbox2d.d.collision.shapes.PolygonShape)gdx_shape;
	}

	final Vector2 tmpV = new Vector2();

	@Override
	public ClosedPolygonalChain getClosedPolygonalChain () {
		final int N = this.gdx_shape.getVertexCount();
		this.chain.setSize(N);
		for (int i = 0; i < N; i++) {
			final Vector2 vx = this.gdx_shape.getVertex(i);
			this.chain.getVertex(i).relative().setXY(vx.x, vx.y);
		}
		return this.chain;
	}

	@Override
	public void setVertices (final Collection<Float2> vertices) {
		this.gdx_shape.set(this.wrap(vertices), vertices.size());
	}
}
