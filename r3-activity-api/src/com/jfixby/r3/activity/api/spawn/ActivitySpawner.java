
package com.jfixby.r3.activity.api.spawn;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.names.ID;

public class ActivitySpawner {
	static private ComponentInstaller<ActivitySpawnerComponent> componentInstaller = new ComponentInstaller<>(
		"ActivitySpawnerComponent");

	public static final void installComponent (final ActivitySpawnerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final ActivitySpawnerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final ActivitySpawnerComponent component () {
		return componentInstaller.getComponent();
	}

	public static Activity spawnActivity (final ID unitClassId) throws ActivitySpawningException {
		return invoke().spawnActivity(unitClassId);
	}
}
