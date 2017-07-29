
package org.box2d.r3.jbox2d.d;

import org.box2d.r3.api.Filter;
import org.box2d.r3.api.FixtureDef;
import org.box2d.r3.api.Shape;

public class JFixtureDef implements FixtureDef, Filter {
	final org.jbox2d.d.dynamics.FixtureDef gdx_fixture;

	public JFixtureDef () {
		this.gdx_fixture = new org.jbox2d.d.dynamics.FixtureDef();
	}

	@Override
	public void setDensity (final double density) {
		this.gdx_fixture.density = density;
	}

	@Override
	public void setRestitution (final double restitution) {
		this.gdx_fixture.restitution = restitution;
	}

	@Override
	public void setFriction (final double friction) {
		this.gdx_fixture.friction = friction;
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
		this.gdx_fixture.shape = ((JShape)boxPoly).getGdxShape();
	}

	@Override
	public void setCategoryBits (final short categoryBits) {
		this.gdx_fixture.filter.categoryBits = categoryBits;
	}

	@Override
	public void setMaskBits (final short maskBits) {
		this.gdx_fixture.filter.maskBits = maskBits;
	}

	public org.jbox2d.d.dynamics.FixtureDef getGdxFixture () {
		return this.gdx_fixture;
	}

	@Override
	public double getDensity () {
		return this.gdx_fixture.density;
	}

}
