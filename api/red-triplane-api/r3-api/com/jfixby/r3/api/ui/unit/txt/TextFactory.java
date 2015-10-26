package com.jfixby.r3.api.ui.unit.txt;

public interface TextFactory {

	TextBarSpecs newTextBarSpecs();

	TextBar newTextBar(TextBarSpecs text_specs);

	FontSize newFontSize();

	FontName newFontName(String font_name_string);

}
