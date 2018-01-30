
package com.jfixby.text.loaders.text;

import java.io.IOException;

import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.string.io.text.TextPackage;
import com.jfixby.r3.string.io.text.TextPackageEntry;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileHash;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class TextFileEntry implements AssetsConsumer {

	private final String hash;
	private final File package_root_file;

	public TextFileEntry (final File package_root_file) throws IOException {

		this.package_root_file = package_root_file;
		if (!package_root_file.extensionIs(TextPackage.PACKAGE_FILE_EXTENSION)) {
			Err.reportError("Wrong file extention: <." + package_root_file.getExtension() + ">, expected: <."
				+ TextPackage.PACKAGE_FILE_EXTENSION + ">");
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

	public void load (final AssetsContainer container) throws IOException {

		final String content = this.package_root_file.readToString();
		final TextPackage data = Json.deserializeFromString(TextPackage.class, content);
		for (final TextPackageEntry entry_srlz : data.entries) {
			final ID asset_id = Names.newID(entry_srlz.id);
			final TextDataEntry entry = new TextDataEntry(asset_id);
			entry.setData(entry_srlz);
// this.loadEntry(entry_srlz, entry);
			container.addAsset(asset_id, entry);
		}
	}

// private void loadEntry (final TextPackageEntry entry_srlz, final TextDataEntry entry) {
// for (final TextLocalization loc : entry_srlz.localizations) {
// final String name = loc.name;
// final AssetID locale_id = Names.newAssetID(loc.container_id);
//
// AssetHandler stringData = AssetsManager.obtainAsset(locale_id, this);
//
// if (stringData == null) {
// AssetsManager.autoResolveAsset(locale_id, null);
// stringData = AssetsManager.obtainAsset(locale_id, this);
// }
//
// final StringData data = stringData.asset();
//
// final RedTextTranslation translation = new RedTextTranslation(name, data);
//
//
// AssetsManager.releaseAsset(stringData, this);
// }
// }

}
