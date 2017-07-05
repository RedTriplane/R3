package com.jfixby.r3.api.ui.unit.camera;

public interface CameraFactory {

	CameraSpecs newCameraSpecs();

	Camera newCamera(CameraSpecs cam_properties);

	// CameraPolicy newCameraPolicy(CameraPolicySpecs policy_specs);

	// CameraPolicySpecs newCameraPolicySpecs();

	ShadowSpecs newShadowSpecs();

	Shadow newShadow(ShadowSpecs shadow_specs);

}
