package com.jfixby.r3.ext.api.font;

import com.jfixby.cmns.api.file.File;
import com.jfixby.rana.api.pkg.PackageReader;


public interface R3FontComponent {

	PackageReader getPackageReader();

	FontGenerator newFontGenerator(File gdx_font_file);

}
