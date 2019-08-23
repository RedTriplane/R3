
package com.jfixby.r3.fokker.sound.api.samples;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.names.ID;

public class FokkerAudioSamples {

	static private ComponentInstaller<FokkerAudioSamplesComponent> componentInstaller = new ComponentInstaller<FokkerAudioSamplesComponent>(
		"FokkerSounds");

	public static final void installComponent (final FokkerAudioSamplesComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerAudioSamplesComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerAudioSamplesComponent component () {
		return componentInstaller.getComponent();
	}

	public static final FokkerAudioSample obtain (final ID assetID) {
		return componentInstaller.getComponent().obtain(assetID);

	}

}
