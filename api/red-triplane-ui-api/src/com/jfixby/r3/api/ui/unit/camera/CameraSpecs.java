
package com.jfixby.r3.api.ui.unit.camera;

public interface CameraSpecs {

	void setCameraManager (CameraManager cameraManager);

	public CameraManager getCameraManager ();

	public void setSimpleCameraPolicy (SIMPLE_CAMERA_POLICY policy);

	SIMPLE_CAMERA_POLICY getSimpleCameraPolicy ();

// void setProjectionMode (PROJECTION_MODE mode);
//
// PROJECTION_MODE getProjectionMode ();

}
