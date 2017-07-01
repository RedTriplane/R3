
package com.jfixby.r3.api.screen;

import com.jfixby.scarabei.api.ComponentInstaller;

public class Screen {

	static private ComponentInstaller<ScreenComponent> componentInstaller = new ComponentInstaller<ScreenComponent>("Screen");

	public static final void installComponent (final ScreenComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final ScreenComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final ScreenComponent component () {
		return componentInstaller.getComponent();
	}

	public static boolean isInValidState () {
		return invoke().isInValidState();
	}

	public static int getScreenWidth () {
		return invoke().getScreenWidth();
	}

	public static int getScreenHeight () {
		return invoke().getScreenHeight();
	}

	public static long getLastUpdateCycleNumber () {
		return invoke().getLastUpdateCycleNumber();
	}

	public static ScreenDimentionsChecker newScreenDimentionsChecker () {
		return invoke().newScreenDimentionsChecker();
	}

	public static ScreenDimentions getScreenDimensions () {
		return invoke().getScreenDimensions();
	}

	public static void setDebugScaleFactor (final double debugScaleFactor) {
		invoke().setDebugScaleFactor(debugScaleFactor);
	}

	public static double getDebugScaleFactor () {
		return invoke().getDebugScaleFactor();
	}

}
