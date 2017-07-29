
package org.box2d.r3.gdx;

import org.box2d.r3.api.Box2DTransform;

import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;

public class GdxTransform implements Box2DTransform {

	public static final int POS_X = com.badlogic.gdx.physics.box2d.Transform.POS_X;
	public static final int POS_Y = com.badlogic.gdx.physics.box2d.Transform.POS_Y;
	public static final int COS = com.badlogic.gdx.physics.box2d.Transform.COS;
	public static final int SIN = com.badlogic.gdx.physics.box2d.Transform.SIN;

	private final com.badlogic.gdx.physics.box2d.Transform gdx_transform;

	public GdxTransform (final com.badlogic.gdx.physics.box2d.Transform gdx_transform) {
		this.gdx_transform = gdx_transform;
	}

	@Override
	public String toString () {
		return "GdxTransform [gdx_transform=" + this.gdx_transform + "]";
	}

	@Override
	public void transform (final Float2 temp) {
		final double x = this.gdx_transform.vals[POS_X] + this.gdx_transform.vals[COS] * temp.getX()
			+ -this.gdx_transform.vals[SIN] * temp.getY();
		final double y = this.gdx_transform.vals[POS_Y] + this.gdx_transform.vals[SIN] * temp.getX()
			+ this.gdx_transform.vals[COS] * temp.getY();
		temp.setXY(x, y);

	}

	@Override
	public void reverse (final Float2 temp_point) {
		Err.reportError("Not supported!");
	}

}
