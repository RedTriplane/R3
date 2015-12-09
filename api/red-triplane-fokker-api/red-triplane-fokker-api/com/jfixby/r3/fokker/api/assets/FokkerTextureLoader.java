package com.jfixby.r3.fokker.api.assets;

import com.jfixby.cmns.api.ComponentInstaller;

public class FokkerTextureLoader {

	static private ComponentInstaller<FokkerTextureLoaderComponent> componentInstaller = new ComponentInstaller<FokkerTextureLoaderComponent>(
			"FokkerTextureLoader");

	public static final void installComponent(
			FokkerTextureLoaderComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerTextureLoaderComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerTextureLoaderComponent component() {
		return componentInstaller.getComponent();
	}

	public static void register() {
		invoke().register();
	}
}
