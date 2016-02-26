package com.jfixby.r3.fokker.api;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.r3.api.ui.unit.camera.ScreenDimentions;

public class Screen {

	static private ComponentInstaller<ScreenComponent> componentInstaller = new ComponentInstaller<ScreenComponent>(
			"Screen");

	public static final void installComponent(
			ScreenComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final ScreenComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final ScreenComponent component() {
		return componentInstaller.getComponent();
	}

	public static boolean isInValidState() {
		return invoke().isInValidState();
	}

	public static int getScreenWidth() {
		return invoke().getScreenWidth();
	}

	public static int getScreenHeight() {
		return invoke().getScreenHeight();
	}

	public static long getLastUpdateCycleNumber() {
		return invoke().getLastUpdateCycleNumber();
	}

	public static ScreenDimentionsChecker newScreenDimentionsChecker() {
		return invoke().newScreenDimentionsChecker();
	}

	public static ScreenDimentions getScreenDimensions() {
		return invoke().getScreenDimensions();
	}

}
