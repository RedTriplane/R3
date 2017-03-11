package com.jfixby.r3.api.ui.unit.txt;

public interface TextTranslationsList {

	TextTranslation getLast();

	void print();

	TextTranslation getByLocalization(String locale_name);

}
