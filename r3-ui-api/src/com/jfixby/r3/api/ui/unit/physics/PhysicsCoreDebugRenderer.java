
package com.jfixby.r3.api.ui.unit.physics;

import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.layer.Component;

public interface PhysicsCoreDebugRenderer extends Component {

	Camera getCamera ();

	public void hide ();

	public void show ();

	public boolean isVisible ();

	// void setIUserInputListener(IUserInputListener touch);

}
