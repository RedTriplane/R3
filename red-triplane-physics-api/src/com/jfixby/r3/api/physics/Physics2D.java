
package com.jfixby.r3.api.physics;

import com.jfixby.scarabei.api.ComponentInstaller;

public class Physics2D {

	static private ComponentInstaller<Physics2DComponent> componentInstaller = new ComponentInstaller<Physics2DComponent>(
		"Physics2D");

	public static final void installComponent (final Physics2DComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final Physics2DComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final Physics2DComponent component () {
		return componentInstaller.getComponent();
	}

	public static PhysicsCoreSpecs newPhysicsCoreSpecs () {
		return invoke().newPhysicsCoreSpecs();
	}

	public static PhysicsCore newPhysicsCore (final PhysicsCoreSpecs core_specs) {
		return invoke().newPhysicsCore(core_specs);
	}

	public static BoxBody newBoxBody () {
		return invoke().newBoxBody();
	}

	public static CircleBody newCircleBody () {
		return invoke().newCircleBody();
	}

	public static PolyBody newPolyBody (final PolyBodySpecs geometry) {
		return invoke().newPolyBody(geometry);
	}

	public static PolyBodySpecs newPolyBodySpecs () {
		return invoke().newPolyBodySpecs();
	}

}
