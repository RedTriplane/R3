
package com.jfixby.r3.ui.api.activity.spawn;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;

public class ActivityMachine {

	static private ComponentInstaller<ActivityMachineComponent> componentInstaller = new ComponentInstaller<ActivityMachineComponent>(
		"ActivityMachine");

	public static final void installComponent (final ActivityMachineComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final ActivityMachineComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final ActivityMachineComponent component () {
		return componentInstaller.getComponent();
	}

	public static void nextActivity (final Intent intent) {
		invoke().nextActivity(intent);
	}

	public static Intent newIntent (final ID unit_class_id) {
		return invoke().newIntent(unit_class_id);
	}

}
