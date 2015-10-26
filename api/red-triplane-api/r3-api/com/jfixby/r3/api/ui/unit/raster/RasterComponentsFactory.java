package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.assets.AssetID;

public interface RasterComponentsFactory {

	Tile newTile(AssetID asset_id);

	Raster newRaster(AssetID asset_id);

}
