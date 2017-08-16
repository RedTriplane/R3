
package com.jfixby.r3.fokker.adaptor.cfg;

import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.names.ID;

public class FokkerStarterConfigAsset implements Asset, AssetsGroup {

	private final FokkerStarterConfig configData;
	private final ID assetID;

	public FokkerStarterConfigAsset (final ID assetID, final FokkerStarterConfig configData) {
		this.assetID = assetID;
		this.configData = configData;
	}

	@Override
	public ID getAssetID () {
		return this.assetID;
	}

	@Override
	public String toString () {
		return "FokkerStarterConfigAsset[" + this.assetID + "]";
	}

	public void print () {
		this.configData.print();
	}

	public String getValue (final String arg0) {
		return this.configData.params.get(arg0);
	}

	@Override
	public AssetsGroup getGroup () {
		return this;
	}

	@Override
	public void dispose () {
	}

	public String getTitle () {
		return this.configData.getValue(FokkerStarterConfig.TITLE);
	}

	public boolean getUseLG30Flag () {
		return Boolean.parseBoolean(this.configData.getValue(FokkerStarterConfig.useGL30));
	}

	public int getWindowWidth () {
		return Integer.parseInt(this.configData.getValue(FokkerStarterConfig.width));
	}

	public int getWindowHeight () {
		return Integer.parseInt(this.configData.getValue(FokkerStarterConfig.height));
	}

}
