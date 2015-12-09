package com.jfixby.r3.api.physics;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.r3.api.unit.components.physics.PhysicsCore;
import com.jfixby.r3.api.unit.components.physics.PhysicsCoreSpecs;
import com.jfixby.r3.api.unit.components.physics.body.BoxBody;
import com.jfixby.r3.api.unit.components.physics.body.CircleBody;
import com.jfixby.r3.api.unit.components.physics.body.PolyBody;

public class Physics2D {

	static private ComponentInstaller<Physics2DComponent> componentInstaller = new ComponentInstaller<Physics2DComponent>(
			"Physics2D");

	public static final void installComponent(
			Physics2DComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final Physics2DComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final Physics2DComponent component() {
		return componentInstaller.getComponent();
	}

	public static PhysicsCoreSpecs newPhysicsCoreSpecs() {
		return invoke().newPhysicsCoreSpecs();
	}

	public static PhysicsCore newPhysicsCore(PhysicsCoreSpecs core_specs) {
		return invoke().newPhysicsCore(core_specs);
	}

	public static BoxBody newBoxBody() {
		return invoke().newBoxBody();
	}

	public static CircleBody newCircleBody() {
		return invoke().newCircleBody();
	}

	public static PolyBody newPolyBody(PolyBodySpecs geometry) {
		return invoke().newPolyBody(geometry);
	}

	public static PolyBodySpecs newPolyBodySpecs() {
		return invoke().newPolyBodySpecs();
	}

	public static PhysicsFactory getPhysicsDepartment() {
		return null;
	}

}
