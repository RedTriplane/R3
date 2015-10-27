package com.jfixby.r3.ext.api.font;

import com.jfixby.r3.api.assets.Asset;
import com.jfixby.r3.api.ui.unit.txt.CharSettings;

public interface Font extends Asset {

	CharSettings getCharSettings(String character);

}
