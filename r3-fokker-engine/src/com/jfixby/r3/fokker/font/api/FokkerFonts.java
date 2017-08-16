
package com.jfixby.r3.fokker.font.api;

import com.jfixby.r3.engine.api.render.FontParameters;
import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.names.ID;

public class FokkerFonts {

	static private ComponentInstaller<FokkerFontsComponent> componentInstaller = new ComponentInstaller<FokkerFontsComponent>(
		"FokkerFonts");

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

	public static final FokkerString obtain (final ID fontID, final FontParameters fontParams, final String stringValue) {
		return componentInstaller.getComponent().obtainString(fontID, fontParams, stringValue);

	}

}
