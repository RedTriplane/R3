package com.jfixby.r3.api.unit.components.physics.body;

import com.jfixby.r3.api.unit.components.physics.U_ContactListener;

public interface BodyListeners {
	void setContactListener(U_ContactListener contact_listener);

	U_ContactListener getContactListener();

	void setDynamicsListener(BodyDynamicsListener gravity_force_applicator);

	BodyDynamicsListener getDynamicsListener();

	// ITimeUpdateListener getOnTimeUpdateListener();
	//
	// void setOnTimeUpdateListener(final ITimeUpdateListener
	// time_update_listener);
}
