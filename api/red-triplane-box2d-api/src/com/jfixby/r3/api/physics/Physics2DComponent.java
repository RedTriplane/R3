package com.jfixby.r3.api.physics;

import com.jfixby.r3.api.unit.components.physics.PhysicsCore;
import com.jfixby.r3.api.unit.components.physics.PhysicsCoreSpecs;
import com.jfixby.r3.api.unit.components.physics.body.BoxBody;
import com.jfixby.r3.api.unit.components.physics.body.CircleBody;
import com.jfixby.r3.api.unit.components.physics.body.PolyBody;

public interface Physics2DComponent {

	PhysicsCoreSpecs newPhysicsCoreSpecs();

	PhysicsCore newPhysicsCore(PhysicsCoreSpecs core_specs);

	BoxBody newBoxBody();

	CircleBody newCircleBody();

	PolyBody newPolyBody(PolyBodySpecs geometry);

	PolyBodySpecs newPolyBodySpecs();

}
