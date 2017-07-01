package com.jfixby.r3.api.ui.unit.camera;

import com.jfixby.r3.api.screen.ScreenDimentions;

public interface CameraManager {

	void onScreenUpdate(ScreenDimentions viewport_update, Camera your_camera);

}
