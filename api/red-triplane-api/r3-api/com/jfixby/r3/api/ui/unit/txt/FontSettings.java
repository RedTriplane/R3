package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.assets.AssetID;

public interface FontSettings {

	FontSize getFontSize();

	AssetID getFontName();

	void setFontName(AssetID font_name);

	void setFontSize(FontSize font_size);

}
