
package com.jfixby.r3.activity.red.input;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.input.CharTypedEvent;
import com.jfixby.r3.activity.api.input.KeyDownEvent;
import com.jfixby.r3.activity.api.input.KeyUpEvent;
import com.jfixby.r3.activity.api.input.MouseExitEvent;
import com.jfixby.r3.activity.api.input.MouseMovedEvent;
import com.jfixby.r3.activity.api.input.MouseScrolledEvent;
import com.jfixby.r3.activity.api.input.TouchDownEvent;
import com.jfixby.r3.activity.api.input.TouchDraggedEvent;
import com.jfixby.r3.activity.api.input.TouchUpEvent;
import com.jfixby.r3.activity.api.user.KeyboardInputEventListener;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;
import com.jfixby.r3.activity.red.cam.RedCamera;
import com.jfixby.r3.engine.api.exe.InputEvent;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.input.Key;
import com.jfixby.scarabei.api.input.MouseButton;

public class ActivityInputEventsDeliveryBox implements MouseMovedEvent, TouchDraggedEvent, TouchUpEvent, TouchDownEvent,
	MouseScrolledEvent, MouseExitEvent, CharTypedEvent, KeyUpEvent, KeyDownEvent {

	final PressedKeys pressed = new PressedKeys();

	@Override
	public String toString () {
		if (this.is_key_down_event) {
			return "KeyDownEvent [" + this.key + "]";
		}

		if (this.is_key_typed_event) {
			return "CharTypedEvent [" + this.char_typed + "]";
		}

		if (this.is_key_up_event) {
			return "KeyUpEvent [" + this.key + "]";
		}

		if (!this.is_within_aperture) {
			return "MouseExitEvent [" + this.canvas_point + "]";
		}

		if (this.is_mouse_down) {
			return "TouchDownEvent [" + this.canvas_point + "]";
		}

		if (this.is_mouse_up) {
			return "TouchUpEvent [" + this.canvas_point + "]";
		}

		if (this.is_mouse_moved) {
			return "MouseMovedEvent [" + this.canvas_point + "]";
		}

		if (this.is_mouse_dragged) {
			return "TouchDraggedEvent [" + this.canvas_point + "]";
		}

		if (this.is_mouse_scrolled) {
			return "MouseScrolledEvent [" + this.scrolled + "]";
		}

		Err.reportError("Unknown event: " + this.toStringOriginal());
		return null;

	}

	final ArrayList<RedCamera> cameras_stack = new ArrayList<RedCamera>();

	private InputEvent current_input_event;
	boolean keyboard_event = false;
	boolean is_key_typed_event = false;
	boolean is_key_down_event = false;
	boolean is_key_up_event = false;
	boolean is_mouse_scrolled = false;

	boolean mouse_event = false;
	boolean is_mouse_moved = false;
	boolean is_mouse_dragged = false;
	boolean is_mouse_down = false;
	boolean is_mouse_up = false;

	Key key;
	char char_typed;

	private double screen_x;
	private double screen_y;

	final Float2 canvas_point = Geometry.newFloat2();

	boolean is_within_aperture = false;

	private int pointer_number;

	private MouseButton mouse_button;

	private int scrolled;

	public void put (final InputEvent input_event) {
		this.current_input_event = input_event;
		this.mouse_event = input_event.isMouseEvent();
		this.screen_x = input_event.getScreenX();
		this.screen_y = input_event.getScreenY();
		this.is_mouse_moved = input_event.isMouseMovedEvent();
		this.is_mouse_dragged = input_event.isMouseDraggedEvent();
		this.is_mouse_down = input_event.isMouseDownEvent();
		this.is_mouse_up = input_event.isMouseUpEvent();
		this.is_mouse_scrolled = input_event.isMouseScrolled();
		this.pointer_number = input_event.getPointerNumber();
		this.mouse_button = input_event.getMouseButton();
		this.scrolled = input_event.getScrollAmount();

		this.keyboard_event = input_event.isKeyboardEvent();
		this.is_key_typed_event = input_event.isKeyTypedEvent();
		this.is_key_down_event = input_event.isKeyDownEvent();
		this.is_key_up_event = input_event.isKeyUpEvent();
		this.key = input_event.getKey();
		this.char_typed = input_event.getCharTyped();

		if (this.is_key_down_event) {
			this.pressed.press(this.key);
		}
		if (this.is_key_up_event) {
			this.pressed.release(this.key);
		}

	}

	public boolean containsKeyboardInputEvent () {
		return this.keyboard_event;
	}

	public boolean containsMouseInputEvent () {
		return this.mouse_event;
	}

	public boolean tryToDeliverTo (final KeyboardInputEventListener keyboard_listener) {
		if (this.is_key_down_event) {
			return keyboard_listener.onKeyDown(this);
		}
		if (this.is_key_up_event) {
			return keyboard_listener.onKeyUp(this);
		}
		if (this.is_key_typed_event) {
			return keyboard_listener.onCharTyped(this);
		}

		Err.reportError("Unknown event: " + this.current_input_event);
		return this.is_key_down_event;
	}

	public boolean tryToDeliverTo (final MouseInputEventListener mouse_listener) {
		if (!this.is_within_aperture) {
			mouse_listener.onMouseExit(this);
			return false;
		}
		if (this.is_mouse_moved) {
			return mouse_listener.onMouseMoved(this);
		}
		if (this.is_mouse_down) {
			return mouse_listener.onTouchDown(this);
		}
		if (this.is_mouse_up) {
			return mouse_listener.onTouchUp(this);
		}
		if (this.is_mouse_dragged) {
			return mouse_listener.onTouchDragged(this);
		}
		if (this.is_mouse_scrolled) {
			return mouse_listener.onMouseScrolled(this);
		}

		Err.reportError("Unknown event: " + this.current_input_event);
		return this.is_key_down_event;

	}

	public void stackInCamera (final RedCamera camera) {
		if (camera != null) {
			// L.d("stackInCamera", camera);

			this.cameras_stack.add(camera);
			camera.setStack(this.cameras_stack);
			this.canvas_point.setXY(this.screen_x, this.screen_y);
			this.is_within_aperture = camera.isWithinAperture(this.canvas_point);
			{
				camera.unProject(this.canvas_point);
			}
		}

	}

	public void stackOutCamera (final RedCamera camera) {
		if (camera != null) {
			if (this.cameras_stack.get(this.cameras_stack.size() - 1) == camera) {
				this.cameras_stack.remove(this.cameras_stack.size() - 1);
				camera.removeStack();
				this.canvas_point.setXY(this.screen_x, this.screen_y);
				this.is_within_aperture = camera.isWithinAperture(this.canvas_point);
				{
					camera.unProject(this.canvas_point);
				}
			} else {
				Collections.newList(this.cameras_stack).print("cameras stack");
				Err.reportError("Cameras stack is corrupted.");
			}
		}
	}

	public void checkStack () {
		if (!this.cameras_stack.isEmpty()) {
			Collections.newList(this.cameras_stack).print("cameras stack");
			Err.reportError("Cameras stack is corrupted.");
		}
	}

	@Override
	public ReadOnlyFloat2 getCanvasPosition () {
		return this.canvas_point;
	}

	public String toStringOriginal () {
		return "ActivityInputEventsDeliveryBox [cameras_stack=" + this.cameras_stack + ", current_input_event="
			+ this.current_input_event + ", keyboard_event=" + this.keyboard_event + ", is_key_typed_event="
			+ this.is_key_typed_event + ", is_key_down_event=" + this.is_key_down_event + ", is_key_up_event=" + this.is_key_up_event
			+ ", is_mouse_scrolled=" + this.is_mouse_scrolled + ", mouse_event=" + this.mouse_event + ", is_mouse_moved="
			+ this.is_mouse_moved + ", is_mouse_dragged=" + this.is_mouse_dragged + ", is_mouse_down=" + this.is_mouse_down
			+ ", is_mouse_up=" + this.is_mouse_up + ", key=" + this.key + ", char_typed=" + this.char_typed + ", screen_x="
			+ this.screen_x + ", screen_y=" + this.screen_y + ", canvas_point=" + this.canvas_point + ", pointer_number="
			+ this.pointer_number + ", mouse_button=" + this.mouse_button + ", scrolled=" + this.scrolled + "]";
	}

	@Override
	public int getPointerNumber () {
		return this.pointer_number;
	}

	@Override
	public int getScrollValue () {
		return this.scrolled;
	}

	@Override
	public char getChar () {
		return this.char_typed;
	}

	@Override
	public Key getKey () {
		return this.key;
	}

	@Override
	public boolean isKeyPressed (final Key otherKey) {
		return this.pressed.isKeyPressed(otherKey);
	}

	public void markAllAllKeysReleased () {
		this.pressed.releaseAllKeys();
	}

	@Override
	public boolean is (final Key other) {
		return this.getKey() == other;
	}

}
