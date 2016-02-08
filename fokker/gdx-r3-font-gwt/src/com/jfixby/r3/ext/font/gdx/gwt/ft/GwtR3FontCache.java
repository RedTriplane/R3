package com.jfixby.r3.ext.font.gdx.gwt.ft;

import com.jfixby.r3.api.ui.unit.txt.StringBounds;
import com.jfixby.r3.ext.api.font.FontCache;

public class GwtR3FontCache implements FontCache {

	private GwtR3FontGenerated redFontGenerated;
	private String string_value;

	public GwtR3FontCache(GwtR3FontGenerated redFontGenerated) {
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
