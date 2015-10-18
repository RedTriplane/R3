package com.jfixby.r3.fokker.api.assets;

import com.jfixby.cmns.api.assets.AssetID;

public interface FokkerRasterDataRegisterComponent {

	void registerRaster(AssetID rester_id, FokkerRasterData data);

	FokkerRasterData getGdxRasterData(AssetID asset_id);

	void printAll(String string);

}
