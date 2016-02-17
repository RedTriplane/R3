package com.jfixby.r3.fokker.api;

public interface EngineState {

	long getCurrentCycleNumber();

	InputQueue getInputQueue();

	boolean isBroken();

}
