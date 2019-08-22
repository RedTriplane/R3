
package com.jfixby.r3.engine.api.sound;

import com.jfixby.scarabei.api.ComponentInstaller;

public class Sound {
	static private ComponentInstaller<SoundComponent> componentInstaller = new ComponentInstaller<>("Sound");

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final void installComponent (final SoundComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final SoundComponent replaceComponent (final SoundComponent component_to_install) {
		return componentInstaller.replaceComponent(component_to_install);
	}

	public static SoundComponent deInstallCurrentComponent () {
		return componentInstaller.deInstallCurrentComponent();
	}

	public static final SoundComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final SoundComponent component () {
		return componentInstaller.getComponent();
	}

	public static SoundGrid newSoundGrid (final SoundGridSpecs config) {
		return invoke().newSoundGrid(config);
	}

}
