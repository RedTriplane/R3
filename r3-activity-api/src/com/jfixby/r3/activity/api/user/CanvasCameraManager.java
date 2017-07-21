
package com.jfixby.r3.activity.api.user;

import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.camera.ScreenDimentions;

public interface CanvasCameraManager {

	void onScreenUpdate (ScreenDimentions viewport_update, CanvasCamera your_camera);

}
