
package com.jfixby.text.loaders.strings;

import java.io.IOException;

import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.file.File;

public class StringPackageLoader implements PackageReader {

	public static final String String = "RedTriplane.String";

	final List<StringFileEntry> files = Collections.newList();

	final List<PackageFormat> acceptablePackageFormats;

	public StringPackageLoader () {
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(String);
		this.acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public void doReadPackage (final PackageReaderInput input) throws IOException {
// final PackageHandler handler = input.getPackageHandler();
// listener.onDependenciesRequired(handler, handler.listDependencies());
		final File root = input.packageRootFile;
		final AssetsContainer container = input.assetsContainer;

		final StringFileEntry entry = new StringFileEntry(root);
		entry.load(container);
	}

}
