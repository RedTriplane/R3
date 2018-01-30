
package com.jfixby.text.loaders.strings;

import java.io.IOException;

import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.r3.string.io.StringsPackage;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.file.File;

public class StringPackageLoader implements PackageLoader {

	final List<StringFileEntry> files = Collections.newList();

	final List<PackageFormat> acceptablePackageFormats;

	public StringPackageLoader () {
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(StringsPackage.PACKAGE_FORMAT);
		this.acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public AssetsContainer doReadPackage (final PackageReaderInput input) throws IOException {
// final PackageHandler handler = input.getPackageHandler();
// listener.onDependenciesRequired(handler, handler.listDependencies());
		final File root = input.packageRootFile;
		final AssetsContainer container = input.assetsContainer;

		final StringFileEntry entry = new StringFileEntry(root);
		entry.load(container);

		return container;
	}

}
