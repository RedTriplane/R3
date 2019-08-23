
package com.jfixby.r3.fokker.shader.api;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.names.ID;

public class FokkerShaders {

	static private ComponentInstaller<FokkerShadersComponent> componentInstaller = new ComponentInstaller<>("FokkerShaders");

	public static final void installComponent (final FokkerShadersComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerShadersComponent component () {
		return componentInstaller.getComponent();
	}

	public static final FokkerShadersComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerShader obtain (final ID assetID) {
		return componentInstaller.getComponent().obtain(assetID);
	}

}
