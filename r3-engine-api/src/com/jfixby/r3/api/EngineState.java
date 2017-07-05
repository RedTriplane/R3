
package com.jfixby.r3.api;

public interface EngineState {

	long getCurrentCycleNumber ();

	InputQueue getInputQueue ();

	boolean isBroken ();

}
