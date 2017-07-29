
package com.jfixby.r3.fokker.texture.api;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;

public class FokkerTextures {

	static private ComponentInstaller<FokkerTexturesComponent> componentInstaller = new ComponentInstaller<FokkerTexturesComponent>(
		"FokkerTextures");

	public static final void installComponent (final FokkerTexturesComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerTexturesComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerTexturesComponent component () {
		return componentInstaller.getComponent();
	}

	public static final FokkerTexture obtain (final ID assetID) {
		return componentInstaller.getComponent().obtain(assetID);

	}

}
