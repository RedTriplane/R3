package com.jfixby.r3.ext.api.font;

import java.util.Vector;

import com.jfixby.cmns.api.assets.AssetID;

public class FontPackage {
	public static final String FONT_PACKAGE_FILE_EXTENSION = "r3-font";
	public static final String FONT_PACKAGE_FORMAT = "RedTriplane.Font";

	public Vector<FontData> fonts = new Vector<FontData>();

	public static AssetID charRasterName(AssetID font_name, char char_value) {
		return font_name.child("raster").child("char-" + (int) char_value);
	}

}
