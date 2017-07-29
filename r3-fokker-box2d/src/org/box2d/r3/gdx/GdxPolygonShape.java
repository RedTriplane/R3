
package org.box2d.r3.gdx;

import org.box2d.r3.api.PolygonShape;

import com.badlogic.gdx.math.Vector2;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.ClosedPolygonalChain;
import com.jfixby.scarabei.api.geometry.Geometry;

public class GdxPolygonShape extends GdxShape implements PolygonShape {

	private com.badlogic.gdx.physics.box2d.PolygonShape gdx_shape;
	final ClosedPolygonalChain chain;

	public GdxPolygonShape () {
		this.gdx_shape = new com.badlogic.gdx.physics.box2d.PolygonShape();
		this.chain = Geometry.newClosedPolygonalChain();
	}

	public GdxPolygonShape (final com.badlogic.gdx.physics.box2d.PolygonShape gdx_shape2) {
		this.gdx_shape = gdx_shape2;
		this.chain = Geometry.newClosedPolygonalChain();
	}

	@Override
	public void setAsBox (final double half_width, final double half_height) {
		this.gdx_shape.setAsBox((float)half_width, (float)half_height);
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
			this.tmp[i].x = (float)vertices.getElementAt(i).getX();
			this.tmp[i].y = (float)vertices.getElementAt(i).getY();
			// = ((GdxPoint2D) vertices.getElementAt(i)).getGdxPoint();
		}
		return this.tmp;
	}

	@Override
	public com.badlogic.gdx.physics.box2d.PolygonShape getGdxShape () {
		return this.gdx_shape;
	}

	@Override
	public void update (final com.badlogic.gdx.physics.box2d.Shape gdx_shape) {
		this.gdx_shape = (com.badlogic.gdx.physics.box2d.PolygonShape)gdx_shape;
	}

	final Vector2 tmpV = new Vector2();

	@Override
	public ClosedPolygonalChain getClosedPolygonalChain () {

		final int N = this.gdx_shape.getVertexCount();
		this.chain.setSize(N);
		for (int i = 0; i < N; i++) {
			this.gdx_shape.getVertex(i, this.tmpV);
			this.chain.getVertex(i).relative().setXY(this.tmpV.x, this.tmpV.y);
		}

		return this.chain;
	}

	@Override
	public void setVertices (final Collection<Float2> vertices) {
		this.gdx_shape.set(this.wrap(vertices));
	}
}
