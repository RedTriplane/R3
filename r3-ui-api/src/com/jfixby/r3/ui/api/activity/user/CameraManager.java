
package com.jfixby.r3.ui.api.activity.user;

import com.jfixby.r3.ui.api.activity.camera.Camera;
import com.jfixby.r3.ui.api.activity.camera.ScreenDimentions;

public interface CameraManager {

	void onScreenUpdate (ScreenDimentions viewport_update, Camera your_camera);

}
