
package com.jfixby.r3.ext.api.font;

import java.util.Vector;

import com.jfixby.cmns.api.assets.AssetID;

public class FontPackage implements java.io.Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 3873830796830395210L;
	public static final String FONT_PACKAGE_FILE_EXTENSION = "r3-font";
	public static final String FONT_PACKAGE_FORMAT = "RedTriplane.Font";

	public Vector<RedFontData> fonts = new Vector<RedFontData>();

	public static AssetID charRasterName (final AssetID font_name, final char char_value) {
		return font_name.child("raster").child("char-" + (int)char_value);
	}

}
