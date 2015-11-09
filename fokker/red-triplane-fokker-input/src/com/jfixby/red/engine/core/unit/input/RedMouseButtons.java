package com.jfixby.red.engine.core.unit.input;

import com.jfixby.cmns.api.input.MouseButton;
import com.jfixby.cmns.api.input.MouseButtons;

public class RedMouseButtons implements MouseButtons {

	final MouseButton LEFT = new MouseButtonImpl("LEFT");

	final MouseButton RIGHT = new MouseButtonImpl("RIGHT");

	final MouseButton MIDDLE = new MouseButtonImpl("MIDDLE");

	@Override
	public MouseButton LEFT() {
		return LEFT;
	}

	@Override
	public MouseButton MIDDLE() {
		return MIDDLE;
	}

	@Override
	public MouseButton RIGHT() {
		return RIGHT;
	}

}
