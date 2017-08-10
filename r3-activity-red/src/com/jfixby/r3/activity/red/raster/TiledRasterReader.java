
package com.jfixby.r3.activity.red.raster;

import java.io.IOException;

import com.jfixby.r3.io.texture.slicer.SlicesCompositionInfo;
import com.jfixby.r3.io.texture.slicer.SlicesCompositionsContainer;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.io.IO;
import com.jfixby.scarabei.api.java.ByteArray;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class TiledRasterReader implements PackageLoader {

	final List<PackageFormat> acceptablePackageFormats = Collections.newList();

	public TiledRasterReader () {
		this.acceptablePackageFormats.add(new PackageFormat(SlicesCompositionsContainer.PACKAGE_FORMAT));
	}

	private final boolean doNotRead = !true;

	@Override
	public Promise<AssetsContainer> doReadPackage (final PackageReaderInput input) throws IOException {
		final Future<Void, AssetsContainer> future = new Future<Void, AssetsContainer>() {
			@Override
			public AssetsContainer deliver (final Void v) throws Throwable {
				if (TiledRasterReader.this.doNotRead) {
					return input.assetsContainer;
				}
				File package_root_file = null;
				try {
					// final PackageHandler handler = input.getPackageHandler();
					final Collection<ID> deplist = input.packageInfo.dependencies;
					// reader_listener.onDependenciesRequired(handler, deplist);
					package_root_file = input.packageRootFile;
					final AssetsContainer container = input.assetsContainer;
					final ByteArray data = package_root_file.readBytes();
					final SlicesCompositionsContainer comp = IO.deserialize(SlicesCompositionsContainer.class, data);

					if (comp.content.size() == 0) {
						throw new IOException("No tiles found: " + input.packageInfo);
					}
					final List<ID> missing = Collections.newList();
					if (input.packageInfo.packedAssets == null) {
						Err.reportError("input.packageInfo.packedAssets==null");
						return input.assetsContainer;
					}
					missing.addAll(input.packageInfo.packedAssets);
					{
						final TileSetGroup group = new TileSetGroup();
						for (final SlicesCompositionInfo sctructure : comp.content) {

							if (sctructure.tiles.size() == 0) {
								throw new IOException("No tiles found: " + sctructure.getAssetID());
							}

							if (sctructure.composition_asset_id_string == null) {
								throw new IOException("Missing composition_asset_id_string");
							}

							final TileSet box = new TileSet(group, sctructure);

							final ID asset_id = box.getAssetID();
							missing.remove(asset_id);

							container.addAsset(asset_id, box);
						}
					}
					if (missing.size() > 0) {
// missing.print("missing");
						L.d("missing", missing);
						Err.reportError("missing assets");
					}

				} catch (final IOException e) {
					final String bad_data = package_root_file.readToString();
					L.e("bad data", bad_data);
					Err.reportError(package_root_file + " " + e);
				}
				return input.assetsContainer;
			}
		};

		return TaskManager.executeAsynchronously("TiledRasterReader.doReadPackage()", future);

	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

}
