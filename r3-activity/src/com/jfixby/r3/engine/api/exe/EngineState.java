
package com.jfixby.r3.engine.api.exe;

public interface EngineState {

	long getCurrentCycleNumber ();

	InputQueue getInputQueue ();

	boolean isBroken ();

}
