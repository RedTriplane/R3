package com.jfixby.r3.api.ui;

import com.jfixby.scarabei.api.ComponentInstaller;

public class UIStarter {

	static private ComponentInstaller<UIStarterComponent> componentInstaller = new ComponentInstaller<UIStarterComponent>(
			"UI");

	public static final void installComponent(UIStarterComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final UIStarterComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final UIStarterComponent component() {
		return componentInstaller.getComponent();
	}

	public static void onUIStart() {
		invoke().onUIStart();
	}

}
