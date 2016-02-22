package com.jfixby.r3.shader.fokker;

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
import com.jfixby.r3.api.resources.StandardPackageFormats;
import com.jfixby.r3.api.shader.ShaderInfo;
import com.jfixby.r3.api.shader.ShadersContainer;
import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetContainer;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.pkg.PackageFormat;
import com.jfixby.rana.api.pkg.PackageHandler;
import com.jfixby.rana.api.pkg.PackageInput;
import com.jfixby.rana.api.pkg.PackageReader;
import com.jfixby.rana.api.pkg.PackageReaderListener;
import com.jfixby.rana.api.pkg.ResourcesManager;

public class FokkerShaderPackageReader implements PackageReader, AssetContainer {

	final List<PackageFormat> acceptablePackageFormats;
	final Map<AssetID, ShaderEntry> register = Collections.newMap();

	public FokkerShaderPackageReader() {
		acceptablePackageFormats = Collections
				.newList(ResourcesManager.newPackageFormat(StandardPackageFormats.RedTriplane.Shader));
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats() {
		return acceptablePackageFormats;
	}

	@Override
	public void doReadPackage(PackageInput input) throws IOException {
		this.doReadPackage(input.getPackageReaderListener(), input.getRootFile(), input.getPackageHandler());

	}

	public void doReadPackage(PackageReaderListener reader_listener, File package_root_file, PackageHandler handler)
			throws IOException {

		resolveDependencies(handler, reader_listener);

		String content;
		try {
			content = package_root_file.readToString();

			// L.d("reading package_root_file", package_root_file);
			// L.d(" content", content);
			ShadersContainer container = Json.deserializeFromString(ShadersContainer.class, content);

			for (ShaderInfo shader : container.shaders) {
				AssetID asset_id = Names.newAssetID(shader.shader_id);

				String shader_folder_name = shader.shader_folder_name;

				File shader_folder = package_root_file.parent().child(shader_folder_name);
				ShaderEntry entry = new ShaderEntry(asset_id, shader, shader_folder, container);

				register.put(asset_id, entry);
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

	@Override
	public Asset getAsset(AssetID asset_id) {
		return this.findStructure(asset_id);
	}

	@Override
	public void printAll() {
		register.print("loaded shaders");
	}

	@Override
	public void checkAll() {
	}

	public Asset findStructure(AssetID asset_id) {
		Debug.checkNull("asset_id", asset_id);
		ShaderEntry entry = this.register.get(asset_id);
		if (entry == null) {
			L.d("asset_id", asset_id);
			register.print("structures_register");
			throw new Error();
		}
		// FontData structure = entry.getFontData();
		return entry;
	}

}
