
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.BodyDynamicsListener;
import com.jfixby.r3.api.physics.BodyListeners;
import com.jfixby.r3.api.physics.U_ContactListener;

public class BodyListenersImpl implements BodyListeners {
	private U_ContactListener contact_listener;
	private final BodyDynamicsImpl dynamics;

	public BodyListenersImpl (BodyDynamicsImpl dynamics) {
		super();
		this.dynamics = dynamics;
	}

	public void setContactListener (final U_ContactListener contact_listener) {
		this.contact_listener = contact_listener;
	}

	public U_ContactListener getContactListener () {
		return this.contact_listener;
	}

	public void onCollisionBeginWith (final BodyAvatar red_body_b) {
		final U_ContactListener listener = this.contact_listener;
		if (listener != null) {
			listener.onContactBegin(red_body_b.getMaster());
		}
	}

	public void onCollisionEndWith (final BodyAvatar red_body_b) {
		final U_ContactListener listener = this.contact_listener;
		if (listener != null) {
			listener.onContactEnd(red_body_b.getMaster());
		}
	}

	public void onCollisionLostWith (final BodyAvatar red_body_b) {
		final U_ContactListener listener = this.contact_listener;
		if (listener != null) {
			listener.onContactLost(red_body_b.getMaster());
		}
	}

	@Override
	public void setDynamicsListener (BodyDynamicsListener dynamics_listener) {
		dynamics.setDynamicsListener(dynamics_listener);
	}

	@Override
	public BodyDynamicsListener getDynamicsListener () {
		return this.dynamics.getDynamicsListener();
	}

}
