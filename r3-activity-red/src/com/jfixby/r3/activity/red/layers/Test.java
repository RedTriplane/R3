
package com.jfixby.r3.activity.red.layers;

public class Test {

	public static void main (final String[] args) {

		test(-22, 10);

	}

	private static void test (final int index, final int N) {
		log("", index % N);
	}

	private static void log (final String tag, final Object v) {
		System.out.println(tag + " > " + v);
	}

}
