
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.BodyDynamicsListener;
import com.jfixby.r3.api.physics.BodyListeners;
import com.jfixby.r3.api.physics.U_ContactListener;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;

public class DuplexBodyListeners implements BodyListeners {

	private final List<BodyListeners> list;

	public DuplexBodyListeners (final List<BodyListeners> list) {
		this.list = list;
	}

	@Override
	public void setContactListener (final U_ContactListener contact_listener) {
		Err.throwNotImplementedYet();
	}

	@Override
	public U_ContactListener getContactListener () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public void setDynamicsListener (final BodyDynamicsListener gravity_force_applicator) {
		Err.throwNotImplementedYet();
	}

	@Override
	public BodyDynamicsListener getDynamicsListener () {
		Err.throwNotImplementedYet();
		return null;
	}

}
