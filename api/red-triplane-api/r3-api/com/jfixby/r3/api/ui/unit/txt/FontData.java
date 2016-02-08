package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.file.File;
import com.jfixby.rana.api.asset.Asset;

public interface FontData extends Asset {

	File getFontFile();

	// TextTilesSequence produceTilesSequence(CharSequence chars,
	// ComponentsFactory componentsFactory);

	// CharSettings getCharSettings(String character);
	//
	// double getCharOffset(CharSequence chars, int char_index);

}
