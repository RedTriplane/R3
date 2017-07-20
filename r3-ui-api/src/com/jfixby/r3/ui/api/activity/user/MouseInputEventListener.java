
package com.jfixby.r3.ui.api.activity.user;

import com.jfixby.r3.ui.api.activity.input.MouseExitEvent;
import com.jfixby.r3.ui.api.activity.input.MouseMovedEvent;
import com.jfixby.r3.ui.api.activity.input.MouseScrolledEvent;
import com.jfixby.r3.ui.api.activity.input.TouchDownEvent;
import com.jfixby.r3.ui.api.activity.input.TouchDraggedEvent;
import com.jfixby.r3.ui.api.activity.input.TouchUpEvent;
import com.jfixby.r3.ui.api.activity.layer.Component;
import com.jfixby.scarabei.api.log.L;

public abstract class MouseInputEventListener implements Component {

	public static final MouseInputEventListener DEBUG = new MouseInputEventListener() {};

	public boolean onMouseMoved (final MouseMovedEvent event) {
		L.d("" + this, event);
		return true;
	}

	public boolean onTouchDown (final TouchDownEvent event) {
		L.d("" + this, event);
		return true;
	}

	public boolean onTouchUp (final TouchUpEvent event) {
		L.d("" + this, event);
		return true;
	}

	public boolean onTouchDragged (final TouchDraggedEvent event) {
		L.d("" + this, event);
		return true;
	}

	public boolean onMouseExit (final MouseExitEvent event) {
		L.d("" + this, event);
		return true;
	}

	public boolean onMouseScrolled (final MouseScrolledEvent event) {
		L.d("" + this, event);
		return true;
	}

}
