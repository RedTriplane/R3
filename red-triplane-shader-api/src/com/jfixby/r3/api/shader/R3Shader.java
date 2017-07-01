
package com.jfixby.r3.api.shader;

import com.jfixby.scarabei.api.ComponentInstaller;

public class R3Shader {

	static private ComponentInstaller<R3ShaderComponent> componentInstaller = new ComponentInstaller<R3ShaderComponent>(
		"R3Shader");

	public static final void installComponent (final R3ShaderComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final R3ShaderComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final R3ShaderComponent component () {
		return componentInstaller.getComponent();
	}

	public static PhotoshopShaders PHOTOSHOP () {
		return invoke().PHOTOSHOP();
	}

}
