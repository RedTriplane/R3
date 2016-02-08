package com.jfixby.r3.ext.font.red;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.r3.api.ui.unit.txt.CharSettings;
import com.jfixby.r3.ext.api.font.CharRasterInfo;

public class RedCharSettings implements CharSettings {

	private CharRasterInfo info;

	public RedCharSettings(CharRasterInfo info) {
		this.info = info;
	}

	@Override
	public AssetID getRasterID() {
		return Names.newAssetID(info.raster_id);
	}

	public double getCharOffset() {
		return info.char_width;
	}

}
