
package com.jfixby.r3.fokker.sound.api;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.names.ID;

public class FokkerSounds {

	static private ComponentInstaller<FokkerSoundsComponent> componentInstaller = new ComponentInstaller<FokkerSoundsComponent>(
		"FokkerSounds");

	public static final void installComponent (final FokkerSoundsComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerSoundsComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerSoundsComponent component () {
		return componentInstaller.getComponent();
	}

	public static final FokkerAudio obtain (final ID assetID) {
		return componentInstaller.getComponent().obtain(assetID);

	}

}
