package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.assets.AssetID;

public interface TextBarSpecs {

	String getName();

	AssetID getID();

	void setID(AssetID id);

	void setName(String name);

	TextBarMargin getMargin();

	TextBarHeight getHeight();

	TextBarWidth getWidth();

	FontSettings getFontSettings();

	TextSettings getTextSettings();

	

}
