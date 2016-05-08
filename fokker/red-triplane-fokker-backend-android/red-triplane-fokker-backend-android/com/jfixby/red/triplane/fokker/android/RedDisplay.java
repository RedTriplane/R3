
package com.jfixby.red.triplane.fokker.android;

import android.view.Display;
import android.view.Surface;

public class RedDisplay {
	public enum ROTATION {
		ROTATION_0, ROTATION_90, ROTATION_180, ROTATION_270

	}

	ROTATION rotation = null;
	private final Display shitDisplay;

	public RedDisplay (final Display shitDisplay) {
		this.shitDisplay = shitDisplay;
		final int oldVlue = this.shitDisplay.getRotation();
		if (Surface.ROTATION_0 == oldVlue) {
			this.rotation = ROTATION.ROTATION_0;
		}
		if (Surface.ROTATION_90 == oldVlue) {
			this.rotation = ROTATION.ROTATION_90;
		}
		if (Surface.ROTATION_180 == oldVlue) {
			this.rotation = ROTATION.ROTATION_180;
		}
		if (Surface.ROTATION_270 == oldVlue) {
			this.rotation = ROTATION.ROTATION_270;
		}
	}

	@Override
	public String toString () {
		return "Display [" + this.rotation + "]";
	}

	public ROTATION getRotation () {
		return this.rotation;
	}

}
