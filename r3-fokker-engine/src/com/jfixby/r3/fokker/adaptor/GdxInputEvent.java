
package com.jfixby.r3.fokker.adaptor;

import com.jfixby.r3.engine.api.exe.InputEvent;
import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.scarabei.api.input.Key;
import com.jfixby.scarabei.api.input.MouseButton;

public class GdxInputEvent implements InputEvent {

	private GDX_INPUT_EVENT_HEADER header = null;
	private Key keycode;
	private char character;
	private double screenX;
	private double screenY;
	private int pointer;
	private MouseButton button;
	private int amount;

	@Override
	public String toString () {
		return "GDXInputEvent[" + this.header + "] " + this.values();
	}

	private String values () {
		if (GDX_INPUT_EVENT_HEADER.KEY_DOWN == this.header) {
			return this.param("keycode", this.keycode);
		}
		if (GDX_INPUT_EVENT_HEADER.KEY_UP == this.header) {
			return this.param("keycode", this.keycode);
		}
		if (GDX_INPUT_EVENT_HEADER.SCROLLED == this.header) {
			return this.param("amount", this.amount);
		}
		if (GDX_INPUT_EVENT_HEADER.KEY_TYPED == this.header) {
			return this.param("character", this.character);
		}
		if (GDX_INPUT_EVENT_HEADER.TOUCH_DOWN == this.header) {
			return this.param("screenX", this.screenX) + this.param("screenY", this.screenY) + this.param("pointer", this.pointer)
				+ this.param("button", this.button);
		}
		if (GDX_INPUT_EVENT_HEADER.TOUCH_UP == this.header) {
			return this.param("screenX", this.screenX) + this.param("screenY", this.screenY) + this.param("pointer", this.pointer)
				+ this.param("button", this.button);
		}
		if (GDX_INPUT_EVENT_HEADER.TOUCH_DRAGGED == this.header) {
			return this.param("screenX", this.screenX) + this.param("screenY", this.screenY) + this.param("pointer", this.pointer);
		}
		if (GDX_INPUT_EVENT_HEADER.MOUSE_MOVED == this.header) {
			return this.param("screenX", this.screenX) + this.param("screenY", this.screenY);
		}
		return null;
	}

	private String param (final String name, final Object value) {
		return name + "=" + value + ", ";
	}

	public void reset () {
		this.header = null;
	}

	private void setHeader (final GDX_INPUT_EVENT_HEADER header) {
		this.header = header;
	}

	public void keyDown (final Key keycode) {
		this.setHeader(GDX_INPUT_EVENT_HEADER.KEY_DOWN);
		this.keycode = keycode;
	}

	public void keyUp (final Key keycode) {
		this.setHeader(GDX_INPUT_EVENT_HEADER.KEY_UP);
		this.keycode = keycode;
	}

	public void keyTyped (final char character) {
		this.setHeader(GDX_INPUT_EVENT_HEADER.KEY_TYPED);
		this.character = character;
	}

	public void touchDown (final int screenX, final int screenY, final int pointer, final MouseButton button) {
		this.setHeader(GDX_INPUT_EVENT_HEADER.TOUCH_DOWN);
		this.screenX = screenX * Screen.component().getDebugScaleFactor();
		this.screenY = screenY * Screen.component().getDebugScaleFactor();
		this.pointer = pointer;
		this.button = button;
	}

	public void touchUp (final int screenX, final int screenY, final int pointer, final MouseButton button) {
		this.setHeader(GDX_INPUT_EVENT_HEADER.TOUCH_UP);
		this.screenX = screenX * Screen.component().getDebugScaleFactor();
		this.screenY = screenY * Screen.component().getDebugScaleFactor();
		this.pointer = pointer;
		this.button = button;
	}

	public void touchDragged (final int screenX, final int screenY, final int pointer) {
		this.setHeader(GDX_INPUT_EVENT_HEADER.TOUCH_DRAGGED);
		this.screenX = screenX * Screen.component().getDebugScaleFactor();
		this.screenY = screenY * Screen.component().getDebugScaleFactor();
		this.pointer = pointer;
	}

	public void mouseMoved (final int screenX, final int screenY) {
		this.setHeader(GDX_INPUT_EVENT_HEADER.MOUSE_MOVED);
		this.screenX = screenX * Screen.component().getDebugScaleFactor();
		this.screenY = screenY * Screen.component().getDebugScaleFactor();
	}

	public void scrolled (final int amount) {
		this.setHeader(GDX_INPUT_EVENT_HEADER.SCROLLED);
		this.amount = amount;
	}

	@Override
	public boolean isMouseEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.MOUSE_MOVED) {
			return true;
		}
		if (this.header == GDX_INPUT_EVENT_HEADER.TOUCH_DOWN) {
			return true;
		}
		if (this.header == GDX_INPUT_EVENT_HEADER.TOUCH_UP) {
			return true;
		}
		if (this.header == GDX_INPUT_EVENT_HEADER.TOUCH_DRAGGED) {
			return true;
		}
		if (this.header == GDX_INPUT_EVENT_HEADER.SCROLLED) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isKeyboardEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.KEY_DOWN) {
			return true;
		}
		if (this.header == GDX_INPUT_EVENT_HEADER.KEY_TYPED) {
			return true;
		}
		if (this.header == GDX_INPUT_EVENT_HEADER.KEY_UP) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isKeyTypedEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.KEY_TYPED) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isKeyDownEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.KEY_DOWN) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isKeyUpEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.KEY_UP) {
			return true;
		}
		return false;
	}

	@Override
	public Key getKey () {
		return this.keycode;
	}

	@Override
	public char getCharTyped () {
		return this.character;
	}

	@Override
	public double getScreenX () {
		return this.screenX;
	}

	@Override
	public double getScreenY () {
		return this.screenY;
	}

	@Override
	public boolean isMouseMovedEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.MOUSE_MOVED) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isMouseDraggedEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.TOUCH_DRAGGED) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isMouseDownEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.TOUCH_DOWN) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isMouseUpEvent () {
		if (this.header == GDX_INPUT_EVENT_HEADER.TOUCH_UP) {
			return true;
		}
		return false;
	}

	@Override
	public int getPointerNumber () {
		return this.pointer;
	}

	@Override
	public MouseButton getMouseButton () {
		return this.button;
	}

	@Override
	public boolean isMouseScrolled () {
		if (this.header == GDX_INPUT_EVENT_HEADER.SCROLLED) {
			return true;
		}
		return false;
	}

	@Override
	public int getScrollAmount () {
		return this.amount;
	}

}
