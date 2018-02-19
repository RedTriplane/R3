
package com.jfixby.r3.fokker.font.api;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.font.StringSpec;
import com.jfixby.scarabei.api.names.ID;

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

	public static final FokkerFont obtain (final ID assetID) {
		return componentInstaller.getComponent().obtainFont(assetID);

	}

	public static final FokkerStringHandler obtainString (final StringSpec stringSpecs) {
		return componentInstaller.getComponent().obtainString(stringSpecs);

	}

	public static StringSpec newStringSpec () {
		return componentInstaller.invokeComponent().newStringSpec();
	}

	public static void disposeString (final FokkerStringHandler string) {
		componentInstaller.invokeComponent().disposeString(string);
	}

}
