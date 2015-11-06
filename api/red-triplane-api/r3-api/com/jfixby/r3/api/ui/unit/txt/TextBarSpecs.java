package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.assets.AssetID;

public interface TextBarSpecs {

	void setFont(AssetID font_id);

	AssetID getFont();

	void setFontSize(float font_size);

	float getFontSize();

	String getText();

	void setText(String text_string);

	public void setPadding(float padding);

	public float getPadding();

}
