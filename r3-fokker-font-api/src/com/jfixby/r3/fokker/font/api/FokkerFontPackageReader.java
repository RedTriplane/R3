
package com.jfixby.r3.fokker.font.api;

import com.jfixby.r3.rana.api.loader.PackageLoader;

public interface FokkerFontPackageReader {
// public static final String PACKAGE_FORMAT_SHADER = "libGDX.Shader";
	public static final String PACKAGE_FORMAT = "TrueTypeFont";

	PackageLoader reader ();
}
