
package com.jfixby.r3.activity.api.user;

import com.jfixby.r3.activity.api.input.MouseAwayEvent;
import com.jfixby.r3.activity.api.input.MouseExitEvent;
import com.jfixby.r3.activity.api.input.MouseMovedEvent;
import com.jfixby.r3.activity.api.input.MouseScrolledEvent;
import com.jfixby.r3.activity.api.input.TouchDownEvent;
import com.jfixby.r3.activity.api.input.TouchDraggedEvent;
import com.jfixby.r3.activity.api.input.TouchUpEvent;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.scarabei.api.log.L;

public abstract class MouseInputEventListener implements Component {

	public static final MouseInputEventListener DEBUG = new MouseInputEventListener() {};

	public boolean onMouseMoved (final MouseMovedEvent event) {
		L.d("" + this, event);
		L.d("     ", event.getLayerPosition());
		return true;
	}

	public boolean onTouchDown (final TouchDownEvent event) {
		L.d("" + this, event);
		L.d("     ", event.getLayerPosition());
		return true;
	}

	public boolean onTouchUp (final TouchUpEvent event) {
		L.d("" + this, event);
		L.d("     ", event.getLayerPosition());
		return true;
	}

	public boolean onTouchDragged (final TouchDraggedEvent event) {
		L.d("" + this, event);
		L.d("     ", event.getLayerPosition());
		return true;
	}

	public boolean onMouseExit (final MouseExitEvent event) {
		L.d("" + this, event);
		L.d("     ", event.getLayerPosition());
		return true;
	}

	public boolean onMouseScrolled (final MouseScrolledEvent event) {
		L.d("" + this, event);
		L.d("     ", event.getLayerPosition());
		return true;
	}

	@Override
	public String toString () {
		return "MouseEvent";
	}

	public boolean onMouseAway (final MouseAwayEvent event) {
		L.d("" + this, event);
		L.d("     ", event.getLayerPosition());
		return true;
	}

}
