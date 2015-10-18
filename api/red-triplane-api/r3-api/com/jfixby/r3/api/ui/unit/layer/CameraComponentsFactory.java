package com.jfixby.r3.api.ui.unit.layer;

import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;

public interface CameraComponentsFactory {

	CameraSpecs newCameraSpecs();

	Camera newCamera(CameraSpecs cam_properties);

	// CameraPolicy newCameraPolicy(CameraPolicySpecs policy_specs);

	// CameraPolicySpecs newCameraPolicySpecs();

	ShadowSpecs newShadowSpecs();

	Shadow newShadow(ShadowSpecs shadow_specs);

}
