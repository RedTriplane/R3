
package com.jfixby.r3.activity.api.physics;

import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.layer.Component;

public interface PhysicsCoreDebugRenderer extends Component {

	CanvasCamera getCamera ();

	public void hide ();

	public void show ();

	public boolean isVisible ();

	// void setIUserInputListener(IUserInputListener touch);

}
