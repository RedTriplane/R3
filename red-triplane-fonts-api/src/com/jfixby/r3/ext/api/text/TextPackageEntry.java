package com.jfixby.r3.ext.api.text;

public class TextPackageEntry {

	public String text_concept_id;
	// public String locale_name;
	public String localized_text_value;

	@Override
	public String toString() {
		return "<" + text_concept_id + "> = " + localized_text_value + "";
	}

}
