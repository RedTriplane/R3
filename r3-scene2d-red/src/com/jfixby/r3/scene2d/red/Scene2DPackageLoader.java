
package com.jfixby.r3.scene2d.red;

import java.io.IOException;

import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.r3.scene2d.io.Scene2DPackage;
import com.jfixby.r3.scene2d.io.SceneStructure;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.io.IO;
import com.jfixby.scarabei.api.java.ByteArray;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class Scene2DPackageLoader implements PackageLoader {

	final List<PackageFormat> acceptablePackageFormats;

	public Scene2DPackageLoader () {
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(Scene2DPackage.SCENE2D_PACKAGE_FORMAT);
		this.acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public AssetsContainer doReadPackage (final PackageReaderInput input) throws IOException {

		final File package_root_file = input.packageRootFile;
		final AssetsContainer storage = input.assetsContainer;
		try {
			final ByteArray content = package_root_file.readBytes();

			final Scene2DPackage container = IO.deserialize(Scene2DPackage.class, content);

			final SceneStructuresGroup group = new SceneStructuresGroup();
			for (final SceneStructure structure : container.structures) {
				final LayerStructureRegistrationEntry entry = new LayerStructureRegistrationEntry();
				final ID asset_id = Names.newID(structure.structure_name);
				entry.setAssetId(asset_id);
				entry.setStructure(structure);
				final SceneStructureAsset asset = new SceneStructureAsset(group, structure);
				storage.addAsset(asset_id, asset);
			}
			return storage;
		} catch (final Throwable e) {
			e.printStackTrace();
			final String bad_data = package_root_file.readToString();
			L.e("bad data", bad_data);
			throw new IOException(package_root_file + " " + e, e);
		}

	}

}
