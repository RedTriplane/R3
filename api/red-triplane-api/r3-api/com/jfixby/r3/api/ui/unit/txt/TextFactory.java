
package com.jfixby.r3.api.ui.unit.txt;

public interface TextFactory {

	public static final String Pangram_EN = "The quick brown fox jumps over the lazy dog.";
	public static final String Pangram_RU = "Съешь же ещё этих мягких французских булок, да выпей чаю.";
	public static final String Pangram_IT = "Quel vituperabile xenofobo zelante assaggia il whisky ed esclama: alleluja!";

	TextBarSpecs newTextBarSpecs ();

	TextBar newTextBar (TextBarSpecs text_specs);

// FontSize newFontSize ();

	RasterizedStringSpecs newRasterStringSpecs ();

	RasterizedString newRasterString (RasterizedStringSpecs specs);

// RasterizedFont newFont (RasterizedFontSpecs specs);
//
// RasterizedFontSpecs newFontSpecs ();

}
