
package com.jfixby.r3.api.screen;

public interface ScreenComponent {

	boolean isInValidState ();

	int getScreenWidth ();

	int getScreenHeight ();

	long getLastUpdateCycleNumber ();

	ScreenDimentionsChecker newScreenDimentionsChecker ();

	ScreenDimentions getScreenDimensions ();

	void setDebugScaleFactor (double debugScaleFactor);

	double getDebugScaleFactor ();

}
