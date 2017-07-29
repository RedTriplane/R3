
package org.box2d.r3.jbox2d.f;

import org.box2d.r3.api.BodyType;
import org.box2d.r3.api.Box2DBody;
import org.box2d.r3.api.Box2DTransform;
import org.box2d.r3.api.Fixture;
import org.box2d.r3.api.FixtureDef;
import org.box2d.r3.api.MassData;
import org.jbox2d.f.common.Vector2;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class JBox2DBody implements Box2DBody {

	private final org.jbox2d.f.dynamics.Body gdx_body;
	private final JMassData mass_data;
	private final Float2 position = Geometry.newFloat2();
	private final Float2 velocity = Geometry.newFloat2();
	private final Float2 center = Geometry.newFloat2();
	private final Vector2 tmp2 = new Vector2();

	public JBox2DBody (final org.jbox2d.f.dynamics.Body createBody) {
		this.gdx_body = createBody;
		this.mass_data = new JMassData(this.gdx_body);
	}

	@Override
	public void setLinearVelocity (final double vx, final double vy) {
		// gdx_body.setLinearVelocity((float) vx, (float) vy);
		this.tmp2.set((float)vx, (float)vy);
		this.gdx_body.setLinearVelocity(this.tmp2);
	}

	@Override
	public void setTransform (final double x, final double y, final double radians) {
		this.tmp2.set((float)x, (float)y);
		this.gdx_body.setTransform(this.tmp2, (float)radians);

	}

	@Override
	public void applyForceToCenter (final double forceX, final double forceY, final boolean wake) {
		this.tmp2.set((float)forceX, (float)forceY);
		this.gdx_body.applyForceToCenter(this.tmp2);

		// gdx_body.applyForceToCenter((float) forceX, (float) forceY, wake);
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
		final JFixtureDef def = (JFixtureDef)fixture;
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
		this.tmp2.set((float)worldCenter.getX(), (float)worldCenter.getY());
		this.gdx_body.setTransform(this.tmp2, (float)angle);
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
		final org.jbox2d.f.common.Transform transform = this.gdx_body.getTransform();
		final JTransform gdx_transform = new JTransform(transform);
		return gdx_transform;
	}

	@Override
	public Collection<Fixture> getFixtureList () {
		org.jbox2d.f.dynamics.Fixture fixtures = this.gdx_body.getFixtureList();

		final List<Fixture> fixs = Collections.newList();
		while (fixtures != null) {
			final JFixture F = new JFixture(fixtures);
			fixs.add(F);
			fixtures = fixtures.getNext();
		}

		return fixs;
	}

	@Override
	public BodyType getType () {
		final org.jbox2d.f.dynamics.BodyType T = this.gdx_body.getType();
		if (T == org.jbox2d.f.dynamics.BodyType.DYNAMIC) {
			return BodyType.DynamicBody;
		}
		if (T == org.jbox2d.f.dynamics.BodyType.KINEMATIC) {
			return BodyType.KinematicBody;
		}
		if (T == org.jbox2d.f.dynamics.BodyType.STATIC) {
			return BodyType.StaticBody;
		}
		Err.throwNotImplementedYet();
		return null;

	}

	@Override
	public boolean isAwake () {
		return this.gdx_body.isAwake();
	}

	public org.jbox2d.f.dynamics.Body getGdxBody () {
		return this.gdx_body;
	}

}
