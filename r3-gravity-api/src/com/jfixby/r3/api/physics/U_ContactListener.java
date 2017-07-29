package com.jfixby.r3.api.physics;

public interface U_ContactListener {

	void onContactBegin(final Body other);

	void onContactEnd(final Body other);

	void onContactLost(final Body other);

}
