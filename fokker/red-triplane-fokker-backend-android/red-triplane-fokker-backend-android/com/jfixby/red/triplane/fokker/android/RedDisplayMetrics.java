
package com.jfixby.red.triplane.fokker.android;

import com.jfixby.android.api.DisplayMetrics;

public class RedDisplayMetrics implements DisplayMetrics {

	private int width;
	private int height;

	public void set (final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public int getHeight () {
		return this.height;
	}

	@Override
	public int getWidth () {
		return this.width;
	}

}
