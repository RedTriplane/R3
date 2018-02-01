
package com.jfixby.r3.fokker.font.red.gdx;

import com.jfixby.scarabei.api.file.File;

public class GdxR3FontGenerator {
	// private final ToGdxFileAdaptor gdx_font_file;

	private final File file;

	public GdxR3FontGenerator (final File file) {
		super();
		this.file = file;
	}

	public GdxR3FontParameters newFontParameters () {
		return new GdxR3FontParameters();
	}

	public RedBitmapFont generateFont (final GdxR3FontParameters params) {
		return new RedBitmapFont(this.file, params);
	}

	public void dispose () {
	}

}
