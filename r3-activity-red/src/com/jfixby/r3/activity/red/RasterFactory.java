
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.raster.GraphicalConsole;
import com.jfixby.r3.activity.api.raster.GraphicalConsoleSpecs;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.raster.RasterComponentsFactory;
import com.jfixby.r3.activity.api.raster.RasterPool;
import com.jfixby.r3.activity.red.raster.RedTile;
import com.jfixby.r3.activity.red.raster.RedTilesComposition;
import com.jfixby.r3.activity.red.raster.TileSet;
import com.jfixby.r3.engine.api.render.R3_GRAPHICS_RENDER_PARAMS;
import com.jfixby.r3.engine.api.render.RasterData;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.r3.rana.api.pkg.PackagesManager;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RasterFactory implements RasterComponentsFactory {

	private final RedComponentsFactory master;

	public RasterFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public Raster newRaster (final ID newAssetID) {
		final boolean allowMissingAsset = SystemSettings.getFlag(R3_GRAPHICS_RENDER_PARAMS.AllowMissingRaster);
		final ID missingAsset = SystemSettings.getSystemAssetID(R3_GRAPHICS_RENDER_PARAMS.RASTER_IS_MISING);
		final boolean reportFail = SystemSettings.getFlag(R3_GRAPHICS_RENDER_PARAMS.PrintLogMessageOnMissingSprite);

		AssetHandler asset_handler = LoadedAssets.obtainAsset(newAssetID, this.master);
		if (asset_handler == null) {
			if (!allowMissingAsset) {
				PackagesManager.printAllIndexes();
				Err.reportError("Asset<" + newAssetID + "> is not loaded.");
				return null;
			}

			asset_handler = LoadedAssets.obtainAsset(missingAsset, this.master);
			if (asset_handler == null) {
				Err.reportError("Asset is not loaded: " + missingAsset);
				return null;
			}

		}

		final Asset asset = asset_handler.asset();

		if (asset instanceof RasterData) {
			return new RedTile(this.master, (RasterData)asset);
		}

		if (asset instanceof TileSet) {
			final TileSet composition = ((TileSet)asset).copy();
			LoadedAssets.releaseAsset(asset_handler, this.master);
			if (composition.size() == 0) {
				Err.reportError("Bad structure<" + newAssetID + "> - has no tiles");
			}
			final RedTilesComposition compos = new RedTilesComposition(this.master, composition);

			return compos;
		}

		Err.reportError("Unsupported raster asset type: " + asset);
		return null;

	}

	@Override
	public RasterPool newRasterPool (final ID assetID) {
		return new RedRasterPool(this.master, assetID);
	}

	@Override
	public GraphicalConsoleSpecs newConsoleSpecs () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public GraphicalConsole newConsole (final GraphicalConsoleSpecs gspec) {
		Err.throwNotImplementedYet();
		return null;
	}

}
