
package org.box2d.r3.jbox2d.f;

import org.box2d.r3.api.BodyDef;
import org.box2d.r3.api.Box2DBody;
import org.box2d.r3.api.Box2DWorld;
import org.box2d.r3.api.Box2DWorldSpecs;
import org.box2d.r3.api.ContactListener;
import org.jbox2d.f.common.Vector2;
import org.jbox2d.f.dynamics.Body;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.floatn.Float2;

public class JWorld implements Box2DWorld {

	private final org.jbox2d.f.dynamics.World gdx_world;

	// private Box2DWorldRenderer renderer;

	public JWorld (final Box2DWorldSpecs specs) {
		final Float2 g = specs.getGravity();
		final Vector2 gravity = new Vector2((float)g.getX(), (float)g.getY());
		this.gdx_world = new org.jbox2d.f.dynamics.World(gravity);
		this.gdx_world.setAllowSleep(specs.getDoSleep());
		// specs.getDoSleep()
	}

	@Override
	public Box2DBody createBody (final BodyDef boxBodyDef) {
		return new JBox2DBody(this.gdx_world.createBody(((JBodyDef)(boxBodyDef)).getGdxBodyDef()));
	}

	@Override
	public void destroyBody (final Box2DBody body) {
		this.gdx_world.destroyBody(((JBox2DBody)body).getGdxBody());
	}

	@Override
	public void step (final double box2d_time_step_delta, final int velocityIterations, final int positionIterations) {
		this.gdx_world.step((float)box2d_time_step_delta, velocityIterations, positionIterations);
	}

	@Override
	public void dispose () {
		// gdx_world.dispose();
	}

	@Override
	public void setContactListener (final ContactListener contactListener) {
		this.gdx_world.setContactListener(new JContactListener(contactListener));
	}

	@Override
	public void setAutoClearForces (final boolean autoclearForces) {
		this.gdx_world.setAutoClearForces(autoclearForces);
	}

	@Override
	public List<Box2DBody> listBodies () {

		Body bodies = this.gdx_world.getBodyList();
		final List<Box2DBody> list = Collections.newList();
		while (bodies != null) {
			final JBox2DBody body = new JBox2DBody(bodies);
			list.add(body);
			bodies = bodies.getNext();
		}
		return list;
	}
	// @Override
	// public Box2DWorldRenderer getRenderer() {
	// if (this.renderer == null) {
	// this.renderer = new GDXBox2DDebugRenderer();
	// }
	// return this.renderer;
	// }

	@Override
	public boolean isFloatPrecision () {
		return true;
	}

	@Override
	public boolean isDoublePrecision () {
		return false;
	}

}
