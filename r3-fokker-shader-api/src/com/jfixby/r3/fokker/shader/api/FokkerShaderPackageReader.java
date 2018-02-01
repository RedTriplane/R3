
package com.jfixby.r3.fokker.shader.api;

import com.jfixby.r3.rana.api.loader.PackageLoader;

public interface FokkerShaderPackageReader {
// public static final String PACKAGE_FORMAT_SHADER = "libGDX.Shader";
	public static final String PACKAGE_FORMAT = "RedTriplane.Shader";

	PackageLoader reader ();
}
