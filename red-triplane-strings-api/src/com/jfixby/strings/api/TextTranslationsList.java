package com.jfixby.strings.api;

public interface TextTranslationsList {

	TextTranslation getLast();

	void print();

	TextTranslation getByLocalization(String locale_name);

}
