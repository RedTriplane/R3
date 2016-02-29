package com.jfixby.r3.ext.font.gdx.gwt;

import java.io.IOException;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.r3.api.ui.unit.txt.FontData;
import com.jfixby.r3.ext.api.font.FontPackage;
import com.jfixby.r3.ext.api.font.RedFontData;
import com.jfixby.rana.api.asset.AssetContainer;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.pkg.PackageFormat;
import com.jfixby.rana.api.pkg.PackageHandler;
import com.jfixby.rana.api.pkg.PackageInput;
import com.jfixby.rana.api.pkg.PackageReader;
import com.jfixby.rana.api.pkg.PackageReaderListener;
import com.jfixby.rana.api.pkg.ResourcesManager;

public class FontPackageLoader implements PackageReader, AssetContainer {

	Map<AssetID, FontDataRegistrationEntry> structures_register = Collections.newMap();

	@Override
	public void printAll() {
		structures_register.print("loaded structures");
	}

	final List<PackageFormat> acceptablePackageFormats;

	public FontPackageLoader() {
		acceptablePackageFormats = Collections.newList();
		PackageFormat format = ResourcesManager.newPackageFormat(FontPackage.FONT_PACKAGE_FORMAT);
		acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats() {
		return acceptablePackageFormats;
	}

	public void doReadPackage(PackageReaderListener reader_listener, File package_root_file, PackageHandler handler) throws IOException {

		resolveDependencies(handler, reader_listener);

		String content;
		try {
			content = package_root_file.readToString();

			// L.d("reading package_root_file", package_root_file);
			// L.d("                         content", content);
			FontPackage container = Json.deserializeFromString(FontPackage.class, content);
			for (RedFontData structure : container.fonts) {
				AssetID asset_id = Names.newAssetID(structure.id);

				String original_font_file_name = structure.original_font_file_name;

				File original_font_file = package_root_file.parent().child(original_font_file_name);
//				ToGdxFileAdaptor original_font_file_adaptor = new ToGdxFileAdaptor(original_font_file);
				FontDataRegistrationEntry entry = new FontDataRegistrationEntry(asset_id, structure, original_font_file);

				structures_register.put(asset_id, entry);
				AssetsManager.registerAsset(asset_id, this);
			}

		} catch (IOException e) {
			// e.printStackTrace();
			throw new IOException("" + e);
		}

	}

	private void resolveDependencies(PackageHandler handler, PackageReaderListener reader_listener) {
		Collection<AssetID> deplist = handler.listDependencies();

		reader_listener.onDependenciesRequired(deplist);

		// AssetsManager.autoresolveDependencies(dep);

	}

	public void print() {
		this.structures_register.print("loaded-structures");
	}

	public FontData findStructure(AssetID asset_id) {
		Debug.checkNull("asset_id", asset_id);
		FontDataRegistrationEntry entry = this.structures_register.get(asset_id);
		if (entry == null) {
			L.d("asset_id", asset_id);
			structures_register.print("structures_register");
			throw new Error();
		}
		// FontData structure = entry.getFontData();
		return entry;
	}

	@Override
	public void doReadPackage(PackageInput input) throws IOException {
		this.doReadPackage(input.getPackageReaderListener(), input.getRootFile(), input.getPackageHandler());
	}

	@Override
	public FontData getAsset(AssetID asset_id) {
		return this.findStructure(asset_id);
	}

	@Override
	public void checkAll() {
	}

}
