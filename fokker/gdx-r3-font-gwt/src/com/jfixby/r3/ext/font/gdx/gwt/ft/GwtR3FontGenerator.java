package com.jfixby.r3.ext.font.gdx.gwt.ft;

import com.jfixby.cmns.api.file.File;
import com.jfixby.r3.ext.api.font.FontGenerator;
import com.jfixby.r3.ext.api.font.FontParameters;

public class GwtR3FontGenerator implements FontGenerator {
	// private final ToGdxFileAdaptor gdx_font_file;

	private File file;

	public GwtR3FontGenerator(File file) {
		super();
		this.file = file;
	}

	@Override
	public FontParameters newFontParameters() {
		return new GwtR3FontParameters();
	}

	@Override
	public com.jfixby.r3.ext.api.font.BitmapFont generateFont(FontParameters params) {
		return new GwtR3FontGenerated(file, params);
	}

	@Override
	public void dispose() {
	}

}
