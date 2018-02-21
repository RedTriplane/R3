
package com.jfixby.r3.fokker.font.red;

import java.io.IOException;

import com.jfixby.r3.fokker.font.api.FokkerFontPackageReader;
import com.jfixby.r3.fokker.font.red.io.RedTTFFont;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.names.ID;

public class TrueTypeFontFontPackageLoader implements PackageLoader, FokkerFontPackageReader {

	final List<PackageFormat> acceptablePackageFormats;

	public TrueTypeFontFontPackageLoader () {
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(FokkerFontPackageReader.PACKAGE_FORMAT);
		this.acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public AssetsContainer doReadPackage (final PackageReaderInput input) throws IOException {
		final File package_root_file = input.packageRootFile;
		// final PackageReaderListener reader_listener = input.getPackageReaderListener();
		// final PackageHandler handler = input.getPackageHandler();
		// final AssetsContainer container = input.assetsContainer;

		final File file = input.packageRootFile;

		final ID asset_id = input.packageInfo.packedAssets.getLast();

		final RedTTFFont entry = new RedTTFFont(asset_id, file);

		input.assetsContainer.addAsset(asset_id, entry);
		// redFokkerFonts.register(asset_id, entry);
		return input.assetsContainer;
	}

	@Override
	public PackageLoader reader () {
		return this;
	}

}
