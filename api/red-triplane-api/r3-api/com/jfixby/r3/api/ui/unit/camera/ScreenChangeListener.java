package com.jfixby.r3.api.ui.unit.camera;

import com.jfixby.r3.api.ui.unit.layer.Component;

public interface ScreenChangeListener extends Component {

	// void onViewportUpdated(ScreenViewportUpdate viewport_update, Camera
	// your_camera);

	void onScreenChanged(ScreenDimentions viewport_update);

}