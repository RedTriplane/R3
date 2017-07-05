
package com.jfixby.r3.api.ui.unit.spawn;

import com.jfixby.r3.api.ui.unit.Intent;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.scarabei.api.ComponentInstaller;

public class UnitsSpawner {
	static private ComponentInstaller<UnitSpawnerComponent> componentInstaller = new ComponentInstaller<UnitSpawnerComponent>(
		"UnitSpawnerComponent");

	public static final void installComponent (final UnitSpawnerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final UnitSpawnerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final UnitSpawnerComponent component () {
		return componentInstaller.getComponent();
	}

	public static Unit spawnUnit (final Intent unitClassId) throws UnitsSpawningException {
		return invoke().spawnUnit(unitClassId);
	}
}
