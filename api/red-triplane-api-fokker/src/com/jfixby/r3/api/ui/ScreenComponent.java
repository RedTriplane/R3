
package com.jfixby.r3.api.ui;

import com.jfixby.r3.api.ui.unit.camera.ScreenDimentions;
import com.jfixby.r3.fokker.api.ScreenDimentionsChecker;

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
