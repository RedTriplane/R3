package com.jfixby.r3.ext.api.font;

import com.jfixby.r3.api.assets.Asset;
import com.jfixby.r3.api.text.Chars;
import com.jfixby.r3.api.ui.unit.txt.CharSettings;

public interface Font extends Asset {

	CharSettings getCharSettings(String character);

	double getCharOffset(Chars chars, int char_index);

}
