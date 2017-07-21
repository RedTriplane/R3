
package com.jfixby.r3.api.activity.user;

import com.jfixby.r3.api.activity.camera.ScreenDimentions;
import com.jfixby.r3.api.activity.layer.Component;

public interface ScreenChangeListener extends Component {

	// void onViewportUpdated(ScreenViewportUpdate viewport_update, Camera
	// your_camera);

	void onScreenChanged (ScreenDimentions viewport_update);

}
