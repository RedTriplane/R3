package com.jfixby.r3.ui.api.activity.camera;

public interface CameraFactory {

	CameraSpecs newCameraSpecs();

	Camera newCamera(CameraSpecs cam_properties);

	// CameraPolicy newCameraPolicy(CameraPolicySpecs policy_specs);

	// CameraPolicySpecs newCameraPolicySpecs();

	ShadowSpecs newShadowSpecs();

	Shadow newShadow(ShadowSpecs shadow_specs);

}
