
package com.jfixby.r3.ui.api.activity.physics;

import com.jfixby.r3.ui.api.activity.camera.Camera;
import com.jfixby.r3.ui.api.activity.layer.Component;

public interface PhysicsCoreDebugRenderer extends Component {

	Camera getCamera ();

	public void hide ();

	public void show ();

	public boolean isVisible ();

	// void setIUserInputListener(IUserInputListener touch);

}
