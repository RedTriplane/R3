
package org.box2d.r3.gdx;

import org.box2d.r3.api.Filter;
import org.box2d.r3.api.FixtureDef;
import org.box2d.r3.api.Shape;

public class GdxFixtureDef implements FixtureDef, Filter {
	final com.badlogic.gdx.physics.box2d.FixtureDef gdx_fixture;

	public GdxFixtureDef () {
		this.gdx_fixture = new com.badlogic.gdx.physics.box2d.FixtureDef();
	}

	@Override
	public void setDensity (final double density) {
		this.gdx_fixture.density = (float)density;
	}

	@Override
	public void setRestitution (final double restitution) {
		this.gdx_fixture.restitution = (float)restitution;
	}

	@Override
	public void setFriction (final double friction) {
		this.gdx_fixture.friction = (float)friction;
	}

	@Override
	public void setIsSensor (final boolean is_sensor) {
		this.gdx_fixture.isSensor = is_sensor;
	}

	@Override
	public Filter filter () {
		return this;
	}

	@Override
	public void setShape (final Shape boxPoly) {
		this.gdx_fixture.shape = ((GdxShape)boxPoly).getGdxShape();
	}

	@Override
	public void setCategoryBits (final short categoryBits) {
		this.gdx_fixture.filter.categoryBits = categoryBits;
	}

	@Override
	public void setMaskBits (final short maskBits) {
		this.gdx_fixture.filter.maskBits = maskBits;
	}

	public com.badlogic.gdx.physics.box2d.FixtureDef getGdxFixture () {
		return this.gdx_fixture;
	}

	@Override
	public double getDensity () {
		return this.gdx_fixture.density;
	}

}
