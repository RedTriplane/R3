package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.assets.AssetID;

public interface RasterizedFont {

	AssetID getName();

	void dispose();

	StringBounds getStringBounds(String string_value);

	float getSize();

}
