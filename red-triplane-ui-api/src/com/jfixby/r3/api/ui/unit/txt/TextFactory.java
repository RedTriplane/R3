
package com.jfixby.r3.api.ui.unit.txt;

public interface TextFactory {

	TextBarSpecs newTextBarSpecs ();

	TextBar newTextBar (TextBarSpecs text_specs);

	RasterizedStringSpecs newRasterStringSpecs ();

	RasterizedString newRasterString (RasterizedStringSpecs specs);

	void dispose (RasterizedString string);

}
