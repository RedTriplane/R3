
package com.jfixby.r3.activity.red.cam;

import com.jfixby.r3.activity.api.camera.CameraFactory;
import com.jfixby.r3.activity.api.camera.CanvasCameraSpecs;
import com.jfixby.r3.activity.api.camera.Shadow;
import com.jfixby.r3.activity.api.camera.ShadowSpecs;
import com.jfixby.r3.activity.red.RedComponentsFactory;

public class RedCameraFactory implements CameraFactory {

	private final RedComponentsFactory master;

	public RedCameraFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public ShadowSpecs newShadowSpecs () {
		return new RedShadowSpecs();
	}

	@Override
	public Shadow newShadow (final ShadowSpecs shadow_specs) {
		return new RedShadow(shadow_specs, this.master);
	}

	@Override
	public CanvasCameraSpecs newCameraSpecs () {
		return new RedCameraSpecs();
	}

	@Override
	public RedCamera newCamera (final CanvasCameraSpecs cam_properties) {
		return new RedCamera(cam_properties, this.master);
	}

}
