
package com.jfixby.r3.fokker.api.assets;

import com.jfixby.cmns.api.assets.AssetID;

public interface FokkerRasterDataRegisterComponent {

	void registerRaster (FokkerRasterDataGroup data);

	FokkerRasterData getGdxRasterData (AssetID asset_id);

	void printAll (String string);

	void unRegisterRaster (FokkerRasterDataGroup group);

}
