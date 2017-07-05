
package com.jfixby.r3.api.ui.unit.user;

import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.ScreenDimentions;

public interface CameraManager {

	void onScreenUpdate (ScreenDimentions viewport_update, Camera your_camera);

}
