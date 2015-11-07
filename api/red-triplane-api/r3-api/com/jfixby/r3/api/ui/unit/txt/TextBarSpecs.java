package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.api.text.StringValue;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public interface TextBarSpecs {

	void setFont(AssetID font_id);

	AssetID getFont();

	void setFontSize(float font_size);

	float getFontSize();

	StringValue getText();

	void setText(StringValue text_string);

	public void setPadding(float padding);

	public float getPadding();

	Raster getBackgroundRaster();

	void setBackgroundRaster(Raster bg_asset_id);

	void setFontScale(float font_scale);

	float getFontScale();

}
