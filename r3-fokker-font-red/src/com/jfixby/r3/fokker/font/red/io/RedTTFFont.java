
package com.jfixby.r3.fokker.font.red.io;

import com.jfixby.r3.fokker.font.api.FokkerFont;
import com.jfixby.r3.fokker.font.red.RedFokkerString;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.font.RasterStringSettings;
import com.jfixby.scarabei.api.names.ID;

public class RedTTFFont implements Asset, FokkerFont {

	final private ID asset_id;
	final private File gdx_font_file;

	@Override
	public String toString () {
		return "TTFFontInfo[" + this.asset_id + "] " + this.gdx_font_file + "";
	}

	@Override
	public RedFokkerString produceString (final RasterStringSettings rasterStringSettings) {

		final RedFokkerString str = new RedFokkerString(this, rasterStringSettings);

		return str;
	}

	public RedTTFFont (final ID asset_id, final File original_font_file) {
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
