package com.jfixby.r3.api;

import com.jfixby.cmns.api.ComponentInstaller;

public class RedTriplane {
	static private ComponentInstaller<RedTriplaneComponent> componentInstaller = new ComponentInstaller<RedTriplaneComponent>(
			"RedTriplane");

	public static final void installComponent(
			RedTriplaneComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final RedTriplaneComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final RedTriplaneComponent component() {
		return componentInstaller.getComponent();
	}

	


}
