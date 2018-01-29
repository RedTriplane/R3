
package com.jfixby.r3.string.io;

import java.util.ArrayList;

public class StringsPackage implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5547035779902819980L;

	public static final String PACKAGE_FILE_EXTENSION = "r3-string";

	public final ArrayList<StringPackageEntry> entries = new ArrayList<>();
}
