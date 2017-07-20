
package com.jfixby.r3.ui.api.activity.raster;

import com.jfixby.scarabei.api.assets.ID;

public interface RasterComponentsFactory {

	Tile newTile (ID asset_id);

	Raster newRaster (ID asset_id);

	RasterPool newRasterPool (ID assetID);

	GraphicalConsoleSpecs newConsoleSpecs ();

	GraphicalConsole newConsole (GraphicalConsoleSpecs gspec);

}
