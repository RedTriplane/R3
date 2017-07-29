
package com.jfixby.r3.physics;

import com.jfixby.scarabei.api.time.TimeStream;

public abstract class RedPhysicsCoreRotor {

	abstract public void onGameUpdate ();

	abstract public void onSystemUpdate ();

	abstract public TimeStream getGameTime ();

}
