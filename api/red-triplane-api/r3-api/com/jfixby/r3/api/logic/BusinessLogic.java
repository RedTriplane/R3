
package com.jfixby.r3.api.logic;

import com.jfixby.scarabei.api.ComponentInstaller;

public class BusinessLogic {

	static private ComponentInstaller<BusinessLogicComponent> componentInstaller = new ComponentInstaller<BusinessLogicComponent>(
		"BusinessLogic");

	public static final void installComponent (final BusinessLogicComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final BusinessLogicComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final BusinessLogicComponent component () {
		return componentInstaller.getComponent();
	}

	public static void start () {
		componentInstaller.invokeComponent().start();
	}

}
