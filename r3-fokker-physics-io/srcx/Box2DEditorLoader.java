
package com.jfixby.r3.fokker.core.assets;

import java.io.IOException;

import com.jfixby.r3.fokker.api.StandardPackageFormats;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;

public class Box2DEditorLoader implements PackageReader {

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return null;
	}

	@Override
	public void doReadPackage (final PackageReaderInput input) throws IOException {
		Err.throwNotImplementedYet();
	}

	final List<PackageFormat> acceptablePackageFormats;

// final GdxShapesRegister register = new GdxShapesRegister();
//
	public Box2DEditorLoader () {
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(StandardPackageFormats.Box2DEditor.Project);
		this.acceptablePackageFormats.add(format);
	}
//
// @Override
// public Collection<PackageFormat> listAcceptablePackageFormats () {
// return this.acceptablePackageFormats;
// }
//
// @Override
// public GdxShapeEntry getAsset (final AssetID asset_id) {
// return this.register.getShapeData(asset_id);
// }
//
// @Override
// public void printAll () {
// this.register.printAll("loaded raster");
// }
//
// @Override
// public void doReadPackage (final PackageInput input) throws IOException {
// try {
// L.d("Reading " + StandardPackageFormats.Box2DEditor.Project, input.getPackageHandler().getPackageName());
// final Box2DEditorProject project = Box2DEditorProject.loadProject(input.getRootFile());
//
// for (int i = 0; i < project.size(); i++) {
// final Box2DEditorShape shape = project.getShape(i);
// final AssetID rester_id = (shape.getID());
// final Box2DEditorShapeData data = new Box2DEditorShapeData(rester_id, shape);
//
// final GdxShapeEntry entry = new GdxShapeEntry(data);
//
// this.register.registerRaster(rester_id, entry);
// AssetsManager.registerAssetContainer(rester_id, this);
// L.d(" ", rester_id);
//
// }
//
// } catch (final Throwable e) {
// throw new IOException("" + e);
// }
// }
//
// @Override
// public long readPackageTimeStamp (final AssetID assetID) {
// Err.reportNotImplementedYet();
// return 0;
// }
//
// @Override
// public long getPackageTimeStamp (final AssetID assetID) {
// Err.reportNotImplementedYet();
// return 0;
// }
//
// @Override
// public void purgeAssets (final Collection<AssetID> relatedAssets) {
// Err.reportNotImplementedYet();
//
// }

}
