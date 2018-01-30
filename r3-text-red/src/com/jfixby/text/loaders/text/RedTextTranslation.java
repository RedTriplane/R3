
package com.jfixby.text.loaders.text;

import com.jfixby.r3.string.StringData;
import com.jfixby.r3.string.TextTranslation;

public class RedTextTranslation implements TextTranslation {

	private final String name;
	private final StringData data;

	public RedTextTranslation (final String name, final StringData data) {
		this.name = name;
		this.data = data;
	}

	@Override
	public String getLocaleName () {
		return this.name;
	}

	@Override
	public StringData getString () {
		return this.data;
	}

	@Override
	public String toString () {
		return this.name + " : " + this.data;
	}

}
