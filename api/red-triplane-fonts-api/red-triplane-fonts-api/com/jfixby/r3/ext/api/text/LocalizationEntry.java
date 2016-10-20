
package com.jfixby.r3.ext.api.text;

public class LocalizationEntry {
	public String locale_name;
	public String string_id;

	@Override
	public String toString () {
		return this.locale_name + " : <" + this.string_id + ">";
	}

}
