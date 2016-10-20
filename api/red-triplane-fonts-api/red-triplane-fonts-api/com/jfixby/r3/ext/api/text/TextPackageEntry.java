
package com.jfixby.r3.ext.api.text;

import java.util.ArrayList;

import com.jfixby.cmns.api.assets.AssetID;

public class TextPackageEntry {

	public String text_id;
	public ArrayList<LocalizationEntry> localizations = new ArrayList<LocalizationEntry>();

	public void addLocalization (final String locale_name, final AssetID string_id) {
		final LocalizationEntry entry = new LocalizationEntry();
		entry.locale_name = locale_name;
		entry.string_id = string_id.toString();
		this.localizations.add(entry);
	}

}
