
package com.jfixby.r3.activity.api.user;

import com.jfixby.r3.activity.api.camera.ScreenDimentions;
import com.jfixby.r3.activity.api.layer.Component;

public interface ScreenChangeListener extends Component {

	// void onViewportUpdated(ScreenViewportUpdate viewport_update, Camera
	// your_camera);

	void onScreenChanged (ScreenDimentions viewport_update);

}
