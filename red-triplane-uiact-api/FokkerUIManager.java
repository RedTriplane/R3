
package com.jfixby.r3.uiact;

import com.jfixby.scarabei.api.ComponentInstaller;

public class FokkerUIManager {

	static private ComponentInstaller<FokkerUIManagerComponent> componentInstaller = new ComponentInstaller<FokkerUIManagerComponent>(
		"FokkerUIManager");

	public static final void installComponent (final FokkerUIManagerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerUIManagerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerUIManagerComponent component () {
		return componentInstaller.getComponent();
	}

	public static void onUIStart () {
		invoke().onUIStart();
	}

}
