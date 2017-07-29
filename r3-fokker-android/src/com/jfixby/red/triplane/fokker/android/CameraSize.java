
package com.jfixby.red.triplane.fokker.android;

import android.hardware.Camera.Size;

public class CameraSize {

	private final Size size;

	public CameraSize (final android.hardware.Camera.Size size) {
		this.size = size;
	}

	@Override
	public String toString () {
		return "CameraSize[" + this.size.width + "," + this.size.height + "]";
	}

}
