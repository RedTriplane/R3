
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.PhysicsCoreRotor;

public interface IRotorCycleListener {

	public static IRotorCycleListener NULL = new IRotorCycleListener() {

		@Override
		public void onPreCycle (PhysicsCoreRotor rotor) {
		}

		@Override
		public void onPostCycle (PhysicsCoreRotor rotor) {
		}

	};

	void onPreCycle (PhysicsCoreRotor rotor);

	void onPostCycle (PhysicsCoreRotor rotor);

}
