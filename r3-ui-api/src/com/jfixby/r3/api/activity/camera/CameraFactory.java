package com.jfixby.r3.api.activity.camera;

public interface CameraFactory {

	CanvasCameraSpecs newCameraSpecs();

	CanvasCamera newCamera(CanvasCameraSpecs cam_properties);

	// CameraPolicy newCameraPolicy(CameraPolicySpecs policy_specs);

	// CameraPolicySpecs newCameraPolicySpecs();

	ShadowSpecs newShadowSpecs();

	Shadow newShadow(ShadowSpecs shadow_specs);

}
