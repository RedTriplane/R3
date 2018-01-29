package com.jfixby.r3.string;

public interface TextTranslationsList {

	TextTranslation getLast();

	void print();

	TextTranslation getByLocalization(String locale_name);

}
