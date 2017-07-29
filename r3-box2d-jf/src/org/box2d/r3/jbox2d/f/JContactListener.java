
package org.box2d.r3.jbox2d.f;

public class JContactListener implements org.jbox2d.f.callbacks.ContactListener {

	private final org.box2d.r3.api.ContactListener contactListener;

	public JContactListener (final org.box2d.r3.api.ContactListener contactListener) {
		this.contactListener = contactListener;
	}

	@Override
	public void beginContact (final org.jbox2d.f.dynamics.contacts.Contact contact) {
		// Err.reportError();
	}

	@Override
	public void endContact (final org.jbox2d.f.dynamics.contacts.Contact contact) {
		// Err.reportError();
	}

	@Override
	public void preSolve (final org.jbox2d.f.dynamics.contacts.Contact contact,
		final org.jbox2d.f.collision.Manifold oldManifold) {
		// Err.reportError();
	}

	@Override
	public void postSolve (final org.jbox2d.f.dynamics.contacts.Contact contact,
		final org.jbox2d.f.callbacks.ContactImpulse impulse) {
		// Err.reportError();
	}

}
