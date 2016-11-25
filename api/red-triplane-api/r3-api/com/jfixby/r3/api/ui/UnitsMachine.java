package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.cmns.api.assets.ID;

public class UnitsMachine {

	static private ComponentInstaller<UnitsMachineComponent> componentInstaller = new ComponentInstaller<UnitsMachineComponent>(
			"UnitsMachine");

	public static final void installComponent(
			UnitsMachineComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final UnitsMachineComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final UnitsMachineComponent component() {
		return componentInstaller.getComponent();
	}

	public static void nextUnit(Intent intent) {
		invoke().nextUnit(intent);
	}

	public static Intent newIntent(ID unit_class_id) {
		return invoke().newIntent(unit_class_id);
	}

}
