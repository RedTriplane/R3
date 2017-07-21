
package com.jfixby.r3.api.activity.user;

import com.jfixby.r3.api.activity.camera.CanvasCamera;
import com.jfixby.r3.api.activity.camera.ScreenDimentions;

public interface CanvasCameraManager {

	void onScreenUpdate (ScreenDimentions viewport_update, CanvasCamera your_camera);

}
