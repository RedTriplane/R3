
package com.jfixby.text.loaders.text;

import com.jfixby.text.loaders.strings.StringDataEntry;

public class RedTextTranslation {

	private final String name;
	private final StringDataEntry data;

	public RedTextTranslation (final String name, final StringDataEntry data) {
		this.name = name;
		this.data = data;
	}

	public String getLocaleName () {
		return this.name;
	}

	public String getString () {
		return this.data.getData();
	}

	@Override
	public String toString () {
		return this.name + " : " + this.data;
	}

}
