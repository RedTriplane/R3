package com.jfixby.r3.ext.api.text;

public class TextPackageEntry {

	public String text_concept_id;
	public String localized_text_value;
	public String locale_name;

	@Override
	public String toString() {
		return "<" + text_concept_id + "." + locale_name + "> = "
				+ localized_text_value + "";
	}

}
