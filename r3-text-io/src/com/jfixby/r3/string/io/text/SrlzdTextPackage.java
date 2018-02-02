
package com.jfixby.r3.string.io.text;

import java.util.ArrayList;

public class SrlzdTextPackage implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8601863485160340293L;

	public static final String PACKAGE_FILE_EXTENSION = "r3-text";

	public static final String PACKAGE_FORMAT = "r3-text";

	public final ArrayList<SrlzdTextPackageEntry> entries = new ArrayList<>();
}
