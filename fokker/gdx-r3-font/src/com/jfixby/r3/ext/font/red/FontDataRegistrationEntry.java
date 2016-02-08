package com.jfixby.r3.ext.font.red;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.file.File;
import com.jfixby.r3.api.ui.unit.txt.FontData;
import com.jfixby.r3.ext.api.font.RedFontData;

public class FontDataRegistrationEntry implements FontData {

	final private AssetID asset_id;
	final private File gdx_font_file;

	public FontDataRegistrationEntry(AssetID asset_id, RedFontData structure, File original_font_file) {
		this.asset_id = asset_id;
		this.gdx_font_file = original_font_file;
	}

	@Override
	public AssetID getAssetID() {
		return asset_id;
	}

	@Override
	public File getFontFile() {
		return gdx_font_file;
	}

}
