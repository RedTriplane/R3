package com.r3.font.core;

import com.jfixby.cmns.api.file.File;
import com.jfixby.r3.ext.api.font.FontGenerator;
import com.jfixby.r3.ext.api.font.R3FontComponent;
import com.jfixby.r3.ext.font.red.FontPackageLoader;
import com.jfixby.rana.api.pkg.PackageReader;

public class GdxR3Font implements R3FontComponent {

	public GdxR3Font() {
		loader = new FontPackageLoader();
	}

	final FontPackageLoader loader;

	@Override
	public PackageReader getPackageReader() {
		return loader;
	}

	@Override
	public FontGenerator newFontGenerator(File gdx_font_file) {
		return new GdxR3FontGenerator(gdx_font_file);
	}

}
