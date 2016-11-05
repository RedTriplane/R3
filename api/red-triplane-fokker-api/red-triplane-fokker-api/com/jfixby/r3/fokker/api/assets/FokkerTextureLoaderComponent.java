
package com.jfixby.r3.fokker.api.assets;

import com.jfixby.r3.api.resources.StandardPackageFormats;
import com.jfixby.rana.api.pkg.PackageFormat;
import com.jfixby.rana.api.pkg.ResourcesManager;

public interface FokkerTextureLoaderComponent {

	public static final PackageFormat TEXTURE = ResourcesManager.newPackageFormat(StandardPackageFormats.libGDX.Texture);
	public static final PackageFormat ATLAS = ResourcesManager.newPackageFormat(StandardPackageFormats.libGDX.Atlas);

	void register ();

}
