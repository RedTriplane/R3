
package com.jfixby.text.loaders.strings;

import java.io.IOException;

import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileHash;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.strings.api.io.string.StringPackageEntry;
import com.jfixby.strings.api.io.string.StringsPackage;

public class StringFileEntry {

	private final String hash;
	private final File package_root_file;

	public StringFileEntry (final File package_root_file) throws IOException {

		this.package_root_file = package_root_file;
		if (!package_root_file.extensionIs(StringsPackage.PACKAGE_FILE_EXTENSION)) {
			Err.reportError("Wrong file extention: <." + package_root_file.getExtension() + ">, expected: <."
				+ StringsPackage.PACKAGE_FILE_EXTENSION + ">");
		}
		this.hash = hash(this.package_root_file);
	}

	public final String getHash () {
		return this.hash;
	}

	public final String getCurentHash () throws IOException {
		return hash(this.package_root_file);
	}

	static final private String hash (final File package_root_file) throws IOException {
		final FileHash hash = package_root_file.calculateHash();
		final String md5 = hash.getMD5HashHexString();
		final long mod = package_root_file.lastModified();
		return mod + "@" + md5;
	}

// public void reload () throws IOException {
// L.d("reloading package", this.package_root_file);
// final String content = this.package_root_file.readToString();
// final StringsPackage container = Json.deserializeFromString(StringsPackage.class, content);
//
// final List<AssetID> assets_list = Collections.newList();
// for (final StringPackageEntry entry_srlz : container.entries) {
// final AssetID asset_id = Names.newAssetID(entry_srlz.id);
// StringDataEntry entry = this.structures_register().get(asset_id);
//
// if (entry == null) {
// entry = new StringDataEntry(asset_id);
// this.structures_register().put(asset_id, entry);
// assets_list.add(asset_id);
// }
// entry.setData(entry_srlz);
//
// }
// this.hash = hash(this.package_root_file);
//
// }
//
	public void load (final AssetsContainer container) throws IOException {

		final String content = this.package_root_file.readToString();
		final StringsPackage data = Json.deserializeFromString(StringsPackage.class, content);
		for (final StringPackageEntry entry_srlz : data.entries) {
			final ID asset_id = Names.newID(entry_srlz.id);

			final StringDataEntry entry = new StringDataEntry(asset_id);
			entry.setData(entry_srlz);
			container.addAsset(asset_id, entry);
		}
	}

}
