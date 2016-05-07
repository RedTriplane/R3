
package com.jfixby.r3.ext.api.font;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.cmns.api.file.File;
import com.jfixby.rana.api.pkg.PackageReader;

public class R3Font {

	public static final String RenderRasterStrings = "R3Font.RenderRasterStrings";

	static private ComponentInstaller<R3FontComponent> componentInstaller = new ComponentInstaller<R3FontComponent>("R3Font");

	public static final void installComponent (final R3FontComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final R3FontComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final R3FontComponent component () {
		return componentInstaller.getComponent();
	}

	public static PackageReader getPackageReader () {
		return invoke().getPackageReader();
	}

	public static FontGenerator newFontGenerator (final File gdx_font_file) {
		return invoke().newFontGenerator(gdx_font_file);
	}

}
