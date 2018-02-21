
package com.jfixby.r3.fokker.font.api;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.font.RasterStringSettings;

public class FokkerFonts {

	static private ComponentInstaller<FokkerFontsComponent> componentInstaller = new ComponentInstaller<>("FokkerFonts");

	public static final void installComponent (final FokkerFontsComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerFontsComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerFontsComponent component () {
		return componentInstaller.getComponent();
	}

	public static final FokkerStringHandler spawnString (final RasterStringSettings rasterStringSettings) {
		return componentInstaller.getComponent().spawnString(rasterStringSettings);

	}

	public static RasterStringSettings newStringSpec () {
		return componentInstaller.invokeComponent().newStringSpec();
	}

	public static void disposeString (final FokkerStringHandler string) {
		componentInstaller.invokeComponent().disposeString(string);
	}

}
