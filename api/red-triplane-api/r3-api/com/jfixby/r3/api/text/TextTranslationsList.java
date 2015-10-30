package com.jfixby.r3.api.text;

public interface TextTranslationsList {

	TextTranslation getLast();

	void print();

	TextTranslation getByLocalization(String locale_name);

}
