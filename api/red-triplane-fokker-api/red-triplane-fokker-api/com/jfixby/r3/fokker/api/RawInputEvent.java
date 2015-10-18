package com.jfixby.r3.fokker.api;

import com.jfixby.cmns.api.input.Key;
import com.jfixby.cmns.api.input.MouseButton;

public interface RawInputEvent {

	boolean isMouseEvent();

	boolean isKeyboardEvent();

	boolean isKeyTypedEvent();

	boolean isKeyDownEvent();

	boolean isKeyUpEvent();

	Key getKey();

	char getCharTyped();

	int getScreenX();

	int getScreenY();

	boolean isMouseMovedEvent();

	boolean isMouseDraggedEvent();

	boolean isMouseDownEvent();

	boolean isMouseUpEvent();

	int getPointerNumber();

	MouseButton getMouseButton();

	boolean isMouseScrolled();

	int getScrollAmount();

}
