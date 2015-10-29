package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.r3.api.ui.unit.raster.CharRaster;

public interface TextFactory {

	TextBarSpecs newTextBarSpecs();

	TextBar newTextBar(TextBarSpecs text_specs);

	FontSize newFontSize();

	CharRaster newCharTile(CharSettings char_settings);

	RasterizedStringSpecs newRasterStringSpecs();

	RasterizedString newRasterString(RasterizedStringSpecs specs);

}
