
package com.jfixby.r3.api.activity.spawn;

import com.jfixby.r3.api.activity.Activity;
import com.jfixby.scarabei.api.ComponentInstaller;

public class ActivitySpawner {
	static private ComponentInstaller<ActivitySpawnerComponent> componentInstaller = new ComponentInstaller<ActivitySpawnerComponent>(
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

	public static Activity spawnActivity (final Intent unitClassId) throws ActivitySpawningException {
		return invoke().spawnActivity(unitClassId);
	}
}
