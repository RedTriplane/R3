
package org.box2d.r3.gdx;

import org.box2d.r3.api.BodyType;
import org.box2d.r3.api.Box2DBody;
import org.box2d.r3.api.Box2DTransform;
import org.box2d.r3.api.Fixture;
import org.box2d.r3.api.FixtureDef;
import org.box2d.r3.api.MassData;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class GdxBox2DBody implements Box2DBody {

	private final com.badlogic.gdx.physics.box2d.Body gdx_body;
	private final GdxMassData mass_data;
	private final Float2 position = Geometry.newFloat2();
	private final Float2 velocity = Geometry.newFloat2();
	private final Float2 center = Geometry.newFloat2();

	public GdxBox2DBody (final com.badlogic.gdx.physics.box2d.Body createBody) {
		this.gdx_body = createBody;
		this.mass_data = new GdxMassData(this.gdx_body);
	}

	@Override
	public void setLinearVelocity (final double vx, final double vy) {
		this.gdx_body.setLinearVelocity((float)vx, (float)vy);
	}

	@Override
	public void setTransform (final double x, final double y, final double radians) {
		this.gdx_body.setTransform((float)x, (float)y, (float)radians);
	}

	@Override
	public void applyForceToCenter (final double forceX, final double forceY, final boolean wake) {
		this.gdx_body.applyForceToCenter((float)forceX, (float)forceY, wake);
	}

	@Override
	public MassData getMassData () {
		return this.mass_data;
	}

	@Override
	public void setMassData (final MassData mass_data) {
		this.mass_data.set(mass_data);
	}

	@Override
	public Float2 getPosition () {
		final Vector2 p = this.gdx_body.getPosition();
		this.position.setXY(p.x, p.y);
		return this.position;
	}

	@Override
	public double getAngle () {
		return this.gdx_body.getAngle();
	}

	@Override
	public Float2 getLinearVelocity () {
		final Vector2 p = this.gdx_body.getLinearVelocity();
		this.velocity.setXY(p.x, p.y);
		return this.velocity;
	}

	@Override
	public void createFixture (final FixtureDef fixture) {
		final GdxFixtureDef def = (GdxFixtureDef)fixture;
		this.gdx_body.createFixture(def.getGdxFixture());
	}

	@Override
	public Float2 getWorldCenter () {
		final Vector2 p = this.gdx_body.getWorldCenter();
		this.center.setXY(p.x, p.y);
		return this.center;
	}

	@Override
	public void setTransform (final Float2 worldCenter, final double angle) {
		this.gdx_body.setTransform((float)worldCenter.getX(), (float)worldCenter.getY(), (float)angle);
	}

	@Override
	public Float2 getLocalPoint (final Float2 anchorA) {
		Err.throwNotImplementedYet();
		return anchorA;
	}

	@Override
	public boolean isActive () {
		return this.gdx_body.isActive();
	}

	@Override
	public Box2DTransform getTransform () {
		final com.badlogic.gdx.physics.box2d.Transform transform = this.gdx_body.getTransform();
		final GdxTransform gdx_transform = new GdxTransform(transform);
		return gdx_transform;
	}

	@Override
	public Collection<Fixture> getFixtureList () {
		final Array<com.badlogic.gdx.physics.box2d.Fixture> fixtures_list = this.gdx_body.getFixtureList();

		final List<Fixture> fixs = Collections.newList();
		for (final com.badlogic.gdx.physics.box2d.Fixture f : fixtures_list) {
			final GdxFixture F = new GdxFixture(f);
			fixs.add(F);
		}

		return fixs;
	}

	@Override
	public BodyType getType () {
		final com.badlogic.gdx.physics.box2d.BodyDef.BodyType T = this.gdx_body.getType();
		if (T == com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody) {
			return BodyType.DynamicBody;
		}
		if (T == com.badlogic.gdx.physics.box2d.BodyDef.BodyType.KinematicBody) {
			return BodyType.KinematicBody;
		}
		if (T == com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody) {
			return BodyType.StaticBody;
		}
		Err.throwNotImplementedYet();
		return null;

	}

	@Override
	public boolean isAwake () {
		return this.gdx_body.isAwake();
	}

	public com.badlogic.gdx.physics.box2d.Body getGdxBody () {
		return this.gdx_body;
	}

}
