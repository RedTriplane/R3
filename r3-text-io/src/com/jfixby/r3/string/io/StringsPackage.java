
package com.jfixby.r3.string.io;

import java.util.ArrayList;

public class StringsPackage implements java.io.Serializable {
	/**
	 *
	 */

	public static final String Pangram_EN = "The quick brown fox jumps over the lazy dog.";
	public static final String Pangram_RU = "Съешь же ещё этих мягких французских булок, да выпей чаю.";
	public static final String Pangram_IT = "Quel vituperabile xenofobo zelante assaggia il whisky ed esclama: alleluja!";

	private static final long serialVersionUID = 5547035779902819980L;

	public static final String PACKAGE_FILE_EXTENSION = "r3-string";

	public static final String PACKAGE_FORMAT = "r3-string";

	public final ArrayList<StringPackageEntry> entries = new ArrayList<>();
}
