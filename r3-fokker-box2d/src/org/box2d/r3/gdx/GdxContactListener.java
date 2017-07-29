
package org.box2d.r3.gdx;

public class GdxContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

	private final org.box2d.r3.api.ContactListener contactListener;

	public GdxContactListener (final org.box2d.r3.api.ContactListener contactListener) {
		this.contactListener = contactListener;
	}

	@Override
	public void beginContact (final com.badlogic.gdx.physics.box2d.Contact contact) {
		// Err.reportError();
	}

	@Override
	public void endContact (final com.badlogic.gdx.physics.box2d.Contact contact) {
		// Err.reportError();
	}

	@Override
	public void preSolve (final com.badlogic.gdx.physics.box2d.Contact contact,
		final com.badlogic.gdx.physics.box2d.Manifold oldManifold) {
		// Err.reportError();
	}

	@Override
	public void postSolve (final com.badlogic.gdx.physics.box2d.Contact contact,
		final com.badlogic.gdx.physics.box2d.ContactImpulse impulse) {
		// Err.reportError();
	}

}
