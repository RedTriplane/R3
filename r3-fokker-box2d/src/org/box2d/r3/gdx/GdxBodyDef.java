
package org.box2d.r3.gdx;

import org.box2d.r3.api.BodyDef;
import org.box2d.r3.api.BodyType;

import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;

public class GdxBodyDef implements BodyDef {

	private final GdxPoint2D position;

	public GdxBodyDef () {
		this.gdx_bodydef = new com.badlogic.gdx.physics.box2d.BodyDef();
		this.position = new GdxPoint2D(this.gdx_bodydef.position);
	}

	private final com.badlogic.gdx.physics.box2d.BodyDef gdx_bodydef;

	@Override
	public Float2 position () {
		return this.position;
	}

	@Override
	public void setType (final BodyType type) {
		this.gdx_bodydef.type = this.resolve(type);
	}

	private com.badlogic.gdx.physics.box2d.BodyDef.BodyType resolve (final BodyType type) {
		if (type == BodyType.DynamicBody) {
			return com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
		}
		if (type == BodyType.KinematicBody) {
			return com.badlogic.gdx.physics.box2d.BodyDef.BodyType.KinematicBody;
		}
		if (type == BodyType.StaticBody) {
			return com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;
		}
		Err.throwNotImplementedYet();
		return null;
	}

	public com.badlogic.gdx.physics.box2d.BodyDef getGdxBodyDef () {
		return this.gdx_bodydef;
	}
}
