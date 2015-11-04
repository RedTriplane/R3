package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.color.Color;

public interface RasterizedFontSpecs {

	void setFontName(AssetID font_id);

	AssetID getFontName();

	float getFontSize();

	void setFontSize(float size);

	String getRequiredCharacters();

	void setRequiredCharacters(String chars);

	void setColor(Color font_color);

	public Color getColor();

}
