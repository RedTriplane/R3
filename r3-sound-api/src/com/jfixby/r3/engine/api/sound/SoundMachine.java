
package com.jfixby.r3.engine.api.sound;

import com.jfixby.scarabei.api.ComponentInstaller;

public class SoundMachine {
	static private ComponentInstaller<SoundMachineComponent> componentInstaller = new ComponentInstaller<>("Sound");

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final void installComponent (final SoundMachineComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final SoundMachineComponent replaceComponent (final SoundMachineComponent component_to_install) {
		return componentInstaller.replaceComponent(component_to_install);
	}

	public static SoundMachineComponent deInstallCurrentComponent () {
		return componentInstaller.deInstallCurrentComponent();
	}

	public static final SoundMachineComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final SoundMachineComponent component () {
		return componentInstaller.getComponent();
	}

	public static void deploy () {
		invoke().deploy();
	}

	public static void destroy () {
		invoke().destroy();
	}

}
