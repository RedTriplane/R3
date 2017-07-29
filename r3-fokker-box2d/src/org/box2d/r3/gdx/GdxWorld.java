
package org.box2d.r3.gdx;

import org.box2d.r3.api.BodyDef;
import org.box2d.r3.api.Box2DBody;
import org.box2d.r3.api.Box2DWorld;
import org.box2d.r3.api.Box2DWorldSpecs;
import org.box2d.r3.api.ContactListener;

//import org.box2d.jfixby.api.Box2DBody;
//import org.box2d.jfixby.api.Box2DWorld;
//import org.box2d.jfixby.api.Box2DWorldSpecs;

//import org.box2d.jfixby.api.BodyDef;
//import org.box2d.jfixby.api.Box2DBody;
//import org.box2d.jfixby.api.Box2DWorld;
//import org.box2d.jfixby.api.Box2DWorldSpecs;
//import org.box2d.jfixby.api.ContactListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.floatn.Float2;

public class GdxWorld implements Box2DWorld {

	private final com.badlogic.gdx.physics.box2d.World gdx_world;

	// private Box2DWorldRenderer renderer;

	public GdxWorld (final Box2DWorldSpecs specs) {
		final Float2 g = specs.getGravity();
		final Vector2 gravity = new Vector2((float)g.getX(), (float)g.getY());
		this.gdx_world = new com.badlogic.gdx.physics.box2d.World(gravity, specs.getDoSleep());
	}

	@Override
	public Box2DBody createBody (final BodyDef boxBodyDef) {
		return new GdxBox2DBody(this.gdx_world.createBody(((GdxBodyDef)(boxBodyDef)).getGdxBodyDef()));
	}

	@Override
	public void destroyBody (final Box2DBody body) {
		this.gdx_world.destroyBody(((GdxBox2DBody)body).getGdxBody());
	}

	@Override
	public void step (final double box2d_time_step_delta, final int velocityIterations, final int positionIterations) {
		this.gdx_world.step((float)box2d_time_step_delta, velocityIterations, positionIterations);
	}

	@Override
	public void dispose () {
		this.gdx_world.dispose();
	}

	@Override
	public void setContactListener (final ContactListener contactListener) {
		this.gdx_world.setContactListener(new GdxContactListener(contactListener));
	}

	@Override
	public void setAutoClearForces (final boolean autoclearForces) {
		this.gdx_world.setAutoClearForces(autoclearForces);
	}

	@Override
	public List<Box2DBody> listBodies () {
		final Array<com.badlogic.gdx.physics.box2d.Body> gdx_array = new Array<>();
		this.gdx_world.getBodies(gdx_array);
		final List<Box2DBody> list = Collections.newList();
		for (final com.badlogic.gdx.physics.box2d.Body i : gdx_array) {
			final GdxBox2DBody body = new GdxBox2DBody(i);
			list.add(body);
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
