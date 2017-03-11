package com.jfixby.r3.api.unit.components.physics;




public interface PhysicsCore {

	PhysicsCoreRotor getRotor();

	void attachBody(AbstractBody body);
	
	void detatchBody(AbstractBody body);

	
}
