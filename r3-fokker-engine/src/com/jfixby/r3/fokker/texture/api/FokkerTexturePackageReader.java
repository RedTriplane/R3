
package com.jfixby.r3.fokker.texture.api;

import com.jfixby.r3.rana.api.loader.PackageLoader;

public interface FokkerTexturePackageReader {

	public static final String PACKAGE_FORMAT_ATLAS = "libGDX.Atlas";
	public static final String PACKAGE_FORMAT_TEXTURE = "libGDX.Texture";

	PackageLoader reader ();

}
