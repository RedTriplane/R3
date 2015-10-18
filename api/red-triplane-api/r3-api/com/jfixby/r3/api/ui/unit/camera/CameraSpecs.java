package com.jfixby.r3.api.ui.unit.camera;

import com.jfixby.r3.api.ui.unit.user.CameraManager;

public interface CameraSpecs {

	void setCameraManager(CameraManager cameraManager);

	public CameraManager getCameraManager();

	public void setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY policy);

	SIMPLE_CAMERA_POLICY getSimpleCameraPolicy();

}
