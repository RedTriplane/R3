
package com.jfixby.r3.physics;

import org.box2d.jfixby.api.BodyType;
import org.box2d.jfixby.api.Box2DBody;
import org.box2d.jfixby.api.Box2DComponent;
import org.box2d.jfixby.api.Box2DWorld;
import org.box2d.jfixby.api.Box2DWorldSpecs;

import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.math.Angle;

public final class Box2DWorldWrap {

	private final Box2DWorld gdx_box2d_world;
	final private int positionIterations;
	final private int velocityIterations;

	final double box2d_time_step_delta;

	final private Box2DWorldContactListener contactListener = new Box2DWorldContactListener();
	private final RedTriplanePhysics redTriplanePhysics;

	public Box2DWorldWrap (Box2DWorldConfig box2d_world_config, RedTriplanePhysics redTriplanePhysics) {
		this.redTriplanePhysics = redTriplanePhysics;

		final Float2 gravity_vector = box2d_world_config.getGravityVector();
		final boolean doSleep = box2d_world_config.allowSleepingBodies();

		Box2DWorldSpecs specs = redTriplanePhysics.box2d.newBox2DWorldSpecs();

		specs.setGravity(gravity_vector);
		specs.setDoSleep(doSleep);

		gdx_box2d_world = redTriplanePhysics.box2d.newBox2DWorld(specs);
		positionIterations = box2d_world_config.getPositionIterations();
		velocityIterations = box2d_world_config.getVelocityIterations();
		box2d_time_step_delta = box2d_world_config.getBox2DTimeStepping();
		gdx_box2d_world.setContactListener(contactListener);
		gdx_box2d_world.setAutoClearForces(box2d_world_config.getAutoclearForces());
		gdx_box2d_world.step(box2d_time_step_delta, velocityIterations, positionIterations);
		// Log.e("box2d_world_config", box2d_world_config);
	}

	public Box2DComponent getBox2DComponent () {
		return redTriplanePhysics.box2d;
	}

	final public void dispose () {
		gdx_box2d_world.dispose();
	}

	final public void make_one_time_step () {
		this.contactListener.open();
		this.gdx_box2d_world.step(this.box2d_time_step_delta, this.velocityIterations, this.positionIterations);
		this.contactListener.close();
	}

	final public void dispatch_collisions () {
		this.contactListener.dispatchAllCollisions();
	}

	public Box2DBody createBoxCall (final Angle angle, final double x, final double y, final double w, final double h,
		final boolean is_sensor, final BodyType type, final double vx, final double vy, final double density, final double friction,
		final double restitution, final short categoryBits, final short maskBits,
		final PhysicalParametersContainer gdx_ph_container) {

		final Box2DBody body = Box2DBodyFactory.createBox(this.gdx_box2d_world, angle.toRadians(), w, h, density, friction,
			restitution, is_sensor, type, x, y, categoryBits, maskBits, gdx_ph_container);
		body.setLinearVelocity(vx, vy);
		return body;

	}

	public Box2DBody createCircleCall (final Angle angle, final double x, final double y, final double radius,
		final boolean is_sensor, final BodyType type, final double vx, final double vy, final double density, final double friction,
		final double restitution, final short categoryBits, final short maskBits,
		final PhysicalParametersContainer gdx_ph_container) {

		final Box2DBody body = Box2DBodyFactory.createCircle(this.gdx_box2d_world, angle.toRadians(), radius, density, friction,
			restitution, is_sensor, type, x, y, categoryBits, maskBits, gdx_ph_container);
		body.setLinearVelocity(vx, vy);
		return body;
	}

	public void disposeBodyCall (final Box2DBody body) {
		Box2DBodyFactory.disposeBody(this.gdx_box2d_world, body);
	}

	public Box2DWorldContactListener getContactListener () {
		return this.contactListener;
	}

	public Box2DBody createPolyBodyCall (final RedPolyBodySpecs shape, final Angle angle, final double x, final double y,
		final boolean is_sensor, final BodyType type, final double vx, final double vy, final double density, final double friction,
		final double restitution, final double scale, final short categoryBits, final short maskBits,
		final PhysicalParametersContainer gdx_ph_container) {
		final Box2DBody body = Box2DBodyFactory.createPoly(shape, this.gdx_box2d_world, angle.toRadians(), density, friction,
			restitution, is_sensor, type, x, y, scale, categoryBits, maskBits, gdx_ph_container);
		body.setLinearVelocity(vx, vy);
		return body;
	}

	public Box2DWorld world () {
		return this.gdx_box2d_world;
	}

	public Box2DWorld getWorldForRender () {
		return gdx_box2d_world;
	}

}
