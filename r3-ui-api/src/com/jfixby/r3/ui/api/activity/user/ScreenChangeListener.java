
package com.jfixby.r3.ui.api.activity.user;

import com.jfixby.r3.ui.api.activity.camera.ScreenDimentions;
import com.jfixby.r3.ui.api.activity.layer.Component;

public interface ScreenChangeListener extends Component {

	// void onViewportUpdated(ScreenViewportUpdate viewport_update, Camera
	// your_camera);

	void onScreenChanged (ScreenDimentions viewport_update);

}
