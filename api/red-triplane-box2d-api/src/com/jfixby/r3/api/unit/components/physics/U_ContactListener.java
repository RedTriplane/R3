package com.jfixby.r3.api.unit.components.physics;

import com.jfixby.r3.api.unit.components.physics.body.Body;

public interface U_ContactListener {

	void onContactBegin(final Body other);

	void onContactEnd(final Body other);

	void onContactLost(final Body other);

}
