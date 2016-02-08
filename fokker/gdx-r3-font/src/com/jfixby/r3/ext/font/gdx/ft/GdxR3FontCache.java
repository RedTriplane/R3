package com.jfixby.r3.ext.font.gdx.ft;

import com.jfixby.r3.api.ui.unit.txt.StringBounds;
import com.jfixby.r3.ext.api.font.FontCache;

public class GdxR3FontCache implements FontCache {

	private GdxR3FontGenerated redFontGenerated;
	private String string_value;

	public GdxR3FontCache(GdxR3FontGenerated redFontGenerated) {
		this.redFontGenerated = redFontGenerated;
	}

	@Override
	public void setText(String string_value) {
		this.string_value = string_value;
	}

	@Override
	public StringBounds getStringBounds() {
		return this.redFontGenerated.getStringBounds(this.string_value);
	}

}
