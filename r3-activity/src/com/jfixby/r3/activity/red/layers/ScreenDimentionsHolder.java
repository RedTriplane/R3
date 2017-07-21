
package com.jfixby.r3.activity.red.layers;

import com.jfixby.r3.activity.api.camera.ScreenDimentions;
import com.jfixby.r3.api.screen.Screen;

public class ScreenDimentionsHolder implements ScreenDimentions {
	public void updateScreenDimentions () {
		final com.jfixby.r3.api.screen.ScreenDimentions dims = Screen.getScreenDimensions();
		this.widthToHeightRatio = dims.getWidthToHeightRatio();
		this.screenHeight = dims.getScreenHeight();
		this.screenWidth = dims.getScreenWidth();
	}

	@Override
	public double getWidthToHeightRatio () {
		return this.widthToHeightRatio;
	}

	@Override
	public int getScreenWidth () {
		return this.screenWidth;
	}

	@Override
	public int getScreenHeight () {
		return this.screenHeight;
	}

	private double widthToHeightRatio;

	private int screenWidth;

	private int screenHeight;
}
