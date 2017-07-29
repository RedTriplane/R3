
package org.box2d.r3.jbox2d.f;

import org.box2d.r3.api.Box2DTransform;
import org.jbox2d.f.common.Vector2;

import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;

public class JTransform implements Box2DTransform {

	// public static final int POS_X = org.jbox2d.common.Transform.POS_X;
	// public static final int POS_Y = org.jbox2d.common.Transform.POS_Y;
	// public static final int COS = org.jbox2d.common.Transform.COS;
	// public static final int SIN = org.jbox2d.common.Transform.SIN;

	org.jbox2d.f.common.Transform gdx_transform;

	public JTransform (final org.jbox2d.f.common.Transform gdx_transform) {
		this.gdx_transform = gdx_transform;
	}

	@Override
	public String toString () {
		return "GdxTransform [gdx_transform=" + this.gdx_transform + "]";
	}

	final Vector2 tmpV = new Vector2();
	final Vector2 tmpO = new Vector2();

	@Override
	public void transform (final Float2 temp) {
		this.tmpV.set((float)temp.getX(), (float)temp.getY());
		org.jbox2d.f.common.Transform.mulToOut(this.gdx_transform, this.tmpV, this.tmpO);
		// L.d("gdx_transform", gdx_transform);
		// L.d("tmpV", tmpV);
		// L.d("tmpO", tmpO);

		temp.setXY(this.tmpO.x, this.tmpO.y);

		//
		// double x = gdx_transform.vals[POS_X] + gdx_transform.vals[COS]
		// * temp.getX() + -gdx_transform.vals[SIN] * temp.getY();
		// double y = gdx_transform.vals[POS_Y] + gdx_transform.vals[SIN]
		// * temp.getX() + gdx_transform.vals[COS] * temp.getY();
		// temp.set(x, y);

	}

	@Override
	public void reverse (final Float2 temp_point) {
		Err.reportError("Not supported!");
	}

}
