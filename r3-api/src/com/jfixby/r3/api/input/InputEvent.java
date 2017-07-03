
package com.jfixby.r3.api.input;

import com.jfixby.scarabei.api.input.Key;
import com.jfixby.scarabei.api.input.MouseButton;

public interface InputEvent {

	boolean isMouseEvent ();

	boolean isKeyboardEvent ();

	boolean isKeyTypedEvent ();

	boolean isKeyDownEvent ();

	boolean isKeyUpEvent ();

	Key getKey ();

	char getCharTyped ();

	double getScreenX ();

	double getScreenY ();

	boolean isMouseMovedEvent ();

	boolean isMouseDraggedEvent ();

	boolean isMouseDownEvent ();

	boolean isMouseUpEvent ();

	int getPointerNumber ();

	MouseButton getMouseButton ();

	boolean isMouseScrolled ();

	int getScrollAmount ();

}
