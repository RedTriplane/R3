package com.jfixby.r3.api.physics;

public interface PhysicsCore {

	PhysicsCoreRotor getRotor();

	void attachBody(AbstractBody body);
	
	void detatchBody(AbstractBody body);

	
}
