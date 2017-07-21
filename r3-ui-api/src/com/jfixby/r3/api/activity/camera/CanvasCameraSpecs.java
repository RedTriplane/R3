
package com.jfixby.r3.api.activity.camera;

import com.jfixby.r3.api.activity.user.CanvasCameraManager;

public interface CanvasCameraSpecs {

	void setCameraManager (CanvasCameraManager cameraManager);

	public CanvasCameraManager getCameraManager ();

	public void setSimpleCameraPolicy (SIMPLE_CAMERA_POLICY policy);

	SIMPLE_CAMERA_POLICY getSimpleCameraPolicy ();

// void setProjectionMode (PROJECTION_MODE mode);
//
// PROJECTION_MODE getProjectionMode ();

}
