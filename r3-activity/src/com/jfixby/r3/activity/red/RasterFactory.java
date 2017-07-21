
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.raster.GraphicalConsole;
import com.jfixby.r3.activity.api.raster.GraphicalConsoleSpecs;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.raster.RasterComponentsFactory;
import com.jfixby.r3.activity.api.raster.RasterPool;
import com.jfixby.r3.activity.red.raster.RedTile;
import com.jfixby.r3.activity.red.raster.RedTilesComposition;
import com.jfixby.r3.activity.red.raster.TileSet;
import com.jfixby.r3.api.render.R3_SYSTEM_ASSETS;
import com.jfixby.r3.api.render.RasterData;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RasterFactory implements RasterComponentsFactory {

	private final RedComponentsFactory master;

	public RasterFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public RedTile newTile (final ID newAssetID) {
		final AssetHandler asset_handler = this.obtainRaster(newAssetID);
		final Asset asset = asset_handler.asset();

		if (asset instanceof RasterData) {
			return new RedTile(this.master, (RasterData)asset);
		}

		if (asset instanceof TileSet) {
			Err.reportError("This is not a tile: " + asset);
		}

		Err.reportError("Unknown asset type: " + asset);
		return null;

	}

	@Override
	public Raster newRaster (final ID newAssetID) {
		final AssetHandler asset_handler = this.obtainRaster(newAssetID);
		final Asset asset = asset_handler.asset();

		if (asset instanceof RasterData) {
			return new RedTile(this.master, (RasterData)asset);
		}

		if (asset instanceof TileSet) {
			final TileSet composition = ((TileSet)asset).copy();
			this.release(asset_handler);
			if (composition.size() == 0) {
				Err.reportError("Bad structure<" + newAssetID + "> - has no tiles");
			}
			final RedTilesComposition compos = new RedTilesComposition(this.master, composition);

			return compos;
		}

		Err.reportError("Unknown asset type: " + asset);
		return null;

	}

	private AssetHandler obtainRaster (final ID newAssetID) {
		return this.master.obtainAsset(newAssetID, SystemSettings.getFlag(R3_SYSTEM_ASSETS.AllowMissingRaster),
			R3_SYSTEM_ASSETS.RASTER_IS_MISING, SystemSettings.getFlag(R3_SYSTEM_ASSETS.PrintLogMessageOnMissingSprite));
	}

	private void release (final AssetHandler asset_handler) {
		LoadedAssets.releaseAsset(asset_handler, this.master);
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
