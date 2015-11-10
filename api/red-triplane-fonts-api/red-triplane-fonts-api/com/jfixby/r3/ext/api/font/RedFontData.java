package com.jfixby.r3.ext.api.font;

import java.util.Vector;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.rana.api.asset.Asset;

public class RedFontData implements Asset {
	public String font_name;

	public Vector<TileSet> tile_sets = new Vector<TileSet>();

	public String id;

	public String original_font_file_name;

	@Override
	public AssetID getAssetID() {
		return Names.newAssetID(id);
	}

}
