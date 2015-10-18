package com.jfixby.r3.fokker.api.assets;

import com.jfixby.cmns.api.components.ComponentInstaller;

public class FokkerAtlasLoader {

	static private ComponentInstaller<FokkerAtlasLoaderComponent> componentInstaller = new ComponentInstaller<FokkerAtlasLoaderComponent>(
			"FokkerAtlasLoader");

	public static final void installComponent(
			FokkerAtlasLoaderComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerAtlasLoaderComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerAtlasLoaderComponent component() {
		return componentInstaller.getComponent();
	}

	public static void register() {
		invoke().register();
	}
}
