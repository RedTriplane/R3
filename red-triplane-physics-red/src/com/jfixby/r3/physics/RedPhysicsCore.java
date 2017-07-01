
package com.jfixby.r3.physics;

import org.box2d.jfixby.api.Box2DWorld;
import org.box2d.jfixby.api.Box2DWorldsContainer;

import com.jfixby.r3.api.physics.AbstractBody;
import com.jfixby.r3.api.physics.Body;
import com.jfixby.r3.api.physics.PhysicsCore;
import com.jfixby.r3.api.physics.PhysicsCoreRotor;
import com.jfixby.r3.api.physics.PhysicsCoreSpecs;
import com.jfixby.r3.physics.body.BodyImpl;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;

public class RedPhysicsCore implements PhysicsCore, Box2DWorldsContainer {

	final private PhysicsCoreRotorImpl rotor;
	final private Box2DWorldWrap box2d_world;

	final private Box2DWorldConfig box2d_world_config = new Box2DWorldConfig();
	private STATE state;
	private final double GameSPS;

	private final BodiesContainer bodies_container;

	// final private Box2DWorldEventsProcessor worl_events_processor;

	enum STATE {
		OK, DISPOSED
	}

	public RedPhysicsCore (final PhysicsCoreSpecs game_universe_config, final RedTriplanePhysics redTriplanePhysics) {

		this.rotor = new PhysicsCoreRotorImpl(game_universe_config, this);
		this.GameSPS = 1000f / game_universe_config.getTimeDeltaPerStepInTheCore();
		this.box2d_world_config.setGravity(game_universe_config.getGravityX(), game_universe_config.getGravityY());

		this.box2d_world = new Box2DWorldWrap(this.box2d_world_config, redTriplanePhysics);
		this.state = STATE.OK;

		this.bodies_container = new BodiesContainer(this.box2d_world);

	}

	final double box2DsecondsToGameSeconds (final double box2d_seconds) {
		return (Box2DWorldConfig.STEPS_PER_BOX2D_SECOND / this.GameSPS) * box2d_seconds;
	}

	@Override
	public PhysicsCoreRotor getRotor () {
		return this.rotor;
	}

	public void dispose () {
		if (this.state == STATE.OK) {
			this.box2d_world.dispose();
			this.state = STATE.DISPOSED;
		} else {
			Err.reportError("PhysicsCore is already disposed!");
		}
	}

	@Override
	public void attachBody (final AbstractBody body) {
		if (body == null) {
			Err.reportError("null");
		}
		if (body instanceof Body) {
			this.bodies_container.attach((BodyImpl)body);
		}

	}

	@Override
	public void detatchBody (final AbstractBody body) {
		if (body instanceof Body) {
			this.bodies_container.detatch((BodyImpl)body);
		}

	}

	// public Box2DWorldRenderer getWorldForRender() {
	// return this.box2d_world.getRenderer();
	// }

	public void doStep (final IRotorStatus rotor_status) {

		/*
		 * Here we check if user moved some bodies and relocate corresponding avatars
		 */
		this.bodies_container.warp_in_all();

		/*
		 * Flag non-static bodies for receiving updated avatar locations after the physical simulation
		 */
		this.bodies_container.prepare_to_warp_out_all();

		/* Redefine bodies shapes an physical properties if changed by user */
		this.bodies_container.redefine_all();

		/* Apply forces */
		this.bodies_container.apply_all_forces();

		/* Run the physical simulation */
		this.box2d_world.make_one_time_step();

		/* Receive updated avatar locations */
		this.bodies_container.warp_out_all();

		/* Process collisions */
		this.box2d_world.dispatch_collisions();

	}

	List<Box2DWorld> c;

	@Override
	public List<Box2DWorld> getWorldsForRender () {
		if (this.c == null) {
			this.c = Collections.newList();
			this.c.add(this.box2d_world.getWorldForRender());
		}
		return this.c;

	}

}
