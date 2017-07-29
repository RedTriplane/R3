
package com.jfixby.r3.fokker.font.red.gdx;

import com.badlogic.gdx.graphics.g2d.BitmapFontCache;

public class GdxR3FontCache {

	private final RedBitmapFont redFontGenerated;
	private String string_value;
	private final BitmapFontCache cache;

	public GdxR3FontCache (final RedBitmapFont redFontGenerated) {
		this.redFontGenerated = redFontGenerated;
		this.cache = redFontGenerated.gdx_bitmap_font.newFontCache();
	}

	public void setText (final String string_value) {
		this.string_value = string_value;
		this.cache.setText(string_value, 0, 0);
	}

	public void dispose () {
		this.cache.clear();
	}

	public int getVertexCount (final int page) {
		return this.cache.getVertexCount(page);
	}

	public float[] getVertices (final int region) {
		return this.cache.getVertices(region);
	}

}
