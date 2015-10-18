package com.jfixby.r3.fokker.api;

import com.jfixby.r3.api.ui.unit.camera.ScreenDimentions;

public interface ScreenComponent {

	boolean isInValidState();

	int getScreenWidth();

	int getScreenHeight();

	long getLastUpdateCycleNumber();

	ScreenDimentionsChecker newScreenDimentionsChecker();

	ScreenDimentions getScreenDimentions();

}
