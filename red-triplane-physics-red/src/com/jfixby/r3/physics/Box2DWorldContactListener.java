
package com.jfixby.r3.physics;

import org.box2d.jfixby.api.Box2DBody;
import org.box2d.jfixby.api.Contact;
import org.box2d.jfixby.api.ContactImpulse;
import org.box2d.jfixby.api.ContactListener;
import org.box2d.jfixby.api.Manifold;

import com.jfixby.r3.physics.RedCollisionEvent.COLLISION_EVENT_HEADER;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Pool;
import com.jfixby.scarabei.api.collections.PoolElementsSpawner;
import com.jfixby.scarabei.api.collections.Queue;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class Box2DWorldContactListener implements ContactListener {

	private boolean opened = false;

	public Box2DWorldContactListener () {
		super();
		// TODO Auto-generated constructor stub
		this.opened = false;
	}

	final Queue<RedCollisionEvent> queue = Collections.newQueue();
	private final PoolElementsSpawner<RedCollisionEvent> spawner = new PoolElementsSpawner<RedCollisionEvent>() {

		@Override
		public RedCollisionEvent spawnNewInstance () {
			return new RedCollisionEvent();
		}
	};
	final Pool<RedCollisionEvent> pool = Collections.newPool(this.spawner);

	private void submit_contact_begin (final BodyAvatar red_body_a, final BodyAvatar red_body_b) {
		final RedCollisionEvent current_collision_event;
		current_collision_event = this.pool.obtain();
		current_collision_event.setHeader(COLLISION_EVENT_HEADER.BEGIN_CONTACT);
		current_collision_event.setAvatarA(red_body_a);
		current_collision_event.setAvatarB(red_body_b);
		this.queue.enqueue(current_collision_event);

	}

	private void submit_contact_end (final BodyAvatar red_body_a, final BodyAvatar red_body_b) {
		// Log.d("submit_end_contact", body_a, body_b);
		final RedCollisionEvent current_collision_event;
		current_collision_event = this.pool.obtain();
		current_collision_event.setHeader(COLLISION_EVENT_HEADER.END_CONTACT);
		current_collision_event.setAvatarA(red_body_a);
		current_collision_event.setAvatarB(red_body_b);
		this.queue.enqueue(current_collision_event);

	}

	private void submit_contact_lost (final BodyAvatar red_body_a, final BodyAvatar red_body_b) {
		// Log.d("submit_end_contact", body_a, body_b);
		final RedCollisionEvent current_collision_event;
		current_collision_event = this.pool.obtain();
		current_collision_event.setHeader(COLLISION_EVENT_HEADER.LOST_CONTACT);
		current_collision_event.setAvatarA(red_body_a);
		current_collision_event.setAvatarB(red_body_b);
		this.queue.enqueue(current_collision_event);

	}

	public void dispatchAllCollisions () {
		while (this.queue.hasMore()) {
			final RedCollisionEvent current_collision_event;
			current_collision_event = this.queue.dequeue();
			this.process_collision_event(current_collision_event);
			current_collision_event.dispose();
			this.pool.free(current_collision_event);
		}
	}

	private void process_collision_event (final RedCollisionEvent collision) {
		// Log.d(collision.toDebugString() + "");
		final BodyAvatar red_body_a = collision.getAvatarA();
		final BodyAvatar red_body_b = collision.getAvatarB();
		final COLLISION_EVENT_HEADER header = collision.getHeader();
		if (header == COLLISION_EVENT_HEADER.BEGIN_CONTACT) {
			red_body_a.onCollisionBeginWith(red_body_b);
			red_body_b.onCollisionBeginWith(red_body_a);
		} else if (header == COLLISION_EVENT_HEADER.BEGIN_CONTACT) {
			red_body_a.onCollisionEndWith(red_body_b);
			red_body_b.onCollisionEndWith(red_body_a);
		} else {
			red_body_a.onCollisionLostWith(red_body_b);
			red_body_b.onCollisionLostWith(red_body_a);
		}

	}

	final Map<Box2DBody, BodyAvatar> registry = Collections.newMap();

	public void register (final Box2DBody gdx_body, final BodyAvatar red_body) {
		// Log.e("register", red_body);
		// if (registry.containsKey(gdx_body)) {
		// Err.reportError("Contact registry is corrupted!");
		// }

		this.registry.put(gdx_body, red_body);

	}

	public void unregister (final Box2DBody gdx_body, final BodyAvatar red_body) {

		if (!this.registry.containsKey(gdx_body)) {
			Err.reportError("Contact registry is corrupted! gdx_body= " + gdx_body + " red_body=" + red_body);
		}

		this.registry.remove(gdx_body);
	}

	@Override
	public void beginContact (final Contact contact) {

		if (this.opened) {
			final Box2DBody body_a = contact.//
				getFixtureA().//
				getBody();
			final Box2DBody body_b = contact.//
				getFixtureB().//
				getBody();

			final BodyAvatar red_body_a = this.registry.get(body_a);
			final BodyAvatar red_body_b = this.registry.get(body_b);

			red_body_a.warpOut();
			red_body_b.warpOut();

			this.submit_contact_begin(red_body_a, red_body_b);

		} else {
			L.e("beginContact???");
		}
	}

	@Override
	public void endContact (final Contact contact) {

		if (this.opened) {
			final Box2DBody body_a = contact.//
				getFixtureA().//
				getBody();
			final Box2DBody body_b = contact.//
				getFixtureB().//
				getBody();

			final BodyAvatar red_body_a = this.registry.get(body_a);
			final BodyAvatar red_body_b = this.registry.get(body_b);

			red_body_a.warpOut();
			red_body_b.warpOut();

			this.submit_contact_end(red_body_a, red_body_b);

		} else {
			final Box2DBody body_a = contact.//
				getFixtureA().//
				getBody();
			final Box2DBody body_b = contact.//
				getFixtureB().//
				getBody();

			final BodyAvatar red_body_a = this.registry.get(body_a);
			final BodyAvatar red_body_b = this.registry.get(body_b);

			red_body_a.warpOut();
			red_body_b.warpOut();

			this.submit_contact_lost(red_body_a, red_body_b);

		}
	}

	@Override
	public void preSolve (final Contact contact, final Manifold oldManifold) {
	}

	@Override
	public void postSolve (final Contact contact, final ContactImpulse impulse) {
	}

	public void open () {
		this.opened = true;

	}

	public void close () {
		this.opened = false;
	}

}
