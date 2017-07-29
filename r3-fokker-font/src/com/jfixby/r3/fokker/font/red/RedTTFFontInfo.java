
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.fokker.font.api.FokkerFont;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;

public class RedTTFFontInfo implements Asset, FokkerFont {

	final private ID asset_id;
	final private File gdx_font_file;

	@Override
	public String toString () {
		return "TTFFontInfo[" + this.asset_id + "] " + this.gdx_font_file + "";
	}

	public RedTTFFontInfo (final ID asset_id, final File original_font_file) {
		this.asset_id = asset_id;
		this.gdx_font_file = original_font_file;
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	public File getFontFile () {
		return this.gdx_font_file;
	}

	@Override
	public AssetsGroup getGroup () {
		Err.throwNotImplementedYet();
		return null;
	}

}
