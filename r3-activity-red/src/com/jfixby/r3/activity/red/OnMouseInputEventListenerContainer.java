
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.input.MouseExitEvent;
import com.jfixby.r3.activity.api.input.MouseMovedEvent;
import com.jfixby.r3.activity.api.input.TouchDownEvent;
import com.jfixby.r3.activity.api.input.TouchDraggedEvent;
import com.jfixby.r3.activity.api.input.TouchUpEvent;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;

public class OnMouseInputEventListenerContainer extends MouseInputEventListener {

	private final ContainerOwner owner;

	public OnMouseInputEventListenerContainer (final ContainerOwner redButton) {
		this.owner = redButton;
		this.listener = MouseInputEventListener.DEBUG;
	}

	private MouseInputEventListener listener;

	@Override
	public boolean onMouseMoved (final MouseMovedEvent input_event) {
		return this.listener.onMouseMoved(input_event);
	}

	@Override
	public boolean onTouchDown (final TouchDownEvent input_event) {
		return this.owner.doPressButton(this.listener.onTouchDown(input_event));
	}

	@Override
	public boolean onTouchUp (final TouchUpEvent input_event) {
		return this.owner.doReleaseButton(this.listener.onTouchUp(input_event));
	}

	@Override
	public boolean onTouchDragged (final TouchDraggedEvent input_event) {
		return this.listener.onTouchDragged(input_event);
	}

	@Override
	public boolean onMouseExit (final MouseExitEvent input_event) {
// L.d("" + this, event);
		return this.listener.onMouseExit(input_event);
	}

	public MouseInputEventListener getListener () {
		return this.listener;
	}

	public void setListener (final MouseInputEventListener listener) {
		this.listener = listener;
		if (this.listener == null) {
			this.listener = MouseInputEventListener.DEBUG;
		}
	}

}
