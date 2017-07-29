
package org.box2d.r3.jbox2d.d;

import org.box2d.r3.api.BodyDef;
import org.box2d.r3.api.BodyType;

import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;

public class JBodyDef implements BodyDef {

	private final JPoint2D position;

	public JBodyDef () {
		this.gdx_bodydef = new org.jbox2d.d.dynamics.BodyDef();
		this.position = new JPoint2D(this.gdx_bodydef.position);
	}

	private final org.jbox2d.d.dynamics.BodyDef gdx_bodydef;

	@Override
	public Float2 position () {
		return this.position;
	}

	@Override
	public void setType (final BodyType type) {
		this.gdx_bodydef.type = this.resolve(type);
	}

	private org.jbox2d.d.dynamics.BodyType resolve (final BodyType type) {
		if (type == BodyType.DynamicBody) {
			return org.jbox2d.d.dynamics.BodyType.DYNAMIC;
		}
		if (type == BodyType.KinematicBody) {
			return org.jbox2d.d.dynamics.BodyType.KINEMATIC;
		}
		if (type == BodyType.StaticBody) {
			return org.jbox2d.d.dynamics.BodyType.STATIC;
		}
		Err.throwNotImplementedYet();
		return null;

	}

	public org.jbox2d.d.dynamics.BodyDef getGdxBodyDef () {
		return this.gdx_bodydef;
	}
}
