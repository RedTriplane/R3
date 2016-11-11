package com.jfixby.r3.api.ui.unit.txt;

public class CHARS_LIST {

	public static String cyrillic = UNICODE_STRING(0x0000, 0x04FF);

	static private String UNICODE_STRING(int a, int b) {
		String result = "";
		for (char c = (char) a; c <= b; c++) {
			result = result + "" + c;
		}
		return result;
	}

}
