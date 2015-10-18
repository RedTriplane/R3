package com.jfixby.r3.api.ui.unit.layer;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.raster.Tile;

public interface RasterComponentsFactory {

	Tile newTile(AssetID asset_id);

	Raster newRaster(AssetID asset_id);

}
