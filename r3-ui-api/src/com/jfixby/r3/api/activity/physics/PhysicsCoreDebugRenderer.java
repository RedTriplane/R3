
package com.jfixby.r3.api.activity.physics;

import com.jfixby.r3.api.activity.camera.CanvasCamera;
import com.jfixby.r3.api.activity.layer.Component;

public interface PhysicsCoreDebugRenderer extends Component {

	CanvasCamera getCamera ();

	public void hide ();

	public void show ();

	public boolean isVisible ();

	// void setIUserInputListener(IUserInputListener touch);

}
