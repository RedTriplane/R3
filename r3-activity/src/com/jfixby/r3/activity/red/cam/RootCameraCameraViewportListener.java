
package com.jfixby.r3.activity.red.cam;

import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.camera.ScreenDimentions;
import com.jfixby.r3.activity.api.user.CanvasCameraManager;

public class RootCameraCameraViewportListener implements CanvasCameraManager {

	@Override
	public void onScreenUpdate (final ScreenDimentions viewport_update, final CanvasCamera your_camera) {

		your_camera.setSize(viewport_update.getScreenWidth(), viewport_update.getScreenHeight());
	}

}
