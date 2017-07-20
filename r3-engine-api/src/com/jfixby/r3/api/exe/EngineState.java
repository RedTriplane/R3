
package com.jfixby.r3.api.exe;

public interface EngineState {

	long getCurrentCycleNumber ();

	InputQueue getInputQueue ();

	boolean isBroken ();

}
