package com.jfixby.r3.ext.api.font;

public interface FontGenerator {

	FontParameters newFontParameters();

	BitmapFont generateFont(FontParameters params);

	void dispose();

}
