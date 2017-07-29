package com.jfixby.r3.api.physics;

public interface Physics2DComponent {

	PhysicsCoreSpecs newPhysicsCoreSpecs();

	PhysicsCore newPhysicsCore(PhysicsCoreSpecs core_specs);

	BoxBody newBoxBody();

	CircleBody newCircleBody();

	PolyBody newPolyBody(PolyBodySpecs geometry);

	PolyBodySpecs newPolyBodySpecs();

}
