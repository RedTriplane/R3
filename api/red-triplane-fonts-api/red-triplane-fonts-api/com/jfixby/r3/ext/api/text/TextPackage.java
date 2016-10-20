
package com.jfixby.r3.ext.api.text;

import java.util.ArrayList;

public class TextPackage {
	public static final String PACKAGE_FILE_EXTENSION = "r3-text";

	public final ArrayList<TextPackageEntry> entries = new ArrayList<TextPackageEntry>();

	public void addText (final TextPackageEntry entry) {
		this.entries.add(entry);
	}
}
