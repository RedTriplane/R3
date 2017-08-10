
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.file.File;

public class RedTTFFontGroup {

	public void read (final PackageReaderInput input, final FokkerFontRegister redFokkerFonts) {

		final File package_root_file = input.packageRootFile;
// final PackageReaderListener reader_listener = input.getPackageReaderListener();
// final PackageHandler handler = input.getPackageHandler();
// final AssetsContainer container = input.assetsContainer;

		final File file = input.packageRootFile;

		final ID asset_id = input.packageInfo.packedAssets.getLast();

		final RedTTFFontInfo entry = new RedTTFFontInfo(asset_id, file);

		input.assetsContainer.addAsset(asset_id, entry);
		redFokkerFonts.register(asset_id, entry);
	}

}
