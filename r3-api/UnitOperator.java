
package com.jfixby.r3.api.unit;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;

public class UnitOperator {

	static private ComponentInstaller<UnitOperatorComponent> componentInstaller = new ComponentInstaller<UnitOperatorComponent>(
		"UnitOperator");

	public static final void installComponent (final UnitOperatorComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final UnitOperatorComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final UnitOperatorComponent component () {
		return componentInstaller.getComponent();
	}

	public static UnitHandler deployUnit (final ID unitID) {
		return invoke().deployUnit(unitID);
	}

	public static UnitHandler push (final UnitHandler unitHandler) {
		return invoke().push(unitHandler);
	}

}
