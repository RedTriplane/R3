
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.PhysicsCore;
import com.jfixby.r3.api.physics.PhysicsCoreRotor;
import com.jfixby.r3.physics.RedPhysicsCoreRotor;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.time.TimeStream;

public class DuplexPhysicsCoreRotor extends RedPhysicsCoreRotor implements PhysicsCoreRotor {

	final private List<PhysicsCore> list;

	public DuplexPhysicsCoreRotor (List<PhysicsCore> list) {
		this.list = list;

	}

	@Override
	public TimeStream getGameTime () {
		return list.getElementAt(0).getRotor().getGameTime();
	}

	@Override
	public long getCurrentCycle () {
		return list.getElementAt(0).getRotor().getCurrentCycle();
	}

	@Override
	public void onGameUpdate () {
		for (int i = 0; i < this.list.size(); i++) {
			((RedPhysicsCoreRotor)(this.list.getElementAt(i).getRotor())).onGameUpdate();
		}
	}

	@Override
	public void onSystemUpdate () {
		for (int i = 0; i < this.list.size(); i++) {
			((RedPhysicsCoreRotor)(this.list.getElementAt(i).getRotor())).onSystemUpdate();
		}
	}

}
